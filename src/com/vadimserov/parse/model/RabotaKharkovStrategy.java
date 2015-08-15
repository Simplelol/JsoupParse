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
public class RabotaKharkovStrategy implements Strategy {
    private static final String URL_FORMAT = "http://www.rabota.kharkov.ua/poisk-v.php3?i=%d&s13b2k=%s";

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, page, searchString);
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (jsoup)").referrer("rabota.kharkov.ua").timeout(5000).get();
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
                Elements elements = document.getElementsByAttributeValue("class", "sptitle");

                if(elements.size() == 0)
                    break;

                for (Element element : elements)
                {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("title", "Перейти на страницу вакансии").text());
                    //vacancy.setSalary(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").text());
                    vacancy.setSalary("");
                    //vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setCity("Харьков");
                    //vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setCompanyName("");
                    vacancy.setSiteName(document.title());
                    // vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                    vacancy.setUrl("http://www.rabota.kharkov.ua/" + element.getElementsByTag("a").attr("href"));
                    list.add(vacancy);
                }
                page = page + 10;
            }
            catch (IOException e)
            {
            }
        }



        return list;
    }

}
