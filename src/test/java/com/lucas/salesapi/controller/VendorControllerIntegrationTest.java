package com.lucas.salesapi.controller;

import com.lucas.salesapi.domain.Sale;
import com.lucas.salesapi.repository.SaleRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VendorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SaleRepository saleRepository;

    @BeforeEach
    void setUp() {
        saleRepository.deleteAll();
    }

    @Test
    void shouldReturnVendorSummaryForGivenPeriod() throws Exception {
        saleRepository.save(new Sale(LocalDate.of(2026, 3, 1), new BigDecimal("100.00"), 1L, "Lucas"));
        saleRepository.save(new Sale(LocalDate.of(2026, 3, 2), new BigDecimal("50.00"), 1L, "Lucas"));
        saleRepository.save(new Sale(LocalDate.of(2026, 3, 2), new BigDecimal("300.00"), 2L, "Marina"));
        saleRepository.save(new Sale(LocalDate.of(2026, 2, 28), new BigDecimal("999.99"), 3L, "Fora do periodo"));

        mockMvc.perform(get("/api/v1/vendors/summary")
                .param("startDate", "2026-03-01")
                .param("endDate", "2026-03-03"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].sellerName").value("Marina"))
            .andExpect(jsonPath("$[0].totalSales").value(300.00))
            .andExpect(jsonPath("$[0].dailyAverageSales").value(100.00))
            .andExpect(jsonPath("$[1].sellerName").value("Lucas"))
            .andExpect(jsonPath("$[1].totalSales").value(150.00))
            .andExpect(jsonPath("$[1].dailyAverageSales").value(50.00));
    }

    @Test
    void shouldRejectInvalidPeriod() throws Exception {
        mockMvc.perform(get("/api/v1/vendors/summary")
                .param("startDate", "2026-03-10")
                .param("endDate", "2026-03-05"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("A data final deve ser maior ou igual a data inicial."));
    }
}
