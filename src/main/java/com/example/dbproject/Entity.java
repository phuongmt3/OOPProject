package com.example.dbproject;

public abstract class Entity {
    private double x, y; //by pixel coordinates
    private final double width = 32, height = 32;

    public Entity(double x, double y) {
        this.x = x * width;
        this.y = y * height;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public String getClassName() {
        return "Entity";
    }
    public boolean checkCollision(Entity other) {
        return false;
    }
}
