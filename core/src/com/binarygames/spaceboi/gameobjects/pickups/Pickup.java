package com.binarygames.spaceboi.gameobjects.pickups;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public abstract class Pickup extends EntityDynamic {
    protected boolean remove = false;
    public boolean hitPlane = false;

    protected Pickup(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
        for (Fixture fixture : getBody().getFixtureList()) {
            fixture.setFriction(1);
        }
    }

    public abstract void onHit(Player player);

    @Override
    public boolean shouldRemove(Vector2 playerPosition) {
        return remove;
    }

     @Override
    public void update(float delta) {
        if (hitPlane) {
            body.setLinearVelocity(0, 0);
        }
     }

    public void setHitPlane(boolean hitPlane) {
       this.hitPlane = hitPlane;
    }
}
