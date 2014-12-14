import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static final String podcastListURL = "http://www.goingquantum.ca/podcastgen/?p=archive&cat=music";
    public static final String saveDirectory = "C:\\Users\\Jonas\\Desktop\\GQ Podcasts\\";
    public static final int connectionsPerThread = 10;

    public static void main(String[] args) throws IOException {
        Elements podcastsToParse = Jsoup.connect(podcastListURL).get().select(".episode");

        ArrayList<Podcast> podcastArrayList = new ArrayList<>();
        for (final Element podcastHtml : podcastsToParse) {
            podcastArrayList.add(new Podcast(podcastHtml.select("a[href]").attr("href"),
                    podcastHtml.select("h3.episode_title a").html(), saveDirectory));
        }

        int counter = 0;
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<Podcast> curThreadPodcasts = new ArrayList<>();
        for (int i = 0; i < podcastArrayList.size(); i++) {
            if (counter % connectionsPerThread == 0 || i == podcastArrayList.size() - 1) {
                threads.add(new Thread(new Downloader(curThreadPodcasts.toArray(new Podcast[curThreadPodcasts.size()]))));
                curThreadPodcasts = new ArrayList<>();
            }

            curThreadPodcasts.add(podcastArrayList.get(i));
            counter++;
        }

        threads.forEach(thread -> thread.start());
    }
}
