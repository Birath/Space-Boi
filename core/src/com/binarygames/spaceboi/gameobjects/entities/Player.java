package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.GrenadeLauncher;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Shotgun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;
import com.binarygames.spaceboi.gameobjects.utils.JointInfo;

import java.util.ArrayList;
import java.util.List;


public class Player extends EntityDynamic {

    private static final int START_HEALTH = 200;
    private static final int MOVE_SPEED = 20;
    private static final int JUMP_HEIGHT = 20;
    private static final float MAX_SPEED = 6000;

    private static final int WALK_FRAME_COLUMNS = 4;
    private static final int WALK_FRAME_ROWS = 15;
    private static final int MAX_RECOIL_ANGLE = 40;

    public int spriteWidth = 500;
    public int spriteHeight = 500;

    private Animation walkAnimation;
    private TextureRegion currentFrame;
    private float animationTime;
    private boolean spriteIsFlipped;

    private boolean mouseHeld;

    private Weapon weapon;
    private List<Weapon> weaponList;
    private boolean toReload = false;

    private List<WeaponAttachment> inventory;

    private Vector2 mouseCoords = new Vector2(0, 0);
    private Vector2 toPlanet = new Vector2(0, 0);
    private Vector2 perpen = new Vector2(0, 0);
    private float playerAngle = 0f;

    private boolean isLaunching = false;

    private GameWorld gameWorld;
    private boolean isChained = false;

    private boolean god = false;
    private boolean infiniteAmmo = false;
    private boolean shouldApplyRecoil = false;
    private Vector2 storedRecoil;

    // TODO DONT DO THIS HERE! FIX SOUNDMANAGER
    private boolean isWalking;
    private long walkingSoundID;
    private Sound walkingSound;
    private Planet launchPlanet;

    public Player(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius, START_HEALTH, MOVE_SPEED, JUMP_HEIGHT);
        body.setUserData(this);
        this.gameWorld = gameWorld;

        weaponList = new ArrayList<>();
        weaponList.add(new Shotgun(gameWorld, this));
        weaponList.add(new Machinegun(gameWorld, this));
        weaponList.add(new GrenadeLauncher(gameWorld, this));
        this.weapon = weaponList.get(0);

        inventory = new ArrayList<>();

        // Walk animation
        Texture walkAtlas = gameWorld.getGame().getAssetManager().get(Assets.PLAYER_WALK_ANIMATION, Texture.class);
        TextureRegion[][] walkTemp = TextureRegion.split(walkAtlas, walkAtlas.getWidth() / WALK_FRAME_COLUMNS, walkAtlas.getHeight() / WALK_FRAME_ROWS);
        TextureRegion[] walkFrames = new TextureRegion[WALK_FRAME_COLUMNS * WALK_FRAME_ROWS];
        int walkIndex = 0;
        for (int i = 0; i < WALK_FRAME_ROWS; i++) {
            for (int j = 0; j < WALK_FRAME_COLUMNS; j++) {
                walkFrames[walkIndex++] = walkTemp[i][j];
            }
        }
        walkAnimation = new Animation(0.01f, walkFrames);
        animationTime = 0;
        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(animationTime, true);

        walkingSound = gameWorld.getGame().getAssetManager().get(Assets.PLAYER_FOOTSTEP, Sound.class);
    }

    @Override
    public void update(float delta) {
        updateToPlanet();
        updatePerpen();
        // http://www.iforce2d.net/b2dtut/constant-speed
        // TODO Check above site about movement
        if (entityState == ENTITY_STATE.STANDING) {
            //Moving
            if (moveRight) {
                body.setLinearVelocity(perpen); //Dynamiska adderande blir kanske bättre än att bara sätta saker och ting
            } else if (moveLeft) {
                body.setLinearVelocity(-perpen.x, -perpen.y);
            }
            if (!moveLeft && !moveRight && !(mouseHeld && weapon.canShoot()) && isChained) {
                body.setLinearVelocity(0, 0);
            } else {
                animationTime += delta;
                currentFrame = (TextureRegion) walkAnimation.getKeyFrame(animationTime, true);
            }

            //Jumping
            if (moveUp) {
                if (isChained) {
                    removeJoints();
                    isChained = false;
                } else {
                    body.setLinearVelocity(-toPlanet.x + body.getLinearVelocity().x, -toPlanet.y + body.getLinearVelocity().y);
                    entityState = ENTITY_STATE.JUMPING;
                }
            }
        } else if (isLaunching) {
            if (launchPlanet.equals(getClosestPlanet())) {
                Vector2 launchVector = toPlanet.cpy();
                launchVector.setLength(1).scl(-LaunchPad.LAUNCH_PAD_SPEED);
                body.setLinearVelocity(launchVector);
            } else {
                isLaunching = false;
            }
        }

        // Walking sound
        if (entityState == ENTITY_STATE.STANDING) {
            if (moveLeft || moveRight) {
                if (!isWalking && gameWorld.getGame().getPreferences().isSoundEnabled()) {
                    isWalking = true;
                    walkingSoundID = walkingSound.loop(gameWorld.getGame().getPreferences().getSoundVolume());
                }
            } else {
                if (isWalking && gameWorld.getGame().getPreferences().isSoundEnabled()) {
                    isWalking = false;
                    walkingSound.stop(walkingSoundID);
                }
            }
        } else {
            if (isWalking && gameWorld.getGame().getPreferences().isSoundEnabled()) {
                isWalking = false;
                walkingSound.stop(walkingSoundID);
            }
        }

        spriteIsFlipped = Gdx.input.getX() <= Gdx.graphics.getWidth() / 2;

        //Reloading
        reloadWeapon();
        //Aiming
        updateMouseCoords();

        if (shouldApplyRecoil) {
            Vector2 speed = body.getLinearVelocity().cpy();
            if (speed.add(storedRecoil).len() <= MAX_SPEED) {
                body.setLinearVelocity(body.getLinearVelocity().add(storedRecoil));
            }
            shouldApplyRecoil = false;
            storedRecoil.scl(0);
        }
        if (mouseHeld && weapon.canShoot()) {
            Shoot();
        }
    }

    private void removeJoints() {
        for (JointEdge jointEdge : body.getJointList()) {
            gameWorld.addJointToRemove(jointEdge.joint);
        }
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        getSprite().setPosition(body.getPosition().x * PPM - getSprite().getWidth() / 2, body.getPosition().y * PPM - getSprite().getHeight() / 2);
        weapon.render(batch, camera, this);
        getSprite().setOrigin(getSprite().getWidth() / 2, getSprite().getHeight() / 2);
        getSprite().setRotation(playerAngle + 90);
        getSprite().draw(batch);

        if (spriteIsFlipped) {
            currentFrame.flip(true, false);
            batch.draw(currentFrame, body.getPosition().x * PPM - spriteWidth / 2, body.getPosition().y * PPM - spriteHeight / 2, spriteWidth / 2, spriteHeight / 2, spriteWidth, spriteHeight, 0.08f, 0.08f, playerAngle + 90);
            currentFrame.flip(true, false);
        } else {
            batch.draw(currentFrame, body.getPosition().x * PPM - spriteWidth / 2, body.getPosition().y * PPM - spriteHeight / 2, spriteWidth / 2, spriteHeight / 2, spriteWidth, spriteHeight, 0.08f, 0.08f, playerAngle + 90);
        }
    }

    @Override
    public void onRemove() {
        gameWorld.respawnPlayer();
    }

    private void Shoot() {
        Vector2 recoil = new Vector2(body.getPosition().x * PPM - mouseCoords.x, body.getPosition().y * PPM - mouseCoords.y);
        recoil.setLength2(1);

        //Setting recoil of player
        recoil.scl(weapon.getRecoil());

        if (isChained && Math.abs(toPlanet.angle(recoil)) < MAX_RECOIL_ANGLE) {
            shouldApplyRecoil = false;
        } else if (isChained) {
            storedRecoil = recoil.cpy();
            shouldApplyRecoil = true;
            removeJoints();
            isChained = false;
            entityState = ENTITY_STATE.JUMPING;
        } else {
            storedRecoil = recoil.cpy();
            shouldApplyRecoil = true;
        }
        /*
            Vector2 newVelocity = body.getLinearVelocity();
            newVelocity.add(recoil);
            body.setLinearVelocity(newVelocity);
       */

        //Shooting the bullet
        recoil.setLength2(1);
        recoil.scl(-(rad * PPM));
        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + recoil.x, body.getPosition().y * PPM + recoil.y);
        weapon.Shoot(shootFrom.x, shootFrom.y, recoil);
    }

    private void updateWeapons(float delta) {
        for (Weapon weaponObject : weaponList) {
            weaponObject.update(delta);
        }
    }

    //Mouse related, called by playerinputprocessor
    public void setMouseHeld(boolean mouseHeld) {
        this.mouseHeld = mouseHeld;
    }

    public Vector2 getMouseCoords() {
        return mouseCoords;
    }

    public boolean isMouseHeld() {
        return mouseHeld;
    }

    public void setMouseCoords(float x, float y) {
        mouseCoords.set(x, y);
    }

    private void updateMouseCoords() {
        Vector3 mouseCoordinatos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameWorld.getCamera().unproject(mouseCoordinatos);
        setMouseCoords(mouseCoordinatos.x, mouseCoordinatos.y);
    }

    //Rotation of player
    private void updateToPlanet() {
        toPlanet = new Vector2(planetBody.getPosition().x - body.getPosition().x, planetBody.getPosition().y - body.getPosition().y);
        toPlanet.setLength2(1);
        toPlanet.scl(jumpHeight);
    }

    private void updatePerpen() {
        perpen = new Vector2(-toPlanet.y, toPlanet.x);
        perpen.setLength2(1);
        perpen.scl(moveSpeed);
    }

    public float getPlayerAngle() {
        return playerAngle; // TODO fix magic number
    }

    public void setPlayerAngle(float angle) {
        playerAngle = angle;
        //Gdx.app.debug("Player", "New playerAngle: " + angle);
    }

    //Handling planet
    @Override
    public void hitPlanet(Planet planet) {
        super.hitPlanet(planet);
        // If the player is shooting, not reloading and the weapon is not on cooldown or using a machin
        if (mouseHeld && !weapon.isReloading() && (weapon.isTimeBetweenShotsIsFinished() || weapon instanceof Machinegun)) {
            // Nothing happens
            // Don't chain the player if they are holding the jump button
        } else if (moveUp) {
            // Nothing happens
        } else {
            gameWorld.addJoints(new JointInfo(body, planet.getBody()));
            isChained = true;
        }
    }

    public void hitLauchPad(LaunchPad launchPad) {
        if (getClosestPlanet() != null) {
            launchPlanet = getClosestPlanet();
            entityState = ENTITY_STATE.JUMPING;
            isLaunching = true;
            removeJoints();
            isChained = false;
        }
        gameWorld.getGame().getSoundManager().play(Assets.LAUNCH_PAD_SOUND);
    }

    @Override
    public void reduceHealth(int amount) {
        if (!god) {
            health -= amount;
        }
    }

    public void increaseHealth(int amount) {
        if (health + amount > START_HEALTH)
            health += amount;
        if (health + amount >= maxHealth) {
            health = maxHealth;
        } else {
            health += amount;
        }
    }

    public void increaseMaxHealth(int amount) {
        maxHealth += amount;
    }

    //Weapon
    public void addWeapon(Weapon weapon) {
        this.weaponList.add(weapon);
    }

    public void setWeapon(int index) {
        this.weapon = weaponList.get(index);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    public void reloadWeapon() {
        if (toReload) {
            weapon.reload();
            toReload = false;
        }
    }

    public void setToReloadTrue() {
        toReload = true;
    }

    public List<WeaponAttachment> getInventory() {
        return inventory;
    }

    public void addToInventory(WeaponAttachment attachment) {
        inventory.add(attachment);
    }

    public boolean isChained() {
        return isChained;
    }

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

    public boolean hasInfiniteAmmo() {
        return infiniteAmmo;
    }

    public void setInfiniteAmmo(boolean infiniteAmmo) {
        this.infiniteAmmo = infiniteAmmo;
    }

}


