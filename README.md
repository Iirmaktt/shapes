# Real-time Shape Movement Visualization

A real-time shape visualization application built with Spring Boot (backend) and React (frontend) that displays moving shapes via WebSocket communication.

## Project Structure

```
simsoft/
├── backend/          # Spring Boot WebSocket server
│   ├── src/
│   └── pom.xml
├── frontend/         # React client application
│   ├── src/
│   └── package.json
└── README.md
```

## Features

### Level 1 - Basic Functionality ✅
- **REST API**: `/api/setShapes?circles=4&rects=5&triangles=3` endpoint to configure shape counts
- **WebSocket Communication**: Real-time shape data broadcasting at 30ms intervals (~33 FPS)
- **Shape Movement Simulation**: Physics-based movement with boundary collision detection
- **Dynamic Movement Groups**: 25% of shapes move at any time, changing every 3 seconds
- **React Visualization**: Real-time shape rendering with proper component structure
- **Multi-client Support**: Multiple browsers can connect simultaneously

### Level 2 - Performance Optimizations ✅
- **React.memo**: Prevents unnecessary component re-renders
- **Direct DOM Manipulation**: Uses `useRef` for position updates to bypass React's reconciliation
- **Memoized Shape Rendering**: `useMemo` to optimize shape list rendering
- **Efficient State Management**: Centralized WebSocket context with optimized updates
- **Performance-Ready**: Tested with 100+ shapes while maintaining smooth animations

## Technical Implementation

### Backend (Spring Boot)
- **WebSocket Configuration**: STOMP protocol over SockJS for reliable real-time communication
- **Shape Management**: Concurrent-safe shape storage with automatic movement simulation
- **Physics Engine**: Basic collision detection with velocity-based movement and boundary reflection
- **Scheduled Tasks**: 
  - 30ms intervals for shape updates and broadcasting
  - 3-second intervals for changing active movement groups
- **CORS Support**: Configured for local development

### Frontend (React)
- **Context Provider Pattern**: Centralized WebSocket management and state distribution
- **Component Architecture**: Modular design with separate components for each concern
- **Performance Optimizations**:
  - `React.memo` for shape components
  - Direct DOM manipulation for position updates
  - Memoized rendering to prevent cascade re-renders
- **Responsive Design**: Adapts to different screen sizes
- **Error Handling**: Automatic reconnection with exponential backoff

## Critical Design Decisions

### WebSocket Message Distribution
- **Context Provider**: Used React Context to manage WebSocket connection centrally
- **Single Subscription**: One WebSocket connection shared across all components
- **Message Types**: Separate handling for panel info and shape data
- **State Synchronization**: All clients receive the same data stream simultaneously

### Performance Optimizations
1. **Shape Component Isolation**: Each shape is a memoized component that only re-renders when its data changes
2. **Direct DOM Updates**: Position changes bypass React's virtual DOM using `useRef` and direct style manipulation
3. **Batched Updates**: Server sends all shape data in single message to reduce WebSocket overhead
4. **Selective Movement**: Only 25% of shapes move at once, reducing computational load

### Real-time Communication
- **STOMP over SockJS**: Reliable WebSocket communication with fallback support
- **Automatic Reconnection**: Exponential backoff strategy for connection failures
- **Panel Size Synchronization**: Server sends panel dimensions on connection establishment
- **Message Queuing**: Built-in message buffering for connection stability

## Development Environment

### Requirements
- **Java**: Version 17 or higher
- **Maven**: Version 3.6 or higher
- **Node.js**: Version 16 or higher
- **npm**: Version 7 or higher

### IDE Recommendations
- **Backend**: IntelliJ IDEA or Eclipse with Spring Tools
- **Frontend**: Visual Studio Code with React extensions

## Installation and Running

### Prerequisites
- **Java**: Version 17 or higher (tested with OpenJDK 21)
- **Node.js**: Version 16 or higher
- **npm**: Version 7 or higher

### Development Environment
- **IDE**: Visual Studio Code (recommended) or IntelliJ IDEA
- **Java Version**: OpenJDK 21 (compatible with Java 17+)
- **Build Tool**: Maven 3.9.6 (via Maven wrapper - ./mvnw)
- **Frontend Framework**: React 18 with Vite 5.4
- **WebSocket**: STOMP over SockJS protocol

### Quick Start

#### Option 1: Use the provided scripts
```bash
# Start both frontend and backend
./start.sh

# Stop all services
./stop.sh
```

#### Option 2: Manual setup

##### Backend (Spring Boot)
```bash
cd backend
./mvnw clean compile    # Build the project
./mvnw spring-boot:run  # Run the server
```
Server runs on `http://localhost:8080`

##### Frontend (React/Vite)
```bash
cd frontend
npm install             # Install dependencies
npm run dev            # Start development server
```
Client runs on `http://localhost:3000` (configured in vite.config.js)

### Testing the Application
1. Open `http://localhost:3000` in your browser (frontend)
2. Verify WebSocket connection (green indicator in header)
3. Use controls to modify shape counts
4. Test with multiple browser tabs for multi-client support
5. For performance testing, try values like circles=50, squares=30, triangles=20

## API Endpoints

### REST API
- `GET /api/setShapes?circles=N&rects=N&triangles=N` - Update shape configuration
- `GET /api/shapes` - Get current shape list
- `GET /api/panel` - Get panel dimensions

### WebSocket
- **Connection**: `/ws/shapes` (SockJS endpoint)
- **Topic**: `/topic/shapes` (shape data and panel info)
- **Message Types**:
  - Panel info: `{messageType: "panel", width: 1000, height: 800}`
  - Shape data: `{messageType: "shapes", shapes: [...]}`

## Performance Characteristics

### Tested Configurations
- **Small Scale**: 10-20 shapes (smooth on all devices)
- **Medium Scale**: 50-100 shapes (smooth on modern browsers)
- **Large Scale**: 100+ shapes (requires performance optimizations)

### Key Metrics
- **Update Frequency**: 33 FPS (30ms intervals)
- **Active Shapes**: 25% moving at any time
- **Movement Change**: Every 3 seconds
- **Memory Usage**: Optimized with React.memo and direct DOM manipulation

## Browser Compatibility
- Chrome 80+
- Firefox 75+
- Safari 13+
- Edge 80+

## Known Limitations
- Maximum recommended shapes: 200 (depends on client hardware)
- WebSocket fallback to long-polling in restrictive networks
- Panel size is fixed at server startup (1000x800px)

## Future Enhancements
- Dynamic panel resizing
- Shape collision detection between shapes
- Configurable movement patterns
- Performance metrics dashboard
- Shape grouping and formations
