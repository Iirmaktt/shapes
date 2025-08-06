import React, { memo, useRef, useEffect } from 'react';

const Shape = memo(({ shape }) => {
  const shapeRef = useRef(null);
  
  useEffect(() => {
    const element = shapeRef.current;
    if (element) {
      element.style.transform = `translate(${shape.x}px, ${shape.y}px)`;
    }
  }, [shape.x, shape.y]);
  
  // Level 2 Performance: Only re-render when shape properties change
  // React.memo prevents re-renders unless shape object changes
  // Direct DOM manipulation via useRef bypasses React reconciliation
  
  const getShapeStyle = () => {
    const baseStyle = {
      width: `${shape.size}px`,
      height: `${shape.size}px`,
      backgroundColor: shape.color,
      position: 'absolute',
      // Performance optimization: disable transitions for smoother animations
      transition: 'none',
      // Use transform3d for hardware acceleration
      willChange: shape.isMoving ? 'transform' : 'auto',
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
      data-moving={shape.isMoving}
    />
  );
}, (prevProps, nextProps) => {
  // Level 2 Custom comparison: Only re-render if position or critical properties changed
  const prev = prevProps.shape;
  const next = nextProps.shape;
  
  return (
    prev.x === next.x &&
    prev.y === next.y &&
    prev.isMoving === next.isMoving &&
    prev.color === next.color &&
    prev.size === next.size
  );
});

Shape.displayName = 'Shape';

export default Shape;
