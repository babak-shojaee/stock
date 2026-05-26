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
        return stockRepository.findAllByDeletedFalse();
    }

    @Override
    public List<Stock> findAllIncludingDeleted() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long id) throws StockNotFoundException {
        return stockRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new StockNotFoundException(STOCK_ENTITY + " not found with id: " + id));
    }

    @Override
    @Transactional
    public Stock updateStock(Long stockId, Stock stock) throws StockNotFoundException {
        Stock oldStock = stockRepository.findByIdAndDeletedFalse(stockId)
                .orElseThrow(() -> new StockNotFoundException(STOCK_ENTITY + " not found with id: " + stockId));
        if (stockRepository.existsByNameIgnoreCaseAndIdNotAndDeletedFalse(stock.getName(), stockId))
            throw new IllegalArgumentException("Stock name '" + stock.getName() + "' already exists");
        oldStock.setName(stock.getName());
        oldStock.setCurrentPrice(stock.getCurrentPrice());
        return stockRepository.save(oldStock);
    }

    @Override
    @Transactional
    public Stock createStock(Stock stock) {
        if (stockRepository.existsByNameIgnoreCaseAndDeletedFalse(stock.getName()))
            throw new IllegalArgumentException("Stock name '" + stock.getName() + "' already exists");
        return stockRepository.save(stock);
    }

    @Override
    @Transactional
    public void deleteStock(Long id) throws StockNotFoundException {
        Stock stock = stockRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new StockNotFoundException(STOCK_ENTITY + " not found with id: " + id));
        stock.setDeleted(true);
        stockRepository.save(stock);
    }

    @Override
    @Transactional
    public void restoreStock(Long id) throws StockNotFoundException {
        Stock stock = stockRepository.findById(id)
                .filter(Stock::isDeleted)
                .orElseThrow(() -> new StockNotFoundException(STOCK_ENTITY + " not found with id: " + id));
        stock.setDeleted(false);
        stockRepository.save(stock);
    }
}
