package com.lucas.salesapi.controller;

import com.lucas.salesapi.dto.VendorSalesSummaryResponse;
import com.lucas.salesapi.service.VendorReportService;
import java.time.LocalDate;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vendors")
@Tag(name = "Vendedores", description = "Relatórios de desempenho por vendedor")
public class VendorController {

    private final VendorReportService vendorReportService;

    public VendorController(VendorReportService vendorReportService) {
        this.vendorReportService = vendorReportService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Resume as vendas por vendedor em um período")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resumo gerado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Período inválido")
    })
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
