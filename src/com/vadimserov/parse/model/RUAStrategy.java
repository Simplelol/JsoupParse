package com.vadimserov.parse.model;

import com.vadimserov.parse.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 11.08.2015.
 */
public class RUAStrategy implements Strategy{
    public static final String URL_FORMAT = "http://rabota.ua/jobsearch/vacancy_list?regionId=21&keyWords=%s&pg=%d";
    // public static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java&area=115&page=%d";

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString,  page);
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36").referrer("http://rabota.ua/").timeout(5000).get();
        return doc;
    }
    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> list = new ArrayList<>();
        int page = 1;
        while(true){
            try
            {
                Document document = getDocument(searchString, page);
                Elements elements = document.getElementsByAttributeValue("class", "rua-g-clearfix");

                if(elements.size() == 0 || elements.size() == 4)
                    break;

                for (Element element : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("class", "t").text());
                    vacancy.setSalary("");
                    vacancy.setCity(element.getElementsByAttributeValue("class", "s").val());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("class", "s").text());
                    String split[] = vacancy.getCompanyName().split("•");
                    if (split.length >= 2) {
                        vacancy.setCompanyName(split[0]);
                        String fullNameWhereIsCompanyBased = "";
                        for (int i = 0; i < split.length - 1; i++) {
                            fullNameWhereIsCompanyBased += split[i + 1] + " ";
                        }
                        vacancy.setCity(fullNameWhereIsCompanyBased);
                    }
                    vacancy.setSiteName(document.title());
                    vacancy.setUrl("http://rabota.ua" + element.getElementsByAttributeValue("class", "t").attr("href"));
                    if (vacancy.getTitle().length() > 40) continue;

                    list.add(vacancy);
                }
                page++;
            }
            catch (IOException e)
            {
            }
        }
        return list;
    }
}
