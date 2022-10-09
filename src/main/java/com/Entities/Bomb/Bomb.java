package com.Entities.Bomb;

import com.Entities.Entity;
import com.Entities.Movers.Bomber;
import com.Entities.Movers.Enemies.EnemyManager;
import com.Renderer.RendererBomb;

import java.util.ArrayList;

public class Bomb extends Entity {
    private RendererBomb renderer = new RendererBomb();
    private int timer = 0;
    private BombManager manager;
    private FlameManager flame;

    public Bomb(double x, double y, BombManager _manager,
                Bomber _bomber, EnemyManager _enemyManager, ArrayList<ArrayList<Entity>> _map) {
        super(x, y);
        manager = _manager;
        flame = new FlameManager(x, y, _bomber, _enemyManager, _map, _manager);
    }

    public void update() throws Exception {
        timer++;
        if (isExploded()) {
            renderer.deleteBomb();
            manager.removeBomb(this);
            flame.updateInfluence();
            flame.render();
        }
    }

    @Override
    public void render() throws Exception {
        renderer.renderBomb(x, y);
    }

    public boolean isExploded() {
        return timer >= BombManager.timeLimit;
    }

    public void setConsecutiveTimer(Bomb otherBomb) {
        timer = BombManager.timeLimit - otherBomb.countdown() - 15;
    }

    @Override
    public String getClassName() {
        return "Bomb";
    }

    public int countdown() {
        return BombManager.timeLimit - timer;
    }

}