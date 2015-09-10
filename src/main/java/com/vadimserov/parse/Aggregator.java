package com.vadimserov.parse;


import com.vadimserov.parse.model.*;
import com.vadimserov.parse.view.HtmlView;

import java.io.IOException;

/**
 * Program used to find job vacancies on sites.
 * Search query looking for Kharkiv only.
 * Currently supported sites:
 * rabota.ua
 * hh.ua
 */
public class Aggregator {
    public static void main(String[] args) throws IOException {

/**
 Creating Html view of parse results
 Создаем html представление результата парсинга страницы
 */
        HtmlView view = new HtmlView();
        /**
         Creating provider that keeps reference to strategy needed for parse
         Создаем поставщика, который хранит в себе ссылку на стратегию нужную для парсинга
         */
        Provider HHstrategy = new Provider(new HHStrategy());
        Provider RUAStrategy = new Provider(new RUAStrategy());
        Provider rabotaKharkovStrategy = new Provider(new RabotaKharkovStrategy());

        /**
         * Creating model that contains reference for type of view that we use and providers to parse
         * Создаем модель, которая хранит ссылки на тип отображения и поставщиков которых будем парсить
         */

        Model model = new Model(view, HHstrategy, RUAStrategy, rabotaKharkovStrategy);

        /**
         * Setting controller for model
         * Устанавливаем контроллер для данной модели
         */
        Controller controller = new Controller(model);
        /**
         * Setting controller for our view
         * Устанавливаем контроллер для нашего отображения
         */
        view.setController(controller);
        /**
         * Setting params of search query for sites
         * Устанавливаем параметры для запроса сайтам
         */
        System.out.println("Wait a little, gathering info . . .");
        view.userSearchQueryInput("Java");
            System.out.println("Here is results: " +
                    "result.html");
            view.openFile();
        }
    }