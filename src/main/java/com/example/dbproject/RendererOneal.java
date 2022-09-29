package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RendererOneal extends Renderer {
    private ImageView oneal, onealdead, enemydead;

    public RendererOneal() {
        super();
        oneal = new ImageView(sheet);
        onealdead = new ImageView(sheet);
        enemydead = new ImageView(sheet);
        oneal.setViewport(new Rectangle2D(side * 11, side * 0, side, side));
        onealdead.setViewport(new Rectangle2D(side * 11, side * 3, side, side));
        enemydead.setViewport(new Rectangle2D(side * 15, side * 0, side, side));
    }

    private void initOneal() {  //add animation
        Main.rootMover.getChildren().add(oneal);
    }

    public void renderOneal(double x, double y) throws Exception {
        oneal.setX(x);
        oneal.setY(y);
        if (firstTime) {
            initOneal();
            firstTime = false;
        }
    }

    public void deleteOneal(double x, double y) {
        t.stop();
        Main.rootMover.getChildren().remove(oneal);

        enemydead.setX(x);
        enemydead.setY(y);
        onealdead.setX(x);
        onealdead.setY(y);
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().add(onealdead);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(onealdead);
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
