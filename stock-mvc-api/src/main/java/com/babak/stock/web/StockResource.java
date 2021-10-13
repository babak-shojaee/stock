package com.babak.stock.web;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.service.StockService;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RequestMapping("/api/stocks")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StockResource {

    private final StockService stockService;

    @GetMapping("/")
    public List<Stock> findAll() {

        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> findById(@Valid @PathVariable(value = "id") Long stockId) throws StockNotFoundException {
        log.info("finding  stock  with id : " + stockId);

        Stock stock = stockService.findById(stockId);

        return ResponseEntity.ok().body(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable(value = "id") Long stockId,
                                             @Valid @RequestBody Stock stock) throws StockNotFoundException {

        Stock updated = stockService.updateStock(stockId, stock);

        log.info(" stocks  found  and updated  by id : " + stock.getId());

        return ResponseEntity.ok().body(updated);
    }

    @PostMapping("/")
    public ResponseEntity<Stock> createStock(@Valid @RequestBody Stock stock) throws URISyntaxException {
        log.info("creating  the stock : " + stock);

        Stock result = stockService.createStock(stock);

        log.info("stocks  saved  with id : " + stock.getId());

        return ResponseEntity
                .created(new URI("/api/stocks/" + result.getId()))
                .body(result);
    }


}
