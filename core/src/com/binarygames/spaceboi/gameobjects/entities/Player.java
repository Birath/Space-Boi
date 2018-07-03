package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.GrenadeLauncher;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Shotgun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.utils.JointInfo;

import java.util.ArrayList;
import java.util.List;

public class Player extends EntityDynamic {

    private boolean mouseHeld;

    private Weapon weapon;
    private List<Weapon> weaponList;

    private Vector2 mouseCoords = new Vector2(0, 0);
    private Vector2 toPlanet = new Vector2(0, 0);
    private Vector2 perpen = new Vector2(0,0);

    private float playerAngle = 0f;

    private GameWorld gameWorld;
    private Planet closestPlanet;
    private boolean chained = false;

    public Player(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
        body.setUserData(this);
        this.gameWorld = gameWorld;

        weaponList = new ArrayList<>();
        weaponList.add(new Shotgun(gameWorld, this));
        weaponList.add(new Machinegun(gameWorld, this));
        weaponList.add(new GrenadeLauncher(gameWorld, this));
        this.weapon = weaponList.get(0);
        this.health = 1000;
        this.jumpHeight = 20;
        this.moveSpeed = 20;
    }

    @Override
    public void update(float delta) {
        if (entityState == ENTITY_STATE.STANDING) {
            updateToPlanet();
            updatePerpen();

            //Moving
            if (moveRight) {
                body.setLinearVelocity(perpen); //Dynamiska adderande blir kanske bättre än att bara sätta saker och ting
            } else if (moveLeft) {
                body.setLinearVelocity(-perpen.x, -perpen.y);
            }
            if( (!moveLeft) && (!moveRight) ) {
                body.setLinearVelocity(0, 0);
            }

            //Jumping
            if (moveUp) {
                if (chained) {
                    for (JointEdge jointEdge : body.getJointList()) {
                        gameWorld.addJointToRemove(jointEdge.joint);
                    }
                    chained = false;
                } else {
                    body.setLinearVelocity(-toPlanet.x + body.getLinearVelocity().x, -toPlanet.y + body.getLinearVelocity().y);
                    entityState = ENTITY_STATE.JUMPING;
                }
            }
        }
        //Aiming
        updateMouseCoords();
        //Shooting
        updateWeapons(delta);
        if (mouseHeld && weapon.canShoot()) {
            Shoot();
        }
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        getSprite().setPosition(body.getPosition().x * PPM - getSprite().getWidth() / 2, body.getPosition().y * PPM - getSprite().getHeight() / 2);
        weapon.render(batch, camera, this);
        getSprite().setOrigin(getSprite().getWidth() / 2, getSprite().getHeight() / 2);
        getSprite().setRotation(getPlayerAngle());
        getSprite().draw(batch);
    }

    private void Shoot() {
        Vector2 recoil = new Vector2(body.getPosition().x * PPM - mouseCoords.x, body.getPosition().y * PPM - mouseCoords.y);
        recoil.setLength2(1);
        System.out.println(recoil);

        //Setting recoil of player
        recoil.scl(weapon.getRecoil());

        Vector2 newVelocity = body.getLinearVelocity();
        newVelocity.add(recoil);
        body.setLinearVelocity(newVelocity);

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
    private void updatePerpen(){
        perpen = new Vector2(-toPlanet.y, toPlanet.x);
        perpen.setLength2(1);
        perpen.scl(moveSpeed);
    }

    public float getPlayerAngle() {
        return playerAngle + 90; // TODO fix magic number
    }

    public void setPlayerAngle(float angle) {
        playerAngle = angle;
    }

    //Handeling planet
    @Override
    public void hitPlanet(Planet planet) {
        super.hitPlanet(planet);
        // Don't chain the player is holding the jump button
        if (!moveUp) {
            gameWorld.addJoints(new JointInfo(body, planet.getBody()));
            Gdx.app.log("ContactListener", "Creating joint: " + this + ", " + planet);
            chained = true;
        }

    }

    public Planet getClosestPlanet() {
        return closestPlanet;
    }

    public void setClosestPlanet(final Planet closestPlanet) {
        this.closestPlanet = closestPlanet;
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

    public boolean isChained() {
        return chained;
    }
}


