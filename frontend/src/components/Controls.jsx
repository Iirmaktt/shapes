import React, { useState } from 'react';
import { useWebSocket } from '../context/WebSocketContext';

const Controls = () => {
  const { updateShapes, stats } = useWebSocket();
  const [circles, setCircles] = useState(3);
  const [squares, setSquares] = useState(3);
  const [triangles, setTriangles] = useState(3);
  const [isUpdating, setIsUpdating] = useState(false);
  
  const handleUpdateShapes = async () => {
    setIsUpdating(true);
    try {
      await updateShapes(circles, squares, triangles);
    } catch (error) {
      console.error('Failed to update shapes:', error);
    } finally {
      setIsUpdating(false);
    }
  };
  
  return (
    <div className="controls">
      <div className="shape-controls">
        <label>
          Circles:
          <input
            type="number"
            min="0"
            max="50"
            value={circles}
            onChange={(e) => setCircles(parseInt(e.target.value) || 0)}
          />
        </label>
      </div>
      
      <div className="shape-controls">
        <label>
          Squares:
          <input
            type="number"
            min="0"
            max="50"
            value={squares}
            onChange={(e) => setSquares(parseInt(e.target.value) || 0)}
          />
        </label>
      </div>
      
      <div className="shape-controls">
        <label>
          Triangles:
          <input
            type="number"
            min="0"
            max="50"
            value={triangles}
            onChange={(e) => setTriangles(parseInt(e.target.value) || 0)}
          />
        </label>
      </div>
      
      <button
        className="update-button"
        onClick={handleUpdateShapes}
        disabled={isUpdating}
      >
        {isUpdating ? 'Updating...' : 'Update Shapes'}
      </button>
      
      <div className="stats">
        <span>Total: {stats.total}</span>
        <span>Moving: {stats.moving}</span>
        <span>Static: {stats.total - stats.moving}</span>
      </div>
    </div>
  );
};

export default Controls;
