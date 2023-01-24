package lk.ijse.hardware.util;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
// d
public class Sound {
    public static void errorSound(){
        Media media=new Media(new File("src\\lk\\ijse\\hardware\\assets\\sound\\windowsError.mp3").toURI().toString());
        setMediaPlayer(media); // error

    }
    public static void infoSound(){
        Media media=new Media(new File("src\\lk\\ijse\\hardware\\assets\\sound\\information.mp3").toURI().toString());
        setMediaPlayer(media);
    }

    public static void setMediaPlayer(Media media){
        MediaPlayer player=new MediaPlayer(media);
        player.setAutoPlay(true);

    }

}
