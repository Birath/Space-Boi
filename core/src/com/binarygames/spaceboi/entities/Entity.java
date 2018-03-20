package com.binarygames.spaceboi.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.binarygames.spaceboi.bodies.BaseBody;

public abstract class Entity extends BaseBody{
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

    private Body body;

    public Entity(World world, float x, float y, String path, int mass){
        super(world, x, y, mass);
        img = new Texture(path);
        this.sprite = new Sprite(img);
        this.x = x;
        this.y = y;
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.DynamicBody;
        groundBodyDef.position.set(pos);
        System.out.println(groundBodyDef);
        this.body = world.createBody(groundBodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(rad);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.8f;
        Fixture fixture = body.createFixture(fixtureDef);
        circleShape.dispose();
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
    public Body getBody() {return body;}



}
