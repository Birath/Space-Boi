package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.effects.ParticleHandler;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Planet;

public class Bullet extends EntityDynamic {

    public static final int MAXIMUM_BULLET_DISTANCE = 9000;
    protected GameWorld gameWorld;

    private int damage;
    private int bioDamage = 0;
    private int mechDamage = 0;
    private int lifeSteal = 0;
    private int slow = 0;
    private EntityDynamic shooter;
    private Weapon weapon;
    protected boolean hasHit = false;
    protected int removeDelay;

    public Bullet(GameWorld gameWorld, float x, float y, String path, Vector2 speed, float mass, float radius, int removeDelay, int damage, EntityDynamic shooter, Weapon weapon) {
        super(gameWorld, x, y, path, mass, radius);
        createBullet(gameWorld, speed, removeDelay, damage, shooter, weapon);
    }


    protected Bullet(GameWorld gameWorld, float x, float y, String path, float mass, float width, float height, Vector2 speed, int removeDelay, int damage, EntityDynamic shooter, Weapon weapon) {
        super(gameWorld, x, y, path, mass, width, height);
        createBullet(gameWorld, speed, removeDelay, damage, shooter, weapon);

    }

    private void createBullet(GameWorld gameWorld, Vector2 speed, int removeDelay, int damage, EntityDynamic shooter, Weapon weapon) {
        this.getBody().setLinearVelocity(speed);

        this.bioDamage = weapon.getBioDamage();
        this.mechDamage = weapon.getMechDamage();
        this.lifeSteal = weapon.getLifeSteal();
        this.slow = weapon.getSlow();

        this.damage = damage;
        this.gameWorld = gameWorld;
        this.shooter = shooter;
        this.weapon = weapon;
        this.removeDelay = removeDelay;

        getSprite().setOriginCenter();

        gameWorld.addDynamicEntity(this);
    }

    @Override
    public void update(float delta) {
        //Do nothing
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        getSprite().setPosition(body.getPosition().x * PPM - getSprite().getWidth() / 2, body.getPosition().y * PPM - getSprite().getHeight() / 2);
        float angle = body.getLinearVelocity().angle();
        getSprite().setRotation(angle - 90);
        getSprite().draw(batch);
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
    }

    public int getDamage() {
        return this.damage;
    }

    public EntityDynamic getShooter() {
        return this.shooter;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getBioDamage() {
        return bioDamage;
    }

    public int getMechDamage() {
        return mechDamage;
    }

    public int getSlow() {
        return slow;
    }

    public int getLifeSteal(){
        return lifeSteal;
    }

    public void applyLifeSteal() {
        if(this.getShooter().getHealth() + this.getLifeSteal() > this.getShooter().getMaxHealth()){
            this.getShooter().setHealth(this.getShooter().getMaxHealth());
        }
        else{
            this.getShooter().setHealth(this.getShooter().getHealth() + this.getLifeSteal());
        }
    }
}
