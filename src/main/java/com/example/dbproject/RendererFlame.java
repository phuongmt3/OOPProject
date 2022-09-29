package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class RendererFlame extends Renderer {
    private ArrayList<ArrayList<ImageView>> flame = new ArrayList<ArrayList<ImageView>>();

    public RendererFlame() {
        super();
        for (int i = 0; i < 7; i++) {
            flame.add(new ArrayList<ImageView>());
            flame.get(i).add(new ImageView(sheet));
            flame.get(i).add(new ImageView(sheet));
            flame.get(i).add(new ImageView(sheet));
        }
        //add 2 remainding frames
        flame.get(Direction.center.ordinal()).get(0).setViewport(new Rectangle2D(side * 0, side * 4, side, side));
        flame.get(Direction.left.ordinal()).get(0).setViewport(new Rectangle2D(side * 0, side * 7, side, side));
        flame.get(Direction.right.ordinal()).get(0).setViewport(new Rectangle2D(side * 2, side * 7, side, side));
        flame.get(Direction.up.ordinal()).get(0).setViewport(new Rectangle2D(side * 1, side * 4, side, side));
        flame.get(Direction.down.ordinal()).get(0).setViewport(new Rectangle2D(side * 1, side * 6, side, side));
        flame.get(Direction.middlecol.ordinal()).get(0).setViewport(new Rectangle2D(side * 1, side * 5, side, side));
        flame.get(Direction.middlerow.ordinal()).get(0).setViewport(new Rectangle2D(side * 1, side * 7, side, side));
    }

    public void renderFlame(double x, double y, Direction dir) throws Exception {
        flame.get(dir.ordinal()).get(0).setX(x);
        //flame.get(dir.ordinal()).get(1).setX(x);
        //flame.get(dir.ordinal()).get(2).setX(x);
        flame.get(dir.ordinal()).get(0).setY(y);
        //flame.get(dir.ordinal()).get(1).setY(y);
        //flame.get(dir.ordinal()).get(2).setY(y);
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().add(flame.get(dir.ordinal()).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flame.get(dir.ordinal()).get(0));
                }));
        t.play();
    }
}
