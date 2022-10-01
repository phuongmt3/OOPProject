package com.example.dbproject;

public class FlameItem extends Brick {
    private boolean used = false;
    public FlameItem(double x, double y) {
        super(x, y, true);
    }

    @Override
    public void setExposed(boolean b) throws Exception {
        super.setExposed(b);
        renderer.renderFlameItem(x, y);
    }

    public void useItem() {
        renderer.deleteFlameItem();
        used = true;
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public String getClassName() {
        return "FlameItem";
    }
}
