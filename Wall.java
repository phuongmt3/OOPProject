package com.example.dbproject;

public class Wall extends Map {
    public Wall(double x, double y) {
        super(x, y);
    }

    @Override
    public void render() {}

    @Override
    public String getClassName() {
        return "Wall";
    }
}
