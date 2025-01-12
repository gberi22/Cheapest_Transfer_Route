package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.RouteResult;

public interface TransferService {
    RouteResult getMaximizedCostRoute();
}
