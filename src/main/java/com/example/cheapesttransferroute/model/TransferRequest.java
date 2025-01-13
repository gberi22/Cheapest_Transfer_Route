package com.example.cheapesttransferroute.model;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    @NotNull(message = "Available transfers list cannot be null")
    @NotEmpty(message = "Available transfers list cannot be empty")
    private List<@Valid Transfer> availableTransfers;
}
