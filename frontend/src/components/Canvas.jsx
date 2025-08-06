import React, { memo, useMemo } from 'react';
import { useWebSocket } from '../context/WebSocketContext';
import Shape from './Shape';

const Canvas = memo(() => {
  const { shapes, panelInfo, isInitialized, isConnected } = useWebSocket();
  
  const renderedShapes = useMemo(() => {
    return shapes.map(shape => (
      <Shape key={shape.id} shape={shape} />
    ));
  }, [shapes]);
  
  // Show loading until we get panel info and are connected
  if (!isInitialized || !isConnected || !panelInfo) {
    return <div>Loading panel info...</div>;
  }
  
  const canvasStyle = {
    width: `${panelInfo.width}px`,
    height: `${panelInfo.height}px`,
    position: 'relative',
    border: '1px solid black',
    overflow: 'hidden',
  };
  
  return (
    <div>
      <div className="canvas" style={canvasStyle}>
        {renderedShapes}
      </div>
      <div>Panel: {panelInfo.width} × {panelInfo.height}px</div>
    </div>
  );
});

Canvas.displayName = 'Canvas';

export default Canvas;
