package com.example.dbproject;

public abstract class Entity {
    protected double x, y; //by pixel coordinates
    protected Renderer renderer;
    protected final double width = 32, height = 32;

    public Entity(double x, double y) {
        if (x < 0 || y < 0 || x > Main.winWidth || y > Main.winHeight){
            System.out.println("Invalid coordinates");
            return;
        }
        this.x = x * width;
        this.y = y * height;
        renderer = new Renderer(this);
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
    abstract public void render() throws Exception;
}
