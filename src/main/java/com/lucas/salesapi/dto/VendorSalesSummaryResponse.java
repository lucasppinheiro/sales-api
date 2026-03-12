package com.lucas.salesapi.dto;

import java.math.BigDecimal;

public class VendorSalesSummaryResponse {

    private final String sellerName;
    private final BigDecimal totalSales;
    private final BigDecimal dailyAverageSales;

    public VendorSalesSummaryResponse(String sellerName, BigDecimal totalSales, BigDecimal dailyAverageSales) {
        this.sellerName = sellerName;
        this.totalSales = totalSales;
        this.dailyAverageSales = dailyAverageSales;
    }

    public String getSellerName() {
        return sellerName;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public BigDecimal getDailyAverageSales() {
        return dailyAverageSales;
    }
}
