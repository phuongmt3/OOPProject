package com.example.dbproject;

import java.util.ArrayList;

public class Bomber extends Mover {
    public Bomber(double x, double y, double speed, ArrayList<ArrayList<Entity>> map, BombManager bombManager) {
        super(x, y, speed, map, bombManager);
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