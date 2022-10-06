package com.Entities;

import java.io.File;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {

    public SoundPlayer(String url) {
       // try {//    ww  w   . d  e  m   o   2s    .  co  m
            AudioClip clip = new AudioClip(new File(url).toURI().toString());

            clip.play();
       // } catch (Exception e) {
       //     e.printStackTrace();
       // }

    }

    public static void main(String[] args) {
        SoundPlayer player = new SoundPlayer("D:\\OOPProject\\src\\main\\java\\res\\sound\\playgame.wav");


    }

}