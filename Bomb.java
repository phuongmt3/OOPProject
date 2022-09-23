package com.example.dbproject;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Bomb extends Entity{
    private long timer = 0;
    private Entity entity;
    private long timeLimit = 2_000_000_000;
    public Bomb(double x, double y) {
        super(x, y);
    }
    private static ImageView bombdown1, bombdown2, bombdown3;
    private static Image sheet;
    public void update() {
        //update timer
            while(true){
                Bomb bomb = new Bomb(10,15);
                bomb.render();
            }

    }

    @Override
    public void render() {
        if (sheet == null) {
            try (InputStream stream = Files.newInputStream(Path.of("src/main/java/res/textures/SpriteSheet.png"))) {
                sheet = new Image(stream, 512, 512, true, false); //add sizeMultiplier
                bombdown1 = new ImageView(sheet);
                bombdown2 = new ImageView(sheet);
                bombdown3 = new ImageView(sheet);
                bombdown1.setViewport(new Rectangle2D(entity.width * 0, entity.height * 3, entity.width, entity.height));
                bombdown2.setViewport(new Rectangle2D(entity.width * 1, entity.height * 3, entity.width, entity.height));
                bombdown3.setViewport(new Rectangle2D(entity.width * 2, entity.height * 3, entity.width, entity.height));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean isExploded() {
        return timer >= timeLimit;
    }

    @Override
    public String getClassName() {
        return "Bomb";
    }
}
