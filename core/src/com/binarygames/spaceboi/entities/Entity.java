package com.binarygames.spaceboi.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {
    private Texture img;
    private Sprite sprite;

    private int speedX = 8;
    private int speedY = 8;

    private float x;
    private float y;

    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveRight = false;
    private boolean moveLeft = false;

    public Entity(float x, float y, String path){
        img = new Texture(path);
        this.sprite = new Sprite(img);
        this.x = x;
        this.y = y;
    }

    public void updateMovement(){
        if(moveUp){
            y += speedY;
            if(y > Gdx.graphics.getHeight() - this.sprite.getHeight()){
                y = Gdx.graphics.getHeight() - this.sprite.getHeight();
            }
        }
        if(moveDown){
            y -= speedY;
            if(y < 0){
                y = 0;
            }
        }
        if(moveRight){
            x += speedX; //Behövs det * deltatime?
            if(x > Gdx.graphics.getWidth() - this.sprite.getWidth()){
                x = Gdx.graphics.getWidth() - this.sprite.getWidth();
            }
        }
        if(moveLeft){
            x -= speedX;
            if(x < 0){
                x = 0;
            }
        }
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp; //Dessa kallas av PlayerInputProcessor, i fallet för Player-Objektet
    }
    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }
    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }


    //Getters and setters
    public float getX() {
        return x;
    }
    public float getY(){
        return y;
    }
    public Sprite getSprite(){
        return sprite;
    }

}
