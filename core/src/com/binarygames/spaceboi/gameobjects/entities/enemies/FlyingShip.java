package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class FlyingShip extends Enemy {
    public FlyingShip(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius, EnemyType.FLYING_SHIP);
    }
    @Override
    protected void updateIdle() {
        this.getBody().setLinearVelocity(0,0);
    }

    @Override
    protected void updateHunting() {
        this.getBody().setLinearVelocity(0,0);
    }

    @Override
    protected void updateAttacking() {
        jump();
    }

    @Override
    protected void updateJumping() {
        updateEnemy();
        if(enemyState == ENEMY_STATE.IDLE || enemyState == ENEMY_STATE.HUNTING){
            this.getBody().setLinearVelocity(0,0);
        }
        else if(enemyState == ENEMY_STATE.ATTACKING){
            if(toShoot()){
                Shoot();
                moveAlongPlanet();
            }
            else{
                moveAlongPlanet();
            }
        }
    }
    private boolean toShoot(){
        if(toPlayer.len2() < 400){
            return true;
        }
        return false;
    }
    private void Shoot(){
        Vector2 shootDirection = new Vector2(toPlayer.x, toPlayer.y).setLength2(1).scl(rad * PPM);
        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + shootDirection.x,
                body.getPosition().y * PPM + shootDirection.y);

        weapon.Shoot(shootFrom.x, shootFrom.y, shootDirection);
    }
}
