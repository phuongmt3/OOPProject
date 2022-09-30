package com.example.dbproject;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

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
        else if (entity instanceof BombItem) {
            brick = new ImageView(sheet);
            bombitem = new ImageView(sheet);
            brick.setViewport(new Rectangle2D(side * 7, side * 1, side, side));
            //setViewport for bomb item
            brick2.setViewport(new Rectangle2D(side * 7, side * 2, side, side));
            brick3.setViewport(new Rectangle2D(side * 7, side * 3, side, side));

        }
        else if (entity instanceof SpeedItem) {
            brick = new ImageView(sheet);
            speeditem = new ImageView(sheet);
            brick.setViewport(new Rectangle2D(side * 7, side * 1, side, side));


            //setViewport for brick
            brick2.setViewport(new Rectangle2D(side * 7, side * 2, side, side));
            brick3.setViewport(new Rectangle2D(side * 7, side * 3, side, side));
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
        else if (entity instanceof Brick) {
            brick1 = new ImageView(sheet);
            brick1.setViewport(new Rectangle2D(side * 7, side * 0, side, side));
            // deal with rendering brick when hits bombs.
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


}
