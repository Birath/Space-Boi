package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.binarygames.spaceboi.gameobjects.entities.LaunchPad;
import com.binarygames.spaceboi.gameobjects.entities.enemies.MeleeEnemy;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Chaser;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Enemy;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.HomingRocket;
import com.binarygames.spaceboi.gameobjects.pickups.Pickup;

public class EntityContactListener implements ContactListener {

    private GameWorld gameWorld;

    public EntityContactListener(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.isSensor()) {
            Gdx.app.log("EntityContactListener", "I'm a sensor");
            //fixtureA.getBody().setLinearVelocity(fixtureA.getBody().getLinearVelocity().scl(-1));
            fixtureA.getBody().setLinearVelocity(0, 0);
        }
        if (fixtureB.isSensor()) {
            Gdx.app.log("EntityContactListener", "I'm a sensor");
            //fixtureB.getBody().setLinearVelocity(fixtureB.getBody().getLinearVelocity().scl(-1));
            fixtureB.getBody().setLinearVelocity(0, 0);
        }
        if (fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null) return;


        //Player x Planet
        if (fixtureA.getBody().getUserData() instanceof Planet &&
                fixtureB.getBody().getUserData() instanceof Player) {
            Player player = (Player) fixtureB.getBody().getUserData();
            Planet planet = (Planet) fixtureA.getBody().getUserData();
            player.hitPlanet(planet);
        } else if (fixtureA.getBody().getUserData() instanceof Player &&
                fixtureB.getBody().getUserData() instanceof Planet) {
            Player player = (Player) fixtureA.getBody().getUserData();
            Planet planet = (Planet) fixtureB.getBody().getUserData();
            player.hitPlanet(planet);
        }
        //Player x Bullet
        else if (fixtureA.getBody().getUserData() instanceof Bullet &&
                fixtureB.getBody().getUserData() instanceof Player) {
            Bullet bullet = (Bullet) fixtureA.getBody().getUserData();
            Player player = (Player) fixtureB.getBody().getUserData();
            if (!bullet.getShooter().equals(player)) {
                player.reduceHealth(bullet.getDamage());
                bullet.setHasHit(true); //Remove bullets only if they dont hit yourself
            }
        } else if (fixtureA.getBody().getUserData() instanceof Player &&
                fixtureB.getBody().getUserData() instanceof Bullet) {
            Bullet bullet = (Bullet) fixtureB.getBody().getUserData();
            Player player = (Player) fixtureA.getBody().getUserData();
            if (!bullet.getShooter().equals(player)) {
                player.reduceHealth(bullet.getDamage());
                bullet.setHasHit(true);
            }
        }
        //Player x Melee enemy
        else if (fixtureA.getBody().getUserData() instanceof MeleeEnemy &&
                fixtureB.getBody().getUserData() instanceof Player) {
            MeleeEnemy meleeEnemy = (MeleeEnemy) fixtureA.getBody().getUserData();
            meleeEnemy.touchedPlayer();
        } else if (fixtureA.getBody().getUserData() instanceof Player &&
                fixtureB.getBody().getUserData() instanceof MeleeEnemy) {
            MeleeEnemy meleeEnemy = (MeleeEnemy) fixtureB.getBody().getUserData();
            meleeEnemy.touchedPlayer();
        }


        //Enemy x Planet
        else if (fixtureA.getBody().getUserData() instanceof Planet &&
                fixtureB.getBody().getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
            enemy.hitPlanet((Planet) fixtureA.getBody().getUserData());
        } else if (fixtureA.getBody().getUserData() instanceof Enemy &&
                fixtureB.getBody().getUserData() instanceof Planet) {
            Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
            enemy.hitPlanet((Planet) fixtureB.getBody().getUserData());
        }
        //Enemy x Bullet
        else if (fixtureA.getBody().getUserData() instanceof Bullet &&
                fixtureB.getBody().getUserData() instanceof Enemy) {
            Bullet bullet = (Bullet) fixtureA.getBody().getUserData();
            Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
            if (!bullet.getShooter().equals(enemy)) {
                enemy.receiveWeaponEffects(bullet);
                bullet.applyLifeSteal();
                bullet.setHasHit(true);
            }
        } else if (fixtureA.getBody().getUserData() instanceof Enemy &&
                fixtureB.getBody().getUserData() instanceof Bullet) {
            Bullet bullet = (Bullet) fixtureB.getBody().getUserData();
            Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
            if (!bullet.getShooter().equals(enemy)) {
                enemy.receiveWeaponEffects(bullet);
                bullet.applyLifeSteal();
                bullet.setHasHit(true);
            }
        }

        //Bullet x Planet
        else if (fixtureA.getBody().getUserData() instanceof Planet &&
                fixtureB.getBody().getUserData() instanceof Bullet) {
            Bullet bullet = (Bullet) fixtureB.getBody().getUserData();
            bullet.setHasHit(true);
        } else if (fixtureA.getBody().getUserData() instanceof Bullet &&
                fixtureB.getBody().getUserData() instanceof Planet) {
            Bullet bullet = (Bullet) fixtureA.getBody().getUserData();
            bullet.setHasHit(true);
        }

        // Pickup x Player
        else if (fixtureA.getBody().getUserData() instanceof Pickup &&
                fixtureB.getBody().getUserData() instanceof Player) {
            Pickup pickup = (Pickup) fixtureA.getBody().getUserData();
            Player player = (Player) fixtureB.getBody().getUserData();
            pickup.onHit(player);

        } else if (fixtureA.getBody().getUserData() instanceof Player &&
                fixtureB.getBody().getUserData() instanceof Pickup) {
            Pickup pickup = (Pickup) fixtureB.getBody().getUserData();
            Player player = (Player) fixtureA.getBody().getUserData();
            pickup.onHit(player);
        }
        // Homingrocket x Bullet
        else if (fixtureA.getBody().getUserData() instanceof Bullet &&
                fixtureB.getBody().getUserData() instanceof HomingRocket) {
            Bullet bullet = (Bullet) fixtureA.getBody().getUserData();
            bullet.setHasHit(true);
            HomingRocket homingRocket = (HomingRocket) fixtureB.getBody().getUserData();
            if (!(bullet instanceof HomingRocket)) {
                homingRocket.setHasHit(true); //Did not work
            }
        } else if (fixtureA.getBody().getUserData() instanceof HomingRocket &&
                fixtureB.getBody().getUserData() instanceof Bullet) {
            Bullet bullet = (Bullet) fixtureB.getBody().getUserData();
            bullet.setHasHit(true);
            HomingRocket homingRocket = (HomingRocket) fixtureA.getBody().getUserData();
            if (!(bullet instanceof HomingRocket)) {
                homingRocket.setHasHit(true);
            }
        } else if (fixtureA.getBody().getUserData() instanceof LaunchPad &&
                fixtureB.getBody().getUserData() instanceof Player) {
            LaunchPad launchPad = (LaunchPad) fixtureA.getBody().getUserData();
            Player player = (Player) fixtureB.getBody().getUserData();
            player.hitLauchPad(launchPad);
        } else if (fixtureB.getBody().getUserData() instanceof LaunchPad &&
                fixtureA.getBody().getUserData() instanceof Player) {
            LaunchPad launchPad = (LaunchPad) fixtureB.getBody().getUserData();
            Player player = (Player) fixtureA.getBody().getUserData();
            player.hitLauchPad(launchPad);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null) return;

        //Player x Planet
        if (fixtureA.getBody().getUserData() instanceof Planet &&
                fixtureB.getBody().getUserData() instanceof Player) {
            Player player = (Player) fixtureB.getBody().getUserData();
            if (!player.isChained()) {
                player.leftPlanet();
            }
        } else if (fixtureA.getBody().getUserData() instanceof Player &&
                fixtureB.getBody().getUserData() instanceof Planet) {
            Player player = (Player) fixtureA.getBody().getUserData();
            if (!player.isChained()) {
                player.leftPlanet();
            }
        }
        //Enemy x Planet
        else if (fixtureA.getBody().getUserData() instanceof Planet &&
                fixtureB.getBody().getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
            enemy.leftPlanet();
        } else if (fixtureA.getBody().getUserData() instanceof Enemy &&
                fixtureB.getBody().getUserData() instanceof Planet) {
            Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
            enemy.leftPlanet();
        }
        //Chaser x Player
        else if (fixtureA.getBody().getUserData() instanceof Player &&
                fixtureB.getBody().getUserData() instanceof Chaser) {
            Chaser chaser = (Chaser) fixtureB.getBody().getUserData();
            chaser.stoppedTouchingPlayer();
        } else if (fixtureA.getBody().getUserData() instanceof Chaser &&
                fixtureB.getBody().getUserData() instanceof Player) {
            Chaser chaser = (Chaser) fixtureA.getBody().getUserData();
            chaser.stoppedTouchingPlayer();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getBody().getUserData() instanceof Pickup) {
            if (!(fixtureB.getBody().getUserData() instanceof Planet)) {
                contact.setEnabled(false);
            } else {
                ((Pickup)fixtureA.getBody().getUserData()).setHitPlane(true);
                contact.setEnabled(true);
            }
        } else if (fixtureB.getBody().getUserData() instanceof Pickup) {
            if (!(fixtureA.getBody().getUserData() instanceof Planet)) {
                contact.setEnabled(false);
            } else {
                ((Pickup)fixtureB.getBody().getUserData()).setHitPlane(true);
                contact.setEnabled(true);
            }
        } else if (fixtureA.getBody().getUserData() instanceof LaunchPad ||
                fixtureB.getBody().getUserData() instanceof LaunchPad) {
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}