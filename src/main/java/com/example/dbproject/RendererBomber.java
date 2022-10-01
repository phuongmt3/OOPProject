package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class RendererBomber extends Renderer {
    private ArrayList<ArrayList<ImageView>> bomberviews = new ArrayList<ArrayList<ImageView>>();
    private ImageView bomberdefault;
    Timeline[] t = new Timeline[4];
    public RendererBomber(double x, double y) {
        super();
        for (int i = 0; i < 5; i++)
            bomberviews.add(new ArrayList<ImageView>());
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 3; j++)
                bomberviews.get(i).add(new ImageView(sheet));

        bomberdefault = new ImageView(sheet);
        bomberdefault.setViewport(new Rectangle2D(side * 2, side * 0, side, side));
        bomberviews.get(Mover.MovementType.DOWN.ordinal()).get(0).setViewport(new Rectangle2D(side * 2, side * 0, side, side));
        bomberviews.get(Mover.MovementType.DOWN.ordinal()).get(1).setViewport(new Rectangle2D(side * 2, side * 1, side, side));
        bomberviews.get(Mover.MovementType.DOWN.ordinal()).get(2).setViewport(new Rectangle2D(side * 2, side * 2, side, side));
        bomberviews.get(4).get(0).setViewport(new Rectangle2D(side * 4, side * 2, side, side));
        bomberviews.get(4).get(1).setViewport(new Rectangle2D(side * 5, side * 2, side, side));
        bomberviews.get(4).get(2).setViewport(new Rectangle2D(side * 6, side * 2, side, side));
        bomberviews.get(Mover.MovementType.UP.ordinal()).get(0).setViewport(new Rectangle2D(side * 0, side * 0, side, side));
        bomberviews.get(Mover.MovementType.UP.ordinal()).get(1).setViewport(new Rectangle2D(side * 0, side * 1, side, side));
        bomberviews.get(Mover.MovementType.UP.ordinal()).get(2).setViewport(new Rectangle2D(side * 0, side * 2, side, side));
        bomberviews.get(Mover.MovementType.LEFT.ordinal()).get(0).setViewport(new Rectangle2D(side * 3, side * 0, side, side));
        bomberviews.get(Mover.MovementType.LEFT.ordinal()).get(1).setViewport(new Rectangle2D(side * 3, side * 1, side, side));
        bomberviews.get(Mover.MovementType.LEFT.ordinal()).get(2).setViewport(new Rectangle2D(side * 3, side * 2, side, side));
        bomberviews.get(Mover.MovementType.RIGHT.ordinal()).get(0).setViewport(new Rectangle2D(side * 1, side * 0, side, side));
        bomberviews.get(Mover.MovementType.RIGHT.ordinal()).get(1).setViewport(new Rectangle2D(side * 1, side * 1, side, side));
        bomberviews.get(Mover.MovementType.RIGHT.ordinal()).get(2).setViewport(new Rectangle2D(side * 1, side * 2, side, side));

        renderBomberDefault(x, y);
        initAnimation(Mover.MovementType.DOWN);
        initAnimation(Mover.MovementType.UP);
        initAnimation(Mover.MovementType.LEFT);
        initAnimation(Mover.MovementType.RIGHT);
    }

    public void renderBomberDefault(double x, double y) {
        bomberdefault.setX(x);
        bomberdefault.setY(y);
        Main.rootMover.getChildren().add(bomberdefault);
    }

    private void initAnimation(Mover.MovementType dir) {
        t[dir.ordinal()] = new Timeline();
        t[dir.ordinal()].setCycleCount(1);
        t[dir.ordinal()].getCycleDuration();
        t[dir.ordinal()].getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberviews.get(dir.ordinal()).get(0));
                    Main.rootMover.getChildren().add(bomberviews.get(dir.ordinal()).get(0));
                }));
        t[dir.ordinal()].getKeyFrames().add(new KeyFrame(Duration.millis(90),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberviews.get(dir.ordinal()).get(0));
                    Main.rootMover.getChildren().add(bomberviews.get(dir.ordinal()).get(1));
                }));
        t[dir.ordinal()].getKeyFrames().add(new KeyFrame(Duration.millis(180),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberviews.get(dir.ordinal()).get(1));
                    Main.rootMover.getChildren().add(bomberviews.get(dir.ordinal()).get(2));
                }));
        t[dir.ordinal()].getKeyFrames().add(new KeyFrame(Duration.millis(270),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberviews.get(dir.ordinal()).get(2));
                    Main.rootMover.getChildren().add(bomberviews.get(dir.ordinal()).get(0));
                }));
    }

    public void stopAnimation(Mover.MovementType dir) {
        Main.rootMover.getChildren().remove(bomberdefault);
        int id = 0;
        if (dir == null)
            id = -1;
        else id = dir.ordinal();
        for (int i = 0; i < 4; i++)
            if (id != i) {
                Main.rootMover.getChildren().remove(bomberviews.get(i).get(0));
                Main.rootMover.getChildren().remove(bomberviews.get(i).get(1));
                Main.rootMover.getChildren().remove(bomberviews.get(i).get(2));
                t[i].stop();
            }
    }

    public void startAnimation(Mover.MovementType dir) {
        stopAnimation(dir);
        t[dir.ordinal()].play();
    }
    public void renderBomber(double x, double y) throws Exception {
        bomberdefault.setX(x);
        bomberdefault.setY(y);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 3; j++) {
                bomberviews.get(i).get(j).setX(x);
                bomberviews.get(i).get(j).setY(y);
            }
    }

    public void deleteBomber(double x, double y) {
        stopAnimation(null);
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().add(bomberviews.get(4).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(200),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberviews.get(4).get(0));
                    Main.rootMover.getChildren().add(bomberviews.get(4).get(1));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(400),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberviews.get(4).get(1));
                    Main.rootMover.getChildren().add(bomberviews.get(4).get(2));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(600),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(bomberviews.get(4).get(2));
                }));
        t.play();
    }
}
