package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.model.TransferRequest;
import com.example.cheapesttransferroute.repository.TransferRepository;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing transfer data and repository operations
 */
@Service
public class TransferManagerService {
    private final TransferRepository transferRepository;

    /**
     * Constructor
     *
     * @param transferRepository the repository managing transfer data
     */
    public TransferManagerService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    /**
     * Processes a transfer request by adding its transfers and maximum weight to the repository
     *
     * @param request client given transfer request containing available transfers and maximum weight
     */
    public void manageService(TransferRequest request) {
        for (Transfer transfer: request.getAvailableTransfers())
            transferRepository.addTransfer(transfer);
        transferRepository.addMaxWeight(request.getMaxWeight());
    }

    /**
     * Prepares repository for new data by clearing existing information
     */
    public void prepareService() {
        transferRepository.clearRepositoryInformation();
    }

    /**
     * Checks if the repository contains available transfers
     *
     * @return true if there is not any transfers given, otherwise false
     */
    public boolean isAvailableTransfersEmpty(){
        return transferRepository.getTransfers().isEmpty();
    }
}