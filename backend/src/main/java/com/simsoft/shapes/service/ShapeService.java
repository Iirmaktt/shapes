package com.simsoft.shapes.service;

import com.simsoft.shapes.model.PanelInfo;
import com.simsoft.shapes.model.Shape;
import com.simsoft.shapes.model.ShapeData;
import com.simsoft.shapes.model.ShapeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Şekil yönetimi ve hareket simülasyonu için ana servis sınıfı
 * Şekillerin oluşturulması, güncellenmesi ve WebSocket üzerinden yayınlanması işlemlerini yönetir
 */
@Service // Spring tarafından servis olarak yönetilir
public class ShapeService {
    
    // WebSocket mesajları göndermek için Spring template'i
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    // Sabit panel boyutları (1000x800 piksel)
    private final PanelInfo panelInfo = new PanelInfo(1000, 800);
    
    // Thread-safe şekil haritası (aynı anda birden fazla thread erişebilir)
    private final Map<String, Shape> shapes = new ConcurrentHashMap<>();
    
    // Şekiller için kullanılacak renk paleti
    private final String[] colors = {"#FF6B6B", "#4ECDC4", "#45B7D1", "#96CEB4", "#FFEAA7", "#DDA0DD", "#98D8C8", "#F7DC6F"};
    
    // Rastgele sayı üreteci (hareket ve renk seçimi için)
    private final Random random = new Random();
    
    /**
     * Constructor - servis başlatıldığında çağrılır
     * Varsayılan şekilleri oluşturur
     */
    public ShapeService() {
        initializeDefaultShapes();
    }
    
    /**
     * Varsayılan şekilleri başlatır
     * Uygulama başladığında 3 daire, 3 kare, 3 üçgen oluşturur
     */
    private void initializeDefaultShapes() {
        createShapes(3, 3, 3);
    }
    
    /**
     * Belirtilen sayılarda yeni şekiller oluşturur
     * Mevcut tüm şekilleri siler ve yenilerini ekler
     * 
     * @param circles Oluşturulacak daire sayısı
     * @param squares Oluşturulacak kare sayısı  
     * @param triangles Oluşturulacak üçgen sayısı
     */
    public void createShapes(int circles, int squares, int triangles) {
        shapes.clear(); // Mevcut tüm şekilleri temizle
        
        // Daireleri oluştur
        for (int i = 0; i < circles; i++) {
            Shape circle = createRandomShape("circle_" + i, ShapeType.CIRCLE);
            shapes.put(circle.getId(), circle);
        }
        
        // Kareleri oluştur
        for (int i = 0; i < squares; i++) {
            Shape square = createRandomShape("square_" + i, ShapeType.SQUARE);
            shapes.put(square.getId(), square);
        }
        
        // Üçgenleri oluştur
        for (int i = 0; i < triangles; i++) {
            Shape triangle = createRandomShape("triangle_" + i, ShapeType.TRIANGLE);
            shapes.put(triangle.getId(), triangle);
        }
        
        // Şekillerin %25'ini hareket ettirecek şekilde ayarla
        setRandomMovingShapes();
    }
    
    /**
     * Rastgele özelliklerle tek bir şekil oluşturur
     * 
     * @param id Şeklin benzersiz kimliği
     * @param type Şekil tipi (daire, kare, üçgen)
     * @return Oluşturulan şekil nesnesi
     */
    private Shape createRandomShape(String id, ShapeType type) {
        double size = 20 + random.nextDouble() * 30; // 20-50 piksel arası boyut
        double x = random.nextDouble() * (panelInfo.getWidth() - size);  // Rastgele X pozisyonu
        double y = random.nextDouble() * (panelInfo.getHeight() - size); // Rastgele Y pozisyonu
        String color = colors[random.nextInt(colors.length)]; // Rastgele renk seçimi
        
        return new Shape(id, type, x, y, size, color);
    }
    
    /**
     * Şekillerin %25'ini hareket edecek şekilde rastgele seçer
     * Görsel dinamizm için kullanılır
     */
    private void setRandomMovingShapes() {
        List<Shape> allShapes = new ArrayList<>(shapes.values());
        
        // Önce tüm şekilleri durdur
        allShapes.forEach(shape -> shape.setMoving(false));
        
        // %25'ini hareket ettir (minimum 1 şekil)
        int movingCount = Math.max(1, allShapes.size() / 4);
        Collections.shuffle(allShapes); // Listeyi karıştır
        
        // İlk movingCount kadar şekli hareket ettirecek şekilde işaretle
        for (int i = 0; i < movingCount && i < allShapes.size(); i++) {
            allShapes.get(i).setMoving(true);
        }
    }
    
    /**
     * Ana güncelleme ve yayın metodu
     * Her 30ms'de bir çağrılır (33 FPS)
     * Hareket eden şekilleri günceller ve tüm istemcilere gönderir
     */
    @Scheduled(fixedRate = 30) // 30ms = ~33 FPS (20-50ms gereksinim aralığında)
    public void updateAndBroadcastShapes() {
        if (shapes.isEmpty()) return; // Eğer şekil yoksa işlem yapma
        
        // Hareket eden şekilleri güncelle
        shapes.values().forEach(shape -> {
            if (shape.isMoving()) {
                // Her hareket eden şekil için fizik simülasyonu uygula
                shape.move(panelInfo.getWidth(), panelInfo.getHeight());
            }
        });
        
        // Tüm şekilleri WebSocket üzerinden yayınla (değişenler + değişmeyenler)
        ShapeData shapeData = new ShapeData(new ArrayList<>(shapes.values()));
        messagingTemplate.convertAndSend("/topic/shapes", shapeData);
    }
    
    /**
     * Hareket eden şekil grubunu değiştiren zamanlayıcı
     * Her 3 saniyede bir çağrılır ve farklı şekilleri hareket ettirir
     */
    @Scheduled(fixedRate = 3000) // 3 saniye aralıklarla
    public void changeMovingShapes() {
        if (!shapes.isEmpty()) {
            setRandomMovingShapes(); // Yeni rastgele şekil grubu seç
        }
    }
    
    /**
     * Yeni bağlanan istemcilere panel bilgilerini gönderir
     * WebSocket bağlantısı kurulduğunda çağrılır
     */
    public void sendPanelInfo() {
        // Panel bilgilerini içeren mesaj oluştur
        Map<String, Object> panelMessage = new HashMap<>();
        panelMessage.put("messageType", "panel");           // Mesaj tipi
        panelMessage.put("width", panelInfo.getWidth());    // Panel genişliği
        panelMessage.put("height", panelInfo.getHeight());  // Panel yüksekliği
        
        // Mesajı tüm bağlı istemcilere gönder
        messagingTemplate.convertAndSend("/topic/shapes", panelMessage);
    }
    
    /**
     * Mevcut tüm şekilleri döndürür
     * REST API çağrıları için kullanılabilir (şu anda kullanılmıyor)
     * 
     * @return Şekillerin kopyası liste halinde
     */
    public List<Shape> getAllShapes() {
        return new ArrayList<>(shapes.values());
    }
    
    /**
     * Panel bilgilerini döndürür
     * REST API çağrıları için kullanılabilir (şu anda kullanılmıyor)
     * 
     * @return Panel boyut bilgileri
     */
    public PanelInfo getPanelInfo() {
        return panelInfo;
    }
}
