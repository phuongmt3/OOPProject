package com.example.dbproject;

public class Grass extends Map{
    public Grass(double x, double y) {
        super(x, y);
    }

    @Override
    public void render() throws Exception {
        renderer.renderGrass(x, y);
    }

    @Override
    public String getClassName() {
        return "Grass";
    }
}
