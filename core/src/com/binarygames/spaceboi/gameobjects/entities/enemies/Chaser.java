package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.ENTITY_STATE;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;

public class Chaser extends Enemy {

    private int damage = 5;

    public Chaser(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);

        this.health = 5;
        this.jumpHeight = 50;
        this.moveSpeed = 15;
        this.weapon = new Machinegun(gameWorld, this);
    }

    @Override
    protected void updateIdle() {
        moveAlongPlanet();
    }

    @Override
    protected void updateHunting() {
        if(toJump()){
            jump();
        }
        else{
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateAttacking() {
        if(toJump()){
            jump();
        }
        else{
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateJumping() {
        //Do nothing
    }

    public int getDamage(){
        return damage;
    }
}
