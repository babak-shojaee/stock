package com.babak.stock;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.service.StockService;
import com.babak.stock.web.StockResource;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockResource.class)
public class StockResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockService service;

    private static final Long STOCK_ID = 1L;
    private static final String FACEBOOK = "Facebook";
    private static final String GOOGLE = "Google";
    private static final Double FACEBOOK_PRICE = 500.7;
    private static final Double GOOGLE_PRICE = 1000d;
    private static final String BASE_URI = "/api/stocks/";
    private static final String ERR_MSG = "Stock not found for this id: " + STOCK_ID;
    private static final ObjectWriter WRITER = new ObjectMapper().writer();

    @Test
    public void createStock() throws Exception {
        Stock in = Stock.builder().id(0L).name(FACEBOOK).currentPrice(FACEBOOK_PRICE).build();
        Stock expected = Stock.builder().id(STOCK_ID).name(FACEBOOK).currentPrice(FACEBOOK_PRICE).build();
        Mockito.when(service.createStock(in)).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URI)
                        .content(WRITER.writeValueAsString(in)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(FACEBOOK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(FACEBOOK_PRICE));
    }

    @Test
    public void updateStock() throws Exception {
        Stock in = Stock.builder().id(STOCK_ID).name(FACEBOOK).currentPrice(FACEBOOK_PRICE).build();
        Mockito.when(service.updateStock(STOCK_ID, in)).thenReturn(in);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URI + "{id}", STOCK_ID)
                        .content(WRITER.writeValueAsString(in)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(FACEBOOK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(FACEBOOK_PRICE));
    }

    @Test
    public void findAll() throws Exception {
        Mockito.when(service.findAllIncludingDeleted()).thenReturn(List.of(
                Stock.builder().id(1L).name(FACEBOOK).currentPrice(FACEBOOK_PRICE).build(),
                Stock.builder().id(2L).name(GOOGLE).currentPrice(GOOGLE_PRICE).build()));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(FACEBOOK))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(GOOGLE));
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(service.findById(STOCK_ID))
                .thenReturn(Stock.builder().id(STOCK_ID).name(FACEBOOK).currentPrice(FACEBOOK_PRICE).build());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URI + STOCK_ID))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(FACEBOOK))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(FACEBOOK_PRICE));
    }

    @Test
    public void findByIdNotFound() throws Exception {
        Mockito.when(service.findById(STOCK_ID)).thenThrow(new StockNotFoundException(ERR_MSG));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URI + STOCK_ID))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StockNotFoundException))
                .andExpect(result -> assertEquals(ERR_MSG,
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void updateStockNotFound() throws Exception {
        Stock in = Stock.builder().id(STOCK_ID).name(FACEBOOK).currentPrice(FACEBOOK_PRICE).build();
        Mockito.when(service.updateStock(STOCK_ID, in)).thenThrow(new StockNotFoundException(ERR_MSG));

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URI + "{id}", STOCK_ID)
                        .content(WRITER.writeValueAsString(in)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StockNotFoundException))
                .andExpect(result -> assertEquals(ERR_MSG,
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
