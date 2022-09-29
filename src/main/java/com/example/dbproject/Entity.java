package com.example.dbproject;

public abstract class Entity {
    protected double x, y, w = Main.defaultSide, h = Main.defaultSide; //by pixel coordinates
    protected Renderer renderer;

    public Entity(double x, double y) {
        if (x < 0 || y < 0 || x > Main.winWidth || y > Main.winHeight){
            System.out.println("Invalid coordinates");
            return;
        }
        this.x = x;
        this.y = y;
        renderer = new Renderer(this);
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        if (!validCoordination(x, this.y)) {
            System.out.println("Invalid coordinates");
            return;
        }
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        if (!validCoordination(this.x, y)) {
            System.out.println("Invalid coordinates");
            return;
        }
        this.y = y;
    }
    public double getW() {
        return w;
    }
    public void setW(double w) {
        this.w = w;
    }
    public double getH() {
        return h;
    }
    public void setH(double h) {
        this.h = h;
    }
    public String getClassName() {
        return "Entity";
    }
    public boolean checkCollision(Entity other) {
        int conditions = 0;
        if (other.getX() >= x && other.getX() < x + w)
            conditions++;
        else if (other.getX() + other.getW() > x && other.getX() + other.getW() <= x + w)
            conditions++;
        if (other.getY() >= y && other.getY() < y + h)
            conditions++;
        else if (other.getY() + other.getH() > y && other.getY() + other.getH() <= y + h)
            conditions++;
        return conditions == 2;
    }

    abstract public void render() throws Exception;
    public static boolean validCoordination(double x, double y) {
        return x >= 0 && x <= (Main.cols - 1) * Main.defaultSide
                && y >= 0 && y <= (Main.rows - 1) * Main.defaultSide;
    }
}