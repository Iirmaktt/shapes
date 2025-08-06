// React uygulamasının giriş noktası (entry point)
// Bu dosya uygulamanın DOM'a mount edilmesini sağlar
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'

// React uygulamasını 'root' id'li DOM elementine mount et
// StrictMode: React'in geliştirme modunda ek kontroller yapmasını sağlar
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
