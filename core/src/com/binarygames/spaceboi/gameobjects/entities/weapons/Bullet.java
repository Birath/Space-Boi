package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.effects.ParticleHandler;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Planet;

public class Bullet extends EntityDynamic {

    protected GameWorld gameWorld;

    private long timeTouched;
    private long removeDelay;
    private int damage;
    private EntityDynamic shooter;
    private boolean hasHitPlanet = false;

    public Bullet(GameWorld gameWorld, float x, float y, String path, Vector2 speed, float mass, float radius, long removeDelay, int damage, EntityDynamic shooter) {
        super(gameWorld, x, y, path, mass, radius);
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

    public void setHasHitPlanetTrue() {
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

    public void onRemove() {
        // Runs once the bullet is removed from game
        gameWorld.getParticleHandler().addEffect(ParticleHandler.EffectType.BLOOD, getBody().getPosition().x * PPM, getBody().getPosition().y * PPM);
    }

    public int getDamage() {
        return this.damage;
    }

    public EntityDynamic getShooter() {
        return this.shooter;
    }
}
