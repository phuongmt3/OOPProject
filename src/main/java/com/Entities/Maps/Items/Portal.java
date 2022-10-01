package com.Entities.Maps.Items;

import com.Entities.Maps.Brick;

public class Portal extends Brick {
    public Portal(double x, double y) {
        super(x, y, true);
    }

    @Override
    public void setExposed(boolean b) throws Exception {
        super.setExposed(b);
        renderer.renderPortal(x, y);
    }

    @Override
    public String getClassName() {
        return "Portal";
    }
}
