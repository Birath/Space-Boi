package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.ENTITY_STATE;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;

public class Chaser extends Enemy {

    private int damage = 5;

    public Chaser(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
        this.gameWorld = gameWorld;
        player = gameWorld.getPlayer();
        body.setUserData(this);

        this.health = 10;
        this.jumpHeight = 50;
        this.moveSpeed = 15;
        this.weapon = new Machinegun(gameWorld, this);
    }

    @Override
    public void update(float delta) {
        updateToPlanet();
        updateWalkingDirection();
        updateEnemyState();
        weapon.update(delta);
        if (entityState == ENTITY_STATE.STANDING) {
            updatePerpen();
            if(toJump()){
                jump();
            }
            else{
                moveAlongPlanet();
            }
        }
    }
    public int getDamage(){
        return damage;
    }
}
