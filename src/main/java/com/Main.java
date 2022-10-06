package com;

import com.Entities.Bomb.Bomb;
import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Maps.*;
import com.Entities.Maps.Items.*;
import com.Entities.Movers.Bomber;
import com.Entities.Movers.Enemies.*;
import com.Entities.Movers.Mover;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

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
    public static final double winWidth = 1000, winHeight = 450;
    public static final double defaultSide = 32.0;
    public static final long timePerFrame = 10000;

    public void init(Stage primaryStage) throws Exception {
        stage = primaryStage;
        enemyManager = new EnemyManager();
        bombManager = new BombManager();
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
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.start();

        stage.setTitle("Bomberman");
        stage.setResizable(false);
        stage.show();
    }

    private void updateBomber(Mover.MovementType dir) {
        bomber.canMoveAndMove(dir);
        try {
            bomber.render(dir);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update() throws Exception {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (bomber.isDead()) {
                    if (event.getCode() == KeyCode.ESCAPE)
                        System.exit(0);
                    return;
                }
                switch (event.getCode()) {
                    case UP -> updateBomber(Bomber.MovementType.UP);
                    case DOWN -> updateBomber(Bomber.MovementType.DOWN);
                    case RIGHT -> updateBomber(Bomber.MovementType.RIGHT);
                    case LEFT -> updateBomber(Bomber.MovementType.LEFT);
                    case SPACE -> bombManager.addBomb(new Bomb(bomber.getX(), bomber.getY(), bombManager, bomber, enemyManager, map));
                    case ESCAPE -> System.exit(0);
                }
            }
        });
        bombManager.update();
        enemyManager.update();
        bomber.update();
        bombManager.render();
        bomber.render();
        enemyManager.render();
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
                                bomber = new Bomber(i * defaultSide, (cntLines - 1) * defaultSide, defaultSide / 5, map, bombManager, enemyManager);
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case '1' -> {
                                enemyManager.addEnemy(new Balloom(i * defaultSide, (cntLines - 1) * defaultSide, 1.5, map, bombManager, enemyManager, bomber));
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case '2' -> {
                                enemyManager.addEnemy(new Oneal(i * defaultSide, (cntLines - 1) * defaultSide, 2, map, bombManager, enemyManager, bomber));
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case '3' -> {
                                enemyManager.addEnemy(new Dahl(i * defaultSide, (cntLines - 1) * defaultSide, 2.25, map, bombManager, enemyManager, bomber));
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case '4' -> {
                                enemyManager.addEnemy(new Doria(i * defaultSide, (cntLines - 1) * defaultSide, 1, map, bombManager, enemyManager, bomber));
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case '5' -> {
                                enemyManager.addEnemy(new Ovape(i * defaultSide, (cntLines - 1) * defaultSide, 2, map, bombManager, enemyManager, bomber));
                                map.get(cntLines - 1).add(new Grass(i * defaultSide, (cntLines - 1) * defaultSide));
                            }
                            case '6' -> {
                                enemyManager.addEnemy(new Pass(i * defaultSide, (cntLines - 1) * defaultSide, 2.4, map, bombManager, enemyManager, bomber));
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
