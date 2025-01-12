package com.example.cheapesttransferroute.model;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResult {
    private List<Transfer> selectedTransfers;
    private int totalCost;
    private int totalWeight;
}
