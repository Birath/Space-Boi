package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Bullet extends EntityDynamic {
    private GameWorld gameWorld;

    private long timeTouched;
    private long removeDelay;
    private int damage;
    private EntityDynamic shooter;

    public Bullet(World world, float x, float y, String path, Vector2 speed, GameWorld gameWorld, float mass, float radius, long removeDelay, int damage, EntityDynamic shooter) {
        super(world, x, y, path, mass, radius);
        this.removeDelay = removeDelay;
        body.setUserData(this);

        this.getBody().setLinearVelocity(speed);

        this.damage = damage;
        this.gameWorld = gameWorld;
        this.shooter = shooter;
        gameWorld.addDynamicEntity(this);
    }

    @Override
    public void hitPlanet(Planet planet) {
        if (this.entityState != ENTITY_STATE.STANDING) {
            this.entityState = ENTITY_STATE.STANDING;
            timeTouched = TimeUtils.millis();
        }
    }

    public boolean toRemove(float x, float y) {
        if (entityState == ENTITY_STATE.STANDING) {
            if ((TimeUtils.millis() - timeTouched) > removeDelay) {
                return true;
            }
        }
        if (this.getBody().getPosition().sub(x, y).len2() > 9000) { //huge factor just to remove bullets that go far off screen
            return true;
        }
        return false;
    }
    public int getDamage(){
        return this.damage;
    }
    public EntityDynamic getShooter(){ return this.shooter;}
}
