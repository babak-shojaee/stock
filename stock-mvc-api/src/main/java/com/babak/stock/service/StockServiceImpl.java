package com.babak.stock.service;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl  implements  StockService {

    private final StockRepository stockRepository;

    private static final String STOCK_ENTITY= "Stock";

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long id)throws StockNotFoundException {

        Optional<Stock> optionalStock = stockRepository.findById(id);

        return optionalStock.orElseThrow(() ->  new StockNotFoundException(STOCK_ENTITY+ " not found  whti id : "+id));
    }

    @Override
    public Stock updateStock(Long stockId, Stock stock) throws StockNotFoundException {

        Stock oldStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException("could not find  "+STOCK_ENTITY+" with id : " + stockId));

        oldStock.setName(stock.getName());

        oldStock.setCurrentPrice(stock.getCurrentPrice());

        return stockRepository.save(oldStock);
    }

    @Override
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }
}
