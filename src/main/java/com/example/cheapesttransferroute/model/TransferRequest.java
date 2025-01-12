package com.example.cheapesttransferroute.model;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    @Min(value = 1, message = "Maximum weight must be at least 1")
    private int maxWeight;

    @NotEmpty(message = "Available transfers list cannot be empty")
    private List<Transfer> availableTransfers;
}
