import React, { memo, useMemo } from 'react';
import { useWebSocket } from '../context/WebSocketContext';
import Shape from './Shape';

const Canvas = memo(() => {
  const { shapes, panelInfo } = useWebSocket();
  
  // Memoize rendered shapes to prevent unnecessary re-renders
  const renderedShapes = useMemo(() => {
    return shapes.map(shape => (
      <Shape key={shape.id} shape={shape} />
    ));
  }, [shapes]);
  
  const canvasStyle = {
    width: `${panelInfo.width}px`,
    height: `${panelInfo.height}px`,
    position: 'relative',
    border: '2px solid #e1e5e9',
    borderRadius: '4px',
    overflow: 'hidden',
    background: `
      linear-gradient(45deg, #f8f9fa 25%, transparent 25%), 
      linear-gradient(-45deg, #f8f9fa 25%, transparent 25%), 
      linear-gradient(45deg, transparent 75%, #f8f9fa 75%), 
      linear-gradient(-45deg, transparent 75%, #f8f9fa 75%)
    `,
    backgroundSize: '20px 20px',
    backgroundPosition: '0 0, 0 10px, 10px -10px, -10px 0px',
  };
  
  if (shapes.length === 0) {
    return (
      <div className="loading">
        <div className="loading-spinner"></div>
        <p>Waiting for shapes...</p>
      </div>
    );
  }
  
  return (
    <div className="canvas-container">
      <div 
        className="canvas"
        style={canvasStyle}
      >
        {renderedShapes}
      </div>
      <div style={{ 
        marginTop: '0.5rem', 
        fontSize: '0.8rem', 
        color: '#666',
        textAlign: 'center'
      }}>
        Panel: {panelInfo.width} × {panelInfo.height}px
      </div>
    </div>
  );
});

Canvas.displayName = 'Canvas';

export default Canvas;
