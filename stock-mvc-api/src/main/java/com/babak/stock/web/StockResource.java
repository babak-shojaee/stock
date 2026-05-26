package com.babak.stock.web;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.service.StockService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockResource {

    private static final Logger log = LoggerFactory.getLogger(StockResource.class);

    private final StockService stockService;

    public StockResource(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<Stock>> findAll() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> findById(@PathVariable Long id) throws StockNotFoundException {
        log.info("Finding stock with id: {}", id);
        return ResponseEntity.ok(stockService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id,
                                             @Valid @RequestBody Stock stock) throws StockNotFoundException {
        Stock updated = stockService.updateStock(id, stock);
        log.info("Stock updated with id: {}", updated.getId());
        return ResponseEntity.ok(updated);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock) {
        Stock result = stockService.createStock(stock);
        log.info("Stock created with id: {}", result.getId());
        var location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(result);
    }
}
