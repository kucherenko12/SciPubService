package com.example.scipubwatch.logic;

import com.example.scipubwatch.entities.Publication;
import com.example.scipubwatch.entities.Teacher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PublicationManipulation
{
    // есть 2 варианта. Считывать параметры при нажатии кнопки поиск или обновлят их динамично
    // каждый раз, когда их меняют и не заводить параметы из запроса, а считывать уже сохраненные из кэша здесь.
    // МОЕ РЕШЕНИЕ: Скорее всего второй вариант.

    // Как реализовать многопоточность? ОТВЕТ: Tomcat сам обеспечит многопоточность

    // -----------------------------------------------------------------------------------------------------------------

    // Как реализовать кэш? !!!!!!!!!!!!!!!

    // Как загружать публикации, с сотрировкой уже загруженніх из html или подавать запросы для сортровки и что бы
    // гугл сколар выдавал уже отсортированые? ОТВЕТ: Лучше сорторовать на сервере.

    // -----------------------------------------------------------------------------------------------------------------

    // Решить вопрос как собрать данные из поля establishment.
    // Скорее всего нужно будет сделать объект "Источник"
    // переписать карточку что бы недели и табличка недель совпадали




    public List<Publication> findPublicationsFast(Teacher teacher, String scrapeType) throws IOException
    {
        List<Publication> foundPublications = new ArrayList<>();

        //Document document =  GetHTML.getHtmlDocument(dao.findTeacher("").get(0).link);
        String addToLink = "";
        if(scrapeType.equals("date")) {addToLink = "&view_op=list_works&sortby=pubdate";}
        Document document =  GetHTML.getHtmlDocument(teacher.getLinkGoogleScholar() + addToLink);

        Elements headerElements = document.getElementsByClass("gsc_a_at");
        Elements authorAndPlaceElements = document.getElementsByClass("gs_gray");
        Elements yearElements = document.getElementsByClass("gsc_a_hc");
        Elements quotationsElements = document.getElementsByClass("gsc_a_ac");

        int id = 0;
        for (Element headerElement : headerElements)
        {
            if(Objects.equals(teacher.surnameNamePatronym[0], "Букасов") && id >= 10) {break;}
            //

            String headerString = headerElement.toString();
            String header = headerString.substring(headerString.indexOf('>') + 1, headerString.lastIndexOf('<'));

            String[] authors = new String[3];
            String authorsString = authorAndPlaceElements.get(id*2).toString();
            if(!authorsString.equals("<div class=\"gs_gray\"></div>"))
            {
                authors = authorsString.substring
                        (authorsString.indexOf('>') + 3, authorsString.lastIndexOf('<') - 1).split(",");
            }
            //String clearAuthorsString
            //        = authorsString.substring(authorsString.indexOf('>') + 3, authorsString.lastIndexOf('<') - 1);
            //if(!clearAuthorsString.equals("")) { authors = clearAuthorsString.split(","); }

            Integer year = null;
            String yearString = yearElements.get(id).toString();
            String clearYearString = yearString.substring(yearString.indexOf('>') + 1, yearString.lastIndexOf('<'));
            if(!clearYearString.equals("")) { year = Integer.parseInt(clearYearString); }

            String placeAndDate = authorAndPlaceElements.get(id*2 + 1).toString();
            String placeAndDateCut1 = placeAndDate.substring(placeAndDate.indexOf('>') + 1, placeAndDate.lastIndexOf('<'));
            String place = null;
            String sourceData = placeAndDateCut1;
            if(!placeAndDateCut1.equals(""))
            {
                String placeAndDateCut2 = placeAndDateCut1.substring(2, placeAndDateCut1.lastIndexOf('<'));
                place = placeAndDateCut2.substring(0, placeAndDateCut2.indexOf('<'));
                //year = Integer.parseInt(placeAndDateCut2.substring(placeAndDateCut2.length() - 4, placeAndDateCut2.length()));
                sourceData = place + ", " + year;
            }
            //else { place = null;}
            //System.out.println(header);
            foundPublications.add(new Publication(authors, header, place, sourceData,
                    null, year, null));
            id++;
        }

        return foundPublications;
    }

    public List<Publication> findPublications(Teacher teacher, String scrapeType) throws IOException
    {
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        List<Publication> foundPublications = new ArrayList<>();

        ScraperService scraperService = new ScraperService
                ("E:/D Programms/Mozilla Firefox/Firefox.exe",
                        "E://D_programming/RuntimeProgramms/WebDriver/geckodriver-v0.33.0-win64/geckodriver.exe",
                        20);
        String addToLink = "";
        if(scrapeType.equals("date")) {addToLink = "&view_op=list_works&sortby=pubdate";}
        scraperService.getUrl(teacher.getLinkGoogleScholar() + addToLink);
        scraperService.clickButtonById("gsc_bpf_more");
        if(scraperService.scrape(ScraperService.FindType.FIND_ELEMENT_BY_CLASS_NAME, "gsc_a_at").length == 20)
        {
            scraperService.waitUntilNumberOfElementsToBeMoreThan(ScraperService.FindType.FIND_ELEMENT_BY_CLASS_NAME, "gsc_a_at", 20);
        }

        //Document document =  GetHTML.getHtmlDocument(dao.findTeacher("").get(0).link);
        //Document document =  GetHTML.getHtmlDocument(teacher.getLink());

        String[] headers = scraperService.scrape(ScraperService.FindType.FIND_ELEMENT_BY_CLASS_NAME, "gsc_a_at");
        String[] authorsAndPlaces = scraperService.scrape(ScraperService.FindType.FIND_ELEMENT_BY_CLASS_NAME, "gs_gray");
        String[] years = scraperService.scrape(ScraperService.FindType.FIND_ELEMENT_BY_CLASS_NAME, "gsc_a_hc");

        scraperService.stopFirefoxDriver();

        int id = 0;
        for (String header : headers)
        {
            System.out.println(header);
            //
            if(Objects.equals(teacher.surnameNamePatronym[0], "Букасов") && id >= 10) {break;}
            //

            String[] authors = authorsAndPlaces[id*2].split(",");
            String place = authorsAndPlaces[id*2 + 1];
            int year;
            if(years[id].equals("")) {year = 0;}
            else {year = Integer.parseInt(years[id]);}
            foundPublications.add(new Publication(authors, header, place, null, null, year, null));
            id++;
        }

        System.out.println("foundPublications length = " + foundPublications.size());
        return foundPublications;
    }

    public List<Publication> findPublicationLinks(Teacher teacher, String scrapeType) throws IOException {
        List<Publication> foundPublications = new ArrayList<>();

        //Document document =  GetHTML.getHtmlDocument(dao.findTeacher("").get(0).link);
        String addToLink = "";
        if(scrapeType.equals("date")) {addToLink = "&view_op=list_works&sortby=pubdate";}
        Document document =  GetHTML.getHtmlDocument(teacher.getLinkGoogleScholar() + addToLink);

        Elements headerElements = document.getElementsByClass("gsc_a_at");
        Elements authorAndPlaceElements = document.getElementsByClass("gs_gray");

        int id = 0;
        for (Element headerElement : headerElements)
        {
            if(Objects.equals(teacher.surnameNamePatronym[0], "Букасов") && id >= 10) {break;}
            //

            String headerString = headerElement.toString();
            String header = headerString.substring(headerString.indexOf('>') + 1, headerString.lastIndexOf('<'));

            String authorsString = authorAndPlaceElements.get(id*2).toString();
            String[] authors = authorsString.substring(authorsString.indexOf('>') + 3, authorsString.lastIndexOf('<') - 1).split(",");

            String placeAndDate = authorAndPlaceElements.get(id*2 + 1).toString();
            String placeAndDateCut1 = placeAndDate.substring(placeAndDate.indexOf('>') + 1, placeAndDate.lastIndexOf('<'));
            String placeAndDateCut2 = placeAndDateCut1.substring(2, placeAndDateCut1.lastIndexOf('<'));
            String place = placeAndDateCut2.substring(0, placeAndDateCut2.indexOf('<'));
            int year = Integer.parseInt(placeAndDateCut2.substring(placeAndDateCut2.length() - 4, placeAndDateCut2.length()));
            System.out.println(header);
            foundPublications.add(new Publication(authors, header, place, null, null, year, null));
            id++;
        }


        return foundPublications;
    }
}
