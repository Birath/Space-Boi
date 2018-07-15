package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Shooter extends Enemy {
    public Shooter(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, EnemyType.SHOOTER);
    }
    @Override
    protected void updateIdle() {
        updateWalkingDirection();
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
        if(toShoot()){
            Shoot();
        }
        else{
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateJumping() {
        //Do nothing
    }

    private boolean toShoot(){
        //Calculating if shooting is to happen
        Vector2 awayFromPlanet = new Vector2(-toPlanet.x, -toPlanet.y);
        float angle = awayFromPlanet.angle(toPlayer);

        return (Math.abs(angle) < 110); //110 should be calculated mathematically
    }

    private void Shoot() {
        Vector2 recoil = new Vector2(-toPlayer.x, -toPlayer.y);
        recoil.setLength2(1);

        //Setting recoil
        recoil.scl(weapon.getRecoil());
        body.setLinearVelocity(recoil);

        //Creating the bulleta
        recoil.setLength2(1);
        recoil.scl(-(rad * PPM));
        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + recoil.x, body.getPosition().y * PPM + recoil.y);

        weapon.Shoot(shootFrom.x, shootFrom.y, recoil);
    }
}
