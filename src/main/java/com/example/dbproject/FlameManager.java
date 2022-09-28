package com.example.dbproject;

import java.util.ArrayList;

public class FlameManager extends Entity {
    private double xleft, xright;    //update those values
    private double yup, ydown;
    private final int flameLength = 2;
    private ArrayList<Flame> flames = new ArrayList<Flame>();
    public FlameManager(double x, double y) {
        super(((int) (x / Main.defaultSide)) * Main.defaultSide, ((int) (y / Main.defaultSide)) * Main.defaultSide);
        if (x - this.x > Main.defaultSide * 0.5)
            this.x += Main.defaultSide;
        if (y - this.y > Main.defaultSide * 0.5)
            this.y += Main.defaultSide;
    }

    @Override
    public void render() throws Exception {
        //add to flames
        flames.add(new Flame(x, y, Renderer.Direction.center));
        if (xleft > 0) {
            for (double i = x - Main.defaultSide; i > xleft; i -= Main.defaultSide)
                flames.add(new Flame(i, y, Renderer.Direction.middlerow));
            flames.add(new Flame(xleft, y, Renderer.Direction.left));
        }
        if (xright > 0) {
            for (double i = x + Main.defaultSide; i < xright; i += Main.defaultSide)
                flames.add(new Flame(i, y, Renderer.Direction.middlerow));
            flames.add(new Flame(xright, y, Renderer.Direction.right));
        }
        if (yup > 0) {
            for (double i = y - Main.defaultSide; i > yup; i -= Main.defaultSide)
                flames.add(new Flame(x, i, Renderer.Direction.middlecol));
            flames.add(new Flame(x, yup, Renderer.Direction.up));
        }
        if (ydown > 0) {
            for (double i = y + Main.defaultSide; i < ydown; i += Main.defaultSide)
                flames.add(new Flame(x, i, Renderer.Direction.middlecol));
            flames.add(new Flame(x, ydown, Renderer.Direction.down));
        }
        for (Map flame : flames)
            flame.render();
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
        //handle kill bomber, enemy
        //bomb nổ liên hoàn
        xleft = xright = -100;
        yup = ydown = -100;
        int posxInMap = (int) (x / Main.defaultSide);
        int posyInMap = (int) (y / Main.defaultSide);
        boolean[] end = new boolean[4];
        end[0] = end[1] = end[2] = end[3] = false;
        for (int i = 1; i <= flameLength; i++) {
            for (Mover.MovementType dir : Mover.MovementType.values()) {
                if (end[dir.ordinal()] || !validCoordination(x + Main.defaultSide * dir.x * i, y + Main.defaultSide * dir.y * i))
                    end[dir.ordinal()] = true;
                else if (map.get(posyInMap + dir.y * i).get(posxInMap + dir.x * i) instanceof Wall)
                    end[dir.ordinal()] = true;
                else if (map.get(posyInMap + dir.y * i).get(posxInMap + dir.x * i) instanceof Brick
                        && !((Brick) map.get(posyInMap + dir.y * i).get(posxInMap + dir.x * i)).isExposed()) {
                    ((Brick) map.get(posyInMap + dir.y * i).get(posxInMap + dir.x * i)).setExposed(true);
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