//package uz.onlineshop.orderservice.rebbitMQ;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import uz.onlineshop.productservice.dtoes.res.ProductResponse;
//
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ProductEventListener {
//
//    private final ProductSnapshotRepository snapshotRepository;
//
//    /**
//     * Queue nomi application.yml dagi order.rabbit.queue bilan bir xil bo'lishi kerak.
//     * RabbitListener jackson converter orqali ProductResponse ga map qiladi (biz mapping o'rnatdik).
//     */
//    @RabbitListener(queues = "${order.rabbit.queue}")
//    @Transactional
//    public void handleProductMessage(ProductResponse product) {
//        if (product == null) {
//            System.out.println("[Listener] Null message keldi");
//            return;
//        }
//
//        Long productId = product.getId();
//        if (productId == null) {
//            System.out.println("[Listener] Product id yo'q, message: " + product);
//            return;
//        }
//
//        // Sizning ProductService delete metodida faqat id va ba'zi maydonlar yuborilyapti.
//        // Agar productName va boshqa maydonlar bo'lmasa, bu delete deb hisoblaymiz.
//        boolean isDeleteCandidate = (product.getProductName() == null || product.getProductName().isBlank())
//                && product.getDescription() == null
//                && product.getPrice() == null
//                && product.getImageUrl() == null
//                && product.getStockQuantity() == null
//                && product.getColor() == null;
//
//        if (isDeleteCandidate) {
//            // o'chirish
//            Optional<ProductSnapshot> existing = snapshotRepository.findById(productId);
//            if (existing.isPresent()) {
//                snapshotRepository.deleteById(productId);
//                System.out.println("[Listener] Product snapshot o'chirildi id=" + productId);
//            } else {
//                System.out.println("[Listener] O'chirish ishi: snapshot topilmadi id=" + productId);
//            }
//            return;
//        }
//
//        // create yoki update
//        ProductSnapshot snapshot = ProductSnapshot.builder()
//                .id(productId)
//                .productName(product.getProductName())
//                .description(product.getDescription())
//                .price(product.getPrice())
//                .color(product.getColor())
//                .imageUrl(product.getImageUrl())
//                .stockQuantity(product.getStockQuantity())
//                .categoryId(product.getCategoryId())
//                .build();
//
//        snapshotRepository.save(snapshot);
//        System.out.println("[Listener] Product snapshot create/update id=" + productId);
//    }
//}
