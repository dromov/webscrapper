package com.scrapper.SourceType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

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

        Elements els = document.select("*[style*=background]");
        String style = els.first().attributes().get("style");

        Elements img = document.getElementsByTag("img");

        for (Element imageElement : img) {
            String src = imageElement.absUrl("src");
            saveImageToDestinationFolder(src);
        }
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
