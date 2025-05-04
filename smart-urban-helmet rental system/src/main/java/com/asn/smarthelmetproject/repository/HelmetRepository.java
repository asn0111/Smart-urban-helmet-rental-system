package com.asn.smarthelmetproject.repository;


import com.asn.smarthelmetproject.model.Helmet;
import com.asn.smarthelmetproject.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HelmetRepository extends JpaRepository<Helmet, Long> {
    List<Helmet> findByLocationAndIsAvailableTrue(Location location);
    Optional<Helmet> findFirstByIsAvailableTrue();

}