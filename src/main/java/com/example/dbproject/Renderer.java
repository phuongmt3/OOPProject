package com.example.dbproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

public class Renderer {
    private static Image sheet;
    private boolean firstTime = true;
    private final double scale = 2.0;
    private final double side = 16 * scale;
    private ImageView bomberdown1, bomberdown2, bomberdown3;
    private ImageView grass, wall, brick, bombitem, flameitem, speeditem, portal;
    private ImageView bomb, bomb2, bomb3;
    private ImageView balloom;
    private ImageView oneal;
    private ArrayList<ArrayList<ImageView>> flame = new ArrayList<ArrayList<ImageView>>();
    Timeline t = new Timeline();
    public static enum Direction {
        center, left, right, up, down, middlerow, middlecol
    };

    public Renderer(Entity entity) {
        if (sheet == null) {
            try (InputStream stream = Files.newInputStream(Path.of("src/main/java/res/textures/SpriteSheet.png"))) {
                sheet = new Image(stream, 256 * scale, 256 * scale, true, false);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (entity instanceof Map) {
            grass = new ImageView(sheet);
            grass.setViewport(new Rectangle2D(side * 6, side * 0, side, side));
        }
        if (entity instanceof Bomber) {
            bomberdown1 = new ImageView(sheet);
            bomberdown2 = new ImageView(sheet);
            bomberdown3 = new ImageView(sheet);
            bomberdown1.setViewport(new Rectangle2D(side * 2, side * 0, side, side));
            bomberdown2.setViewport(new Rectangle2D(side * 2, side * 1, side, side));
            bomberdown3.setViewport(new Rectangle2D(side * 2, side * 2, side, side));
        }
        else if (entity instanceof Wall) {
            wall = new ImageView(sheet);
            wall.setViewport(new Rectangle2D(side * 5, side * 0, side, side));
        }
        else if (entity instanceof BombItem) {
            brick = new ImageView(sheet);
            bombitem = new ImageView(sheet);
            brick.setViewport(new Rectangle2D(side * 7, side * 1, side, side));
            //setViewport for bomb item
        }
        else if (entity instanceof SpeedItem) {
            brick = new ImageView(sheet);
            speeditem = new ImageView(sheet);
            brick.setViewport(new Rectangle2D(side * 7, side * 1, side, side));
            //setViewport for brick
        }
        else if (entity instanceof Portal) {
            brick = new ImageView(sheet);
            portal = new ImageView(sheet);
            brick.setViewport(new Rectangle2D(side * 7, side * 1, side, side));
            //setViewport for portal
        }
        else if (entity instanceof FlameItem) {
            brick = new ImageView(sheet);
            flameitem = new ImageView(sheet);
            brick.setViewport(new Rectangle2D(side * 7, side * 1, side, side));
            //setViewport for flame item
        }
        else if (entity instanceof Bomb) {
            bomb = new ImageView(sheet);
            bomb.setViewport(new Rectangle2D(side * 0, side * 3, side, side));
            bomb2 = new ImageView(sheet);
            bomb2.setViewport(new Rectangle2D(side * 1, side * 3, side, side));
            bomb3 = new ImageView(sheet);
            bomb3.setViewport(new Rectangle2D(side * 2, side * 3, side, side));
        }
        else if (entity instanceof Flame) {
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
        else if (entity instanceof Balloom) {
            balloom = new ImageView(sheet);
            balloom.setViewport(new Rectangle2D(side * 9, side * 0, side, side));
        }
        else if (entity instanceof Oneal) {
            oneal = new ImageView(sheet);
            oneal.setViewport(new Rectangle2D(side * 11, side * 0, side, side));
        }
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

    public void renderGrass(double x, double y) throws Exception {
        grass.setX(x);
        grass.setY(y);
        Main.rootMap.getChildren().add(grass);
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

    private void initBallom() { //add animation
        Main.rootMover.getChildren().add(balloom);
    }

    public void renderBallom(double x, double y) throws Exception {
        balloom.setX(x);
        balloom.setY(y);
        if (firstTime) {
            initBallom();
            firstTime = false;
        }
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

    //code renderWall, brick, item, portal
    public void renderWall(double x, double y) throws Exception {
        wall.setX(x);
        wall.setY(y);
        Main.rootMap.getChildren().add(wall);
    }
}