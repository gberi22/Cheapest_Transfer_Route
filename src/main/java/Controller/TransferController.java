package Controller;

import Model.RouteResult;
import Model.Transfer;
import Repository.TransferRepository;
import Service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final TransferRepository transferRepository;

    @Autowired
    public TransferController(TransferService transferService, TransferRepository transferRepository) {
        this.transferService = transferService;
        this.transferRepository = transferRepository;
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transfer>> getEveryTransfer() {
        return ResponseEntity.ok(transferRepository.findEveryTransfer());
    }

    @PostMapping
    public ResponseEntity<Void> createTransfer(@RequestBody Transfer transfer) {
        transferRepository.addTransfer(transfer);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/chosen_route")
    public ResponseEntity<RouteResult> chosenRoute(@RequestBody int maxWeight, @RequestBody List<Transfer> transfers) {
        return ResponseEntity.ok(transferService.findMaximizedCostRoute(maxWeight, transfers));
    }


//    @DeleteMapping
//    public ResponseEntity<Void> deleteTransfer(@RequestBody Transfer transfer) {
//        transferRepository.clearTransfers();
//        return ResponseEntity.ok().build();
//    }


}
