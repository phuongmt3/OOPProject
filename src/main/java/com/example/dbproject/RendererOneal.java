package com.example.dbproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class RendererOneal extends Renderer {
    private ArrayList<ArrayList<ImageView>> onealviews = new ArrayList<ArrayList<ImageView>>();
    private Timeline[] t = new Timeline[2];
    public RendererOneal() {
        super();
        for (int i = 0; i < 3; i++) {
            onealviews.add(new ArrayList<ImageView>());
            onealviews.get(i).add(new ImageView(sheet));
            onealviews.get(i).add(new ImageView(sheet));
            onealviews.get(i).add(new ImageView(sheet));
        }
        onealviews.get(2).add(new ImageView(sheet));

        onealviews.get(0).get(0).setViewport(new Rectangle2D(side * 11, side * 0, side, side));
        onealviews.get(0).get(1).setViewport(new Rectangle2D(side * 11, side * 1, side, side));
        onealviews.get(0).get(2).setViewport(new Rectangle2D(side * 11, side * 2, side, side));
        onealviews.get(1).get(0).setViewport(new Rectangle2D(side * 12, side * 0, side, side));
        onealviews.get(1).get(1).setViewport(new Rectangle2D(side * 12, side * 1, side, side));
        onealviews.get(1).get(2).setViewport(new Rectangle2D(side * 12, side * 2, side, side));
        onealviews.get(2).get(0).setViewport(new Rectangle2D(side * 11, side * 3, side, side));
        onealviews.get(2).get(1).setViewport(new Rectangle2D(side * 15, side * 0, side, side));
        onealviews.get(2).get(2).setViewport(new Rectangle2D(side * 15, side * 1, side, side));
        onealviews.get(2).get(3).setViewport(new Rectangle2D(side * 15, side * 2, side, side));
        initAnimation(0);
        initAnimation(1);
    }

    private void initAnimation(int type) {
        t[type] = new Timeline();
        t[type].setCycleCount(1);
        t[type].getKeyFrames().add(new KeyFrame(Duration.millis(300),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(onealviews.get(type).get(0));
                    Main.rootMover.getChildren().add(onealviews.get(type).get(1));
                }));
        t[type].getKeyFrames().add(new KeyFrame(Duration.millis(600),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(onealviews.get(type).get(1));
                    Main.rootMover.getChildren().add(onealviews.get(type).get(2));
                }));
        t[type].getKeyFrames().add(new KeyFrame(Duration.millis(900),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(onealviews.get(type).get(2));
                }));
    }

    private void stopAnimation(Mover.MovementType dir) {
        int id = 0;
        if (dir == null)
            id = -1;
        else id = dir.ordinal() / 2;

        for (int i = 0; i < 2; i++)
            if (id != i) {
                Main.rootMover.getChildren().remove(onealviews.get(i).get(0));
                Main.rootMover.getChildren().remove(onealviews.get(i).get(1));
                Main.rootMover.getChildren().remove(onealviews.get(i).get(2));
                t[i].stop();
            }
        if (id > -1) {
            boolean has = false;
            for (Node node: Main.rootMover.getChildren())
                if (node == onealviews.get(id).get(0)) {
                    has = true;
                    break;
                }
            if (!has)
                Main.rootMover.getChildren().add(onealviews.get(id).get(0));
        }
    }

    public void startAnimation(Mover.MovementType dir) {
        stopAnimation(dir);
        t[dir.ordinal() / 2].play();
    }

    public void renderOneal(double x, double y) throws Exception {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < onealviews.get(i).size(); j++) {
                onealviews.get(i).get(j).setX(x);
                onealviews.get(i).get(j).setY(y);
            }
    }

    public void deleteOneal() {
        stopAnimation(null);
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().add(onealviews.get(2).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(onealviews.get(2).get(0));
                    Main.rootMover.getChildren().add(onealviews.get(2).get(1));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1200),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(onealviews.get(2).get(1));
                    Main.rootMover.getChildren().add(onealviews.get(2).get(2));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1400),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(onealviews.get(2).get(2));
                    Main.rootMover.getChildren().add(onealviews.get(2).get(3));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1600),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(onealviews.get(2).get(3));
                }));
        t.play();
    }
}
