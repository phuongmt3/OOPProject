package com.example.dbproject;

public class Bomber extends Mover {

    public Bomber(double x, double y, double speed) {
        super(x, y, speed);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {}
    public void putBomb() {}

    @Override
    public String getClassName() {
        return "Bomber";
    }
}
