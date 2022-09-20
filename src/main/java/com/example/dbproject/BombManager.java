package com.example.dbproject;

import java.util.ArrayList;

public class BombManager {
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    private int cntlimit = 1;

    public void addBomb(Bomb bomb) {}
    public void removeBomb(Bomb bomb) {}

    public void update() {
        for (Bomb bomb : bombs) {
            bomb.update();
        }
    }

    public void render() {
        for (Bomb bomb : bombs) {
            bomb.render();
        }
    }

    public void increaseCntLimit() {
        cntlimit += 1;
    }
}
