package com.example.dbproject;

import java.util.ArrayList;

public class BombManager {
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    private int cntlimit = 1;

    public void addBomb(Bomb bomb) {
        if (bombs.size() == cntlimit)
            return;
        bombs.add(bomb);
    }
    public void removeBomb(Bomb bomb) {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).equals(bomb)) {
                bombs.remove(i);
                return;
            }
        }
    }

    public void update() throws Exception {
        int oldBombsCount = bombs.size();
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
            if (oldBombsCount != bombs.size())
                i--;
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
