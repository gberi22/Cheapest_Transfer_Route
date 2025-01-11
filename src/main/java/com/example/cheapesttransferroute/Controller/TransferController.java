package com.example.cheapesttransferroute.Controller;

import com.example.cheapesttransferroute.Model.RouteResult;
import com.example.cheapesttransferroute.Model.TransferRequest;
import com.example.cheapesttransferroute.Service.TransferService;
import com.example.cheapesttransferroute.Service.TransferManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final TransferService transferService;
    private final TransferManagerService transferManagerService;

    @Autowired
    public TransferController(TransferService transferService, TransferManagerService transferManagerService) {
        this.transferService = transferService;
        this.transferManagerService = transferManagerService;
    }

    @GetMapping("/getRoute")
    public ResponseEntity<RouteResult> getMaximizedCostRoute() {
        logger.info("GET request received at /api/transfers/getRoute");
        return ResponseEntity.ok(transferService.getShortestRoute());
    }

    @PostMapping("/inputRoutes")
    public ResponseEntity<Void> chosenRoute(@RequestBody TransferRequest request) {
        logger.info("POST request received at /api/transfers/inputRoutes");
        transferManagerService.ManageService(request);
        return ResponseEntity.ok().build();
    }
}
