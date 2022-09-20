package com.example.dbproject;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Main extends Application {
    static public SpriteSheet renderer;
    private Bomber bomber;
    private Scene scene;
    private Group root;

    @Override
    public void init() {
        bomber = new Bomber(50, 50);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        root = new Group();
        scene = new Scene(root, 800, 600, Color.DARKGRAY);
        primaryStage.setScene(scene);
        renderer = new SpriteSheet(primaryStage);
        init();
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
            final private long timePerFrame =  10_000_000;
            @Override
            public void handle(long now) {
                if (now - lastTime > timePerFrame) {
                    lastTime = now;
                    try {
                        update();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.start();

        primaryStage.setTitle("Bomberman");
        primaryStage.show();
    }
    public void update() throws Exception {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP -> bomber.move(Bomber.MovementType.UP);
                    case DOWN -> bomber.move(Bomber.MovementType.DOWN);
                    case RIGHT -> bomber.move(Bomber.MovementType.RIGHT);
                    case LEFT -> bomber.move(Bomber.MovementType.LEFT);
                    case ESCAPE -> System.exit(0);
                }
            }
        });
        renderer.renderBomber(bomber);
    }

    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        launch();
    }
}