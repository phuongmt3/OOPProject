package com.Entities.Bomb;

import com.Entities.Maps.Map;
import com.Renderer.Renderer;
import com.Renderer.RendererFlame;

public class Flame extends Map {
    private RendererFlame renderer = new RendererFlame();
    private final Renderer.Direction dir;
    public Flame(double x, double y, Renderer.Direction dir) {
        super(x, y);
        this.dir = dir;
    }

    @Override
    public void render() throws Exception {
        renderer.renderFlame(x, y, dir);
    }
}
