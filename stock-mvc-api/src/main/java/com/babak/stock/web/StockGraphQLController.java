package com.babak.stock.web;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.service.StockService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StockGraphQLController {

    private final StockService stockService;

    public StockGraphQLController(StockService stockService) {
        this.stockService = stockService;
    }

    @QueryMapping
    public List<Stock> stocks() {
        return stockService.findAllIncludingDeleted();
    }

    @QueryMapping
    public Stock stock(@Argument Long id) throws StockNotFoundException {
        return stockService.findById(id);
    }

    @MutationMapping
    public Stock createStock(@Argument String name, @Argument Double currentPrice) {
        Stock s = new Stock();
        s.setName(name);
        s.setCurrentPrice(currentPrice);
        return stockService.createStock(s);
    }

    @MutationMapping
    public Stock updateStock(@Argument Long id, @Argument String name, @Argument Double currentPrice) throws StockNotFoundException {
        Stock s = new Stock();
        s.setName(name);
        s.setCurrentPrice(currentPrice);
        return stockService.updateStock(id, s);
    }

    @MutationMapping
    public boolean deleteStock(@Argument Long id) throws StockNotFoundException {
        stockService.deleteStock(id);
        return true;
    }

    @MutationMapping
    public boolean restoreStock(@Argument Long id) throws StockNotFoundException {
        stockService.restoreStock(id);
        return true;
    }
}
