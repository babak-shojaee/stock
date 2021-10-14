package com.babak.stock;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.service.StockService;
import com.babak.stock.web.StockResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private StockService service;

    private final Long stockId = 1L;
    private final Long stockId2= 2L;
    private final String stockFacebookName = "Facebook";
    private final String stockGoogleName = "Google";
    private final Double currentFacebookPrice = 500.7;
    private final Double currentGooglePrice = 1000d;


    private final String expectedErrMsg = "Stock not found for this id: " + stockId;
    private final String baseUri = "/api/stocks/";
    private  ObjectWriter objectWriter = new ObjectMapper().writer();



    @Test
    public void createStock ()throws Exception {
        Stock expected = Stock.builder().id(stockId).name(stockFacebookName).currentPrice(currentFacebookPrice).build();

        Stock in = Stock.builder().id(0L).name(stockFacebookName).currentPrice(currentFacebookPrice).build();

        Mockito.when(service.createStock(in)).thenReturn(expected);
        String JsonString = objectWriter.writeValueAsString(in);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post(baseUri).content(JsonString).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Facebook"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(500.7d));


    }

    @Test
    public void updateStock() throws Exception {
        
        
        Stock in = Stock.builder().id(stockId).name(stockFacebookName).currentPrice(currentFacebookPrice).build();
        
        Mockito.when(service.updateStock(stockId, in)).thenReturn(in);
        String JsonString = objectWriter.writeValueAsString(in);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put(baseUri + "{id}", stockId)
                        .content(JsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Facebook"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(500.7));
    }
    

    @Test
    public void findAll() throws Exception {
        Mockito.when(service.findAll()).thenReturn(
                List.of(
                        Stock.builder().id(1L).name(stockFacebookName).currentPrice(currentFacebookPrice).build(),
                         Stock.builder().id(2L).name(stockGoogleName).currentPrice(currentGooglePrice).build()));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(baseUri))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Facebook"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currentPrice").value(500.7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Google"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].currentPrice").value(1000));
    }

    @Test
    public void findById() throws Exception {
        Mockito.when(service.findById(stockId)).thenReturn(
                Stock.builder().id(1L).name(stockFacebookName).currentPrice(currentFacebookPrice).build()
        );
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(baseUri + stockId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Facebook"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPrice").value(500.7d));
    }

    @Test
    public void findByIdWithWrongId() throws Exception {
        Mockito.when(service.findById(stockId)).thenThrow(
                new StockNotFoundException(expectedErrMsg)
        );
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(baseUri + stockId))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StockNotFoundException))
                .andExpect(result -> assertEquals(expectedErrMsg,
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }





    @Test
    public void updateStockWithWrongId() throws Exception {
        Stock in = Stock.builder().id(stockId).name(stockFacebookName).currentPrice(currentFacebookPrice).build();

        Mockito.when(service.updateStock(stockId, in)).thenThrow(
                new StockNotFoundException(expectedErrMsg)
        );
        String JsonString = objectWriter.writeValueAsString(in);

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put(baseUri + "{id}", stockId)
                        .content(JsonString)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StockNotFoundException))
                .andExpect(result -> assertEquals(expectedErrMsg,
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }




}
