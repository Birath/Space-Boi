package com.binarygames.spaceboi.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Player extends Entity {
    public Player(World world, float x, float y, String path, int mass) {
        super(world, x, y, path, mass);
    }
}


