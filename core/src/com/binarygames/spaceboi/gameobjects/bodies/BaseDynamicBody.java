package com.binarygames.spaceboi.gameobjects.bodies;

import com.badlogic.gdx.physics.box2d.*;

public abstract class BaseDynamicBody extends BaseBody {

    protected Body body;

    public BaseDynamicBody(World aWorld, float x, float y, float mass, float radius) {
        super(aWorld, x, y, mass, radius);

        //Skapar en body till denna sorts objekt
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.DynamicBody;
        groundBodyDef.position.set(pos);
        body = world.createBody(groundBodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(rad);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0f;
        Fixture fixture = body.createFixture(fixtureDef);
        circleShape.dispose();
    }

    public Body getBody() {
        return body;
    }
}