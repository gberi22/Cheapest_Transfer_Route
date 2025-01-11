package Service;

import Model.RouteResult;
import Model.Transfer;

import java.util.List;

public interface TransferService {
    RouteResult findMaximizedCostRoute(int maxWeight, List<Transfer> allTransfers);
}
