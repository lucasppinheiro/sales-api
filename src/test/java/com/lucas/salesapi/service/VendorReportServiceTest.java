package com.lucas.salesapi.service;

import com.lucas.salesapi.exception.BusinessException;
import com.lucas.salesapi.repository.SaleRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendorReportServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private VendorReportService vendorReportService;

    @Test
    void shouldCalculateDailyAverageUsingFullPeriod() {
        when(saleRepository.summarizeByPeriod(LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 5)))
            .thenReturn(asList(new ProjectionStub("Lucas", new BigDecimal("500.00"))));

        BigDecimal dailyAverage = vendorReportService
            .getSummaryByPeriod(LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 5))
            .get(0)
            .getDailyAverageSales();

        assertEquals(new BigDecimal("100.00"), dailyAverage);
    }

    @Test
    void shouldThrowExceptionWhenPeriodIsInvalid() {
        assertThrows(
            BusinessException.class,
            () -> vendorReportService.getSummaryByPeriod(LocalDate.of(2026, 3, 10), LocalDate.of(2026, 3, 9))
        );
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoSales() {
        when(saleRepository.summarizeByPeriod(LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 5)))
            .thenReturn(Collections.emptyList());

        assertEquals(
            0,
            vendorReportService.getSummaryByPeriod(LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 5)).size()
        );
    }

    private static class ProjectionStub implements SaleRepository.VendorSalesSummaryProjection {

        private final String sellerName;
        private final BigDecimal totalSales;

        private ProjectionStub(String sellerName, BigDecimal totalSales) {
            this.sellerName = sellerName;
            this.totalSales = totalSales;
        }

        @Override
        public Long getSellerId() {
            return 1L;
        }

        @Override
        public String getSellerName() {
            return sellerName;
        }

        @Override
        public BigDecimal getTotalSales() {
            return totalSales;
        }
    }
}
