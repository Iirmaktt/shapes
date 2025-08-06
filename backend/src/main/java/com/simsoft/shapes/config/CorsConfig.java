package com.simsoft.shapes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS (Cross-Origin Resource Sharing) konfigürasyonu
 * Frontend ile backend arasındaki çapraz kaynak paylaşımını etkinleştirir
 */
@Configuration // Spring konfigürasyon sınıfı
public class CorsConfig implements WebMvcConfigurer {
    
    /**
     * CORS mapping'lerini ekler
     * Frontend'in backend API'lerine erişmesine izin verir
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint'ler için CORS etkinleştir
                // İzin verilen frontend adresleri
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                // İzin verilen HTTP metodları
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Tüm header'lara izin ver
                .allowedHeaders("*")
                // Çerezlerin gönderilmesine izin ver
                .allowCredentials(true);
    }
}
