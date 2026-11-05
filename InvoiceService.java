package com.ecommerce.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;

@Service
public class InvoiceService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public InvoiceService(OrderRepository orderRepository,
            OrderItemRepository orderItemRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public byte[] generateInvoice(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> items = orderItemRepository.findByOrder(order);

        try (PDDocument document = new PDDocument();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream content =
                    new PDPageContentStream(document, page)) {

                float y = 750;

                writeText(content, "E-COMMERCE INVOICE", 50, y, 20, true);
                y -= 50;

                writeText(content, "Invoice Number: INV-" + order.getId(),
                        50, y, 12, false);

                y -= 25;

                writeText(content, "Customer: "
                        + order.getUser().getFullName(), 50, y, 12, false);

                y -= 25;

                String orderDate = order.getOrderDate() == null
                        ? "N/A"
                        : order.getOrderDate().format(
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

                writeText(content, "Order Date: " + orderDate,
                        50, y, 12, false);

                y -= 40;

                writeText(content, "Product", 50, y, 12, true);
                writeText(content, "Quantity", 300, y, 12, true);
                writeText(content, "Price", 400, y, 12, true);

                y -= 25;

                for (OrderItem item : items) {

                    String productName = item.getProduct().getProductName();

                    writeText(content, productName, 50, y, 11, false);

                    writeText(content, String.valueOf(item.getQuantity()),
                            300, y, 11, false);

                    writeText(content, "INR " + item.getPrice(),
                            400, y, 11, false);

                    y -= 25;
                }

                y -= 20;

                writeText(content, "Total Amount: INR "
                        + order.getTotalAmount(), 50, y, 14, true);

                y -= 40;

                writeText(content, "Thank you for shopping with us!",
                        50, y, 12, false);
            }

            document.save(outputStream);

            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Unable to generate invoice PDF", e);
        }
    }

    private void writeText(PDPageContentStream content,
            String text, float x, float y, int fontSize, boolean bold)
            throws IOException {

        PDType1Font font = new PDType1Font(
                bold
                        ? Standard14Fonts.FontName.HELVETICA_BOLD
                        : Standard14Fonts.FontName.HELVETICA
        );

        // Standard PDF fonts support a limited character set.
        String safeText = text.replaceAll("[^\\x20-\\x7E]", "?");

        content.beginText();
        content.setFont(font, fontSize);
        content.newLineAtOffset(x, y);
        content.showText(safeText);
        content.endText();
    }
}
