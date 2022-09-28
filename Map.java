package com.example.dbproject;

abstract public class Map extends Entity{
    public Map(double x, double y) {
        super(x, y);
    }

    @Override
    public String getClassName() {
        return "Map";
    }
}
