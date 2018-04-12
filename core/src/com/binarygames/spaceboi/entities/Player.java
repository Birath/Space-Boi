package com.binarygames.spaceboi.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Player extends EntityDynamic {

    private PLAYER_STATE playerState;

    public Player(World world, float x, float y, String path, float mass, float radius) {
        super(world, x, y, path, mass, radius);
    }

    @Override
    public void updateMovement() {
        if (playerState == PLAYER_STATE.STANDING) {
            if (moveUp) { body.applyForceToCenter(0f, 200f, true); }
            if (moveRight) body.applyForceToCenter(-200f, 0, true);
            if (moveLeft) body.applyForceToCenter(200f, 0, true);
        }
    }

    public PLAYER_STATE getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PLAYER_STATE playerState) {
        this.playerState = playerState;
    }
}


