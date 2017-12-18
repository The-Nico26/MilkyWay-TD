package td;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.player.advanced.*;

import java.io.File;

public class Sound extends PlaybackListener implements Runnable {
    private static Sound sound;
    public MediaPlayer mP;

    public static Sound getInstance(){
        if(sound == null){
            sound = new Sound();
        }

        return sound;
    }
    private AdvancedPlayer player;
    private Thread playerThread;
    private String filePath;

    public void replay(){
        Sound.getInstance().play(this.filePath);
    }

    public void play(String filePath)
    {
        if(playerThread != null){
            player.close();
        }
        this.filePath = filePath;
        try
        {
            String urlAsString =
                    "file:///"
                            + new java.io.File(".").getCanonicalPath()
                            + "/"
                            + filePath;

            this.player = new AdvancedPlayer
                    (
                            new java.net.URL(urlAsString).openStream(),
                            javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
                    );

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        }
        catch (Exception ex)
        {
            play(filePath);
            ex.printStackTrace();
        }
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent)
    {
    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {
        Sound.getInstance().replay();
    }

    // Runnable members

    public void run()
    {
        try
        {
            this.player.play();
        }
        catch (javazoom.jl.decoder.JavaLayerException ex)
        {
            ex.printStackTrace();
        }

    }

    public void close() {
        if(playerThread != null){
            player.close();
            playerThread = null;
        }
    }
}
