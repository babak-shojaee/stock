package com.babak.stock.web;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RequestMapping("/api/stocks")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StockResource {

    private final StockService stockService;

    @GetMapping({"", "/"})
    public List<Stock> findAll() {
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> findById(@PathVariable(value = "id") Long stockId) throws StockNotFoundException {
        log.info("Finding stock with id: {}", stockId);

        Stock stock = stockService.findById(stockId);

        return ResponseEntity.ok().body(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable(value = "id") Long stockId,
                                             @Valid @RequestBody Stock stock) throws StockNotFoundException {

        Stock updated = stockService.updateStock(stockId, stock);

        log.info("Stock updated with id: {}", updated.getId());

        return ResponseEntity.ok().body(updated);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock) {
        log.info("Creating stock: {}", stock);

        Stock result = stockService.createStock(stock);

        log.info("Stock saved with id: {}", result.getId());

        var location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(result);
    }


}
