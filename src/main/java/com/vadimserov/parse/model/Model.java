package com.vadimserov.parse.model;


import com.vadimserov.parse.view.View;
import com.vadimserov.parse.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores data that is retrieved to the controller and displayed in the view.
 * Whenever there is a change to the data it is updated by the controller.
 * Предоставляет знания: данные и методы работы с этими данными, реагирует на запросы,
 * изменяя своё состояние. Не содержит информации, как эти знания можно визуализировать.
 */
public class Model {
    private View view;
    private Provider[] providers;

    public Model(View view, Provider...providers) {
        if (providers.length == 0 || view == null || providers == null)
            throw new IllegalArgumentException();
        this.providers = providers;
        this.view = view;

    }
    /**
     * Gathers all vacancies and send them in view.
     * Собирает все вакансии и отправляет для отображения.
     * @param searchString - String search query
     */
    public void selectQuery(String searchString){
        List<Vacancy> vacancies = new ArrayList<>();
        for (Provider provider :providers)
            vacancies.addAll(provider.getJavaVacancies(searchString));
        view.update(vacancies);

    }
}

