import React, { memo, useRef, useEffect } from 'react';

const Shape = memo(({ shape }) => {
  const shapeRef = useRef(null);
  
  useEffect(() => {
    const element = shapeRef.current;
    if (element) {
      element.style.transform = `translate(${shape.x}px, ${shape.y}px)`;
    }
  }, [shape.x, shape.y]);
  
  const getShapeStyle = () => {
    const baseStyle = {
      width: `${shape.size}px`,
      height: `${shape.size}px`,
      backgroundColor: shape.color,
      position: 'absolute',
    };
    
    if (shape.type === 'triangle') {
      return {
        ...baseStyle,
        width: 0,
        height: 0,
        backgroundColor: 'transparent',
        borderLeft: `${shape.size / 2}px solid transparent`,
        borderRight: `${shape.size / 2}px solid transparent`,
        borderBottom: `${shape.size}px solid ${shape.color}`,
      };
    }
    
    if (shape.type === 'circle') {
      baseStyle.borderRadius = '50%';
    }
    
    return baseStyle;
  };
  
  return (
    <div
      ref={shapeRef}
      style={getShapeStyle()}
    />
  );
});

Shape.displayName = 'Shape';

export default Shape;
