package com.example.cheapesttransferroute.model;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResult {
    /**
     * A list of transfers selected for the route
     */
    private List<Transfer> selectedTransfers;

    /**
     * The total fee for the selected transfers
     */
    private int totalCost;

    /**
     * The total weight for the selected transfers
     */
    private int totalWeight;
}
