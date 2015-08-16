package com.vadimserov.parse.view;

/**
 * Created by HP on 11.08.2015.
 */

import com.vadimserov.parse.Controller;
import com.vadimserov.parse.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.*;
import java.io.*;
import java.util.List;

/** Creating html page with all vacancies on it
    Создаю html страницу со всеми вакансиями
 */
public class HtmlView implements View {
    private Controller controller;
    /**Path to the html file with parsing results
    * Путь к html странице с результатом парсинга*/
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replace('.', '/') + "/result.html";
   // private final String filePath = "result.html";
    public void openFile()  {
        try {
            File queryResult = new File(filePath, "UTF-8");
            Desktop.getDesktop().browse(queryResult.toURI());
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**Updating content of html file with vacancies
    * Обновление контента html файла с вакансиями*/
    @Override
    public void update(List<Vacancy> vacancies) {
        updateFile(getUpdatedFileContent(vacancies));
    }

    public void userSearchQueryInput(String stringQuery) {
        controller.onCitySelect(stringQuery);
    }

    /**
     * Returns string with all parsed vacancies
     * Возвращает строку со всеми вакансиями
     * @param vacancies list to be used for updating
     * @return String with all parsed vacancies
     */
    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        String fileContent = null;
        try {
            Document doc = getDocument();


            Element templateElement = doc.select(".template").first();
            Element patternElement = templateElement.clone();
            patternElement.removeAttr("style");
            patternElement.removeClass("template");
            doc.select("tr[class=vacancy]").remove();



            for (Vacancy vacancy : vacancies) {
                Element newVacancyElement = patternElement.clone();
                newVacancyElement.getElementsByClass("city").first().text(vacancy.getCity());
                newVacancyElement.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                newVacancyElement.getElementsByClass("salary").first().text(vacancy.getSalary());
                Element link = newVacancyElement.getElementsByTag("a").first();
                link.text(vacancy.getTitle());
                link.attr("href", vacancy.getUrl());

                templateElement.before(newVacancyElement.outerHtml());
            }
            fileContent = doc.html();
        }
        catch (IOException e) {
            e.printStackTrace();
            fileContent = "Some exception occurred";
        }
        return fileContent;
    }

    /**
     * Writes result of parse to file
     * Записывает результат парсинга в файл
     * @param fileContent String to be written
     */
    private void updateFile(String fileContent) {
        try {
            BufferedWriter fWriter = new BufferedWriter(new FileWriter(filePath));
            fWriter.write(fileContent);
            fWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает документ, который должен хранить результат парсинга
     * @return Document used to keep result of parsing
     * @throws IOException if file does not exists
     */
    protected Document getDocument() throws IOException {
 /*       File f = new File("result.html");
        f.createNewFile();*/
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}