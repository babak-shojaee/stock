package com.babak.stock;

import com.babak.stock.config.DataSeeder;
import com.babak.stock.model.Stock;
import com.babak.stock.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataSeederTest {

    @Mock StockRepository repo;
    @InjectMocks DataSeeder seeder;

    @Test
    void run_skipsSeeding_whenDataExists() throws Exception {
        when(repo.count()).thenReturn(5L);
        seeder.run(new DefaultApplicationArguments());
        verify(repo, never()).saveAll(any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void run_seedsStocks_whenEmpty() throws Exception {
        when(repo.count()).thenReturn(0L);
        seeder.run(new DefaultApplicationArguments());

        ArgumentCaptor<List<Stock>> captor = ArgumentCaptor.forClass(List.class);
        verify(repo).saveAll(captor.capture());
        assertThat(captor.getValue()).isNotEmpty();
        // all ids must be null (cleared before save)
        assertThat(captor.getValue()).allMatch(s -> s.getId() == null);
    }
}
