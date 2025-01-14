package com.example.cheapesttransferroute.model;

import lombok.*;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    /**
     * The weight the transfer can handle
     */
    private int weight;

    /**
     * The fee for that transfer
     */
    private int cost;
}
