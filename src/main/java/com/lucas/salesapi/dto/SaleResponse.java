package com.lucas.salesapi.dto;

import com.lucas.salesapi.domain.Sale;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SaleResponse {

    private final Long id;
    private final LocalDate saleDate;
    private final BigDecimal value;
    private final Long sellerId;
    private final String sellerName;

    public SaleResponse(Long id, LocalDate saleDate, BigDecimal value, Long sellerId, String sellerName) {
        this.id = id;
        this.saleDate = saleDate;
        this.value = value;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }

    public static SaleResponse fromEntity(Sale sale) {
        return new SaleResponse(
            sale.getId(),
            sale.getSaleDate(),
            sale.getValue(),
            sale.getSellerId(),
            sale.getSellerName()
        );
    }

    public Long getId() {
        return id;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }
}
