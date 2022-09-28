package com.example.dbproject;

import java.util.ArrayList;

public class Flame extends Entity {
    private double xleft, xright;    //update those values
    private double yup, ydown;
    private final int flameLength = 1;
    public Flame(double x, double y) {
        super(x, y);
    }

    @Override
    public void render() throws Exception {
        renderer.renderFlame(this);
    }

    public double getXleft() {
        return xleft;
    }
    public double getXright() {
        return xright;
    }
    public double getYup() {
        return yup;
    }
    public double getYdown() {
        return ydown;
    }

    public void updateInfluence(Bomber bomber, EnemyManager enemyManager, ArrayList<ArrayList<Entity>> map) {
        //handle kill bomber, enemy, destroy brick, stop when meet wall/brick
        //bomb nổ liên hoàn
        xleft = xright = -100;
        yup = ydown = -100;
        int posxInMap = (int) (x / Main.defaultSide);
        int posyInMap = (int) (y / Main.defaultSide);
        boolean[] end = new boolean[4];
        end[0] = end[1] = end[2] = end[3] = false;
        //handle bomb explode not on default slot on map
        for (int i = 1; i <= flameLength; i++) {
            for (Mover.MovementType dir : Mover.MovementType.values()) {
                if (end[dir.ordinal()] || !validCoordination(xleft + Main.defaultSide * dir.x, y + Main.defaultSide * dir.y))
                    end[dir.ordinal()] = true;
                else if (map.get(posxInMap + dir.x).get(posyInMap + dir.y) instanceof Wall)
                    end[dir.ordinal()] = true;
                else if (map.get(posxInMap + dir.x).get(posyInMap + dir.y) instanceof Brick
                        && !((Brick) map.get(posxInMap + dir.x).get(posyInMap + dir.y)).isExposed()) {
                    ((Brick) map.get(posxInMap + dir.x).get(posyInMap + dir.y)).setExposed(true);
                    end[dir.ordinal()] = true;
                }
                else {
                    if (dir == Mover.MovementType.LEFT)
                        xleft = x - Main.defaultSide * i;
                    else if (dir == Mover.MovementType.RIGHT)
                        xright = x + Main.defaultSide * i;
                    else if (dir == Mover.MovementType.UP)
                        yup = y - Main.defaultSide * i;
                    else if (dir == Mover.MovementType.DOWN)
                        ydown = y + Main.defaultSide * i;
                }
            }
        }
    }

}