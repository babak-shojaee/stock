package com.babak.stock.model;


import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    @DecimalMin(value = "0.1", inclusive = false)
    private  Double currentPrice;

    @UpdateTimestamp
    private  Date lastUpdate;



}
