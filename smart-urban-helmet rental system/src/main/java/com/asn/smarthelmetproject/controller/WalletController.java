package com.asn.smarthelmetproject.controller;

import com.asn.smarthelmetproject.model.Wallet;
import com.asn.smarthelmetproject.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    // Endpoint to recharge wallet by phone number
    @PostMapping("/recharge")
    public ResponseEntity<String> rechargeWallet(@RequestParam String phoneNumber, @RequestParam double amount) {
        try {
            Wallet wallet = walletService.rechargeWallet(phoneNumber, amount);
            return ResponseEntity.status(200).body("Wallet recharged successfully. New balance: " + wallet.getBalance());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam Long userId) {
        Double balance = walletService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }
//    @PostMapping("/deductFare")
//    public ResponseEntity<String> deductFare(@RequestBody Map<String, Object> payload) {
//        Long userId = Long.parseLong(payload.get("userId").toString());
//        double fare = Double.parseDouble(payload.get("fare").toString());
//
//        boolean success = walletService.deductFare(userId, fare);
//
//        if (success) {
//            return ResponseEntity.ok("Fare deducted successfully");
//        } else {
//            return ResponseEntity.status(400).body("Insufficient balance");
//        }
//    }
}
