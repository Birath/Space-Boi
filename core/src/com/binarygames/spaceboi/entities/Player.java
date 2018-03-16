package com.binarygames.spaceboi.entities;

import com.badlogic.gdx.Gdx;

public class Player {

    private int speedX = 5;
    private int speedY = 5;

    private float x;
    private float y;

    private boolean moveRight = false;
    private boolean moveLeft = false;

    public Player(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void updateMovement(){
        if(moveLeft){
            x -= speedX * Gdx.graphics.getDeltaTime();
        }
        if(moveRight){
            x += speedX * Gdx.graphics.getDeltaTime();
        }
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }
    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public float getX() {
        return x;
    }
    public float getY(){
        return y;
    }
}
