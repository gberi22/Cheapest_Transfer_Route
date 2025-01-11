package Service;

import Model.RouteResult;
import Model.Transfer;
import Repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {


    private final TransferRepository transferRepository;

    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public RouteResult getShortestRoute() {
        List<Transfer> list = transferRepository.getTransfers();
        int maxWeight = transferRepository.getMaxWeight();
        return findMaximizedCostRoute(maxWeight, list);
    }



    /**
     * This logic requires dp algorithm. dp array tracks maximum cost for each weight from 0 to maxWeight
     * 1. We must consider each Transfer one by one
     * 2. For each transfer, update dp array but in reverse order to be sure we don't use same transfer multiple times
     * 3. dp must be 2D array, so I can keep my eyes on which transfers dp chose.
     * 4. after dp is filled, I need to take out the information to return final route information
     * 5. Backtracking to find out which transfers were chosen.
     * 6. When I find the route weight is easily computable
     */
    public RouteResult findMaximizedCostRoute(int maxWeight, List<Transfer> allTransfers) {
        List<Transfer> selectedTransfers = new ArrayList<Transfer>();
        int totalWeight = 0;
        int totalCost = 0;

        int quantityOfTransfers = allTransfers.size();
        int [][]dp_array = new int[quantityOfTransfers+1][maxWeight+1];

        for(int eachTransfer=1; eachTransfer<=quantityOfTransfers; eachTransfer++) {
            Transfer transfer = allTransfers.get(eachTransfer-1);
            for (int eachWeight = maxWeight; eachWeight >= transfer.getWeight(); eachWeight--) {
                dp_array[eachTransfer][eachWeight] = Math.max(dp_array[eachTransfer-1][eachWeight],
                        dp_array[eachTransfer-1][eachWeight - transfer.getWeight()] + transfer.getCost());
            }
        }

        for(int i=quantityOfTransfers; i>0; i--) {
            if (maxWeight <= 0) break;
            if(dp_array[i][maxWeight] != dp_array[i-1][maxWeight]) {
                Transfer transfer = new Transfer(dp_array[i][maxWeight], maxWeight);
                selectedTransfers.add(transfer);
                totalWeight += transfer.getWeight();
                maxWeight -= transfer.getWeight();
            }
        }

        return new RouteResult(selectedTransfers, totalCost, totalWeight);
    }

}
