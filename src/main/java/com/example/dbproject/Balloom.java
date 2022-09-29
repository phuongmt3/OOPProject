package com.example.dbproject;

import java.util.ArrayList;

public class Balloom extends Enemy {
    private MovementType direction = MovementType.RIGHT;

    public Balloom(double x, double y, double speed, ArrayList<ArrayList<Entity>> map, BombManager bombManager) {
        super(x, y, speed, map, bombManager);
    }

    @Override
    public void update() {
        //auto move
        if (direction == MovementType.RIGHT && !canMoveAndMove(MovementType.RIGHT)) {
            canMoveAndMove(MovementType.LEFT);
            direction = MovementType.LEFT;
        }
        else if (direction == MovementType.LEFT && !canMoveAndMove(MovementType.LEFT)) {
            canMoveAndMove(MovementType.RIGHT);
            direction = MovementType.RIGHT;
        }
    }

    @Override
    public void render() throws Exception {
        renderer.renderBallom(x, y);
    }
}
