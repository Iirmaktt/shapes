#!/bin/bash

echo "=== Starting Shape Movement Visualization Application ==="
echo ""

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check prerequisites
echo "Checking prerequisites..."

if ! command_exists java; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Maven will be set up automatically using wrapper if needed

if ! command_exists node; then
    echo "❌ Node.js is not installed. Please install Node.js 16 or higher."
    exit 1
fi

if ! command_exists npm; then
    echo "❌ npm is not installed. Please install npm 7 or higher."
    exit 1
fi

echo "✅ All prerequisites are available"
echo ""

# Start backend
echo "🚀 Starting Spring Boot backend..."
cd backend
./mvnw clean install -q
if [ $? -ne 0 ]; then
    echo "❌ Backend build failed"
    exit 1
fi

echo "✅ Backend built successfully"
echo "Starting Spring Boot server on port 8080..."
./mvnw spring-boot:run > ../backend.log 2>&1 &
BACKEND_PID=$!
cd ..

# Wait for backend to start
echo "⏳ Waiting for backend to start..."
for i in {1..30}; do
    if curl -s http://localhost:8080/api/panel > /dev/null 2>&1; then
        echo "✅ Backend is running"
        break
    fi
    sleep 1
    if [ $i -eq 30 ]; then
        echo "❌ Backend failed to start within 30 seconds"
        kill $BACKEND_PID 2>/dev/null
        exit 1
    fi
done

echo ""

# Start frontend
echo "🚀 Starting React frontend..."
cd frontend
npm install -q
if [ $? -ne 0 ]; then
    echo "❌ Frontend dependencies installation failed"
    kill $BACKEND_PID 2>/dev/null
    exit 1
fi

echo "✅ Frontend dependencies installed"
echo "Starting React development server on port 3000..."
npm run dev > ../frontend.log 2>&1 &
FRONTEND_PID=$!
cd ..

# Wait for frontend to start
echo "⏳ Waiting for frontend to start..."
for i in {1..20}; do
    if curl -s http://localhost:3000 > /dev/null 2>&1; then
        echo "✅ Frontend is running"
        break
    fi
    sleep 1
    if [ $i -eq 20 ]; then
        echo "❌ Frontend failed to start within 20 seconds"
        kill $BACKEND_PID 2>/dev/null
        kill $FRONTEND_PID 2>/dev/null
        exit 1
    fi
done

echo ""
echo "🎉 Application is ready!"
echo ""
echo "📊 Access the application:"
echo "   Frontend: http://localhost:3000"
echo "   Backend API: http://localhost:8080/api"
echo ""
echo "🔧 Test shape configuration:"
echo "   http://localhost:8080/api/setShapes?circles=10&rects=8&triangles=5"
echo ""
echo "💡 Note: Panel size and shapes are now sent via WebSocket on connection"
echo "   Only the /setShapes endpoint is available via REST API"
echo ""
echo "📝 Logs:"
echo "   Backend: tail -f backend.log"
echo "   Frontend: tail -f frontend.log"
echo ""
echo "🛑 To stop the application:"
echo "   kill $BACKEND_PID $FRONTEND_PID"
echo ""

# Keep script running
echo "Press Ctrl+C to stop all services..."
trap "echo 'Stopping services...'; kill $BACKEND_PID $FRONTEND_PID 2>/dev/null; exit 0" INT
wait
