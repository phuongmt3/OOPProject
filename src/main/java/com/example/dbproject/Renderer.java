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
    private ImageView bomberdown1, bomberdown2, bomberdown3;
    private ImageView grass, wall, brick, bombitem, flameitem, speeditem, portal;

    public Renderer(Entity entity) {
        if (sheet == null) {
            try (InputStream stream = Files.newInputStream(Path.of("src/main/java/res/textures/SpriteSheet.png"))) {
                sheet = new Image(stream, 512, 512, true, false); //add sizeMultiplier
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (entity instanceof Bomber) {
            bomberdown1 = new ImageView(sheet);
            bomberdown2 = new ImageView(sheet);
            bomberdown3 = new ImageView(sheet);
            bomberdown1.setViewport(new Rectangle2D(entity.width * 2, entity.height * 0, entity.width, entity.height));
            bomberdown2.setViewport(new Rectangle2D(entity.width * 2, entity.height * 1, entity.width, entity.height));
            bomberdown3.setViewport(new Rectangle2D(entity.width * 2, entity.height * 2, entity.width, entity.height));
        }
        else if (entity instanceof Grass) {
            grass = new ImageView(sheet);
            grass.setViewport(new Rectangle2D(entity.width * 6, entity.height * 0, entity.width, entity.height));
        }
        else if (entity instanceof Wall) {
            wall = new ImageView(sheet);
            wall.setViewport(new Rectangle2D(entity.width * 5, entity.height * 0, entity.width, entity.height));
        }
        else if (entity instanceof Brick) {
            brick = new ImageView(sheet);
            bombitem = new ImageView(sheet);
            flameitem = new ImageView(sheet);
            speeditem = new ImageView(sheet);
            portal = new ImageView(sheet);
            brick.setViewport(new Rectangle2D(entity.width * 7, entity.height * 1, entity.width, entity.height));
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
    //code renderWall, brick, item, portal

}
