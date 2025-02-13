package com.example.cheapesttransferroute.controller;

import com.example.cheapesttransferroute.model.RouteResult;
import com.example.cheapesttransferroute.model.TransferRequest;
import com.example.cheapesttransferroute.service.TransferService;
import com.example.cheapesttransferroute.service.TransferManagerService;
import com.example.cheapesttransferroute.service.ValidationInputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling transfer-related API endpoints.
 */
@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final TransferService transferService;
    private final TransferManagerService transferManagerService;
    private final ValidationInputService validationInputService;

    /**
     * Constructor for dependency injection of services
     *
     * @param transferService service for transfer operations
     * @param transferManagerService service for managing transfers
     * @param validationInputService service for validating input requests
     */
    @Autowired
    public TransferController(TransferService transferService, TransferManagerService transferManagerService, ValidationInputService validationInputService) {
        this.transferService = transferService;
        this.transferManagerService = transferManagerService;
        this.validationInputService = validationInputService;
    }

    /**
     * Endpoint to retrieve the route with maximized cost
     *
     * @return ResponseEntity containing the route result or a NOT_FOUND status if no transfers are available
     */
    @GetMapping("/getRoutes")
    public ResponseEntity<RouteResult> getMaximizedCostRoute() {
        logger.info("GET request received at /api/transfers/getRoute");
        if (transferManagerService.isAvailableTransfersEmpty()){
            logger.error("Error: There is no available transfers given");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(transferService.getMaximizedCostRoute());
    }

    /**
     * Endpoint to process and manage a chosen route based on the provided request
     *
     * @param request client given request containing route details
     * @return ResponseEntity with an OK status if request is valid
     */
    @PostMapping("/inputRoutes")
    public ResponseEntity<Void> chosenRoute(@RequestBody TransferRequest request) {
        logger.info("POST request received at /api/transfers/inputRoutes");
        validationInputService.validateTransferRequest(request);
        transferManagerService.prepareService();
        transferManagerService.manageService(request);
        return ResponseEntity.ok().build();
    }
}
