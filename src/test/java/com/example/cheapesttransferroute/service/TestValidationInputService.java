package com.example.cheapesttransferroute.service;

import com.example.cheapesttransferroute.model.Transfer;
import com.example.cheapesttransferroute.model.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class  TestValidationInputService {

    private ValidationInputService validationInputService;

    @BeforeEach
    public void setUp() {
        validationInputService = new ValidationInputService();
    }

    @Test
    public void testValidateTransferRequestValidInput() {
        Transfer validTransfer1 = new Transfer(10, 20);
        Transfer validTransfer2 = new Transfer(5, 15);
        TransferRequest validRequest = new TransferRequest(50, Arrays.asList(validTransfer1, validTransfer2));

        assertDoesNotThrow(() -> validationInputService.validateTransferRequest(validRequest));
    }

    @Test
    public void testValidateTransferRequestMaxWeightLessThanOne() {
        TransferRequest invalidRequest = new TransferRequest(0, Arrays.asList(new Transfer(10, 20)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validationInputService.validateTransferRequest(invalidRequest));
        assert exception.getMessage().equals("Maximum weight must be an integer and at least 1");
    }

    @Test
    public void testValidateTransferRequestEmptyTransfersList() {
        TransferRequest invalidRequest = new TransferRequest(10, Collections.emptyList());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validationInputService.validateTransferRequest(invalidRequest));
        assert exception.getMessage().equals("Available transfers list cannot be empty");
    }

    @Test
    public void testValidateTransferRequestInvalidTransferWeight() {
        Transfer invalidTransfer = new Transfer(0, 10);
        TransferRequest invalidRequest = new TransferRequest(10, Arrays.asList(invalidTransfer));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validationInputService.validateTransferRequest(invalidRequest));
        assert exception.getMessage().equals("Weight must be an integer and at least 1");
    }

    @Test
    public void testValidateTransferRequestInvalidTransferCost() {
        Transfer invalidTransfer = new Transfer(5, -10);
        TransferRequest invalidRequest = new TransferRequest(10, Arrays.asList(invalidTransfer));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validationInputService.validateTransferRequest(invalidRequest));
        assert exception.getMessage().equals("Cost must be an integer and at least 1");
    }
}
