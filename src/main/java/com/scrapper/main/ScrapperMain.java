package com.scrapper.main;

import com.scrapper.SourceType.ImageScrapperProcessor;
import com.scrapper.SourceType.ScrapperProcessor;
import com.scrapper.SourceType.TableTypeProcessor;
import com.scrapper.configuration.AppConfig;
import com.scrapper.dao.InmateDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class ScrapperMain {

    @Autowired
    private Environment environment;

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        InmateDAO personDAO = (InmateDAO) context.getBean("inmateDao");

        ArrayList<ScrapperProcessor> processors = new ArrayList<>();

        processors.add(new TableTypeProcessor("https://jailtracker.com/JTClientWeb/(S(sh4cuafck0hejs45zl5ch1m0))/JailTracker/GetInmateList?start=0&limit=10&sort=LastName&dir=ASC", personDAO));
        processors.add(new ImageScrapperProcessor("https://www.parentsformeganslaw.org/", "/Users/dromov/Documents/PHOTO/"));

        for (ScrapperProcessor processor : processors) {
            processor.process();
        }

        context.close();
    }


    private static void directlyFromUrl() throws IOException, ParserConfigurationException {
        String rawresult = Jsoup.connect("https://www.parentsformeganslaw.org").ignoreContentType(true).execute().body();

        JSONObject jObject = new JSONObject(rawresult);
        JSONArray list = (JSONArray) jObject.get("data"); Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            JSONObject element = (JSONObject) iterator.next();
            String lastName = (String) element.get("LastName");
            String middleName = (String) element.get("MiddleName");
            String OriginalBookDateTime = (String) element.get("OriginalBookDateTime");
            String finalReleaseDateTime = (String) element.get("FinalReleaseDateTime");
            String jacket = (String) element.get("Jacket");
            String arrestNo = (String) element.get("ArrestNo");
        }
    }

    public static void directFromUrl2() throws Exception {
        URL oracle = new URL("https://www.parentsformeganslaw.org");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }

}