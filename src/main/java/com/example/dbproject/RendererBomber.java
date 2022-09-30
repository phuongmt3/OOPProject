package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static com.example.dbproject.Mover.MovementType.DOWN;
import static com.example.dbproject.Mover.MovementType.UP;

public class RendererBomber extends Renderer {
    private ImageView bomberdown1, bomberdown2, bomberdown3, bomberdead;
    private ImageView bomberdead2, bomberdead3;

    private ImageView bomberup1, bomberup2, bomberup3;

    private ImageView bomberleft1, bomberleft2, bomberleft3;

    private ImageView bomberright1, bomberright2, bomberright3;



    public RendererBomber() {
        super();
        bomberdown1 = new ImageView(sheet);
        bomberdown2 = new ImageView(sheet);
        bomberdown3 = new ImageView(sheet);
        bomberdead = new ImageView(sheet);
        bomberdead2 = new ImageView(sheet);
        bomberdead3 = new ImageView(sheet);
        bomberup1 = new ImageView(sheet);
        bomberup2 = new ImageView(sheet);
        bomberup3 = new ImageView(sheet);
        bomberleft1 = new ImageView(sheet);
        bomberleft2 = new ImageView(sheet);
        bomberleft3 = new ImageView(sheet);
        bomberright1 = new ImageView(sheet);
        bomberright2 = new ImageView(sheet);
        bomberright3 = new ImageView(sheet);

        bomberdown1.setViewport(new Rectangle2D(side * 2, side * 0, side, side));
        bomberdown2.setViewport(new Rectangle2D(side * 2, side * 1, side, side));
        bomberdown3.setViewport(new Rectangle2D(side * 2, side * 2, side, side));
        bomberdead.setViewport(new Rectangle2D(side * 4, side * 2, side, side));
        bomberdead2.setViewport(new Rectangle2D(side * 5, side * 2, side, side));
        bomberdead3.setViewport(new Rectangle2D(side * 6, side * 2, side, side));
        bomberup1.setViewport(new Rectangle2D(side * 0, side * 0, side, side));
        bomberup2.setViewport(new Rectangle2D(side * 1, side * 0, side, side));
        bomberup2.setViewport(new Rectangle2D(side * 2, side * 0, side, side));
        bomberleft1.setViewport(new Rectangle2D(side * 0, side * 3, side, side));
        bomberleft2.setViewport(new Rectangle2D(side * 1, side * 3, side, side));
        bomberleft3.setViewport(new Rectangle2D(side * 2, side * 3, side, side));
        bomberright1.setViewport(new Rectangle2D(side * 1, side * 0, side, side));
        bomberright2.setViewport(new Rectangle2D(side * 1, side * 1, side, side));
        bomberright3.setViewport(new Rectangle2D(side * 1, side * 2, side, side));

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
        //if key down
        bomberdown1.setX(x);
        bomberdown2.setX(x);
        bomberdown3.setX(x);
        bomberdown1.setY(y);
        bomberdown2.setY(y);
        bomberdown3.setY(y);
        //if key up
        bomberup1.setX(x);
        bomberup2.setX(x);
        bomberup3.setX(x);
        bomberup1.setY(y);
        bomberup2.setY(y);
        bomberup3.setY(y);
        //if key left
        bomberleft1.setX(x);
        bomberleft2.setX(x);
        bomberleft3.setX(x);
        bomberleft1.setY(y);
        bomberleft2.setY(y);
        bomberleft3.setY(y);
        //if key right
        bomberright1.setX(x);
        bomberright2.setX(x);
        bomberright3.setX(x);
        bomberright1.setY(y);
        bomberright2.setY(y);
        bomberright3.setY(y);

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
        Main.rootMover.getChildren().remove(bomberup1);
        Main.rootMover.getChildren().remove(bomberup2);
        Main.rootMover.getChildren().remove(bomberup3);
        Main.rootMover.getChildren().remove(bomberleft1);
        Main.rootMover.getChildren().remove(bomberleft2);
        Main.rootMover.getChildren().remove(bomberleft3);
        Main.rootMover.getChildren().remove(bomberright1);
        Main.rootMover.getChildren().remove(bomberright2);
        Main.rootMover.getChildren().remove(bomberright3);

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
