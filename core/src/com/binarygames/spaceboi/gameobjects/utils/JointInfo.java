package com.binarygames.spaceboi.gameobjects.utils;

import com.badlogic.gdx.physics.box2d.Body;

public class JointInfo {
    public Body bodyA;
    public Body bodyB;

    public JointInfo(Body bodyA, Body bodyB) {
        this.bodyA = bodyA;
        this.bodyB = bodyB;
    }


}
