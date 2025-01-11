package Service;

import Model.Transfer;
import Model.TransferRequest;
import Repository.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class saveService {
    private final TransferRepository transferRepository;

    public saveService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public void saveServices(TransferRequest request) {
        for (Transfer transfer: request.getSelectedTransfers())
            transferRepository.addTransfer(transfer);
        transferRepository.addMaxWeight(request.getMaxWeight());
    }
}
