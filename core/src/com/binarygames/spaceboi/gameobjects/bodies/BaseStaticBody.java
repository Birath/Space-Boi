package com.binarygames.spaceboi.gameobjects.bodies;

import com.binarygames.spaceboi.gameobjects.GameWorld;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.StaticBody;

public abstract class BaseStaticBody extends BaseBody {

    public BaseStaticBody(GameWorld gameWorld, float x, float y, float mass, float radius) {
        super(gameWorld, x, y, mass, radius, StaticBody);
    }

    public BaseStaticBody(GameWorld gameWorld, float x, float y, float mass, float width, float height) {
        super(gameWorld, x, y, mass, width, height, StaticBody);


    }
}
