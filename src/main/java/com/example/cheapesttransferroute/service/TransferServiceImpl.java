package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.RouteResult;
import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service implementation for managing transfer operations
 */
@Service
public class TransferServiceImpl implements TransferService {


    private final TransferRepository transferRepository;

    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    /**
     * Finds the combination of transfers that maximizes the total
     * cost while ensuring the total weight of the transfers is less
     * than or equal to maxWeight
     * @return result of the final route containing selected list of transfers, total cost and total weight.
     */
    @Override
    public RouteResult getMaximizedCostRoute() {
        List<Transfer> list = transferRepository.getTransfers();
        int maxWeight = transferRepository.getMaxWeight();
        return findMaximizedCostRoute(maxWeight, list);
    }

    /**
     * This logic requires dp algorithm. dp array tracks maximum cost for each weight from 0 to maxWeight
     * 1. Consider each Transfer one by one
     * 2. For each transfer, update dp array but in reverse order to avoid usage of same transfer multiple times
     * 3. dp must be 2D array, to keep track on which transfers dp chooses.
     * 4. after dp is filled, the information must be taken out to return final route information
     * 5. Backtracking to find out which transfers were chosen.
     * 6. When the route is found, weight and cost are easily computable
     *
     * @param maxWeight the maximum allowable weight for the route
     * @param allTransfers the list of all available transfers
     * @return RouteResult containing the selected transfers, total cost, and total weight
     */
    private RouteResult findMaximizedCostRoute(int maxWeight, List<Transfer> allTransfers) {
        int quantityOfTransfers = allTransfers.size();
        int [][]dp_array = new int[quantityOfTransfers + 1][maxWeight + 1];

        for (int i = 1; i <= quantityOfTransfers; i++) {
            Transfer transfer = allTransfers.get(i - 1);
            for (int w = 0; w <= maxWeight; w++) {
                if (transfer.getWeight() <= w) {
                    dp_array[i][w] = Math.max(dp_array[i - 1][w],
                            dp_array[i - 1][w - transfer.getWeight()] + transfer.getCost());
                } else {
                    dp_array[i][w] = dp_array[i - 1][w];
                }
            }
        }

        List<Transfer> selectedTransfers = new ArrayList<>();
        int totalWeight = 0;
        int totalCost = 0;
        int w = maxWeight;

        for (int i = quantityOfTransfers; i > 0 && w >= 0; i--) {
            if (dp_array[i][w] != dp_array[i - 1][w]) {
                Transfer transfer = allTransfers.get(i - 1);
                selectedTransfers.add(transfer);
                totalWeight += transfer.getWeight();
                totalCost += transfer.getCost();
                w -= transfer.getWeight();
            }
        }

        Collections.reverse(selectedTransfers);

        return new RouteResult(selectedTransfers, totalCost, totalWeight);
    }

}
