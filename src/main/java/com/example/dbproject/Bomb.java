package com.example.dbproject;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class Bomb extends Entity{
    private long timer = 0;//handle increase time
    private long timeLimit = Main.timePerFrame * 500;
    private BombManager manager;
    private FlameManager flame;

    public Bomb(double x, double y, BombManager _manager,
                Bomber _bomber, EnemyManager _enemyManager, ArrayList<ArrayList<Entity>> _map) {
        super(x, y);
        manager = _manager;
        flame = new FlameManager(x, y, _bomber, _enemyManager, _map, _manager);
    }

    public void update() throws Exception {
        //update timer
        timer += Main.timePerFrame;
        if (isExploded()) {
            renderer.deleteBomb();
            manager.removeBomb(this);
            flame.updateInfluence();
            flame.render();
        }
    }

    @Override
    public void render() throws Exception {
        renderer.renderBomb(x, y);
    }

    public boolean isExploded() {
        return timer >= timeLimit;
    }

    public void explode() {
        timer = timeLimit;
    }

    @Override
    public String getClassName() {
        return "Bomb";
    }

    public long getTimer() {
        return timer;
    }
}