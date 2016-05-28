package com.journaldev.main;

import com.journaldev.SourceType.SourceProcessor;
import com.journaldev.SourceType.TableTypeProcessor;
import com.journaldev.dao.PersonDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SpringHibernateMain {

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, ParseException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        PersonDAO personDAO = context.getBean(PersonDAO.class);

        ArrayList<SourceProcessor> processors = new ArrayList<>();

        processors.add(new TableTypeProcessor("https://jailtracker.com/JTClientWeb/%28S%28yvvsl12xsv02cfj0k3b0rque%29%29/JailTracker/GetInmateList?start=0&limit=10&sort=LastName&dir=ASC", personDAO));

        for (SourceProcessor processor : processors) {
            processor.process();
        }




        context.close();
//        directlyFromUrl();
    }


    private static void directlyFromUrl() throws IOException, ParserConfigurationException {
        String rawresult = Jsoup.connect("https://jailtracker.com/JTClientWeb/%28S%28yvvsl12xsv02cfj0k3b0rque%29%29/JailTracker/GetInmateList?start=0&limit=10&sort=LastName&dir=ASC").ignoreContentType(true).execute().body();

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

}