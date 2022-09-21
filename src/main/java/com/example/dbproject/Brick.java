package com.example.dbproject;

public class Brick extends Map {
    protected boolean isExposed = false;
    protected boolean hasItem;
    public Brick(double x, double y, boolean hasItem) {
        super(x, y);
        this.hasItem = hasItem;
    }

    @Override
    public void render() {}

    public void update() {
        if (!hasItem && isExposed); //render grass
        //else render brick

    }

    @Override
    public String getClassName() {
        return "Brick";
    }
}
