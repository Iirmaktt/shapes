package com.simsoft.shapes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Ana Spring Boot uygulaması
 * Bu sınıf uygulamanın başlangıç noktasıdır
 */
@SpringBootApplication // Spring Boot otomatik konfigürasyonunu etkinleştirir
@EnableScheduling // Zamanlı görevleri (@Scheduled) etkinleştirir - şekil güncellemeleri için gerekli
public class ShapeMovementApplication {
    /**
     * Uygulamanın main metodu
     * JVM tarafından çağrılır ve Spring Boot uygulamasını başlatır
     */
    public static void main(String[] args) {
        // Spring Boot konteynerini başlat ve uygulamayı çalıştır
        SpringApplication.run(ShapeMovementApplication.class, args);
    }
}
