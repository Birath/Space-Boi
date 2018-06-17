package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.bodies.BaseDynamicBody;

public abstract class EntityDynamic extends BaseDynamicBody {

    public ENTITY_STATE entityState; //How to do this in a nice way without setting them public?

    public Body planetBody;

    private Sprite sprite;

    private int speedX = 8;
    private int speedY = 8;

    private float x;
    private float y;

    protected boolean moveUp = false;
    protected boolean moveDown = false;
    protected boolean moveRight = false;
    protected boolean moveLeft = false;

    public EntityDynamic(World world, float x, float y, String path, float mass, float radius) {
        super(world, x, y, mass, radius);
        this.sprite = new Sprite(new Texture(path));
        sprite.setSize(radius * 2, radius * 2);
        this.x = x;
        this.y = y;
    }

    public void updateMovement() {
        if (entityState == ENTITY_STATE.STANDING) {
            Vector2 toPlanet = new Vector2(planetBody.getPosition().x - body.getPosition().x, planetBody.getPosition().y - body.getPosition().y);
            toPlanet.setLength2(1);
            toPlanet.scl(50);

            Vector2 perpen = new Vector2(-toPlanet.y, toPlanet.x);
            perpen.setLength2(1);
            perpen.scl(20);

            //MOVE
            if (moveRight) {
                body.setLinearVelocity(perpen);
            } else if (moveLeft) {
                body.setLinearVelocity(-perpen.x, -perpen.y);
            } else {
                body.setLinearVelocity(0, 0);
            }

            //JUMP
            if (moveUp) {
                body.setLinearVelocity(-toPlanet.x + body.getLinearVelocity().x, -toPlanet.y + body.getLinearVelocity().y);
                entityState = ENTITY_STATE.JUMPING;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        sprite.setPosition(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp; //Dessa kallas av PlayerInputProcessor, i fallet för Player-Objektet
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


    //Getters and setters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }

    //Planetbody and ENTITY_STATE stuff
    public void setPlanetBody(Body planetBody) {
        this.planetBody = planetBody;
    }

    public void hitPlanet(Planet planet) {
        entityState = ENTITY_STATE.STANDING;
        planetBody = planet.getBody();
    }

    public void leftPlanet() {
        entityState = ENTITY_STATE.JUMPING;
    }

    public ENTITY_STATE getEntityState() {
        return entityState;
    }

    public void setEntityState(ENTITY_STATE playerState) {
        this.entityState = playerState;
    }


}
