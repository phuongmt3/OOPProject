package com.example.dbproject;

public class Flame extends Entity {
    private double xleft, yleft, xright, yright;    //update those values
    private double xup, yup, xdown, ydown;
    private static int flameLength = 1;
    public Flame(double x, double y) {
        super(x, y);
    }

    @Override
    public void render() throws Exception {
        renderer.renderFlame(this);
    }
    

}
