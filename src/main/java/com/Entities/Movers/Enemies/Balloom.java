package com.Entities.Movers.Enemies;

import com.Entities.Bomb.BombManager;
import com.Entities.Entity;
import com.Entities.Movers.Mover;
import com.Renderer.RendererBalloom;
import com.Entities.Movers.Enemies.Enemy;
import com.Entities.Movers.Enemies.EnemyManager;

import java.util.ArrayList;

public class Balloom extends Enemy {
    private RendererBalloom renderer = new RendererBalloom();
    private Mover.MovementType direction = Mover.MovementType.RIGHT;

    public Balloom(double x, double y, double speed, ArrayList<ArrayList<Entity>> map,
                   BombManager bombManager, EnemyManager enemyManager) {
        super(x, y, speed, map, bombManager, enemyManager);
    }

    @Override
    public void update() {
        if (isDead()) {
            renderer.deleteBalloom();
            enemyManager.removeEnemy(this);
        }
        if (direction == Mover.MovementType.RIGHT && !canMoveAndMove(Mover.MovementType.RIGHT)) {
            canMoveAndMove(Mover.MovementType.LEFT);
            direction = Mover.MovementType.LEFT;
        }
        else if (direction == Mover.MovementType.LEFT && !canMoveAndMove(Mover.MovementType.LEFT)) {
            canMoveAndMove(Mover.MovementType.RIGHT);
            direction = Mover.MovementType.RIGHT;
        }
        if (!isDead)
            renderer.startAnimation(direction);
    }

    @Override
    public void render() throws Exception {
        renderer.renderBallom(x, y);
    }
}
