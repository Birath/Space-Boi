package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends EntityDynamic {

    private PLAYER_STATE playerState;

    private Body planetBody;

    public Player(World world, float x, float y, String path, float mass, float radius) {
        super(world, x, y, path, mass, radius);
        body.setUserData("player");
    }

    @Override
    public void updateMovement() {
        if (playerState == PLAYER_STATE.STANDING) {
            System.out.println("Planet location " + planetBody.getPosition());
            System.out.println("Player location " + body.getPosition());
            Vector2 toPlanet = new Vector2(planetBody.getPosition().x - body.getPosition().x, planetBody.getPosition().y - body.getPosition().y);
            Vector2 perpen = new Vector2(-toPlanet.y, toPlanet.x);
            System.out.println("Perpendicular: " + perpen);
            if (moveUp) {
                body.setLinearVelocity(-toPlanet.x*2 + body.getLinearVelocity().x, -toPlanet.y*2 + body.getLinearVelocity().y);
                playerState = PLAYER_STATE.JUMPING;
            }
            if (moveRight) body.setLinearVelocity(perpen);
            if (moveLeft) body.setLinearVelocity(-perpen.x, -perpen.y);
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
}


