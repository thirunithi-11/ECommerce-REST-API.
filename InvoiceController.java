
package com.ecommerce.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ContentDisposition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{orderId}/download")
    public ResponseEntity<byte[]> downloadInvoice(
            @PathVariable Long orderId) {

        byte[] pdf = invoiceService.generateInvoice(orderId);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_PDF);

        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("invoice-" + orderId + ".pdf")
                        .build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}

