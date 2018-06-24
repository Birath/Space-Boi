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
    private boolean hasHitPlanet = false;

    public Bullet(World world, float x, float y, String path, Vector2 speed, GameWorld gameWorld, float mass, float radius, long removeDelay, int damage, EntityDynamic shooter) {
        super(world, x, y, path, mass, radius);
        this.removeDelay = removeDelay;
        body.setUserData(this);

        this.getBody().setLinearVelocity(speed);

        this.damage = damage;
        this.gameWorld = gameWorld;
        this.shooter = shooter;
        timeTouched = TimeUtils.millis();
        gameWorld.addDynamicEntity(this);
    }

    @Override
    public void hitPlanet(Planet planet) {
        //Do nothing
    }
    public void setHasHitPlanetTrue(){
        this.hasHitPlanet = true;
    }

    public boolean toRemove(float x, float y) {
        if (hasHitPlanet) {
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
