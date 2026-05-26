package com.babak.stock.model;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String name;

    @DecimalMin(value = "0.1", inclusive = false)
    private Double currentPrice;

    @UpdateTimestamp
    private Instant lastUpdate;

    @Column(nullable = false)
    private boolean deleted = false;

    public Stock() {}

    public Stock(Long id, String name, Double currentPrice, Instant lastUpdate) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }
    public Instant getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Instant lastUpdate) { this.lastUpdate = lastUpdate; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    @Override
    public String toString() {
        return "Stock{id=" + id + ", name='" + name + "', currentPrice=" + currentPrice + ", lastUpdate=" + lastUpdate + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock s)) return false;
        return Objects.equals(id, s.id) && Objects.equals(name, s.name) && Objects.equals(currentPrice, s.currentPrice);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name, currentPrice); }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private Double currentPrice;
        private Instant lastUpdate;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder currentPrice(Double currentPrice) { this.currentPrice = currentPrice; return this; }
        public Builder lastUpdate(Instant lastUpdate) { this.lastUpdate = lastUpdate; return this; }
        public Stock build() { return new Stock(id, name, currentPrice, lastUpdate); }
    }
}
