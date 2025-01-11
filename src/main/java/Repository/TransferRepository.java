package Repository;

import Model.Transfer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TransferRepository {
    private final List<Transfer> transfers = new ArrayList<>();

    public List<Transfer> findEveryTransfer() {
        return new ArrayList<>(transfers);
    }

    public void addTransfer(Transfer transfer) {
        transfers.add(transfer);
    }

//    public void clearTransfers() {
//        transfers.clear();
//    }
}
