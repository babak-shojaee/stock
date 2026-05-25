package com.babak.stock.config;

import com.babak.stock.model.Stock;
import com.babak.stock.repository.StockRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final StockRepository stockRepository;

    public DataSeeder(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (stockRepository.count() > 0) return;

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Stock>> typeReference = new TypeReference<>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
        List<Stock> stockList = mapper.readValue(inputStream, typeReference);
        stockList.forEach(stock -> stock.setId(null));
        stockRepository.saveAll(stockList);
        log.info("Seeded {} stocks", stockList.size());
    }
}
