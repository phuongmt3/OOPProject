package com.example.dbproject;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SpriteSheet {
    private ImageView imageView = new ImageView();
    private Image sheet;
    private Group root;
    private Scene scene;

    public SpriteSheet(Stage stage) {
        try (InputStream stream = Files.newInputStream(Path.of("src/main/java/res/textures/SpriteSheet.png"))) {
            sheet = new Image(stream, 512, 512, true, false);
            imageView.setImage(sheet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        scene = stage.getScene();
        root = (Group) scene.getRoot();
        root.getChildren().add(imageView);
    }

    public void renderBomber(Bomber bomber) throws Exception {
        imageView.setViewport(new Rectangle2D(32 * 3, 0, 32, 32));
        imageView.setX(bomber.getX());
        imageView.setY(bomber.getY());
    }

}
