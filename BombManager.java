package com.example.dbproject;

import java.util.ArrayList;

import static com.example.dbproject.Sprite.*;

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
            bomb.loadAnimated(bomb_0, bomb_1, bomb_2);
        }
    }

    public void increaseCntLimit() {
        cntlimit += 1;
    }
}
