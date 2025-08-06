// Vite geliştirme ortamı yapılandırma dosyası
// Bu dosya React uygulamasının build ve development server ayarlarını yönetir
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Vite konfigürasyonu - React plugin ve server ayarları
// https://vitejs.dev/config/
export default defineConfig({
  // React uygulaması için gerekli plugin'leri tanımla
  plugins: [react()],
  
  // Development server ayarları
  server: {
    // Uygulamanın çalışacağı port numarası (Spring Boot 8080'de çalıştığı için 3000 kullanıyoruz)
    port: 3000
  },
  
  // Global değişken tanımlamaları - WebSocket bağlantıları için gerekli
  define: {
    // Node.js global objesini browser ortamında kullanılabilir hale getir
    global: 'globalThis',
  }
})
