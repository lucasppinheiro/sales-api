package com.lucas.salesapi.service;

import com.lucas.salesapi.domain.Sale;
import com.lucas.salesapi.dto.CreateSaleRequest;
import com.lucas.salesapi.dto.SaleResponse;
import com.lucas.salesapi.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Transactional
    public SaleResponse createSale(CreateSaleRequest request) {
        Sale sale = new Sale(
            request.getSaleDate(),
            request.getValue(),
            request.getSellerId(),
            request.getSellerName().trim()
        );

        Sale savedSale = saleRepository.save(sale);
        return SaleResponse.fromEntity(savedSale);
    }
}
