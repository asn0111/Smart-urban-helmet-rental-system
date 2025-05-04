package com.asn.smarthelmetproject.controller;

import com.asn.smarthelmetproject.model.Helmet;
import com.asn.smarthelmetproject.model.Location;
import com.asn.smarthelmetproject.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAll() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{name}/helmets")
    public List<Helmet> getHelmets(@PathVariable String name) {
        return locationService.getAvailableHelmets(name);
    }
}
