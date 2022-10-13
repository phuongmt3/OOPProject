package com;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GameSound {
    public void playMusic(String musicLocation) {
        try
        {
            File musicPath = new File(musicLocation);
            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

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
