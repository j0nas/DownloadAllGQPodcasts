import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Podcast {
    private URL url;
    private String title;
    private File savePath;

    public Podcast(final String url, final String title, final String baseSaveDirectory) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.title = title;
        this.savePath = new File(baseSaveDirectory +
                title.replaceAll("[^a-zA-Z0-9\\.\\-]", " ").trim() + ".mp3");
    }

    public String getTitle() {
        return title;
    }

    public boolean download() {
        System.out.println("Downloading: " + getTitle());
        try {
            org.apache.commons.io.FileUtils.copyURLToFile(url, savePath, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savePath.exists();
    }
}
