package com.example.dbproject;

public class Brick extends Map {
    protected RendererMap renderer;
    protected boolean isExposed = false;
    protected boolean hasItem;

    public Brick(double x, double y, boolean hasItem) {
        super(x, y);
        this.hasItem = hasItem;
        renderer = new RendererMap(this);
    }

    @Override
    public void render() throws Exception {
        renderer.renderBrick(x, y);
    }

    @Override
    public String getClassName() {
        return "Brick";
    }

    public boolean isExposed() {
        return isExposed;
    }

    public boolean isHasItem() {
        return hasItem;
    }

    public void setExposed(boolean b) throws Exception {
        isExposed = b;
        renderer.deleteBrick(x, y);
        renderer.renderGrass(x, y);
    }
}
