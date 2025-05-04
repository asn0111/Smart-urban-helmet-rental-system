package com.asn.smarthelmetproject.service.Impl;

import com.asn.smarthelmetproject.model.Helmet;
import com.asn.smarthelmetproject.model.Location;
import com.asn.smarthelmetproject.repository.HelmetRepository;
import com.asn.smarthelmetproject.repository.LocationRepository;
import com.asn.smarthelmetproject.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepo;
    @Autowired private HelmetRepository helmetRepo;

    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }

    public List<Helmet> getAvailableHelmets(String locationName) {
        Location location = locationRepo.findByName(locationName).orElseThrow();
        return helmetRepo.findByLocationAndIsAvailableTrue(location);
    }
}
