package com.babak.stock;

import com.babak.stock.model.Stock;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class StockModelTest {

    @Test
    void builder_setsAllFields() {
        Instant now = Instant.now();
        Stock s = Stock.builder().id(1L).name("AAPL").currentPrice(150.0).lastUpdate(now).build();
        assertThat(s.getId()).isEqualTo(1L);
        assertThat(s.getName()).isEqualTo("AAPL");
        assertThat(s.getCurrentPrice()).isEqualTo(150.0);
        assertThat(s.getLastUpdate()).isEqualTo(now);
        assertThat(s.isDeleted()).isFalse();
    }

    @Test
    void equals_sameIdNamePrice() {
        Stock a = Stock.builder().id(1L).name("AAPL").currentPrice(150.0).build();
        Stock b = Stock.builder().id(1L).name("AAPL").currentPrice(150.0).build();
        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
    }

    @Test
    void equals_differentId_notEqual() {
        Stock a = Stock.builder().id(1L).name("AAPL").currentPrice(150.0).build();
        Stock b = Stock.builder().id(2L).name("AAPL").currentPrice(150.0).build();
        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void equals_self() {
        Stock s = Stock.builder().id(1L).name("X").currentPrice(1.0).build();
        assertThat(s).isEqualTo(s);
    }

    @Test
    void equals_null_notEqual() {
        assertThat(Stock.builder().id(1L).name("X").currentPrice(1.0).build()).isNotEqualTo(null);
    }

    @Test
    void setters_work() {
        Stock s = new Stock();
        s.setId(5L);
        s.setName("GOOG");
        s.setCurrentPrice(200.0);
        s.setDeleted(true);
        assertThat(s.getId()).isEqualTo(5L);
        assertThat(s.getName()).isEqualTo("GOOG");
        assertThat(s.getCurrentPrice()).isEqualTo(200.0);
        assertThat(s.isDeleted()).isTrue();
    }

    @Test
    void toString_containsFields() {
        Stock s = Stock.builder().id(1L).name("AAPL").currentPrice(150.0).build();
        assertThat(s.toString()).contains("AAPL").contains("150.0");
    }
}
