package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.bodies.BaseDynamicBody;

public abstract class EntityDynamic extends BaseDynamicBody {

    protected ENTITY_STATE entityState;

    public Body planetBody;
    private Planet closestPlanet;

    private Sprite sprite;

    protected int maxHealth;
    protected int health;

    protected boolean moveUp = false;
    protected boolean moveDown = false;
    protected boolean moveRight = false;
    protected boolean moveLeft = false;

    protected int jumpHeight;
    protected int moveSpeed;

    protected EntityDynamic(GameWorld gameWorld, float x, float y, String path, float mass, float radius, int health, int moveSpeed, int jumpHeight) {
        super(gameWorld, x, y, mass, radius);
        this.health = this.maxHealth = health;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        createEntityDynamic(gameWorld, path, radius);
    }

    protected EntityDynamic(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, mass, radius);
        this.health = 0;
        this.moveSpeed = 0;
        this.jumpHeight = 0;
        createEntityDynamic(gameWorld, path, radius);
    }

    private void createEntityDynamic(GameWorld gameWorld, String path, float radius) {
        this.sprite = new Sprite(gameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(radius * 2, radius * 2);
        body.setUserData(this);
    }

    public abstract void update(float delta);

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        sprite.setPosition(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.draw(batch);

    }

    //Movement - called by inputprocessor
    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    //Body and sprite
    public Sprite getSprite() {
        return sprite;
    }

    //Health
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void reduceHealth(int amount) {
        health -= amount;
    }

    public boolean shouldRemove(Vector2 playerPosition) {
        return health <= 0;
    }

    public boolean shouldRemove() {
        return health <= 0;
    }

    public abstract void onRemove();

    //Entity state
    public ENTITY_STATE getEntityState() {
        return entityState;
    }

    public void setEntityState(ENTITY_STATE playerState) {
        this.entityState = playerState;
    }

    //Planet interaction
    public void setPlanetBody(Body planetBody) {
        this.planetBody = planetBody;
    }

    public Body getPlanetBody() {
        return planetBody;
    }

    public void hitPlanet(Planet planet) {
        entityState = ENTITY_STATE.STANDING;
        planetBody = planet.getBody();
    }

    public void leftPlanet() {
        entityState = ENTITY_STATE.JUMPING;
    }

    public boolean isAffectedByGravity() {
        return true;
    }

    public void setClosestPlanet(final Planet closestPlanet) {
        this.closestPlanet = closestPlanet;
        this.planetBody = closestPlanet.getBody();
    }

    public Planet getClosestPlanet() {
        return closestPlanet;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
}
