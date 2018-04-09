package com.binarygames.spaceboi.entities;

import com.badlogic.gdx.physics.box2d.World;


public class Planet extends EntityStatic {
    public Planet(World aWorld, float x, float y, float mass, float radius) {
        super(aWorld, x, y, mass, radius);
    }
}
