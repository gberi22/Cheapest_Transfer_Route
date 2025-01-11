package Controller;

import Model.RouteResult;
import Model.Transfer;
import Model.TransferRequest;
import Service.TransferService;
import Service.saveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final saveService saveServices;

    @Autowired
    public TransferController(TransferService transferService, saveService saveServices) {
        this.transferService = transferService;
        this.saveServices = saveServices;
    }

    @GetMapping("/getRoute")
    public ResponseEntity<RouteResult> getMaximizedCostRoute() {
        return ResponseEntity.ok(transferService.getShortestRoute());
    }

    @PostMapping("/inputRoutes")
    public ResponseEntity<Void> chosenRoute(@RequestBody TransferRequest request) {
        System.out.println(request.getMaxWeight());
        for(Transfer tr: request.getSelectedTransfers()){
            System.out.println(tr.getCost());
            System.out.println(tr.getWeight());
        }
        saveServices.saveServices(request);
        return ResponseEntity.ok().build();
    }

}
