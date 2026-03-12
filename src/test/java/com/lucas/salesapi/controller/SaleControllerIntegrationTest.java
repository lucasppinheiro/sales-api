package com.lucas.salesapi.controller;

import com.lucas.salesapi.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SaleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SaleRepository saleRepository;

    @BeforeEach
    void setUp() {
        saleRepository.deleteAll();
    }

    @Test
    void shouldCreateSaleSuccessfully() throws Exception {
        String payload = "{"
            + "\"saleDate\":\"2024-03-10\","
            + "\"value\":1200.50,"
            + "\"sellerId\":10,"
            + "\"sellerName\":\"Lucas Silva\""
            + "}";

        mockMvc.perform(post("/api/v1/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", matchesPattern("/api/v1/sales/\\d+")))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.saleDate").value("2024-03-10"))
            .andExpect(jsonPath("$.value").value(1200.50))
            .andExpect(jsonPath("$.sellerId").value(10))
            .andExpect(jsonPath("$.sellerName").value("Lucas Silva"));
    }

    @Test
    void shouldRejectInvalidPayload() throws Exception {
        String payload = "{"
            + "\"saleDate\":\"2099-03-20\","
            + "\"value\":0,"
            + "\"sellerName\":\"\""
            + "}";

        mockMvc.perform(post("/api/v1/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.details", hasSize(4)));
    }
}
