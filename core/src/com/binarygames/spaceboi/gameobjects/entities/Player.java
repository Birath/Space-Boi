package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Player extends EntityDynamic {

    private boolean mouseHeld;
    private Vector2 mouseCoord = new Vector2(0, 0);

    private GameWorld gameWorld;

    public Player(World world, float x, float y, String path, float mass, float radius, GameWorld gameWorld) {
        super(world, x, y, path, mass, radius);
        body.setUserData(this);
        this.gameWorld = gameWorld;
    }

    @Override
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
        //SHOOTING
        if (mouseHeld) {
            Vector2 recoil = new Vector2(body.getPosition().x * PPM - mouseCoord.x, body.getPosition().y * PPM - mouseCoord.y);
            recoil.setLength2(1);
            recoil.scl(60);

            body.setLinearVelocity(recoil);

            createBullet(recoil);
        }
    }

    private void createBullet(Vector2 recoil){
        recoil.setLength2(1);
        recoil.scl(-(rad * PPM));
        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + recoil.x, body.getPosition().y * PPM + recoil.y);
        EntityDynamic bullet = new Bullet(world, shootFrom.x, shootFrom.y, "playerShip.png", 8, 2f);

        recoil.scl(15);
        bullet.getBody().setLinearVelocity(recoil);
        gameWorld.addDynamicEntity(bullet);
    }

    public void setMouseHeld(boolean mouseHeld) {
        this.mouseHeld = mouseHeld;
    }

    public void setMouseXAndMouseY(float x, float y) {
        mouseCoord.set(x, y);
    }
}


