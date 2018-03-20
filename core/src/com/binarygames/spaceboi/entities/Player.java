package com.binarygames.spaceboi.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.binarygames.spaceboi.bodies.BaseBody;

public class Player extends BaseBody {

    private int speedX = 5;
    private int speedY = 5;

    private float x;
    private float y;

    private boolean moveRight = false;
    private boolean moveLeft = false;

    private Body body;


    public Player(World world, float x, float y, int mass){
        super(world, x, y, mass);
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
        if(moveLeft){
            //x -= speedX;
            body.applyForceToCenter(-200f, 0, true);
        }
        if(moveRight){
            //x += speedX;
            body.applyForceToCenter(200f, 0, true);
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

    @Override
    public Body getBody() {
        return body;
    }
}
