package com.babak.stock;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.service.StockService;
import com.babak.stock.web.StockGraphQLController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@GraphQlTest(StockGraphQLController.class)
class StockGraphQLControllerTest {

    @Autowired GraphQlTester tester;
    @MockitoBean StockService service;

    private Stock stock(Long id, String name, double price) {
        return Stock.builder().id(id).name(name).currentPrice(price).build();
    }

    @Test
    void query_stocks() {
        when(service.findAllIncludingDeleted()).thenReturn(List.of(stock(1L, "AAPL", 150.0)));

        tester.document("{ stocks { id name currentPrice } }")
                .execute()
                .path("stocks[0].name").entity(String.class).isEqualTo("AAPL");
    }

    @Test
    void query_stock_byId() throws StockNotFoundException {
        when(service.findById(1L)).thenReturn(stock(1L, "AAPL", 150.0));

        tester.document("{ stock(id: 1) { name currentPrice } }")
                .execute()
                .path("stock.name").entity(String.class).isEqualTo("AAPL");
    }

    @Test
    void query_stock_notFound() throws StockNotFoundException {
        when(service.findById(99L)).thenThrow(new StockNotFoundException("not found"));

        tester.document("{ stock(id: 99) { name } }")
                .execute()
                .errors().satisfy(errors -> org.assertj.core.api.Assertions.assertThat(errors).isNotEmpty());
    }

    @Test
    void mutation_createStock() {
        when(service.createStock(any())).thenReturn(stock(1L, "TSLA", 200.0));

        tester.document("mutation { createStock(name: \"TSLA\", currentPrice: 200.0) { id name } }")
                .execute()
                .path("createStock.name").entity(String.class).isEqualTo("TSLA");
    }

    @Test
    void mutation_updateStock() throws StockNotFoundException {
        when(service.updateStock(eq(1L), any())).thenReturn(stock(1L, "TSLA", 250.0));

        tester.document("mutation { updateStock(id: 1, name: \"TSLA\", currentPrice: 250.0) { currentPrice } }")
                .execute()
                .path("updateStock.currentPrice").entity(Double.class).isEqualTo(250.0);
    }

    @Test
    void mutation_deleteStock() throws StockNotFoundException {
        tester.document("mutation { deleteStock(id: 1) }")
                .execute()
                .path("deleteStock").entity(Boolean.class).isEqualTo(true);
    }

    @Test
    void mutation_restoreStock() throws StockNotFoundException {
        tester.document("mutation { restoreStock(id: 1) }")
                .execute()
                .path("restoreStock").entity(Boolean.class).isEqualTo(true);
    }
}
