package com.babak.stock.service;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;

import java.util.List;

public interface StockService {


    Stock createStock(Stock stock);

    List<Stock> findAll();

    Stock findById(Long id)throws StockNotFoundException;

    Stock updateStock(Long stockId, Stock stock)throws StockNotFoundException;


}
