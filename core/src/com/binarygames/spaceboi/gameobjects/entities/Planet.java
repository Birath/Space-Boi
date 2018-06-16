package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.physics.box2d.World;


public class Planet extends EntityStatic {
    public Planet(World aWorld, float x, float y, String path, float mass, float radius) {
        super(aWorld, x, y, path, mass, radius);
        body.setUserData(this);
    }
}
