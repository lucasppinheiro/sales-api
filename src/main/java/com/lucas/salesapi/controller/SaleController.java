package com.lucas.salesapi.controller;

import com.lucas.salesapi.dto.CreateSaleRequest;
import com.lucas.salesapi.dto.SaleResponse;
import com.lucas.salesapi.service.SaleService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody CreateSaleRequest request) {
        SaleResponse response = saleService.createSale(request);
        URI location = URI.create("/api/v1/sales/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }
}
