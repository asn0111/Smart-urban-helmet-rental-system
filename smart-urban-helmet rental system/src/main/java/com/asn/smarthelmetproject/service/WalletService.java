package com.asn.smarthelmetproject.service;

import com.asn.smarthelmetproject.model.Wallet;
public interface WalletService {
    void topUp(Long userId, double amount);
    boolean deductFare(Long userId, double fare);
    double getBalance(Long userId);
    Wallet rechargeWallet(String phoneNumber, double amount);

}

//    Wallet getWalletByUserId(Long userId);
//    Wallet rechargeWallet(Long userId, double amount);
//    double getBalance(Long userId);
//}
