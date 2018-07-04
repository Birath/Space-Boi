package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.ENTITY_STATE;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Enemy extends EntityDynamic {

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
        player = gameWorld.getPlayer();
        body.setUserData(this);

        this.health = 50;
        this.jumpHeight = 50;
        this.moveSpeed = 5;
        this.weapon = new Machinegun(gameWorld, this);
    }

    @Override
    public void update(float delta) {
        updateWalkingDirection();
        updateEnemyState();
        weapon.update(delta);
        if (entityState == ENTITY_STATE.STANDING) {
            updateToPlanet();
            updatePerpen();

            if (enemyState == ENEMY_STATE.ATTACKING) {
                if(toShoot()){
                    Shoot();
                }
                else{
                    moveAlongPlanet();
                }
            }
            else if (enemyState == ENEMY_STATE.HUNTING){
                if(toJump()){
                    jump();
                }
                else{
                    moveAlongPlanet();
                }
            }
        }
    }

    @Override
    public void onRemove() {

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
        toPlayer = player.getBody().getPosition().sub(this.getBody().getPosition()); //From enemy to player

        float angle = perpen.angle(toPlayer);
        if (Math.abs(angle) < 90) {
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
        if(player.getPlanetBody() != this.getPlanetBody()){
            enemyState = ENEMY_STATE.HUNTING;
        }
        else{
            enemyState = ENEMY_STATE.ATTACKING;
        }
    }

    protected boolean toShoot(){
        //Calculating if shooting is to happen
        Vector2 awayFromPlanet = new Vector2(-toPlanet.x, -toPlanet.y);
        float angle = awayFromPlanet.angle(toPlayer);

        return (Math.abs(angle) < 110); //110 should be calculated mathematically
    }

    protected void Shoot() {
        Vector2 recoil = new Vector2(-toPlayer.x, -toPlayer.y);
        recoil.setLength2(1);

        //Setting recoil
        recoil.scl(weapon.getRecoil());
        body.setLinearVelocity(recoil);

        //Creating the bullet
        recoil.setLength2(1);
        recoil.scl(-(rad * PPM));
        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + recoil.x, body.getPosition().y * PPM + recoil.y);

        weapon.Shoot(shootFrom.x, shootFrom.y, recoil);
    }
}