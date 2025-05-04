package com.asn.smarthelmetproject.service.Impl;

import com.asn.smarthelmetproject.model.Helmet;
import com.asn.smarthelmetproject.model.Transaction;
import com.asn.smarthelmetproject.model.User;
import com.asn.smarthelmetproject.repository.HelmetRepository;
import com.asn.smarthelmetproject.repository.TransactionRepository;
import com.asn.smarthelmetproject.repository.UserRepository;
import com.asn.smarthelmetproject.service.TransactionService;
import com.asn.smarthelmetproject.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final double FARE_PER_MINUTE = 0.5;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HelmetRepository helmetRepository;

    @Autowired
    private WalletService walletService;

    @Override
    public Transaction createTransaction(Long userId, Long helmetId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
        Helmet helmet = helmetRepository.findById(helmetId).orElseThrow(() -> new RuntimeException("Helmet not found."));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setHelmet(helmet);
        transaction.setStartTime(null); // will be set when ride starts
        transaction.setCode(generateUnique5DigitCode());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction startTransaction(String code) {
        Transaction tx = transactionRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid unlock code."));
        tx.setStartTime(LocalDateTime.now());
        return transactionRepository.save(tx);
    }

    @Override
    public Transaction endTransaction(String code) {
        Transaction tx = transactionRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid return code."));

        if (tx.getStartTime() == null) {
            throw new RuntimeException("Transaction has not been started.");
        }

        LocalDateTime endTime = LocalDateTime.now();
        tx.setEndTime(endTime);

        long minutes = Duration.between(tx.getStartTime(), endTime).toMinutes();
        double fare = minutes * FARE_PER_MINUTE;
        tx.setFare(fare);

        walletService.deductFare(tx.getUser().getId(), fare);
        return transactionRepository.save(tx);
    }

    @Override
    public Transaction getTransactionByCode(String code) {
        return transactionRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("No transaction found with code: " + code));
    }

    private String generateUnique5DigitCode() {
        Random random = new Random();
        String code;
        do {
            code = String.format("%05d", random.nextInt(100000));
        } while (transactionRepository.existsByCode(code));
        return code;
    }
}
