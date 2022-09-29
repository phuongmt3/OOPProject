package com.example.dbproject;

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
