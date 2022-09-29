package com.example.dbproject;

import java.util.ArrayList;

public abstract class Enemy extends Mover{
    public Enemy(double x, double y, double speed, ArrayList<ArrayList<Entity>> map, BombManager bombManager) {
        super(x, y, speed, map, bombManager);
    }

    @Override
    public String getClassName() {
        return "Enemy";
    }

}
