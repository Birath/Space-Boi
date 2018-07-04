package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.ENTITY_STATE;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public abstract class Enemy extends EntityDynamic {

    protected Vector2 toPlanet = new Vector2(0, 0);
    protected Vector2 toPlayer = new Vector2(0, 0);
    protected Vector2 perpen = new Vector2(0, 0);
    protected GameWorld gameWorld;
    protected Player player;

    protected Weapon weapon;

    protected ENEMY_STATE enemyState = ENEMY_STATE.HUNTING;

    public Enemy(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
        this.gameWorld = gameWorld;
        body.setUserData(this);
    }

    @Override
    public void update(float delta) {
        weapon.update(delta);
        if (entityState == ENTITY_STATE.STANDING) {
            updateEnemy();
            if(enemyState == ENEMY_STATE.IDLE){
                updateIdle();
            }
            else if (enemyState == ENEMY_STATE.ATTACKING) {
                updateAttacking();
            }
            else if (enemyState == ENEMY_STATE.HUNTING){
                updateHunting();
            }
        }
        else if (entityState == ENTITY_STATE.JUMPING){
            updateJumping();
        }
    }
    protected abstract void updateIdle();
    protected abstract void updateHunting();
    protected abstract void updateAttacking();
    protected abstract void updateJumping();

    protected void updateEnemy(){
        updateToPlanet();
        updatePerpen();
        updateWalkingDirection();
        updateEnemyState();
    }
    protected void updateToPlanet() {
        toPlanet = new Vector2(planetBody.getPosition().x - body.getPosition().x, planetBody.getPosition().y - body.getPosition().y);
        toPlanet.setLength2(1);
        toPlanet.scl(50);
    }
    protected void updatePerpen(){
        perpen.set(-toPlanet.y, toPlanet.x);
        perpen.setLength2(1);
        perpen.scl(moveSpeed);
    }
    protected void updateWalkingDirection() {
        player = gameWorld.getPlayer();
        toPlayer = player.getBody().getPosition().sub(this.getBody().getPosition()); //From enemy to player

        float angle = perpen.angle(toPlayer);

        if(enemyState == ENEMY_STATE.IDLE){
            moveLeft = false;
            moveRight = false;
        }
        else if (Math.abs(angle) < 90) {
            moveLeft = false;
            moveRight = true;
        } else {
            moveRight = false;
            moveLeft = true;
        }
    }
    protected void moveAlongPlanet(){
        //MOVE
        if (moveRight) {
            body.setLinearVelocity(perpen);
        } else if (moveLeft) {
            body.setLinearVelocity(-perpen.x, -perpen.y);
        } else {
            body.setLinearVelocity(0, 0);
        }
    }
    protected boolean toJump(){
        float angle = toPlanet.angle(toPlayer);
        return (Math.abs(angle) > 150);
    }
    protected void jump(){
        body.setLinearVelocity(-toPlanet.x + body.getLinearVelocity().x, -toPlanet.y + body.getLinearVelocity().y);
        entityState = ENTITY_STATE.JUMPING;
    }

    protected void updateEnemyState(){
        if(toPlayer.len2() > 1000){
            enemyState = ENEMY_STATE.IDLE;
        }
        else if(player.getPlanetBody() != this.getPlanetBody()){
            enemyState = ENEMY_STATE.HUNTING;
        }
        else{
            enemyState = ENEMY_STATE.ATTACKING;
        }
    }
}