package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.ENTITY_STATE;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Shotgun;

public class FlyingShip extends Enemy {

    private Shotgun shotgun;

    public FlyingShip(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, EnemyType.FLYING_SHIP);
        this.entityState = ENTITY_STATE.JUMPING;

        shotgun = new Shotgun(gameWorld, this);
    }
    @Override
    protected void updateIdle() {
        //Do nothing
    }

    @Override
    protected void updateHunting() {
        //Do nothing
    }

    @Override
    protected void updateAttacking() {
        //Do nothing
    }

    @Override
    public boolean isAffectedByGravity(){
        return false;
    }
    private void updateIdleJumping(){
        moveAlongPlanet();
    }
    private void updateHuntingJumping(){
        this.getBody().setLinearVelocity(0,0);
    }
    private void updateAttackingJumping(){
        if(toShootRockets() && weapon.canShoot()){
            ShootRockets();
        }
        else if(toShootShotgun() && shotgun.canShoot()){
            ShootShotgun();
        }
        else{
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateJumping() {
        if(this.planetBody != null){
            if(enemyState == ENEMY_STATE.IDLE){
                updateIdleJumping();
            }
            else if(enemyState == ENEMY_STATE.HUNTING){
                updateHuntingJumping();
            }
            else if(enemyState == ENEMY_STATE.ATTACKING){
                updateAttackingJumping();
            }
        }
        else{
            this.getBody().setLinearVelocity(0,0);
        }

    }
    private boolean toShootShotgun(){
        float angle = Math.abs(toPlanet.angle(toPlayer));
        if(angle < 45){
            return true;
        }
        return false;
    }
    private boolean toShootRockets(){
        float angle = Math.abs(toPlanet.angle(toPlayer));
        if(45 < angle && angle < 75){
            return true;
        }
        return false;
    }
    private void ShootShotgun(){
        Vector2 shootDirection = new Vector2(toPlayer.x, toPlayer.y).setLength2(1).scl(rad * PPM);
        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + shootDirection.x,
                body.getPosition().y * PPM + shootDirection.y);

        shotgun.Shoot(shootFrom.x, shootFrom.y, shootDirection);
    }
    private void ShootRockets(){
        Vector2 shootDirection = new Vector2(toPlayer.x, toPlayer.y).setLength2(1).scl(rad * PPM);
        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + shootDirection.x,
                body.getPosition().y * PPM + shootDirection.y);

        weapon.Shoot(shootFrom.x, shootFrom.y, shootDirection);
    }
}
