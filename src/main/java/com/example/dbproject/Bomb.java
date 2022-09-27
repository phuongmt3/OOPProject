package com.example.dbproject;

import javafx.animation.AnimationTimer;

public class Bomb extends Entity{
    private long timer = 0;//handle increase time
    private long timeLimit = 15_000;
    BombManager manager;
    public Bomb(double x, double y, BombManager _manager) {
        super(x, y);
        manager = _manager;
    }

    public void update() throws Exception {
        //update timer
        timer += Main.timePerFrame;
        if (isExploded()) {
            renderer.deleteBomb();
            manager.removeBomb(this);
        }
    }

    @Override
    public void render() throws Exception {
        renderer.renderBomb(x, y);
    }

    public boolean isExploded() {
        return timer >= timeLimit;
    }

    @Override
    public String getClassName() {
        return "Bomb";
    }

    public long getTimer() {
        return timer;
    }

    public boolean equals(Bomb other) {
        return x == other.getX() && y == other.getY() && timer == other.getTimer();
    }
}
