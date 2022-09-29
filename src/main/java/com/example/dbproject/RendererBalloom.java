package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RendererBalloom extends Renderer {
    private ImageView balloom, balloomdead, enemydead;

    public RendererBalloom() {
        super();
        balloom = new ImageView(sheet);
        balloomdead = new ImageView(sheet);
        enemydead = new ImageView(sheet);
        balloom.setViewport(new Rectangle2D(side * 9, side * 0, side, side));
        balloomdead.setViewport(new Rectangle2D(side * 9, side * 3, side, side));
        enemydead.setViewport(new Rectangle2D(side * 15, side * 0, side, side));
    }

    private void initBallom() {
        //add animation
        Main.rootMover.getChildren().add(balloom);
    }

    public void renderBallom(double x, double y) throws Exception {
        balloom.setX(x);
        balloom.setY(y);
        if (firstTime) {
            initBallom();
            firstTime = false;
        }
    }

    public void deleteBalloom(double x, double y) {
        t.stop();
        Main.rootMover.getChildren().remove(balloom);

        enemydead.setX(x);
        enemydead.setY(x);
        balloomdead.setX(y);
        balloomdead.setY(y);
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().add(balloomdead);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(balloomdead);
                    Main.rootMover.getChildren().add(enemydead);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(enemydead);
                }));
        //add more keyframes
        t.play();
    }
}
