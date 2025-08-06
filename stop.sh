#!/bin/bash

echo "=== Stopping Shape Movement Visualization Application ==="

# Function to kill process on port
kill_port() {
    local port=$1
    local pid=$(lsof -t -i:$port 2>/dev/null)
    if [ ! -z "$pid" ]; then
        echo "🛑 Stopping process on port $port (PID: $pid)"
        kill $pid 2>/dev/null
        sleep 2
        # Force kill if still running
        if kill -0 $pid 2>/dev/null; then
            echo "Force killing process $pid"
            kill -9 $pid 2>/dev/null
        fi
    else
        echo "ℹ️  No process found on port $port"
    fi
}

# Stop backend (port 8080)
kill_port 8080

# Stop frontend (port 3000)
kill_port 3000

# Clean up any remaining Java processes
pkill -f "spring-boot:run" 2>/dev/null

# Clean up any remaining Node processes
pkill -f "vite" 2>/dev/null

echo "✅ All services stopped"

# Clean up log files
if [ -f "backend.log" ]; then
    rm backend.log
    echo "🗑️  Cleaned backend.log"
fi

if [ -f "frontend.log" ]; then
    rm frontend.log
    echo "🗑️  Cleaned frontend.log"
fi

echo "🎉 Cleanup complete!"
