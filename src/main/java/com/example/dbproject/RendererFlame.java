package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class RendererFlame extends Renderer {
    private ArrayList<ArrayList<ImageView>> flame = new ArrayList<ArrayList<ImageView>>();
    private void setFrames(int stt, int r, int c) {
        flame.get(Direction.center.ordinal()).get(stt).setViewport(new Rectangle2D(side * 0, side * c, side, side)); //5r
        flame.get(Direction.left.ordinal()).get(stt).setViewport(new Rectangle2D(side * 0, side * (c+3), side, side)); //8r
        flame.get(Direction.right.ordinal()).get(stt).setViewport(new Rectangle2D(side * 2, side * (c+3), side, side));//8r
        flame.get(Direction.up.ordinal()).get(stt).setViewport(new Rectangle2D(side * r, side * 4, side, side));//2l
        flame.get(Direction.down.ordinal()).get(stt).setViewport(new Rectangle2D(side * r, side * 6, side, side));//2l
        flame.get(Direction.middlecol.ordinal()).get(stt).setViewport(new Rectangle2D(side * r, side * 5, side, side));//2l
        flame.get(Direction.middlerow.ordinal()).get(stt).setViewport(new Rectangle2D(side * 1, side * (c+3), side, side));//8r
    }

    public RendererFlame() {
        super();
        for (int i = 0; i < 7; i++) {
            flame.add(new ArrayList<ImageView>());
            flame.get(i).add(new ImageView(sheet));
            flame.get(i).add(new ImageView(sheet));
            flame.get(i).add(new ImageView(sheet));
        }
        setFrames(0,1,4);
        setFrames(1,2,5);
        setFrames(2,3,6);
    }

    public void renderFlame(double x, double y, Direction dir) throws Exception {
        for (int j = 0; j < 3; j++) {
            flame.get(dir.ordinal()).get(j).setX(x);
            flame.get(dir.ordinal()).get(j).setY(y);
        }
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().add(flame.get(dir.ordinal()).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flame.get(dir.ordinal()).get(0));
                    Main.rootBomb.getChildren().add(flame.get(dir.ordinal()).get(1));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(200),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flame.get(dir.ordinal()).get(1));
                    Main.rootBomb.getChildren().add(flame.get(dir.ordinal()).get(2));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(300),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flame.get(dir.ordinal()).get(2));
                    Main.rootBomb.getChildren().add(flame.get(dir.ordinal()).get(1));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(400),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flame.get(dir.ordinal()).get(1));
                    Main.rootBomb.getChildren().add(flame.get(dir.ordinal()).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(500),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flame.get(dir.ordinal()).get(0));
                }));
        t.play();
    }
}
