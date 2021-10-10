package com.babak.stock;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.service.StockService;
import com.babak.stock.web.StockResource;
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



@WebMvcTest(StockResource.class)
public class StockControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService service;

    private final long stockId = 1;
    private final String expectedErrMsg = "Stock not found for this id: " + stockId;
    private final String baseUri = "/api/stocks/";
    private final ObjectWriter ow = new ObjectMapper().writer();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");

    @Test
    public void findAll_ReturnsStockList() throws Exception {


        Mockito.when(service.findAll()).thenReturn(
                List.of(Stock.builder().id(1L).name("Facebook").currentPrice(200.7).build(),
                        Stock.builder().id(2L).name("Google").currentPrice(1000.5).build()));


    }



}
