package com.example.dbproject;

public class Portal extends Brick {
    public Portal(double x, double y) {
        super(x, y, true);
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

    @Override
    public String getClassName() {
        return "Portal";
    }
}
