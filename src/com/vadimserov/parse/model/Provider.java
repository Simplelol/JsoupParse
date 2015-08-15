package com.vadimserov.parse.model;


import com.vadimserov.parse.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 29.07.2015.
 */
public class Provider {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Provider(Strategy strategy) {

        this.strategy = strategy;
    }
    public List<Vacancy> getJavaVacancies(String searchString){
        return strategy.getVacancies(searchString);
    }
}
