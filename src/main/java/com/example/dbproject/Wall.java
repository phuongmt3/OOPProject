package com.example.dbproject;

public class Wall extends Map {
    public Wall(double x, double y) {
        super(x, y);
    }

    @Override
    public void render() throws Exception {
        renderer.renderWall(x, y);
    }

    @Override
    public String getClassName() {
        return "Wall";
    }
}
