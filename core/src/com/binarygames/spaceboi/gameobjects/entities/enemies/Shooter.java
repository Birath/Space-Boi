package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Shooter extends Enemy {

    private Machinegun machinegun;

    public Shooter(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.SHOOTER);

        this.machinegun = new Machinegun(gameWorld, this);
    }

    @Override
    protected void updateIdle() {
        standStill();
    }

    @Override
    protected void updateHunting() {
        if (toJump()) {
            jump();
        } else {
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateAttacking() {
        Gdx.app.log("Shooter", "Angle to player: " + Math.abs(toPlanet.angle(toPlayer)));
        if (shouldShootWithNormalGun()) {
            shoot(machinegun);
        } else if (shouldShootWithNonGravityGun()) {
            shoot(weapon);
        } else {
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateJumping() {
        //Do nothing
    }

    private boolean toShoot() {
        //Calculating if shooting is to happen
        Vector2 awayFromPlanet = new Vector2(-toPlanet.x, -toPlanet.y);
        float angle = awayFromPlanet.angle(toPlayer);
        return (Math.abs(angle) < 110); //110 should be calculated mathematically
    }

    private boolean shouldShootWithNormalGun() {
        float angle = Math.abs(toPlanet.angle(toPlayer));
        return 80 < angle;
    }

    private boolean shouldShootWithNonGravityGun() {
        return 60 < Math.abs(toPlanet.angle(toPlayer));
    }

    private void shoot(Weapon shootWeapon) {
        Vector2 recoil = new Vector2(-toPlayer.x, -toPlayer.y);
        recoil.setLength2(1);

        //Setting recoil
        recoil.scl(shootWeapon.getRecoil());
        body.setLinearVelocity(recoil);

        perpen = new Vector2(-toPlanet.y, toPlanet.x);
        perpen.setLength2(1).scl(rad * PPM);
        if (Math.abs(perpen.angle(toPlayer)) > 90) {
            perpen.rotate(180);
        }

        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + perpen.x,
            body.getPosition().y * PPM + perpen.y);
        if (shootWeapon.getClass().getName().equals(Machinegun.class.getName())) {
            Vector2 shootDirection = new Vector2(toPlayer.x, toPlayer.y).setLength2(1).scl(rad * PPM);
            shootWeapon.Shoot(shootFrom.x, shootFrom.y, shootDirection);
        } else {
            shootWeapon.Shoot(shootFrom.x, shootFrom.y, perpen);
        }
    }
}
