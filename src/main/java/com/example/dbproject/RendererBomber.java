package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RendererBomber extends Renderer {
    private ImageView bomberdown1, bomberdown2, bomberdown3, bomberdead;

    public RendererBomber() {
        super();
        bomberdown1 = new ImageView(sheet);
        bomberdown2 = new ImageView(sheet);
        bomberdown3 = new ImageView(sheet);
        bomberdead = new ImageView(sheet);
        bomberdown1.setViewport(new Rectangle2D(side * 2, side * 0, side, side));
        bomberdown2.setViewport(new Rectangle2D(side * 2, side * 1, side, side));
        bomberdown3.setViewport(new Rectangle2D(side * 2, side * 2, side, side));
        bomberdead.setViewport(new Rectangle2D(side * 4, side * 2, side, side));
    }

    private void initBomber() {
        Main.rootMover.getChildren().add(bomberdown1);
        t.setCycleCount(Timeline.INDEFINITE);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(200),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberdown1);
                    Main.rootMover.getChildren().add(bomberdown2);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(400),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberdown2);
                    Main.rootMover.getChildren().add(bomberdown3);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(600),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberdown3);
                    Main.rootMover.getChildren().add(bomberdown1);
                }));
        t.play();
    }
    public void renderBomber(double x, double y) throws Exception {
        bomberdown1.setX(x);
        bomberdown2.setX(x);
        bomberdown3.setX(x);
        bomberdown1.setY(y);
        bomberdown2.setY(y);
        bomberdown3.setY(y);
        if (firstTime) {
            initBomber();
            firstTime = false;
        }
    }

    public void deleteBomber(double x, double y) {  //animation for bomber die
        t.getKeyFrames().removeAll();
        t.stop();
        Main.rootMover.getChildren().remove(bomberdown1);
        Main.rootMover.getChildren().remove(bomberdown2);
        Main.rootMover.getChildren().remove(bomberdown3);

        bomberdead.setX(x);
        bomberdead.setY(y);
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().add(bomberdead);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberdead);
                }));
        //add more keyframes
        t.play();
    }
}
