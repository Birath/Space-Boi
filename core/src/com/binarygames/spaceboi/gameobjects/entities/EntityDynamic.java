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

    private Sprite sprite;

    protected int health;

    protected boolean moveUp = false;
    protected boolean moveDown = false;
    protected boolean moveRight = false;
    protected boolean moveLeft = false;

    protected int jumpHeight;
    protected int moveSpeed;

    public EntityDynamic(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, mass, radius);
        this.sprite = new Sprite(gameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(radius * 2, radius * 2);
    }

    public void update(float delta) {
        //Specific for each sub-entity.
    }

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

    public void reduceHealth(int amount) {
        health -= amount;
    }

    protected abstract boolean shouldRemove(Vector2 playerPosition);

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

}
