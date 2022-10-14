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
    public static final String playgame = "src/main/java/res/sound/playgame.wav";
    public static final String bomberdie = "src/main/java/res/sound/bomber_die.wav";

    public static final String lose = "src/main/java/res/sound/lose.wav";
    public static final String menu = "src/main/java/res/sound/menu.wav";
    public static final String bomb_bang = "src/main/java/res/sound/bomb_bang.wav";


    public static Clip PLAYGAME = makeClip(playgame);
    public static Clip BOMBERDIE = makeClip(bomberdie);
    public static Clip LOSE = makeClip(lose);
    public static Clip MENU = makeClip(menu);
    public static Clip BOMBBANG = makeClip(bomb_bang);




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







