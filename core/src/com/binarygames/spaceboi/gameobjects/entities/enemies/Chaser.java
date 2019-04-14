package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.animation.AnimationHandler;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Chaser extends Enemy implements MeleeEnemy {

    private int damage = 30;

    private long timeLastTouched;
    private boolean touchingPlayer = false;

    private final int damageDelay = 700;

    //Animation
    private static final int RUN_FRAME_COLUMNS = 7;
    private static final int RUN_FRAME_ROWS = 2;
    private static final float runFrameDuration = 0.05f;

    private static final int dog_width = 2419;
    private static final int dog_height = 1747;

    public Chaser(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.CHASER);
        animationHandler = new AnimationHandler(gameWorld, RUN_FRAME_COLUMNS, RUN_FRAME_ROWS, runFrameDuration, Assets.DOG_RUNNING_ANIMATION);
    }

    @Override
    protected void getSounds() {
        attackSounds.add(Assets.DOG1);
        attackSounds.add(Assets.DOG2);
        attackSounds.add(Assets.DOG3);
    }

    @Override
    protected void updateIdle(float delta) {
        standStill();
    }

    @Override
    protected void updateHunting(float delta) {
        if(toJump()){
            jump();
        }
        else{
            moveAlongPlanet();
            animationHandler.updateAnimation(delta);
        }
    }

    @Override
    protected void updateAttacking(float delta) {
        dealDamage();
        if(toJump()){
            jump();
        }
        else{
            moveAlongPlanet();
            animationHandler.updateAnimation(delta);
        }
    }


    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
        //sprite.setPosition(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        //batch.draw(animationHandler.getCurrentFrame(), body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        batch.draw(animationHandler.getCurrentFrame(), body.getPosition().x * PPM - dog_width / 2, body.getPosition().y * PPM - dog_height / 2, dog_width / 2, dog_height / 2, dog_width, dog_height, 0.05f, 0.05f, body.getAngle() + 90);
    }

    @Override
    protected void updateJumping(float delta) {
        //Do nothing
    }

    private void dealDamage(){
        if ((TimeUtils.millis() - timeLastTouched > damageDelay) && touchingPlayer) {
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
