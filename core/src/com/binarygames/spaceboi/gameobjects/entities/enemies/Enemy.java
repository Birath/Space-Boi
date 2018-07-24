package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.ENTITY_STATE;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.*;

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

    public Enemy(GameWorld gameWorld, float x, float y, String path, EnemyType enemyType) {
        super(gameWorld, x, y, path, enemyType.getMass(), enemyType.getRad(), enemyType.getHealth(), enemyType.getMoveSpeed(), enemyType.getJumpHeight());
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
            default:
                throw new IllegalArgumentException("Invalid enemy type");
        }

        this.gameWorld = gameWorld;
        Skin uiSkin = gameWorld.getGame().getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);
        healthBar = new ProgressBar(0, health, 1, false, uiSkin);
        lastHealth = maxHealth;
    }

    @Override
    public void update(float delta) {
        updateEnemy();
        if (entityState == ENTITY_STATE.STANDING) {
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
        if(planetBody != null){
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
        gameWorld.getXp_handler().increaseXP(enemyXP);
        /*
        System.out.println("level " + gameWorld.getXp_handler().getLevel());
        System.out.println("xp " + gameWorld.getXp_handler().getCurrentXP());
        System.out.println("nextlevel " + gameWorld.getXp_handler().getNextLevel());
        */
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
    protected void updateToPlayer(){
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
    protected void standStill(){
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
    protected void lookForPlayer(){
        if(toPlayer.len2() > deAggroDistance && hasNoticedPlayer){ //set player idle if he is far away
            hasNoticedPlayer = false;
            lastHealth = health;
        }
        else if(toPlayer.len2() < aggroDistance && !hasNoticedPlayer){
            hasNoticedPlayer = true;
        }
        else if(health != lastHealth && !hasNoticedPlayer){
            hasNoticedPlayer = true;
        }
    }

    protected void updateEnemyState() {
        if (!hasNoticedPlayer) {
            enemyState = ENEMY_STATE.IDLE;
        } else if (player.getPlanetBody() != this.getPlanetBody()) {
            enemyState = ENEMY_STATE.HUNTING;
        } else {
            enemyState = ENEMY_STATE.ATTACKING;
        }
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
        healthBar.setBounds(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
        healthBar.setValue(health);
        healthBar.draw(batch, 1);

    }
}