package com.binarygames.spaceboi.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {

    private PLAYER_STATES playerState;

    public Player(World world, float x, float y, String path, int mass) {
        super(world, x, y, path, mass);
    }

    public PLAYER_STATES getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PLAYER_STATES playerState) {
        this.playerState = playerState;
    }
}


