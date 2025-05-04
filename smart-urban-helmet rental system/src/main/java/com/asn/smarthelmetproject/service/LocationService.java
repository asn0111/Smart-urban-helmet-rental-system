package com.asn.smarthelmetproject.service;

import com.asn.smarthelmetproject.model.Helmet;
import com.asn.smarthelmetproject.model.Location;

import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();
    List<Helmet> getAvailableHelmets(String locationName);
}