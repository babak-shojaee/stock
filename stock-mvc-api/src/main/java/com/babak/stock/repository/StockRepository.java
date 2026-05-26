package com.babak.stock.repository;

import com.babak.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByDeletedFalse();
    Optional<Stock> findByIdAndDeletedFalse(Long id);
    boolean existsByNameIgnoreCaseAndDeletedFalse(String name);
    boolean existsByNameIgnoreCaseAndIdNotAndDeletedFalse(String name, Long id);
}
