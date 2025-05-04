package com.asn.smarthelmetproject.service.Impl;

import com.asn.smarthelmetproject.model.User;
import com.asn.smarthelmetproject.model.Wallet;
import com.asn.smarthelmetproject.repository.UserRepository;
import com.asn.smarthelmetproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // In-memory OTP store (for dev/test)
    private final Map<String, String> otpStore = new HashMap<>();

    @Override
    public User register(String phoneNumber, String name) {
        String otp = String.format("%05d", new Random().nextInt(100000)); // Generate 5-digit OTP

        System.out.println("Generated OTP for " + phoneNumber + ": " + otp);
        otpStore.put(phoneNumber, otp); // Store OTP temporarily
        return null; // No need to store name here, but store OTP for verification
    }

    @Override
    public User verifyOtp(String phoneNumber, String otp, String name) {
        String storedOtp = otpStore.get(phoneNumber);

        if (storedOtp == null || !storedOtp.equals(otp)) {
            return null; // Invalid OTP
        }

        // OTP is valid, proceed to save user with name
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setName(name);  // Set the name received during registration
        user.setVerified(true); // User is verified after successful OTP

        // Create wallet with zero balance
        user.setWallet(new Wallet(0.0, user));

        // Save the user with the name and phone number
        User savedUser = userRepository.save(user);
        otpStore.remove(phoneNumber); // Cleanup OTP after verification

        return savedUser;
    }

}