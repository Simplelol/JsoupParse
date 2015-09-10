package com.vadimserov.parse.model;

import com.vadimserov.parse.vo.Vacancy;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RabotaKharkovStrategy implements Strategy {
    private static final String URL_FORMAT = "http://www.rabota.kharkov.ua/poisk-v.php3?i=%d&p=10&showa=1&keywordsmode=1&vacancynotpay=1&s13b2k=%s&f_currency=1";
    private final static Logger logger = Logger.getLogger(RabotaKharkovStrategy.class);


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
                    if(!element.getElementsByAttributeValue("title", "Перейти на страницу вакансии").text().contains("Java")) continue;
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("title", "Перейти на страницу вакансии").text());
                    vacancy.setSalary("");
                    vacancy.setCity("Харьков");
                    vacancy.setCompanyName("");
                    vacancy.setSiteName(document.title());
                    vacancy.setUrl("http://www.rabota.kharkov.ua/" + element.getElementsByTag("a").attr("href"));
                    list.add(vacancy);
                }
                page = page + 10;
            }
            catch (IOException e)
            {
                logger.error(e);
                break;
            }
        }



        return list;
    }

}
