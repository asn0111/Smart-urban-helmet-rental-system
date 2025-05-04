package com.asn.smarthelmetproject.controller;

import com.asn.smarthelmetproject.model.Helmet;
import com.asn.smarthelmetproject.service.HelmetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/helmets")
public class HelmetController {

    @Autowired
    private HelmetService helmetService;

    // Step 1: Vend a helmet and return 5-digit unlock code
    @PostMapping("/vend")
    public ResponseEntity<?> vendHelmet(@RequestParam Long userId) {
        try {
            Map<String, Object> result = helmetService.vendHelmet(userId); // handles both helmet + transaction
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Step 3: Return helmet using the code
    @PostMapping("/return")
    public ResponseEntity<?> returnHelmet(@RequestParam String code) {
        try {
            helmetService.returnHelmet(code); // internally ends transaction
            return ResponseEntity.ok(Map.of("message", "Helmet returned successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
