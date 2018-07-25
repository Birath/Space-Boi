package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class GravityFreeBullet extends Bullet {

    private boolean invertPerpen = false;
    Vector2 toPlanet;
    Vector2 perpen;
    private static final int MAXIMUM_DISTANCE = 2300;

    public GravityFreeBullet(GameWorld gameWorld, float x, float y, String path, Vector2 speed, float mass, float radius, long removeDelay, int damage, EntityDynamic shooter, Weapon weapon) {
        super(gameWorld, x, y, path, speed, mass, radius, removeDelay, damage, shooter, weapon);

        Vector2 toPlayer = new Vector2(gameWorld.getPlayer().getBody().getPosition().x - this.getBody().getPosition().x,
                gameWorld.getPlayer().getBody().getPosition().y - this.getBody().getPosition().y);
        this.setPlanetBody(shooter.getPlanetBody());
        toPlanet = new Vector2(planetBody.getPosition().x - body.getPosition().x, planetBody.getPosition().y - body.getPosition().y);
        perpen = new Vector2(-toPlanet.y, toPlanet.x);
        if (Math.abs(perpen.angle(toPlayer)) > 90) {
            invertPerpen = true;
        }
    }

    @Override
    public boolean isAffectedByGravity() {
        return false;
    }

    @Override
    public void update(float delta) {
        toPlanet = new Vector2(planetBody.getPosition().x - body.getPosition().x, planetBody.getPosition().y - body.getPosition().y);
        perpen = new Vector2(-toPlanet.y, toPlanet.x);
        perpen.setLength2(this.getBody().getLinearVelocity().len2());
        if (invertPerpen) {
            perpen.rotate(180);
        }

        this.getBody().setLinearVelocity(perpen);
    }

    @Override
    public boolean shouldRemove(Vector2 playerPosition) {
        return (this.getBody().getPosition().sub(this.getShooter().getBody().getPosition()).len2() > MAXIMUM_DISTANCE) || hasHit; //shorter range to removal
    }

}
