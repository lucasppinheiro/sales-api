package com.lucas.salesapi.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "sale_value", nullable = false, precision = 15, scale = 2)
    private BigDecimal value;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "seller_name", nullable = false, length = 120)
    private String sellerName;

    protected Sale() {
    }

    public Sale(LocalDate saleDate, BigDecimal value, Long sellerId, String sellerName) {
        this.saleDate = saleDate;
        this.value = value;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
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
