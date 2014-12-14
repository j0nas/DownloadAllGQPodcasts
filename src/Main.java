import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("http://www.goingquantum.ca/podcastgen/?p=archive&cat=music").get();
        Elements podcasts = document.select(".episode");

        final String saveDir = "C:\\Users\\Jonas\\Desktop\\GQ Podcasts\\";
        for (final Element podcast : podcasts) {
            URL downloadUrl = new URL(podcast.select("a[href]").attr("href"));

            String title = podcast.select("h3.episode_title a").html();
            File downloadLocation = new File(saveDir + title.replaceAll("[^a-zA-Z0-9\\.\\-]", " ").trim() + ".mp3");

            System.out.println("Downloading: " + title + " to " + downloadLocation.getAbsolutePath());
            org.apache.commons.io.FileUtils.copyURLToFile(downloadUrl, downloadLocation, 0, 0);
        }
    }
}
