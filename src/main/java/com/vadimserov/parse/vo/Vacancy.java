package com.vadimserov.parse.vo;

/**
 * Created by HP on 29.07.2015.
 */

/**
Objects of this class will contain result of parse
Class does not contains any methods except get and set
Объекты этого класа будут содержать результат парсинга страницы
Класс не содержит никаких методов кроме геттеров и сэттеров
 */
public class Vacancy {
    String title;
    String salary;
    String city;
    String companyName;
    String siteName;
    String url;



    public void setCity(String city) {
        this.city = city;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getCity() {

        return city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSalary() {
        return salary;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
