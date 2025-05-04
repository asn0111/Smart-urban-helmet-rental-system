package com.asn.smarthelmetproject.controller;

import com.asn.smarthelmetproject.model.User;
import com.asn.smarthelmetproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final double MIN_BALANCE = 50.0;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam String phoneNumber, @RequestParam String name) {
        userService.register(phoneNumber, name); // Send OTP and store temporarily
        return ResponseEntity.ok("OTP sent to console for " + phoneNumber + ".");
    }
    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp, @RequestParam String name) {
        User user = userService.verifyOtp(phoneNumber, otp, name);

        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }

        double balance = user.getWallet().getBalance();
        if (balance < MIN_BALANCE) {
            return ResponseEntity.badRequest().body("Insufficient balance. Please recharge your wallet with at least ₹" + MIN_BALANCE);
        }

        return ResponseEntity.ok("User verified and saved successfully.");
    }

}
