package com.Renderer;

import com.Entities.Movers.Mover;
import com.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class RendererPass extends Renderer {
    private ArrayList<ArrayList<ImageView>> passviews = new ArrayList<>();
    private Timeline[] t = new Timeline[2];

    public RendererPass() {
        super();
        for (int i = 0; i < 3; i++) {
            passviews.add(new ArrayList<ImageView>());
            passviews.get(i).add(new ImageView(sheet));
            passviews.get(i).add(new ImageView(sheet));
            passviews.get(i).add(new ImageView(sheet));
        }
        passviews.get(2).add(new ImageView(sheet));

        passviews.get(0).get(0).setViewport(new Rectangle2D(side * 4, side * 5, side, side));
        passviews.get(0).get(1).setViewport(new Rectangle2D(side * 4, side * 6, side, side));
        passviews.get(0).get(2).setViewport(new Rectangle2D(side * 4, side * 7, side, side));
        passviews.get(1).get(0).setViewport(new Rectangle2D(side * 5, side * 5, side, side));
        passviews.get(1).get(1).setViewport(new Rectangle2D(side * 5, side * 6, side, side));
        passviews.get(1).get(2).setViewport(new Rectangle2D(side * 5, side * 7, side, side));
        passviews.get(2).get(0).setViewport(new Rectangle2D(side * 4, side * 8, side, side));
        passviews.get(2).get(1).setViewport(new Rectangle2D(side * 15, side * 0, side, side));
        passviews.get(2).get(2).setViewport(new Rectangle2D(side * 15, side * 1, side, side));
        passviews.get(2).get(3).setViewport(new Rectangle2D(side * 15, side * 2, side, side));
        initAnimation(0);
        initAnimation(1);
    }

    public void initAnimation(int type) {
        t[type] = new Timeline();
        t[type].setCycleCount(1);
        t[type].getKeyFrames().add(new KeyFrame(Duration.millis(300),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(passviews.get(0).get(0));
                    Main.rootMover.getChildren().remove(passviews.get(1).get(0));
                    Main.rootMover.getChildren().add(passviews.get(type).get(1));
                }));
        t[type].getKeyFrames().add(new KeyFrame(Duration.millis(600),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(passviews.get(type).get(1));
                    Main.rootMover.getChildren().add(passviews.get(type).get(2));
                }));
        t[type].getKeyFrames().add(new KeyFrame(Duration.millis(900),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(passviews.get(type).get(2));
                    Main.rootMover.getChildren().add(passviews.get(type).get(0));
                }));
    }

    public void stopAnimation(Mover.MovementType dir) {
        int id = 0;
        if (dir == null)
            id = -1;
        else id = dir.ordinal() / 2;

        for (int i = 0; i < 2; i++)
            if (id != i) {
                Main.rootMover.getChildren().remove(passviews.get(i).get(1));
                Main.rootMover.getChildren().remove(passviews.get(i).get(2));
                t[i].stop();
            }
        if (id > -1) {
            boolean has = false;
            for (Node node: Main.rootMover.getChildren())
                if (node == passviews.get(id).get(0)) {
                    has = true;
                    break;
                }
            if (!has && oldDir != dir.ordinal() / 2)
                Main.rootMover.getChildren().add(passviews.get(id).get(0));
        }
    }

    public void pauseAnimation(Mover.MovementType dir) {
        t[dir.ordinal() / 2].pause();
    }

    public void startAnimation(Mover.MovementType dir) {
        stopAnimation(dir);
        t[dir.ordinal() / 2].play();
        oldDir = dir.ordinal() / 2;
    }

    public void render(double x, double y) throws Exception {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < passviews.get(i).size(); j++) {
                passviews.get(i).get(j).setX(x);
                passviews.get(i).get(j).setY(y);
            }
    }

    public void clear() {
        stopAnimation(null);
        Main.rootMover.getChildren().remove(passviews.get(0).get(0));
        Main.rootMover.getChildren().remove(passviews.get(1).get(0));
    }

    public void delete() {
        clear();
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().add(passviews.get(2).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(passviews.get(2).get(0));
                    Main.rootMover.getChildren().add(passviews.get(2).get(1));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1200),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(passviews.get(2).get(1));
                    Main.rootMover.getChildren().add(passviews.get(2).get(2));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1400),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(passviews.get(2).get(2));
                    Main.rootMover.getChildren().add(passviews.get(2).get(3));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1600),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(passviews.get(2).get(3));
                }));
        t.play();
    }
}
