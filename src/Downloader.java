public class Downloader implements Runnable {
    Podcast[] podcasts;

    public Downloader(Podcast[] podcasts) {
        this.podcasts = podcasts;
    }

    @Override
    public void run() {
        for (final Podcast podcast : podcasts) {
            podcast.download();
        }
    }
}
