package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.RouteResult;

/**
 * Finds the combination of transfers that maximizes the total
 * cost while ensuring the total weight of the transfers is less
 * than or equal to maxWeight.
 */
public interface TransferService {
    RouteResult getMaximizedCostRoute();
}
