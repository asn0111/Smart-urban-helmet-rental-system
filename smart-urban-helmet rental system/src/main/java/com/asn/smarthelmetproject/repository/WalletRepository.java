package com.asn.smarthelmetproject.repository;

import com.asn.smarthelmetproject.model.Wallet;
import com.asn.smarthelmetproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user); // Find wallet by associated user
}
