package com.example.dbproject;

public class Enemy extends Mover{
    private int type; // 1 is Balloon, 2 is Oneal
    public Enemy(double x, double y, double speed, int type) {
        super(x, y, speed);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {}

    @Override
    public String getClassName() {
        return "Enemy";
    }
}
