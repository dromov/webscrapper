package com.journaldev.SourceType;

import com.journaldev.dao.PersonDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.context.support.AbstractXmlApplicationContext;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by dromov on 27.05.2016.
 */
public class ImageTypeProcessor implements SourceProcessor {
    private String sourceUrl;
    private AbstractXmlApplicationContext context;
    private PersonDAO dao;

    public ImageTypeProcessor(String sourceUrl, AbstractXmlApplicationContext context, PersonDAO dao) {
        this.sourceUrl = sourceUrl;
        this.context = context;
        this.dao = dao;
    }

    @Override
    public void process() throws IOException {
        String rawResult = Jsoup.connect(sourceUrl).ignoreContentType(true).execute().body();

        JSONObject jObject = new JSONObject(rawResult);
        JSONArray list = (JSONArray) jObject.get("data"); Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            JSONObject element = (JSONObject) iterator.next();
        }

    }
}
