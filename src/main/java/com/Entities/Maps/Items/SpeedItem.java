package com.Entities.Maps.Items;

import com.Entities.Maps.Brick;
import com.GameSound;

public class SpeedItem extends Brick {
    private boolean used = false;
    public SpeedItem(double x, double y) {
        super(x, y, true);
    }

    @Override
    public void setExposed(boolean b) throws Exception {
        super.setExposed(b);
        renderer.renderSpeedItem(x, y);
    }

    public void useItem() {
        renderer.deleteSpeedItem();
        GameSound.playClip(GameSound.ITEM);
        used = true;
        hasItem = false;
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public String getClassName() {
        return "SpeedItem";
    }
}
