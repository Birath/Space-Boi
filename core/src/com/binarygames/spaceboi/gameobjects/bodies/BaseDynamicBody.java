package com.binarygames.spaceboi.gameobjects.bodies;

import com.badlogic.gdx.physics.box2d.*;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public abstract class BaseDynamicBody extends BaseBody {

    protected Fixture fixture = null;

    protected BaseDynamicBody(GameWorld gameWorld, float x, float y, float mass, float radius) {
        super(gameWorld, x, y, mass, radius);

        //Skapar en body till denna sorts objekt
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.DynamicBody;
        groundBodyDef.position.set(pos);
        body = gameWorld.getWorld().createBody(groundBodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(rad);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0f;
        this.fixture = body.createFixture(fixtureDef);
        circleShape.dispose();
    }

}