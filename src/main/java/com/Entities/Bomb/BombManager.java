package com.Entities.Bomb;

import com.Entities.Entity;
import com.Entities.Movers.Mover;
import com.GameSound;
import com.Main;

import java.util.ArrayList;

import static com.GameSound.bomberdie;
import static com.GameSound.playgame;

public class BombManager {
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    private ArrayList<ArrayList<Entity>> map;
    public static final int timeLimit = 150;
    private int cntlimit = 1;

    public void setMap(ArrayList<ArrayList<Entity>> map) {
        this.map = map;
    }
    public boolean canPutBomb() {
        return bombs.size() < cntlimit;
    }
    public void addBomb(Bomb bomb) {
        if (!canPutBomb())
            return;
        for (Bomb curbomb : bombs) {
            int idx = (int) Math.round(curbomb.getX() / Main.defaultSide);
            int idy = (int) Math.round(curbomb.getY() / Main.defaultSide);
            for (Mover.MovementType type : Mover.MovementType.values()) {
                if (type == Mover.MovementType.STILL)
                    continue;
                for (int len = 0; len <= FlameManager.getFlameLength(); len++) {
                    if (!curbomb.availableAreaInMap(map.get(idy + len * type.y).get(idx + len * type.x)))
                        break;
                    if (bomb.checkCollision(map.get(idy + len * type.y).get(idx + len * type.x))
                            && bomb.countdown() > curbomb.countdown())
                        bomb.setConsecutiveTimer(curbomb);
                }
            }
        }
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
