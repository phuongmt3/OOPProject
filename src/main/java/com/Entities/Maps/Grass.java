package com.Entities.Maps;

import com.Renderer.RendererMap;

public class Grass extends Map{
    private RendererMap renderer;

    public Grass(double x, double y) {
        super(x, y);
        renderer = new RendererMap(this);
    }

    @Override
    public void render() throws Exception {
        renderer.renderGrass(x, y);
    }

    @Override
    public String getClassName() {
        return "Grass";
    }
}
