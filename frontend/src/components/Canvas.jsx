import React, { memo, useMemo } from 'react';
import { useWebSocket } from '../context/WebSocketContext';
import Shape from './Shape';

const Canvas = memo(() => {
  const { shapes, panelInfo } = useWebSocket();
  
  const renderedShapes = useMemo(() => {
    return shapes.map(shape => (
      <Shape key={shape.id} shape={shape} />
    ));
  }, [shapes]);
  
  const canvasStyle = {
    width: `${panelInfo.width}px`,
    height: `${panelInfo.height}px`,
    position: 'relative',
    border: '1px solid black',
    overflow: 'hidden',
  };
  
  if (shapes.length === 0) {
    return <div>Waiting for shapes...</div>;
  }
  
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
