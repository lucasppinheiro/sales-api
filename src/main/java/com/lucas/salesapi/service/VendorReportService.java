package com.lucas.salesapi.service;

import com.lucas.salesapi.dto.VendorSalesSummaryResponse;
import com.lucas.salesapi.exception.BusinessException;
import com.lucas.salesapi.repository.SaleRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorReportService {

    private final SaleRepository saleRepository;

    public VendorReportService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Transactional(readOnly = true)
    public List<VendorSalesSummaryResponse> getSummaryByPeriod(LocalDate startDate, LocalDate endDate) {
        validatePeriod(startDate, endDate);

        long daysInPeriod = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        // A media diaria considera todo o intervalo informado, inclusive dias sem venda.
        return saleRepository.summarizeByPeriod(startDate, endDate)
            .stream()
            .map(summary -> new VendorSalesSummaryResponse(
                summary.getSellerName(),
                summary.getTotalSales(),
                calculateDailyAverage(summary.getTotalSales(), daysInPeriod)
            ))
            .collect(Collectors.toList());
    }

    private void validatePeriod(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new BusinessException("A data final deve ser maior ou igual a data inicial.");
        }
    }

    private BigDecimal calculateDailyAverage(BigDecimal totalSales, long daysInPeriod) {
        return totalSales.divide(BigDecimal.valueOf(daysInPeriod), 2, RoundingMode.HALF_UP);
    }
}
