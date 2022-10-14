package com;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class GameSound {
    static final String playgame = "src//main//java//res//sound//playgame.wav";

    public void playMusic(String musicLocation) {
        try
        {
            File musicPath = new File(musicLocation);
            if(musicPath.exists()) {
                //   for (int i = 0; i < 100; i++) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                JOptionPane.showMessageDialog(null, "Press OK to start playing!");
               // long clipTimePosition = clip.getMicrosecondPosition();
              //  clip.stop();

                //  JOptionPane.showMessageDialog(null, "Press OK to resume playing!");
                //  clip.setMicrosecondPosition(clipTimePosition);
                // clip.start();


                //  }
            }
            else {
                System.out.println("Can't find files!");
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }


}
