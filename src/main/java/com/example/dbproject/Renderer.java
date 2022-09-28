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
import java.util.Set;

public class Renderer {
    private static Image sheet;
    private boolean firstTime = true;
    private final double scale = 2.0;
    private final double side = 16 * scale;
    private ImageView bomberdown1, bomberdown2, bomberdown3;
    private ImageView grass, wall, brick, bombitem, flameitem, speeditem, portal;
    private ImageView bomb, flamecenter, flameleft, flameright, flameup, flamedown, flamemiddle;

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
        }
        else if (entity instanceof Flame) {
            flamecenter = new ImageView(sheet);
            flameleft = new ImageView(sheet);
            flameright = new ImageView(sheet);
            flameup = new ImageView(sheet);
            flamedown = new ImageView(sheet);
            flamemiddle = new ImageView(sheet);
            flamecenter.setViewport(new Rectangle2D(side * 0, side * 4, side, side));
            flameleft.setViewport(new Rectangle2D(side * 0, side * 4, side, side));
            flameright.setViewport(new Rectangle2D(side * 0, side * 4, side, side));
            flameup.setViewport(new Rectangle2D(side * 0, side * 4, side, side));
            flamedown.setViewport(new Rectangle2D(side * 0, side * 4, side, side));
            flamemiddle.setViewport(new Rectangle2D(side * 0, side * 4, side, side));
            //setViewport những flame còn lại
        }
    }

    private void initBomber() {
        if (firstTime) {
            Main.rootMover.getChildren().add(bomberdown1);
            Timeline t = new Timeline();
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
        firstTime = false;
    }
    public void renderBomber(double x, double y) throws Exception {
        bomberdown1.setX(x);
        bomberdown2.setX(x);
        bomberdown3.setX(x);
        bomberdown1.setY(y);
        bomberdown2.setY(y);
        bomberdown3.setY(y);
        initBomber();
    }

    public void renderGrass(double x, double y) throws Exception {
        grass.setX(x);
        grass.setY(y);
        Main.rootMap.getChildren().add(grass);
    }

    private void initBomb() {   // làm hiệu ứng cho bomb tương tự như bomber
        if (firstTime) {
            Main.rootBomb.getChildren().add(bomb);
        }
        firstTime = false;
    }
    public void renderBomb(double x, double y) throws Exception {
        bomb.setX(x);
        bomb.setY(y);
        initBomb();
    }

    public void deleteBomb() throws Exception {
        Main.rootBomb.getChildren().remove(bomb);
    }

    public void renderFlame(Flame flame) throws Exception {
        flamecenter.setX(flame.getX());
        flamecenter.setY(flame.getY());
        flameleft.setX(flame.getXleft());
        flameleft.setY(flame.getY());
        flameright.setX(flame.getXright());
        flameright.setY(flame.getY());
        flameup.setX(flame.getX());
        flameup.setY(flame.getYup());
        flamedown.setX(flame.getX());
        flamedown.setY(flame.getYdown());
        System.out.println(flameleft.getX() + " " +flameleft.getY());
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().add(flamecenter);
                    Main.rootBomb.getChildren().add(flameleft);
                    Main.rootBomb.getChildren().add(flameright);
                    Main.rootBomb.getChildren().add(flameup);
                    Main.rootBomb.getChildren().add(flamedown);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                (ActionEvent event) -> {
                    Main.rootBomb.getChildren().remove(flamecenter);
                    Main.rootBomb.getChildren().remove(flameleft);
                    Main.rootBomb.getChildren().remove(flameright);
                    Main.rootBomb.getChildren().remove(flameup);
                    Main.rootBomb.getChildren().remove(flamedown);
                }));
        t.play();
    }
    //code renderWall, brick, item, portal

}