package com.asn.smarthelmetproject.service.Impl;

import com.asn.smarthelmetproject.model.User;
import com.asn.smarthelmetproject.model.Wallet;
import com.asn.smarthelmetproject.repository.UserRepository;
import com.asn.smarthelmetproject.repository.WalletRepository;
import com.asn.smarthelmetproject.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void topUp(Long userId, double amount) {
        // Fetch the user and then the associated wallet
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance() + amount);
        walletRepo.save(wallet);
    }

    @Override
    public boolean deductFare(Long userId, double fare) {
        // Fetch the user and then the associated wallet
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() >= fare) {
            wallet.setBalance(wallet.getBalance() - fare);
            walletRepo.save(wallet);
            return true;
        }
        return false;
    }

    @Override
    public double getBalance(Long userId) {
        // Fetch the user and then the associated wallet
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return wallet.getBalance();
    }

    // Add method to recharge wallet using phone number
    public Wallet rechargeWallet(String phoneNumber, double amount) {
        // Fetch the user based on phone number
        User user = userRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch or create the associated wallet for the user
        Wallet wallet = walletRepo.findByUser(user)
                .orElseGet(() -> {
                    Wallet newWallet = new Wallet(0.0, user);
                    return walletRepo.save(newWallet);
                });

        wallet.setBalance(wallet.getBalance() + amount);
        walletRepo.save(wallet);
        return wallet;
    }
}
