package com.example.cheapesttransferroute.model;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private int maxWeight;
    private List<Transfer> availableTransfers;
}
