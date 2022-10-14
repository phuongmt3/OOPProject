package com;

import com.Entities.Movers.Bomber;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class GameSound {

    public static Clip makeClip(String musicLocation)  {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
               // clip.start();
                //  clip.loop(Clip.LOOP_CONTINUOUSLY);
                //   clip.stop();
                return clip;


            } else {
                System.out.println("Can't find files!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void playClip(Clip clip) {
        //Scanner sc = new Scanner(System.in);
        //String res = sc.next();
        clip.start();
    }

    public static void loopClip(Clip clip) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopClip(Clip clip) {
        clip.stop();
    }

}

   /* public void playMusic(String musicLocation) {
        try
        {
            File musicPath = new File(musicLocation);
            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
              //  clip.loop(Clip.LOOP_CONTINUOUSLY);
             //   clip.stop();



            }
            else {
                System.out.println("Can't find files!");
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void BomberDieMusic(String musicLocation) {
        try
        {
            File musicPath = new File(musicLocation);
            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
               // clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.stop();



            }
            else {
                System.out.println("Can't find files!");
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    }*/







