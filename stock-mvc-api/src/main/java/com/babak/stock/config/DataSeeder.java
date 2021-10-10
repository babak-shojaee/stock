package com.babak.stock.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.babak.stock.model.Stock;
import com.babak.stock.repository.StockRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Component
public class DataSeeder implements ApplicationRunner {

    private final StockRepository stockRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Stock>> typeReference = new TypeReference<>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
        List<Stock> stockList = mapper.readValue(inputStream, typeReference);

            stockList.forEach(
                    stock -> log.info("Data is Loading " + stockRepository.save(stock)));

    }
}
