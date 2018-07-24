package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.effects.ParticleHandler;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Planet;

public class Bullet extends EntityDynamic {

    public static final int MAXIMUM_BULLET_DISTANCE = 9000;
    protected GameWorld gameWorld;

    private int damage;
    private EntityDynamic shooter;
    protected boolean hasHit = false;

    public Bullet(GameWorld gameWorld, float x, float y, String path, Vector2 speed, float mass, float radius, long removeDelay, int damage, EntityDynamic shooter) {
        super(gameWorld, x, y, path, mass, radius);

        this.getBody().setLinearVelocity(speed);

        this.damage = damage;
        this.gameWorld = gameWorld;
        this.shooter = shooter;

        gameWorld.addDynamicEntity(this);
    }

    @Override
    public void update(float delta) {
        //Do nothing
    }

    @Override
    public void hitPlanet(Planet planet) {
    }

    public void setHasHit(boolean hasHit) {
        this.hasHit = hasHit;
    }

    @Override
    public boolean shouldRemove(Vector2 playerPosition) {
        return (this.getBody().getPosition().dst(playerPosition) > MAXIMUM_BULLET_DISTANCE) || hasHit;
    }
    @Override
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
