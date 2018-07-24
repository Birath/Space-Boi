package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.utils.TimeUtils;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Chaser extends Enemy {

    private int damage = 30;

    private long timeLastTouched;
    private boolean touchingPlayer = false;

    private final int damageDelay = 700;

    public Chaser(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.CHASER);
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
        dealDamage();
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

    private void dealDamage(){
        if ((TimeUtils.millis() - timeLastTouched > damageDelay) && touchingPlayer){
            gameWorld.getPlayer().reduceHealth(damage);
            //Play bleed animation
            timeLastTouched = TimeUtils.millis();
        }
    }
    public void touchedPlayer(){
        timeLastTouched = TimeUtils.millis();
        touchingPlayer = true;
        //play bite animation
    }
    public void stoppedTouchingPlayer(){
        touchingPlayer = false;
    }
}
