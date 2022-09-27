package com.example.dbproject;

import java.util.ArrayList;

public class BombManager {
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    private int cntlimit = 1;

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }
    public void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
    }

    public void update() {
        for (Bomb bomb : bombs) {
            bomb.update();
        }
    }

    public void render() throws Exception {
        for (Bomb bomb : bombs) {
            bomb.render();
        }
    }

    public void increaseCntLimit() {
        cntlimit += 1;
    }

    public int countBomb() {
        return bombs.size();
    }
}
