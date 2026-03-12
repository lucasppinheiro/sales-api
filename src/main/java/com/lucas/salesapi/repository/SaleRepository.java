package com.lucas.salesapi.repository;

import com.lucas.salesapi.domain.Sale;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("select s.sellerId as sellerId, "
        + "s.sellerName as sellerName, "
        + "coalesce(sum(s.value), 0) as totalSales "
        + "from Sale s "
        + "where s.saleDate between :startDate and :endDate "
        + "group by s.sellerId, s.sellerName "
        + "order by sum(s.value) desc")
    List<VendorSalesSummaryProjection> summarizeByPeriod(@Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);

    interface VendorSalesSummaryProjection {
        Long getSellerId();

        String getSellerName();

        BigDecimal getTotalSales();
    }
}
