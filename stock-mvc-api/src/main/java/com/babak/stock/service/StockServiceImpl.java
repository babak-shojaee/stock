package com.babak.stock.service;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {

    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    private static final String STOCK_ENTITY = "Stock";

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long id) throws StockNotFoundException {
        return stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException(STOCK_ENTITY + " not found with id: " + id));
    }

    @Override
    @Transactional
    public Stock updateStock(Long stockId, Stock stock) throws StockNotFoundException {
        Stock oldStock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(STOCK_ENTITY + " not found with id: " + stockId));
        oldStock.setName(stock.getName());
        oldStock.setCurrentPrice(stock.getCurrentPrice());
        return stockRepository.save(oldStock);
    }

    @Override
    @Transactional
    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }
}
