package com.example.cheapesttransferroute.Service;

import com.example.cheapesttransferroute.Model.Transfer;
import com.example.cheapesttransferroute.Model.TransferRequest;
import com.example.cheapesttransferroute.Repository.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferManagerService {
    private final TransferRepository transferRepository;

    public TransferManagerService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public void ManageService(TransferRequest request) {
        for (Transfer transfer: request.getAvailableTransfers())
            transferRepository.addTransfer(transfer);
        transferRepository.addMaxWeight(request.getMaxWeight());
    }
}
