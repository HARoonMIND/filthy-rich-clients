package swinghacks.ch10.Audio.hack74;

//import javax.media.*;
import java.io.File;

public class MP3Player {

    public static void main(String[] args) throws Exception {
       File file = new File("test.mp3");
       MediaLocator mrl = new MediaLocator(file.toURL());
       Player player = Manager.createPlayer(mrl);
       player.start();
    }

}
