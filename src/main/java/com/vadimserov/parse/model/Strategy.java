package com.vadimserov.parse.model;



import com.vadimserov.parse.vo.Vacancy;

import java.util.List;

public interface Strategy {
    /**
     * Возвращает список вакансий
     * @param searchString String to be search query
     * @return List of vacancies
     */
     List<Vacancy> getVacancies(String searchString);
}
