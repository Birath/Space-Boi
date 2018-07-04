package com.binarygames.spaceboi.gameobjects.bodies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public abstract class BaseStaticBody extends BaseBody {

    public BaseStaticBody(GameWorld gameWorld, float x, float y, float mass, float radius) {
        super(gameWorld, x, y, mass, radius);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(pos);

        body = gameWorld.getWorld().createBody(groundBodyDef);

        CircleShape groundCircle = new CircleShape();
        groundCircle.setRadius(rad);

        body.createFixture(groundCircle, 0.0f);
        groundCircle.dispose();
    }

}
