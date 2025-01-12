package com.example.cheapesttransferroute.model;

import lombok.*;

import javax.validation.constraints.Min;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Min(value = 1, message = "Weight must be at least 1")
    private int weight;

    @Min(value = 0, message = "Cost must be at least 0")
    private int cost;
}
