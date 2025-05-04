package com.asn.smarthelmetproject.service;

import com.asn.smarthelmetproject.model.Helmet;

import java.util.Map;

public interface HelmetService {
   // Helmet vendHelmet(Long userId, Long helmetId);
    void returnHelmet(String code);
    Map<String, Object> vendHelmet(Long userId); // Instead of requiring helmetId

}
