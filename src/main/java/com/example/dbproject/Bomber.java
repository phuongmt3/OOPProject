package com.example.dbproject;

public class Bomber extends Entity {
    private boolean isDead = false;
    private final double speed = 20;
    enum MovementType {
        LEFT(-1, 0), RIGHT(1, 0),
        UP(0, -1), DOWN(0, 1);
        public final int x, y;
        MovementType(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public Bomber(double x, double y) {
        super(x, y);
    }

    public void update() {

    }

    public void move(MovementType type) {
        setX(getX() + type.x * speed);
        setY(getY() + type.y * speed);
    }

    public boolean isDead() {
        return isDead;
    }
}
