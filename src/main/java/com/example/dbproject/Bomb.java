package com.example.dbproject;

public class Bomb extends Entity{
    private long timer = 0;
    private long timeLimit = 2_000_000_000;
    public Bomb(double x, double y) {
        super(x, y);
    }

    public void update() {
        //update timer
    }

    @Override
    public void render() {}

    public boolean isExploded() {
        return timer >= timeLimit;
    }

    @Override
    public String getClassName() {
        return "Bomb";
    }
}
