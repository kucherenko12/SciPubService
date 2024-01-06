package com.example.scipubwatch.logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class GetHTML
{
    public static String getHtmlString(String url) throws IOException
    {
        String html = Jsoup.connect(url).get().html();
        return(html);
    }

    public static Document getHtmlDocument(String url) throws IOException
    {
        Document doc = Jsoup.connect(url).get();
        //
        //String title = doc.title();
        //System.out.println("title is: " + title);
        //
        return(doc);
    }
}
