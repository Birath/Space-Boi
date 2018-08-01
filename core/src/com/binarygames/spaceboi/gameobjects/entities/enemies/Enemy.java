package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.effects.ParticleHandler;
import com.binarygames.spaceboi.gameobjects.entities.ENTITY_STATE;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class Enemy extends EntityDynamic {

    protected Vector2 toPlanet = new Vector2(0, 0);
    protected Vector2 toPlayer = new Vector2(0, 0);
    protected Vector2 perpen = new Vector2(0, 0);
    protected GameWorld gameWorld;
    protected Player player;
    protected int enemyXP = 20;

    private int aggroDistance = 1000;
    private int deAggroDistance = 2000;

    private boolean hasNoticedPlayer = false;

    private int lastHealth;

    private ProgressBar healthBar;

    protected Weapon weapon;

    protected ENEMY_STATE enemyState = ENEMY_STATE.HUNTING;

    private Bullet lastBullet;

    private EnemyKind enemyKind;
    private static final int MINIMUM_MOVESPEED = 2;

    protected List<String> attackSounds = new ArrayList();
    protected List<String> damagedSounds = new ArrayList();
    protected List<String> deathSounds = new ArrayList();



    public Enemy(GameWorld gameWorld, float x, float y, String path, EnemyType enemyType) {
        super(gameWorld, x, y, path, enemyType.getMass(), enemyType.getRad(), enemyType.getHealth(), enemyType.getMoveSpeed(), enemyType.getJumpHeight());
        getSounds();
        switch (enemyType) {
            case CHASER:
                this.weapon = new Machinegun(gameWorld, this);
                break;
            case SHOOTER:
                this.weapon = new GravityFreeMachineGun(gameWorld, this);
                break;
            case FLYING_SHIP:
                this.weapon = new HomingRocketLauncher(gameWorld, this);
                break;
            case SPAWNER:
                break;
            default:
                throw new IllegalArgumentException("Invalid enemy type");
        }

        this.gameWorld = gameWorld;
        Skin uiSkin = gameWorld.getGame().getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);
        healthBar = new ProgressBar(0, health, 1, false, uiSkin);
        lastHealth = maxHealth;
        this.enemyKind = enemyType.getEnemyKind();
    }

    protected abstract void getSounds();


    @Override
    public void update(float delta) {
        updateEnemy();
        if (entityState == ENTITY_STATE.STANDING) {
            if (enemyState == ENEMY_STATE.IDLE) {
                updateIdle(delta);
            } else if (enemyState == ENEMY_STATE.ATTACKING) {
                updateAttacking(delta);
            } else if (enemyState == ENEMY_STATE.HUNTING) {
                updateHunting(delta);
            }
        } else if (entityState == ENTITY_STATE.JUMPING) {
            updateJumping(delta);
        }
    }

    protected abstract void updateIdle(float delta);

    protected abstract void updateHunting(float delta);

    protected abstract void updateAttacking(float delta);

    protected abstract void updateJumping(float delta);

    protected void updateEnemy() {
        if (planetBody != null) {
            updateToPlanet();
            updatePerpen();
            updateToPlayer();
            lookForPlayer();
            updateEnemyState();
            updateWalkingDirection();
        }
    }

    @Override
    public void onRemove() {
        int xpIncrease;
        if (lastBullet != null) {
            xpIncrease = Math.round(enemyXP * lastBullet.getWeapon().getXpFactor());
        } else {
            xpIncrease = enemyXP;
        }

        playRandomSound(deathSounds);
        gameWorld.getXp_handler().increaseXP(xpIncrease);
    }

    private void playRandomSound(List<String> soundList){
        if(soundList.size() < 1){
            return;
        }
        Random random = new Random();
        int selectedSound = random.nextInt(soundList.size());
        gameWorld.getGame().getSoundManager().play(soundList.get(selectedSound));
    }

    protected void updateToPlanet() {
        toPlanet = new Vector2(planetBody.getPosition().x - body.getPosition().x, planetBody.getPosition().y - body.getPosition().y);
        toPlanet.setLength2(1);
        toPlanet.scl(50);
    }

    protected void updatePerpen() {
        perpen.set(-toPlanet.y, toPlanet.x);
        perpen.setLength2(1);
        perpen.scl(moveSpeed);
    }

    protected void updateToPlayer() {
        player = gameWorld.getPlayer();
        toPlayer = player.getBody().getPosition().sub(this.getBody().getPosition()); //From enemy to player
    }

    protected void updateWalkingDirection() {
        float angle = perpen.angle(toPlayer);

        if (enemyState == ENEMY_STATE.IDLE) {
            moveLeft = false;
            moveRight = false;
        } else if (Math.abs(angle) < 90) {
            moveLeft = false;
            moveRight = true;
        } else {
            moveRight = false;
            moveLeft = true;
        }
    }

    protected void moveAlongPlanet() {
        //MOVE
        if (moveRight) {
            body.setLinearVelocity(perpen);
        } else if (moveLeft) {
            body.setLinearVelocity(-perpen.x, -perpen.y);
        } else {
            standStill();
        }
    }

    protected void standStill() {
        body.setLinearVelocity(0, 0);
    }

    protected boolean toJump() {
        float angle = toPlanet.angle(toPlayer);
        return (Math.abs(angle) > 150);
    }

    protected void jump() {
        body.setLinearVelocity(-toPlanet.x + body.getLinearVelocity().x, -toPlanet.y + body.getLinearVelocity().y);
        entityState = ENTITY_STATE.JUMPING;
    }

    protected void lookForPlayer() {
        if (toPlayer.len2() > deAggroDistance && hasNoticedPlayer) { //set player idle if he is far away
            hasNoticedPlayer = false;
            lastHealth = health;
        } else if (toPlayer.len2() < aggroDistance && !hasNoticedPlayer) {
            hasNoticedPlayer = true;
            playRandomSound(attackSounds);
        } else if (health != lastHealth && !hasNoticedPlayer) {
            hasNoticedPlayer = true;
        }
    }

    protected void updateEnemyState() {
        if (!hasNoticedPlayer) {
            enemyState = ENEMY_STATE.IDLE;
        } else if (!Objects.equals(player.getPlanetBody(), this.getPlanetBody())) {
            enemyState = ENEMY_STATE.HUNTING;
        } else {
            enemyState = ENEMY_STATE.ATTACKING;
        }
    }

    public void setLastBullet(Bullet bullet) {
        lastBullet = bullet;
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
        healthBar.setBounds(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
        healthBar.setValue(health);
        healthBar.draw(batch, 1);

    }
    public void receiveWeaponEffects(Bullet bullet){
        this.reduceHealth(bullet.getDamage());
        playRandomSound(damagedSounds);

        if(enemyKind == EnemyKind.BIOLOGICAL){
            this.reduceHealth(bullet.getBioDamage());
            if(bullet.getBioDamage() > 0){
                gameWorld.getParticleHandler().addEffect(ParticleHandler.EffectType.FIRE,
                        this.getBody().getPosition().x * PPM, this.getBody().getPosition().y * PPM);
            }
        }
        else if(enemyKind == EnemyKind.MECHANICAL){
            this.reduceHealth(bullet.getMechDamage());
            if(bullet.getMechDamage() > 0){
                //apply mechdamage graphical effect
            }
        }
        else{
            gameWorld.getParticleHandler().addEffect(ParticleHandler.EffectType.BLOOD,
                    this.getBody().getPosition().x * PPM, this.getBody().getPosition().y * PPM);
        }

        if (MINIMUM_MOVESPEED < this.getMoveSpeed() - bullet.getSlow()){
            this.setMoveSpeed(this.getMoveSpeed() - bullet.getSlow());
            if(bullet.getMechDamage() > 0){
                //Apply slow particle
            }
        }
    }
}