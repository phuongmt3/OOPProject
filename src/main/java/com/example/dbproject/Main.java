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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {
    static public Stage stage;
    private ArrayList<ArrayList<Entity>> map = new ArrayList<ArrayList<Entity>>();
    private Bomber bomber;
    private BombManager bombManager;
    private EnemyManager enemyManager;
    private Scene scene;
    private Group root;
    public static Group rootMap, rootMover;
    public static int level, rows, cols;
    public static final double winWidth = 800, winHeight = 600;

    public void init(Stage primaryStage) throws Exception {
        stage = primaryStage;
        bomber = new Bomber(0, 0, 5);
        bombManager = new BombManager();
        enemyManager = new EnemyManager();
        //read file input -> init map
        readFile("src/main/java/res/levels/Level1.txt");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        rootMap = new Group();
        rootMover = new Group();
        root = new Group(rootMap, rootMover);
        scene = new Scene(root, winWidth, winHeight, Color.DARKGRAY);
        primaryStage.setScene(scene);
        init(primaryStage);
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
            private final long timePerFrame =  1_000;
            @Override
            public void handle(long now) {
                if (now - lastTime > timePerFrame) {
                    lastTime = now;
                    try {
                        update();
                        //handle endgame
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.start();

        stage.setTitle("Bomberman");
        stage.show();
    }
    public void update() throws Exception {
        //below is just example of handling keyboards
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
        bomber.render();
    }

    @Override
    public void stop() {
        //release resources
    }

    public static void main(String[] args) {
        launch();
    }

    private void readFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            int cntLines = 0;
            while ((line = reader.readLine())!= null) {
                if (cntLines == 0) {
                    String[] split = line.split("\\s");
                    level = Integer.parseInt(split[0]);
                    rows = Integer.parseInt(split[1]);
                    cols = Integer.parseInt(split[2]);

                    for (int i = 0; i < rows; i++) {
                        map.add(new ArrayList<Entity>());
                    }
                }
                else {
                    for (int i = 0; i < cols; i++) {
                        char c = line.charAt(i);
                        switch (c) {
                            case '#' -> map.get(cntLines - 1).add(new Wall(cntLines - 1, i));
                            case '*' -> map.get(cntLines - 1).add(new Brick(cntLines - 1, i, false));
                            case 'x' -> map.get(cntLines - 1).add(new Portal(cntLines - 1, i));
                            case ' ' -> map.get(cntLines - 1).add(new Grass(cntLines - 1, i));
                            case 'p' -> {
                                bomber.setX((cntLines - 1) * bomber.getWidth());
                                bomber.setY(i * bomber.getHeight());
                                map.get(cntLines - 1).add(new Grass(cntLines - 1, i));
                            }
                            case '1' -> {
                                enemyManager.addEnemy(new Enemy(cntLines - 1, i, 0.2, 1));
                                map.get(cntLines - 1).add(new Grass(cntLines - 1, i));
                            }
                            case '2' -> {
                                enemyManager.addEnemy(new Enemy(cntLines - 1, i, 10, 2));
                                map.get(cntLines - 1).add(new Grass(cntLines - 1, i));
                            }
                            case 'b' -> map.get(cntLines - 1).add(new BombItem(cntLines - 1, i));
                            case 'f' -> map.get(cntLines - 1).add(new FlameItem(cntLines - 1, i));
                            case 's' -> map.get(cntLines - 1).add(new SpeedItem(cntLines - 1, i));
                        }
                    }
                }
                if (rows == cntLines)
                    break;
                cntLines++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

//github
//handle end game
