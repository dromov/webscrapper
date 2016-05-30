package com.scrapper.SourceType;

import com.scrapper.dao.InmateDAO;
import com.scrapper.model.Inmate;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;

/**
 * Created by dromov on 27.05.2016.
 */
public class TableTypeProcessor implements ScrapperProcessor {
    private String sourceUrl;
    private InmateDAO dao;

    private static final Logger logger = LoggerFactory.logger(TableTypeProcessor.class);

    public TableTypeProcessor(String sourceUrl, InmateDAO dao) {
        this.sourceUrl = sourceUrl;
        this.dao = dao;
    }

    @Override
    public void process() {
        String rawResult = null;
        try {
            rawResult = Jsoup.connect(sourceUrl).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            logger.error("Cann't connect to destination url " + sourceUrl);
        }

        JSONObject jObject = new JSONObject(rawResult);
        JSONArray list = (JSONArray) jObject.get("data");
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            JSONObject element = (JSONObject) iterator.next();
            Inmate inmate = buildInmateFromJSONObject(element);

            dao.save(inmate);
        }
    }

    private Inmate buildInmateFromJSONObject(JSONObject element) {
        String firstName = (String) element.get("FirstName");
        String lastName = (String) element.get("LastName");
        String middleName = (String) element.get("MiddleName");
        String suffix = (String) element.get("Suffix");

        Date originalBookDateTime = null;
        try {
            originalBookDateTime = Date.valueOf((String) element.get("OriginalBookDateTime"));
        } catch (Exception e) {
            logger.error("Invalid date formst in the source table");
        }
        Date finalReleaseDateTime = null;
        try {
            finalReleaseDateTime = Date.valueOf((String) element.get("FinalReleaseDateTime"));
        } catch (Exception e){
            logger.error("Invalid date formst in the source table");
        }
        String jacket = (String) element.get("Jacket");
        int arrestNo = (int) element.get("ArrestNo");

        Inmate inmate = new Inmate(firstName, lastName, middleName, jacket, suffix, arrestNo, originalBookDateTime, finalReleaseDateTime);
        return inmate;
    }
}
