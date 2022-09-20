package com.example.dbproject;

public class BombItem extends Brick {
    public BombItem(double x, double y) {
        super(x, y);
    }

    @Override
    public void render() {
        //if isExposed render SpeedItem image
        //else render Brick image
    }

    @Override
    public void update() {
        //update isExposed
        //update if bomber go there
    }

}
