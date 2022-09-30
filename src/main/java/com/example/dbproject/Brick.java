package com.example.dbproject;

public class Brick extends Map {
    private RendererMap renderer;
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

    public void update() throws Exception {
        if (!hasItem && isExposed)
            renderer.renderGrass(x, y);
        //else renderer.renderBrick(x, y);
    }

    @Override
    public String getClassName() {
        return "Brick";
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean b) {
        isExposed = b;
    }
}
