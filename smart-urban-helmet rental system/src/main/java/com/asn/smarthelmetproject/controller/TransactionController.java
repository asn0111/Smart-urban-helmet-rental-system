package com.asn.smarthelmetproject.controller;

import com.asn.smarthelmetproject.model.Transaction;
import com.asn.smarthelmetproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Step 2: Unlock via code - starts the rental timer
    @PostMapping("/start")
    public ResponseEntity<String> startTransaction(@RequestParam String code) {
        transactionService.startTransaction(code);
        return ResponseEntity.ok("Transaction started for code: " + code);
    }

    // Optional: Return transaction status
    @GetMapping("/status")
    public ResponseEntity<?> getTransactionStatus(@RequestParam String code) {
        return ResponseEntity.ok(transactionService.getTransactionByCode(code));
    }
}
