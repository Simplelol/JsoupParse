package com.vadimserov.parse;

import com.vadimserov.parse.model.Model;


/**
 * Controller contains methods for work with user
 * Контроллер содержит методы для работы с юзером
 */
public class Controller {
    Model model;


    public Controller(Model model) {
        if (model == null) throw new IllegalArgumentException();
        this.model = model;
    }
    public void querySelect(String cityName){
        model.selectQuery(cityName);
    }
}
