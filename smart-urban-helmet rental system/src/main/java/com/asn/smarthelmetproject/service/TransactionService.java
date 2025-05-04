package com.asn.smarthelmetproject.service;

import com.asn.smarthelmetproject.model.Transaction;

public interface TransactionService {
    Transaction createTransaction(Long userId, Long helmetId);
    Transaction startTransaction(String code);
    Transaction endTransaction(String code);
    Transaction getTransactionByCode(String code); // Optional utility
}
