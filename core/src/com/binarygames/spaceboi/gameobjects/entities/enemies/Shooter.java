package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Shooter extends Enemy {
    public Shooter(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.SHOOTER);
    }
    @Override
    protected void updateIdle() {
        standStill();
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

        perpen = new Vector2(-toPlanet.y, toPlanet.x);
        perpen.setLength2(1).scl(rad * PPM);
        if(Math.abs(perpen.angle(toPlayer)) > 90){
            perpen.rotate(180);
        }

        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + perpen.x,
                body.getPosition().y * PPM + perpen.y);

        weapon.Shoot(shootFrom.x, shootFrom.y, perpen);
    }
}
