package com.babak.stock;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.service.StockService;
import com.babak.stock.web.StockResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockResource.class)
class ControllersAdviceTest {

    @Autowired MockMvc mockMvc;
    @MockitoBean StockService service;

    @Test
    void stockNotFound_returns404WithErrorBody() throws Exception {
        when(service.findById(99L)).thenThrow(new StockNotFoundException("Stock not found with id: 99"));

        mockMvc.perform(get("/api/stocks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value("INV-ID"))
                .andExpect(jsonPath("$.message").value("Stock not found with id: 99"));
    }

    @Test
    void illegalArgument_returns400() throws Exception {
        when(service.findAllIncludingDeleted()).thenThrow(new IllegalArgumentException("bad input"));

        mockMvc.perform(get("/api/stocks/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("INV-INP"));
    }

    @Test
    void typeMismatch_returns400() throws Exception {
        mockMvc.perform(get("/api/stocks/not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.code").value("INV-INP"));
    }

    @Test
    void unexpectedException_returns500() throws Exception {
        when(service.findAllIncludingDeleted()).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(get("/api/stocks/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.code").value("INT-ERR"));
    }
}
