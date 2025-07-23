package com.centraltaxis.service;

import com.centraltaxis.model.Taxi;
import java.util.ArrayList;
import java.util.List;

public class TaxiService {
    private List<Taxi> taxis = new ArrayList<>();

    public List<Taxi> findAllTaxis() {
        return taxis;
    }

    public void saveTaxi(Taxi taxi) {
        taxis.add(taxi);
    }
}