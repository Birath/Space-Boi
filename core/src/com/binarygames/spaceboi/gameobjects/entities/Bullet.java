package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Bullet extends EntityDynamic {
    public Bullet(World world, float x, float y, String path, float mass, float radius) {
        super(world, x, y, path, mass, radius);
        body.setUserData(this);
    }
    @Override
    public void hitPlanet(Planet planet){
        this.entityState = ENTITY_STATE.STANDING;
    }
}
