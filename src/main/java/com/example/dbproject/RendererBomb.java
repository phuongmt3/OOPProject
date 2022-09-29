package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RendererBomb extends Renderer {
    private ImageView bomb, bomb2, bomb3;

    public RendererBomb() {
        super();
        bomb = new ImageView(sheet);
        bomb.setViewport(new Rectangle2D(side * 0, side * 3, side, side));
        bomb2 = new ImageView(sheet);
        bomb2.setViewport(new Rectangle2D(side * 1, side * 3, side, side));
        bomb3 = new ImageView(sheet);
        bomb3.setViewport(new Rectangle2D(side * 2, side * 3, side, side));
    }

    private void initBomb() {
        Main.rootBomb.getChildren().add(bomb);
        t.setCycleCount(Timeline.INDEFINITE);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(500),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(bomb);
                    Main.rootBomb.getChildren().add(bomb2);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(bomb2);
                    Main.rootBomb.getChildren().add(bomb3);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1500),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(bomb3);
                    Main.rootBomb.getChildren().add(bomb);
                }));
        t.play();
    }
    public void renderBomb(double x, double y) throws Exception {
        bomb.setX(x);
        bomb.setY(y);
        bomb2.setX(x);
        bomb2.setY(y);
        bomb3.setX(x);
        bomb3.setY(y);
        if (firstTime) {
            initBomb();
            firstTime = false;
        }
    }

    public void deleteBomb() throws Exception {
        t.stop();
        Main.rootBomb.getChildren().remove(bomb);
        Main.rootBomb.getChildren().remove(bomb2);
        Main.rootBomb.getChildren().remove(bomb3);
    }
}
