import java.io.*;
import java.util.ArrayList;

/**
 * A program that recommends music based on the users' music profile preferences
 *
 * @author Dylan Miller
 * @version October 13, 2022
 */

public class MusicRecommender {
    private String musicListFileName; //name of the file containing the formatted music list
    private ArrayList<Music> music = new ArrayList<>(); //List of music parsed from the music list file

    public MusicRecommender(String musicListFileName) throws FileNotFoundException, MusicFileFormatException {
        this.musicListFileName = musicListFileName;
        File input = new File(musicListFileName);

        if (!input.exists()) {
            throw new FileNotFoundException();
        }

        FileReader fr = new FileReader(input);
        try (BufferedReader bfr = new BufferedReader(fr)) {
            String line = bfr.readLine();
            while (line != null) {
                music.add(parseMusic(line));
                line = bfr.readLine();
            }
        } catch (MusicFileFormatException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Music BPMBasedRecommendation(MusicProfile musicProfile) throws NoRecommendationException {
        int bpm = musicProfile.getPreferredBPM();
        int current = 0;
        Music song = null;
        boolean likesPopular = musicProfile.isLikePopular();
        for (Music i : music) {
            if (Math.abs(bpm - i.getBPM()) < Math.abs(bpm - current)) {
                current = i.getBPM();
                song = i;
            } else if (Math.abs(bpm - i.getBPM()) == Math.abs(bpm - current)) {
                if (song != null) {
                    if (likesPopular && i.getPopularity() > song.getPopularity()) {
                        current = i.getBPM();
                        song = i;
                    } else if (!likesPopular && i.getPopularity() < song.getPopularity()) {
                        current = i.getBPM();
                        song = i;
                    }
                }
            }
        }

        if (Math.abs(bpm - current) > 20) {
            throw new NoRecommendationException("There was no music with your preferred BPM!");
        }

        if (song != null) {
            song.setPopularity(song.getPopularity() + 1);
        }
        return song;
    }

    public Music genreBasedRecommendation(MusicProfile musicProfile) throws NoRecommendationException {
        String genre = musicProfile.getPreferredGenre();
        genre = genre.toLowerCase();
        Music song = null;
        boolean likesPopular = musicProfile.isLikePopular();
        for (Music i : music) {
            if (i.getGenre().toLowerCase().contains(genre)) {
                if (song == null) {
                    song = i;
                } else if (likesPopular && i.getPopularity() > song.getPopularity()) {
                    song = i;
                } else if (!likesPopular && i.getPopularity() < song.getPopularity()) {
                    song = i;
                }
            }
        }

        if (song == null) {
            throw new NoRecommendationException("There was no music with your preferred genre!");
        }
        song.setPopularity(song.getPopularity() + 1);
        return song;
    }

    public ArrayList<Music> searchArtists(MusicProfile musicProfile) throws NoRecommendationException {
        String preferredArtist = musicProfile.getPreferredArtist();
        preferredArtist = preferredArtist.toLowerCase();
        ArrayList<Music> artists = new ArrayList<>();

        for (Music i : music) {
            if (i.getArtist().toLowerCase().contains(preferredArtist)) {
                i.setPopularity(i.getPopularity() + 1);
                artists.add(i);
            }
        }

        if (artists.size() == 0) {
            throw new NoRecommendationException("No music by your preferred artist is in the list!");
        }
        return artists;
    }

    public Music getMostPopularMusic() {
        int mostPopular = 0;
        int current;
        Music mostPopularMusic = null;

        for (Music i : music) {
            current = i.getPopularity();

            if (current > mostPopular) {
                mostPopular = current;
                mostPopularMusic = i;
            }
        }
        if (mostPopularMusic != null) {
            mostPopularMusic.setPopularity(mostPopularMusic.getPopularity() + 1);
        }
        return mostPopularMusic;
    }

    private static Music parseMusic(String musicInfoLine) throws MusicFileFormatException {
        String[] info = musicInfoLine.split(" ");

        if (info.length != 5) {
            throw new MusicFileFormatException("One of the lines of the music list file is malformed!");
        }

        for (int i = 0; i < info.length; i++) {
            info[i] = info[i].replaceAll("_", " ");
        }

        Music song;
        try {
            song = new Music(info[0], info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]));
        } catch (NumberFormatException e) {
            throw new MusicFileFormatException("One of the lines of the music list file is malformed!");
        }
        return song;
    }

    public void saveMusicList() {
        File f = new File(musicListFileName);
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(f, false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        PrintWriter pw = new PrintWriter(fos);

        for (Music i : music) {
            String track = i.getTrack().replaceAll(" ", "_");
            String artist = i.getArtist().replaceAll(" ", "_");
            String genre = i.getGenre().replaceAll(" ", "_");
            int bpm = i.getBPM();
            int popularity = i.getPopularity();
            pw.println(track + " " + artist + " " + genre + " " + bpm + " " + popularity);
        }

        pw.close();
    }
}
