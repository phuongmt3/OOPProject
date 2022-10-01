package com.Renderer;

import com.*;
import com.Entities.Entity;
import com.Entities.Maps.Brick;
import com.Entities.Maps.Items.BombItem;
import com.Entities.Maps.Items.FlameItem;
import com.Entities.Maps.Items.Portal;
import com.Entities.Maps.Items.SpeedItem;
import com.Entities.Maps.Wall;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RendererMap extends Renderer {
    private ImageView grass, wall, brick, bombitem, flameitem, speeditem, portal;
    private ImageView brick1, brick2, brick3;

    public RendererMap(Entity entity) {
        super();
        grass = new ImageView(sheet);
        grass.setViewport(new Rectangle2D(side * 6, side * 0, side, side));
        if (entity instanceof Wall) {
            wall = new ImageView(sheet);
            wall.setViewport(new Rectangle2D(side * 5, side * 0, side, side));
        }
        else if (entity instanceof Brick) {
            brick = new ImageView(sheet);
            brick.setViewport(new Rectangle2D(side * 7, side * 0, side, side));
            brick1 = new ImageView(sheet);
            brick1.setViewport(new Rectangle2D(side * 7, side * 1, side, side));
            brick2 = new ImageView(sheet);
            brick2.setViewport(new Rectangle2D(side * 7, side * 2, side, side));
            brick3 = new ImageView(sheet);
            brick3.setViewport(new Rectangle2D(side * 7, side * 3, side, side));
        }
        if (entity instanceof BombItem) {
            bombitem = new ImageView(sheet);
            bombitem.setViewport(new Rectangle2D(side * 0, side * 10, side, side));
        }
        else if (entity instanceof SpeedItem) {
            speeditem = new ImageView(sheet);
            speeditem.setViewport(new Rectangle2D(side * 2, side * 10, side, side));
        }
        else if (entity instanceof Portal) {
            portal = new ImageView(sheet);
            portal.setViewport(new Rectangle2D(side * 4, side * 0, side, side));
        }
        else if (entity instanceof FlameItem) {
            flameitem = new ImageView(sheet);
            flameitem.setViewport(new Rectangle2D(side * 1, side * 10, side, side));
        }
    }

    public void renderGrass(double x, double y) throws Exception {
        grass.setX(x);
        grass.setY(y);
        Main.rootMap.getChildren().add(grass);
    }

    public void renderWall(double x, double y) throws Exception {
        wall.setX(x);
        wall.setY(y);
        Main.rootMap.getChildren().add(wall);
    }

    public void renderBrick(double x, double y) throws Exception {
        brick.setX(x);
        brick.setY(y);
        Main.rootMap.getChildren().add(brick);
    }

    public void deleteBrick(double x, double y) throws Exception {
        Main.rootMap.getChildren().remove(brick);
        brick1.setX(x);
        brick1.setY(y);
        brick2.setX(x);
        brick2.setY(y);
        brick3.setX(x);
        brick3.setY(y);
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(Duration.millis(0),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().add(brick1);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(150),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(brick1);
                    Main.rootMover.getChildren().add(brick2);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(300),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(brick2);
                    Main.rootMover.getChildren().add(brick3);
                }));
        t.getKeyFrames().add(new KeyFrame(Duration.millis(450),
                (ActionEvent event) -> {
                    Main.rootMover.getChildren().remove(brick3);
                }));
        t.play();
    }

    public void renderBombItem(double x, double y) throws Exception {
        bombitem.setX(x);
        bombitem.setY(y);
        Main.rootMap.getChildren().add(bombitem);
    }

    public void deleteBombItem() {
        Main.rootMap.getChildren().remove(bombitem);
    }

    public void renderFlameItem(double x, double y) throws Exception {
        flameitem.setX(x);
        flameitem.setY(y);
        Main.rootMap.getChildren().add(flameitem);
    }

    public void deleteFlameItem() {
        Main.rootMap.getChildren().remove(flameitem);
    }

    public void renderSpeedItem(double x, double y) throws Exception {
        speeditem.setX(x);
        speeditem.setY(y);
        Main.rootMap.getChildren().add(speeditem);
    }

    public void deleteSpeedItem() {
        Main.rootMap.getChildren().remove(speeditem);
    }

    public void renderPortal(double x, double y) throws Exception {
        portal.setX(x);
        portal.setY(y);
        Main.rootMap.getChildren().add(portal);
    }
}
