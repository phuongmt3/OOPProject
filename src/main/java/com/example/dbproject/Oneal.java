package com.example.dbproject;

import java.util.ArrayList;

public class Oneal extends Enemy {
    private MovementType direction = MovementType.UP;
    public Oneal(double x, double y, double speed, ArrayList<ArrayList<Entity>> map, BombManager bombManager) {
        super(x, y, speed, map, bombManager);
    }

    @Override
    public void render() throws Exception {
        renderer.renderOneal(x, y);
    }

    @Override
    public void update() {
        if (direction == MovementType.UP && !canMoveAndMove(MovementType.UP)) {
            canMoveAndMove(MovementType.DOWN);
            direction = MovementType.DOWN;
        }
        else if (direction == MovementType.DOWN && !canMoveAndMove(MovementType.DOWN)) {
            canMoveAndMove(MovementType.UP);
            direction = MovementType.UP;
        }
    }
}
