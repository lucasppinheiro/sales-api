package com.lucas.salesapi.controller;

import com.lucas.salesapi.dto.VendorSalesSummaryResponse;
import com.lucas.salesapi.service.VendorReportService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorReportService vendorReportService;

    public VendorController(VendorReportService vendorReportService) {
        this.vendorReportService = vendorReportService;
    }

    @GetMapping("/summary")
    public List<VendorSalesSummaryResponse> getSummaryByPeriod(
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startDate,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate endDate
    ) {
        return vendorReportService.getSummaryByPeriod(startDate, endDate);
    }
}
