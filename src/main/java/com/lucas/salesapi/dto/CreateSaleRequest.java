package com.lucas.salesapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class CreateSaleRequest {

    @NotNull(message = "A data da venda deve ser informada.")
    @PastOrPresent(message = "A data da venda nao pode estar no futuro.")
    private LocalDate saleDate;

    @NotNull(message = "O valor da venda deve ser informado.")
    @Positive(message = "O valor da venda deve ser maior que zero.")
    @Digits(integer = 12, fraction = 2, message = "O valor da venda deve possuir no maximo 2 casas decimais.")
    private BigDecimal value;

    @NotNull(message = "O id do vendedor deve ser informado.")
    @Positive(message = "O id do vendedor deve ser maior que zero.")
    private Long sellerId;

    @NotBlank(message = "O nome do vendedor deve ser informado.")
    private String sellerName;

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
