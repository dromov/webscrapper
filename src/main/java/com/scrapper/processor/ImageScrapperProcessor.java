package com.scrapper.processor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dromov on 27.05.2016.
 */
public class ImageScrapperProcessor implements ScrapperProcessor {
    private String sourceUrl;
    private String destinationFolderPath;

    public ImageScrapperProcessor(String sourceUrl, String destinationFolderPath) {
        this.sourceUrl = sourceUrl;
        this.destinationFolderPath = destinationFolderPath;
    }

    @Override
    public void process() throws IOException {
        Document document = Jsoup.connect(sourceUrl).get();
        List<String> allImageUrls = getImageUrlsFromNativeTag(document);

        allImageUrls.addAll(getImageUrlsFromBackgroundElement(document));

        for (String imageAbsUrl : allImageUrls) {
            saveImageToDestinationFolder(imageAbsUrl);
        }
    }

    private ArrayList<String> getImageUrlsFromBackgroundElement(Document document) {
        Elements backgroundElements = document.select("*[style*=background]");
        ArrayList<String> imageAbsUrlList = new ArrayList();

        for (Element backgroundElement : backgroundElements) {
            String baseUrl = backgroundElement.baseUri();
            String styleRepresentation = backgroundElement.attributes().get("style");

            Matcher m = Pattern.compile("url\\((.*?)\\)").matcher(styleRepresentation);
            if (m.find()) {
                String absUrl = baseUrl + m.group(1);
                imageAbsUrlList.add(absUrl);
            }

        }

        return imageAbsUrlList;
    }

    private ArrayList<String> getImageUrlsFromNativeTag(Document document) {
        Elements imgTagElement = document.getElementsByTag("img");
        ArrayList<String> imageAbsUrlList = new ArrayList();

        for (Element imageElement : imgTagElement) {
            String imageAbsUrl = imageElement.absUrl("src");
            imageAbsUrlList.add(imageAbsUrl);
        }

        return imageAbsUrlList;

    }


    private void saveImageToDestinationFolder(String src) throws IOException {
        int lastSlashIndex = src.lastIndexOf("/");
        String imageName = src.substring(lastSlashIndex + 1, src.length());

        URL url = new URL(src);
        InputStream in = url.openStream();

        OutputStream out = new BufferedOutputStream(new FileOutputStream(destinationFolderPath + imageName));

        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }

        out.close();
        in.close();
    }
}
