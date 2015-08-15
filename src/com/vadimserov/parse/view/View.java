package com.vadimserov.parse.view;

/**
 * Created by HP on 11.08.2015.
 */

import com.vadimserov.parse.Controller;
import com.vadimserov.parse.vo.Vacancy;

import java.util.List;

public interface View {
    /**
    Updating content of html file with vacancies
*   Обновление контента html файла с вакансиями
* */
    void update(List<Vacancy> vacancies);
    /**
    Setting controller for class that implements View
    Устанавливаем контроллер для класса реализующего интерфейс View
     */
    void setController(Controller controller);
}
