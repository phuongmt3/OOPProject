package com.Entities.Maps;

import com.Renderer.RendererMap;

public class Wall extends Map {
    private RendererMap renderer;
    public Wall(double x, double y) {
        super(x, y);
        renderer = new RendererMap(this);
    }

    @Override
    public void render() throws Exception {
        renderer.renderWall(x, y);
    }

    @Override
    public String getClassName() {
        return "Wall";
    }
}
