package com.scrapper;

import com.scrapper.configuration.AppConfig;
import com.scrapper.dao.InmateDAO;
import com.scrapper.processor.ImageScrapperProcessor;
import com.scrapper.processor.ScrapperProcessor;
import com.scrapper.processor.TableTypeProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;

public class ScrapperExecutable {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        InmateDAO personDAO = (InmateDAO) context.getBean("inmateDao");

        ArrayList<ScrapperProcessor> processors = new ArrayList<>();

//        processors.add(new TableTypeProcessor("https://jailtracker.com/JTClientWeb/(S(sh4cuafck0hejs45zl5ch1m0))/JailTracker/GetInmateList?start=0&limit=10&sort=LastName&dir=ASC", personDAO));
        processors.add(new ImageScrapperProcessor("https://www.parentsformeganslaw.org/", "/Users/dromov/Documents/PHOTO/"));

        for (ScrapperProcessor processor : processors) {
            processor.process();
        }

        context.close();
    }

}