package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Player extends EntityDynamic {
    public Player(World world, float x, float y, String path, float mass, float radius) {
        super(world, x, y, path, mass, radius);
    }
}


