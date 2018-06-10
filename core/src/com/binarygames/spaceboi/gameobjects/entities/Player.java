package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends EntityDynamic {

    private PLAYER_STATE playerState;

    private Body planetBody;

    private boolean mouseHeld;
    private Vector2 mouseCoord = new Vector2(0, 0);

    public Player(World world, float x, float y, String path, float mass, float radius) {
        super(world, x, y, path, mass, radius);
        body.setUserData("player");
    }

    @Override
    public void updateMovement() {
        if (playerState == PLAYER_STATE.STANDING) {

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
                playerState = PLAYER_STATE.JUMPING;
            }
        }
        //SHOOTING
        if (mouseHeld) {
            Vector2 recoil = new Vector2(body.getPosition().x - mouseCoord.x, body.getPosition().y - mouseCoord.y);
            recoil.setLength2(1);
            recoil.scl(20);

            body.setLinearVelocity(recoil);
        }
    }

    public PLAYER_STATE getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PLAYER_STATE playerState) {
        this.playerState = playerState;
    }

    public void setPlanetBody(Body planetBody) {
        this.planetBody = planetBody;
    }

    public void setMouseHeld(boolean mouseHeld) {
        this.mouseHeld = mouseHeld;
    }

    public void setMouseXAndMouseY(float x, float y) {
        mouseCoord.set(x, y);
    }
}


