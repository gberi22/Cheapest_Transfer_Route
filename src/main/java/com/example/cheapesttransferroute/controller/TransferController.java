package com.example.cheapesttransferroute.controller;

import com.example.cheapesttransferroute.model.RouteResult;
import com.example.cheapesttransferroute.model.TransferRequest;
import com.example.cheapesttransferroute.service.TransferService;
import com.example.cheapesttransferroute.service.TransferManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/transfers")
@Validated
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
        if (transferManagerService.isAvailableTransfersEmpty()){
            logger.error("Error: There is no available transfers given");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(transferService.getMaximizedCostRoute());
    }
  
    @PostMapping("/inputRoutes")
    public ResponseEntity<Void> chosenRoute(@Valid @RequestBody TransferRequest request) {
        logger.info("POST request received at /api/transfers/inputRoutes");
        transferManagerService.prepareService();
        transferManagerService.manageService(request);
        return ResponseEntity.ok().build();
    }
}
