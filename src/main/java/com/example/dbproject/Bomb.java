package com.example.dbproject;

public class Bomb extends Entity{
    private long timer = 0;//handle increase time
    private long timeLimit = 1_000_000_000;
    public Bomb(double x, double y) {
        super(x, y);
    }

    public void update() {
        //update timer
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
}
