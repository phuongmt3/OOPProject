package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class RendererFlame extends Renderer {
    private ArrayList<ArrayList<ImageView>> flame = new ArrayList<ArrayList<ImageView>>();
    private ArrayList<ArrayList<ImageView>> flame1 = new ArrayList<ArrayList<ImageView>>();
    private ArrayList<ArrayList<ImageView>> flame2 = new ArrayList<ArrayList<ImageView>>();
    public void setFrames(ArrayList<ArrayList<ImageView>> flame, int r, int c) {
        flame.get(Direction.center.ordinal()).get(0).setViewport(new Rectangle2D(side * 0, side * c, side, side)); //5r
        flame.get(Direction.left.ordinal()).get(0).setViewport(new Rectangle2D(side * 0, side * (c+3), side, side)); //8r
        flame.get(Direction.right.ordinal()).get(0).setViewport(new Rectangle2D(side * 2, side * (c+3), side, side));//8r
        flame.get(Direction.up.ordinal()).get(0).setViewport(new Rectangle2D(side * r, side * 4, side, side));//2l
        flame.get(Direction.down.ordinal()).get(0).setViewport(new Rectangle2D(side * r, side * 6, side, side));//2l
        flame.get(Direction.middlecol.ordinal()).get(0).setViewport(new Rectangle2D(side * r, side * 5, side, side));//2l
        flame.get(Direction.middlerow.ordinal()).get(0).setViewport(new Rectangle2D(side * 1, side * (c+3), side, side));//8r
    }

    public RendererFlame() {
        super();
        for (int i = 0; i < 7; i++) {
            flame.add(new ArrayList<ImageView>());
            flame.get(i).add(new ImageView(sheet));
            flame.get(i).add(new ImageView(sheet));
            flame.get(i).add(new ImageView(sheet));
            flame1.add(new ArrayList<ImageView>());
            flame1.get(i).add(new ImageView(sheet));
            flame1.get(i).add(new ImageView(sheet));
            flame1.get(i).add(new ImageView(sheet));
            flame2.add(new ArrayList<ImageView>());
            flame2.get(i).add(new ImageView(sheet));
            flame2.get(i).add(new ImageView(sheet));
            flame2.get(i).add(new ImageView(sheet));
        }
        //add 2 remainding frames    ===> FAILED.
        setFrames(flame,1,4);
        setFrames(flame1,2,5);
        setFrames(flame2,3,6);

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
       /* t.getKeyFrames().add(new KeyFrame(Duration.millis(2000),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().add(flame1.get(dir.ordinal()).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(3000),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flame1.get(dir.ordinal()).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(4000),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().add(flame2.get(dir.ordinal()).get(0));
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(5000),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flame2.get(dir.ordinal()).get(0));
                }));*/
        t.play();
    }
}
