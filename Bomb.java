package com.example.dbproject;

import javafx.scene.image.Image;


abstract public class Bomb extends Entity{
    private long timer = 0;
    protected int _animate = 0;
    protected double _timeToExplode = 120;

    protected static int damage = 1;
    private long timeLimit = 2_000_000_000;

    protected Image img;
    public Bomb(double x, double y) {
        super(x, y);
    }

    public void animate() {
        if (_animate > 90) _animate = 0;
        else _animate++;
        _timeToExplode--;
    }
    public void loadAnimated(Sprite sprite1, Sprite sprite2, Sprite sprite3) {
        img = Sprite.movingSprite(sprite1.getFxImage(), sprite2.getFxImage(), sprite3.getFxImage(), _animate, 30);
    }
    public void update() {
        //update timer
        animate();
        if (_timeToExplode < 0) {
           // GameSound.playMusic(GameSound.BONG_BANG);
            // bombExplode();
            return;
        }
        loadAnimated(Sprite.bomb_0, Sprite.bomb_1, Sprite.bomb_2);
    }


 /*   public void render() {}

    public boolean isExploded() {
        return timer >= timeLimit;
    }*/
 /*protected void bombExplode() {
     BombermanGame.setFlame(new FlameItem(tile, damage));
     BombermanGame.removeBomb();
 }*/

    public static int getDamage() {
        return damage;
    }

    public static void setDamage(int damage) {
        Bomb.damage = damage;
    }

    @Override
    public String getClassName() {
        return "Bomb";
    }
}
