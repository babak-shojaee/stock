package com.babak.stock;

import com.babak.stock.model.Stock;
import com.babak.stock.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StockRepositoryTest {

    @Autowired StockRepository repo;

    private Stock active;
    private Stock deleted;

    @BeforeEach
    void setUp() {
        active = new Stock(); active.setName("Active"); active.setCurrentPrice(10.0);
        deleted = new Stock(); deleted.setName("Deleted"); deleted.setCurrentPrice(5.0); deleted.setDeleted(true);
        repo.saveAll(List.of(active, deleted));
    }

    @Test
    void findAllByDeletedFalse_excludesDeleted() {
        assertThat(repo.findAllByDeletedFalse()).extracting(Stock::getName).containsExactly("Active");
    }

    @Test
    void findByIdAndDeletedFalse_findsActive() {
        assertThat(repo.findByIdAndDeletedFalse(active.getId())).isPresent();
    }

    @Test
    void findByIdAndDeletedFalse_doesNotFindDeleted() {
        assertThat(repo.findByIdAndDeletedFalse(deleted.getId())).isEmpty();
    }

    @Test
    void existsByNameIgnoreCaseAndDeletedFalse_caseInsensitive() {
        assertThat(repo.existsByNameIgnoreCaseAndDeletedFalse("active")).isTrue();
        assertThat(repo.existsByNameIgnoreCaseAndDeletedFalse("ACTIVE")).isTrue();
        assertThat(repo.existsByNameIgnoreCaseAndDeletedFalse("deleted")).isFalse(); // soft-deleted
    }

    @Test
    void existsByNameIgnoreCaseAndIdNotAndDeletedFalse() {
        Stock other = new Stock(); other.setName("Other"); other.setCurrentPrice(1.0);
        repo.save(other);
        assertThat(repo.existsByNameIgnoreCaseAndIdNotAndDeletedFalse("Active", other.getId())).isTrue();
        assertThat(repo.existsByNameIgnoreCaseAndIdNotAndDeletedFalse("Active", active.getId())).isFalse();
    }
}
