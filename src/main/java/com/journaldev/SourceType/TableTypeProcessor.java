package com.journaldev.SourceType;

import com.journaldev.dao.PersonDAO;
import com.journaldev.model.Person;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;

/**
 * Created by dromov on 27.05.2016.
 */
public class TableTypeProcessor implements SourceProcessor {
    private String sourceUrl;
    private PersonDAO dao;

    public TableTypeProcessor(String sourceUrl, PersonDAO dao) {
        this.sourceUrl = sourceUrl;
        this.dao = dao;
    }

    @Override
    public void process() throws IOException {
        String rawResult = Jsoup.connect(sourceUrl).ignoreContentType(true).execute().body();

        JSONObject jObject = new JSONObject(rawResult);
        JSONArray list = (JSONArray) jObject.get("data");
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            JSONObject element = (JSONObject) iterator.next();
            Person inmate = buildInmateFromJSONObject(element);

            dao.save(inmate);
        }
    }

    private Person buildInmateFromJSONObject(JSONObject element) {
        String firstName = (String) element.get("FirstName");
        String lastName = (String) element.get("LastName");
        String middleName = (String) element.get("MiddleName");
        String suffix = (String) element.get("Suffix");

        Date originalBookDateTime = null;
        try {
            originalBookDateTime = Date.valueOf((String) element.get("OriginalBookDateTime"));
        } catch (Exception e) {

        }
        Date finalReleaseDateTime = null;
        try {
            finalReleaseDateTime = Date.valueOf((String) element.get("FinalReleaseDateTime"));
        } catch (Exception e){

        }
        String jacket = (String) element.get("Jacket");
        int arrestNo = (int) element.get("ArrestNo");

        Person person = new Person(firstName, lastName, middleName, jacket, suffix, arrestNo, originalBookDateTime, finalReleaseDateTime);
        return person;
    }
}
