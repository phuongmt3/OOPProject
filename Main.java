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
    public static Group rootMap, rootMover, rootBomb;
    public static int level, rows, cols;
    public static final double winWidth = 1000, winHeight = 600;
    public static final double defaultSide = 32.0;
    public static final long timePerFrame = 100;

    public void init(Stage primaryStage) throws Exception {
        stage = primaryStage;
        bomber = new Bomber(0, 0, 2);
        bombManager = new BombManager();
        enemyManager = new EnemyManager();
        //read file input -> init map
        readFile("src/main/java/res/levels/Level1.txt");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        rootMap = new Group();
        rootMover = new Group();
        rootBomb = new Group();
        root = new Group(rootMap, rootBomb, rootMover);
        scene = new Scene(root, winWidth, winHeight, Color.DARKGRAY);
        primaryStage.setScene(scene);
        init(primaryStage);

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                map.get(i).get(j).render();

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
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
                    case UP -> bomber.move(Bomber.MovementType.UP, map);
                    case DOWN -> bomber.move(Bomber.MovementType.DOWN, map);
                    case RIGHT -> bomber.move(Bomber.MovementType.RIGHT, map);
                    case LEFT -> bomber.move(Bomber.MovementType.LEFT, map);
                    case SPACE -> bombManager.addBomb(new Bomb(bomber.getX(), bomber.getY(), bombManager));
                    case ESCAPE -> System.exit(0);
                }
            }
        });
        bombManager.update();
        bomber.render();
        bombManager.render();
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
                            case '#' -> map.get(cntLines - 1).add(new Wall(i * defaultSide, (cntLines - 1) * defaultSide));
                            case '*' -> map.get(cntLines - 1).add(new Brick(i * defaultSide, (cntLines - 1) * defaultSide, false));
                            case 'x' -> map.get(cntLines - 1).add(new Portal(i * defaultSide, (cntLines - 1) * defaultSide));
                            case ' ' -> map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            case 'p' -> {
                                bomber.setX(i * defaultSide);
                                bomber.setY((cntLines - 1) * defaultSide);
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case '1' -> {
                                enemyManager.addEnemy(new Enemy(i * defaultSide, (cntLines - 1) * defaultSide, 0.2, 1));
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case '2' -> {
                                enemyManager.addEnemy(new Enemy(i * defaultSide, (cntLines - 1) * defaultSide, 10, 2));
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case 'b' -> map.get(cntLines - 1).add(new BombItem(i * defaultSide, (cntLines - 1) * defaultSide));
                            case 'f' -> map.get(cntLines - 1).add(new FlameItem(i * defaultSide, (cntLines - 1) * defaultSide));
                            case 's' -> map.get(cntLines - 1).add(new SpeedItem(i * defaultSide, (cntLines - 1) * defaultSide));
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

//handle end game
