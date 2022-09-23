package com.example.dbproject;

abstract public class Mover extends Entity {
    protected boolean isDead = false;
    protected final double speed;
    enum MovementType {
        LEFT(-1, 0), RIGHT(1, 0),
        UP(0, -1), DOWN(0, 1);
        public final int x, y;
        MovementType(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public Mover(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
    }
    public void update() {}

    public void move(MovementType type) {
        double newX = x + type.x * speed;
        double newY = y + type.y * speed;
        newX = Math.max(0, Math.min(newX, (Main.cols - 1) * width));
        newY = Math.max(0, Math.min(newY, (Main.rows - 1) * height));
        setX(newX);
        setY(newY);
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public String getClassName() {
        return "Mover";
    }
}
