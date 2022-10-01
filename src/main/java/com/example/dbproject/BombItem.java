package com.example.dbproject;

public class BombItem extends Brick {
    public BombItem(double x, double y) {
        super(x, y, true);
    }

    @Override
    public void render() throws Exception{
       // super.render();
        //if (isExposed) renderer.renderBombItem(x, y);
        //if isExposed render SpeedItem image
        //else render Brick image
    }

    @Override
    public void update() {
        //update isExposed
        //update if bomber go there
    }

    @Override
    public String getClassName() {
        return "BombItem";
    }
}
