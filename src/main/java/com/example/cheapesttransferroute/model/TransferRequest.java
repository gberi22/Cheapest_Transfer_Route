package com.example.cheapesttransferroute.model;
import javax.validation.Valid;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    /**
     * The total weight that can be transferred in one route
     */
    private int maxWeight;

    /**
     * A list of available transfers
     */
    private List<Transfer> availableTransfers;
}
