package com.vadimserov.parse.view;

/**
 * Created by HP on 11.08.2015.
 */

import com.vadimserov.parse.Controller;
import com.vadimserov.parse.vo.Vacancy;
import org.apache.log4j.Logger;
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
    /**Path to the html file with parsing results.
     * Use this path if you run project from .jar file
     * Путь к html странице с результатом парсинга
     * Используйте этот путь если вы запускаете программу через .jar файл.
     * */


    /**
     * Use this path if you run project from IDE
     * Испольуйте этот путь если запускаете проект с вашей среды разработки.
     */
    private final static Logger logger = Logger.getLogger(HtmlView.class);
    private final String backup = "./src/main/java/" + this.getClass().getPackage().getName().replace('.', '/') + "/backup.html";
    private final String filePath = "./src/main/java/" + this.getClass().getPackage().getName().replace('.', '/') + "/result.html";

    private final String document = "<!DOCTYPE html>\n" +
            "<html lang=\"ru\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Вакансии</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<table>\n" +
            "    <tr>\n" +
            "        <th>Title</th>\n" +
            "        <th>City</th>\n" +
            "        <th>Company Name</th>\n" +
            "        <th>Salary</th>\n" +
            "    </tr>\n" +
            "    <tr class=\"vacancy template\" style=\"display: none\">\n" +
            "        <td class=\"title\"><a href=\"url\"></a></td>\n" +
            "        <td class=\"city\"></td>\n" +
            "        <td class=\"companyName\"></td>\n" +
            "        <td class=\"salary\"></td>\n" +
            "    </tr>\n" +
            "</table>\n" +
            "</body>\n" +
            "</html>/";
    public void openFile()  {
        try {
            File queryResult = new File ("result.html");
           // File queryResult = new File(filePath.replace("./", ""));
            Desktop.getDesktop().browse(queryResult.toURI());
            logger.info("Program success! Result opened in browser.");
        }catch (IOException e){
            logger.fatal("Can't open vacanices file to show in browser.", e);
            System.exit(-1);
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
            Document doc = Jsoup.parse(document, "UTF-8");

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
        return fileContent;
    }

    /**
     * Writes result of parse to file
     * Записывает результат парсинга в файл
     * @param fileContent String to be written
     */
    private void updateFile(String fileContent) {
        try {
            File f = new File("result.html");
            if (!f.exists())
                f.createNewFile();
            BufferedWriter fWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF8"));
            fWriter.write(fileContent);
            fWriter.close();
        }
        catch (IOException e) {
            logger.error("Can't write update to html file", e);
            System.exit(-1);
        }
    }

    /**
     * Возвращает документ, который должен хранить результат парсинга
     * @return Document used to keep result of parsing
     * @throws IOException if file does not exists
     */
    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(backup), "UTF-8");
    }
}