package Service;

import Model.RouteResult;
import Model.Transfer;
import Model.TransferRequest;

import java.util.List;

public interface TransferService {
    RouteResult getShortestRoute();
}
