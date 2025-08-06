import React, { createContext, useContext, useEffect, useState, useCallback, useRef } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const WebSocketContext = createContext(null);

export const useWebSocket = () => {
  const context = useContext(WebSocketContext);
  if (!context) {
    throw new Error('useWebSocket must be used within a WebSocketProvider');
  }
  return context;
};

export const WebSocketProvider = ({ children }) => {
  const [isConnected, setIsConnected] = useState(false);
  const [shapes, setShapes] = useState([]);
  const [panelInfo, setPanelInfo] = useState(null); // Start as null to indicate not loaded
  const [stats, setStats] = useState({ total: 0, moving: 0 });
  const [isInitialized, setIsInitialized] = useState(false); // Track if we got initial data
  
  const stompClientRef = useRef(null);
  const reconnectTimeoutRef = useRef(null);
  const reconnectAttempts = useRef(0);
  
  const connectWebSocket = useCallback(() => {
    try {
      const socket = new SockJS('http://localhost:8080/ws/shapes');
      const stompClient = Stomp.over(socket);
      
      // Disable console logging
      stompClient.debug = null;
      
      stompClient.connect({}, 
        (frame) => {
          console.log('Connected to WebSocket:', frame);
          setIsConnected(true);
          reconnectAttempts.current = 0;
          
          // Subscribe to shape updates
          stompClient.subscribe('/topic/shapes', (message) => {
            try {
              const data = JSON.parse(message.body);
              
              if (data.messageType === 'panel') {
                setPanelInfo({ width: data.width, height: data.height });
                setIsInitialized(true); // Mark as initialized when we get panel info
              } else if (data.messageType === 'shapes' || data.shapes) {
                const shapeList = data.shapes || [];
                setShapes(shapeList);
                
                // Update stats
                const movingCount = shapeList.filter(shape => shape.isMoving).length;
                setStats({ total: shapeList.length, moving: movingCount });
              }
            } catch (error) {
              console.error('Error parsing WebSocket message:', error);
            }
          });
          
          // Request initial data
          stompClient.send('/app/connect', {}, JSON.stringify({}));
        },
        (error) => {
          console.error('WebSocket connection error:', error);
          setIsConnected(false);
          scheduleReconnect();
        }
      );
      
      stompClientRef.current = stompClient;
      
      // Handle socket close
      socket.onclose = () => {
        console.log('WebSocket connection closed');
        setIsConnected(false);
        scheduleReconnect();
      };
      
    } catch (error) {
      console.error('Error creating WebSocket connection:', error);
      scheduleReconnect();
    }
  }, []);
  
  const scheduleReconnect = useCallback(() => {
    if (reconnectTimeoutRef.current) {
      clearTimeout(reconnectTimeoutRef.current);
    }
    
    const maxAttempts = 10;
    const baseDelay = 1000;
    
    if (reconnectAttempts.current < maxAttempts) {
      const delay = Math.min(baseDelay * Math.pow(2, reconnectAttempts.current), 30000);
      console.log(`Reconnecting in ${delay}ms (attempt ${reconnectAttempts.current + 1})`);
      
      reconnectTimeoutRef.current = setTimeout(() => {
        reconnectAttempts.current++;
        connectWebSocket();
      }, delay);
    } else {
      console.error('Max reconnection attempts reached');
    }
  }, [connectWebSocket]);
  
  const disconnect = useCallback(() => {
    if (reconnectTimeoutRef.current) {
      clearTimeout(reconnectTimeoutRef.current);
      reconnectTimeoutRef.current = null;
    }
    
    if (stompClientRef.current && stompClientRef.current.connected) {
      stompClientRef.current.disconnect();
    }
    
    setIsConnected(false);
    setShapes([]);
    setStats({ total: 0, moving: 0 });
    setPanelInfo(null);
    setIsInitialized(false);
  }, []);
  
  const updateShapes = useCallback(async (circles, squares, triangles) => {
    try {
      const response = await fetch(`http://localhost:8080/api/setShapes?circles=${circles}&rects=${squares}&triangles=${triangles}`);
      if (!response.ok) {
        throw new Error('Failed to update shapes');
      }
      const result = await response.json();
      console.log('Shapes updated:', result);
      return result;
    } catch (error) {
      console.error('Error updating shapes:', error);
      throw error;
    }
  }, []);
  
  useEffect(() => {
    connectWebSocket();
    
    return () => {
      disconnect();
    };
  }, [connectWebSocket, disconnect]);
  
  // Cleanup on unmount
  useEffect(() => {
    return () => {
      if (reconnectTimeoutRef.current) {
        clearTimeout(reconnectTimeoutRef.current);
      }
    };
  }, []);
  
  const value = {
    isConnected,
    shapes,
    panelInfo,
    stats,
    updateShapes,
    reconnect: connectWebSocket,
    isInitialized
  };
  
  return (
    <WebSocketContext.Provider value={value}>
      {children}
    </WebSocketContext.Provider>
  );
};
