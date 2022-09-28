package com.example.dbproject;

import java.util.ArrayList;

public class BombManager {
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    private int cntlimit = 3;

    public void addBomb(Bomb bomb) {
        if (bombs.size() == cntlimit)
            return;
        bombs.add(bomb);
    }
    public void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
    }

    public void update() throws Exception {
        int oldBombsCount = bombs.size();
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
            if (oldBombsCount != bombs.size()) {
                i--;
                oldBombsCount = bombs.size();
            }
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

    public Bomb getBomb(int id) {
        return bombs.get(id);
    }
}
