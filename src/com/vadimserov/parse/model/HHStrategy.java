package com.vadimserov.parse.model;

import com.vadimserov.parse.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HHStrategy implements Strategy{
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=%s&area=135&page=%d";

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT,searchString, page);
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (jsoup)").referrer("some text").timeout(5000).get();
        return doc;
    }
    @Override
    public List<Vacancy> getVacancies(String searchString)
    {
        List<Vacancy> list = new ArrayList<>();
        int page = 0;

        while(true)
        {
            try
            {
                Document document = getDocument(searchString, page);
                Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

                if(elements.size() == 0)
                    break;

                for (Element element : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setSalary(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setSiteName(document.title());
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));

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
