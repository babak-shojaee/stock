package com.babak.stock.model;


import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@EqualsAndHashCode
public class Stock  {


    @Id
    @GeneratedValue
    private  Long id;

    @NotNull
    @NotEmpty
    private  String name;

    @NotNull
    @DecimalMin(value = "0.1", inclusive = false)
    private  Double currentPrice;

    @UpdateTimestamp
    private  Date lastUpdate;



}
