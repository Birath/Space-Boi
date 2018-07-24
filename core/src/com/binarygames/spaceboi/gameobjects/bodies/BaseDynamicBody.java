package com.binarygames.spaceboi.gameobjects.bodies;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public abstract class BaseDynamicBody extends BaseBody {

    // Circle body
    protected BaseDynamicBody(GameWorld gameWorld, float x, float y, float mass, float radius) {
        super(gameWorld, x, y, mass, radius, BodyType.DynamicBody);
    }
    // Polygon body
    protected BaseDynamicBody(GameWorld gameWorld, float x, float y, float mass, float width, float height) {
        super(gameWorld, x, y, mass, width, height, BodyType.DynamicBody);
    }

}