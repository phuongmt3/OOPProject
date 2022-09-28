package com.example.dbproject;

public class Bomber extends Mover {
    public final double width = 20, height = 20;
    public Bomber(double x, double y, double speed) {
        super(x, y, speed);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() throws Exception {
        renderer.renderBomber(x, y);
    }

    @Override
    public String getClassName() {
        return "Bomber";
    }
}
