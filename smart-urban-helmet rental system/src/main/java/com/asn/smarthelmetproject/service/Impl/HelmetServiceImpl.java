package com.asn.smarthelmetproject.service.Impl;

import com.asn.smarthelmetproject.model.Helmet;
import com.asn.smarthelmetproject.model.Transaction;
import com.asn.smarthelmetproject.repository.HelmetRepository;
import com.asn.smarthelmetproject.service.HelmetService;
import com.asn.smarthelmetproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class HelmetServiceImpl implements HelmetService {

    @Autowired
    private HelmetRepository helmetRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Map<String, Object> vendHelmet(Long userId) {
        Optional<Helmet> availableHelmet = helmetRepository.findFirstByIsAvailableTrue();
        if (availableHelmet.isEmpty()) {
            throw new RuntimeException("No helmets available at this time.");
        }

        availableHelmet.get().setAvailable(false); // reserve
        helmetRepository.save(availableHelmet.get());


        Transaction transaction = transactionService.createTransaction(userId, availableHelmet.get().getId());

        // TODO: Send unlock signal to ESP32 for this helmet
        System.out.println("Unlock helmet ID " + availableHelmet.get().getId() + " for user " + userId);

        Map<String, Object> result = new HashMap<>();
        result.put("helmetId", availableHelmet.get().getId());
        result.put("code", transaction.getCode());
        return result;
    }

    @Override
    public void returnHelmet(String code) {
        // TODO: Validate RFID and weight here
        // For now, assume it's valid

        // End transaction, deduct fare, mark helmet as available
        Transaction tx = transactionService.endTransaction(code);

        Helmet helmet = tx.getHelmet();
        helmet.setAvailable(true);
        helmetRepository.save(helmet);

//         TODO: Lock box via ESP32 signal
        System.out.println("Helmet ID " + helmet.getId() + " returned and locked.");
    }
}
