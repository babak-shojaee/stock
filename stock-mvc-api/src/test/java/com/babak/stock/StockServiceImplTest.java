package com.babak.stock;

import com.babak.stock.exception.StockNotFoundException;
import com.babak.stock.model.Stock;
import com.babak.stock.repository.StockRepository;
import com.babak.stock.service.StockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock StockRepository repo;
    @InjectMocks StockServiceImpl service;

    private Stock stock(Long id, String name, double price) {
        return Stock.builder().id(id).name(name).currentPrice(price).build();
    }

    @Test
    void findAll_returnsNonDeleted() {
        when(repo.findAllByDeletedFalse()).thenReturn(List.of(stock(1L, "A", 1.0)));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    void findAllIncludingDeleted_returnsAll() {
        when(repo.findAll()).thenReturn(List.of(stock(1L, "A", 1.0), stock(2L, "B", 2.0)));
        assertThat(service.findAllIncludingDeleted()).hasSize(2);
    }

    @Test
    void findById_found() throws StockNotFoundException {
        Stock s = stock(1L, "A", 1.0);
        when(repo.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(s));
        assertThat(service.findById(1L)).isEqualTo(s);
    }

    @Test
    void findById_notFound_throws() {
        when(repo.findByIdAndDeletedFalse(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(StockNotFoundException.class);
    }

    @Test
    void createStock_success() {
        Stock s = stock(null, "New", 5.0);
        when(repo.existsByNameIgnoreCaseAndDeletedFalse("New")).thenReturn(false);
        when(repo.save(s)).thenReturn(stock(1L, "New", 5.0));
        assertThat(service.createStock(s).getId()).isEqualTo(1L);
    }

    @Test
    void createStock_duplicateName_throws() {
        Stock s = stock(null, "Dup", 5.0);
        when(repo.existsByNameIgnoreCaseAndDeletedFalse("Dup")).thenReturn(true);
        assertThatThrownBy(() -> service.createStock(s)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateStock_success() throws StockNotFoundException {
        Stock existing = stock(1L, "Old", 1.0);
        Stock update = stock(1L, "New", 9.0);
        when(repo.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(existing));
        when(repo.existsByNameIgnoreCaseAndIdNotAndDeletedFalse("New", 1L)).thenReturn(false);
        when(repo.save(existing)).thenReturn(existing);
        Stock result = service.updateStock(1L, update);
        assertThat(result.getName()).isEqualTo("New");
        assertThat(result.getCurrentPrice()).isEqualTo(9.0);
    }

    @Test
    void updateStock_notFound_throws() {
        when(repo.findByIdAndDeletedFalse(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.updateStock(99L, stock(99L, "X", 1.0)))
                .isInstanceOf(StockNotFoundException.class);
    }

    @Test
    void updateStock_duplicateName_throws() {
        Stock existing = stock(1L, "Old", 1.0);
        when(repo.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(existing));
        when(repo.existsByNameIgnoreCaseAndIdNotAndDeletedFalse("Dup", 1L)).thenReturn(true);
        assertThatThrownBy(() -> service.updateStock(1L, stock(1L, "Dup", 1.0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deleteStock_softDeletes() throws StockNotFoundException {
        Stock s = stock(1L, "A", 1.0);
        when(repo.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(s));
        service.deleteStock(1L);
        assertThat(s.isDeleted()).isTrue();
        verify(repo).save(s);
    }

    @Test
    void deleteStock_notFound_throws() {
        when(repo.findByIdAndDeletedFalse(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.deleteStock(99L)).isInstanceOf(StockNotFoundException.class);
    }

    @Test
    void restoreStock_success() throws StockNotFoundException {
        Stock s = stock(1L, "A", 1.0);
        s.setDeleted(true);
        when(repo.findById(1L)).thenReturn(Optional.of(s));
        service.restoreStock(1L);
        assertThat(s.isDeleted()).isFalse();
        verify(repo).save(s);
    }

    @Test
    void restoreStock_notDeleted_throws() {
        Stock s = stock(1L, "A", 1.0); // deleted=false
        when(repo.findById(1L)).thenReturn(Optional.of(s));
        assertThatThrownBy(() -> service.restoreStock(1L)).isInstanceOf(StockNotFoundException.class);
    }
}
