package com.example.dbproject;

public class BombItem extends Brick {
    private boolean used = false;
    public BombItem(double x, double y) {
        super(x, y, true);
    }

    @Override
    public void setExposed(boolean b) throws Exception {
        super.setExposed(b);
        renderer.renderBombItem(x, y);
    }

    public void useItem() {
        renderer.deleteBombItem();
        used = true;
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public String getClassName() {
        return "BombItem";
    }
}
