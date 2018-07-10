package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.physics.box2d.*;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Chaser;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Enemy;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.pickups.HealthPickup;
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
        if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Player.class.isInstance(fixtureB.getBody().getUserData())) {
            Player player = (Player) fixtureB.getBody().getUserData();
            Planet planet = (Planet) fixtureA.getBody().getUserData();
            player.hitPlanet(planet);
        } else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            Player player = (Player) fixtureA.getBody().getUserData();
            Planet planet = (Planet) fixtureB.getBody().getUserData();
            player.hitPlanet(planet);
        }
        //Player x Bullet
        else if (Bullet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Player.class.isInstance(fixtureB.getBody().getUserData())) {
            Bullet bullet = (Bullet) fixtureA.getBody().getUserData();
            Player player = (Player) fixtureB.getBody().getUserData();
            if (!bullet.getShooter().equals(player)) {
                player.reduceHealth(bullet.getDamage());
                bullet.setHasHit(true); //Remove bullets only if they dont hit yourself
            }
        } else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                Bullet.class.isInstance(fixtureB.getBody().getUserData())) {
            Bullet bullet = (Bullet) fixtureB.getBody().getUserData();
            Player player = (Player) fixtureA.getBody().getUserData();
            if (!bullet.getShooter().equals(player)) {
                player.reduceHealth(bullet.getDamage());
                bullet.setHasHit(true);
            }
        }
        //Player x Chaser
        else if (Chaser.class.isInstance(fixtureA.getBody().getUserData()) &&
                Player.class.isInstance(fixtureB.getBody().getUserData())) {
            Chaser chaser = (Chaser) fixtureA.getBody().getUserData();
            Player player = (Player) fixtureB.getBody().getUserData();
            player.reduceHealth(chaser.getDamage());
        } else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                Chaser.class.isInstance(fixtureB.getBody().getUserData())) {
            Chaser chaser = (Chaser) fixtureB.getBody().getUserData();
            Player player = (Player) fixtureA.getBody().getUserData();
            player.reduceHealth(chaser.getDamage());
        }


        //Enemy x Planet
        else if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Enemy.class.isInstance(fixtureB.getBody().getUserData())) {
            Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
            enemy.hitPlanet((Planet) fixtureA.getBody().getUserData());
        } else if (Enemy.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
            enemy.hitPlanet((Planet) fixtureB.getBody().getUserData());
        }
        //Enemy x Bullet
        else if (Bullet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Enemy.class.isInstance(fixtureB.getBody().getUserData())) {
            Bullet bullet = (Bullet) fixtureA.getBody().getUserData();
            Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
            if (!bullet.getShooter().equals(enemy)) {
                enemy.reduceHealth(bullet.getDamage());
                bullet.setHasHit(true);
            }
        } else if (Enemy.class.isInstance(fixtureA.getBody().getUserData()) &&
                Bullet.class.isInstance(fixtureB.getBody().getUserData())) {
            Bullet bullet = (Bullet) fixtureB.getBody().getUserData();
            Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
            if (!bullet.getShooter().equals(enemy)) {
                enemy.reduceHealth(bullet.getDamage());
                bullet.setHasHit(true);
            }
        }

        //Bullet x Planet
        else if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Bullet.class.isInstance(fixtureB.getBody().getUserData())) {
            Bullet bullet = (Bullet) fixtureB.getBody().getUserData();
            bullet.setHasHit(true);
        } else if (Bullet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            Bullet bullet = (Bullet) fixtureA.getBody().getUserData();
            bullet.setHasHit(true);
        }

        // Pickup x Player
        else if (Pickup.class.isInstance(fixtureA.getBody().getUserData()) &&
                Player.class.isInstance(fixtureB.getBody().getUserData())) {
            Pickup pickup = (Pickup) fixtureA.getBody().getUserData();
            Player player = (Player) fixtureB.getBody().getUserData();
            pickup.onHit(player);

        } else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                Pickup.class.isInstance(fixtureB.getBody().getUserData())) {
            Pickup pickup = (Pickup) fixtureB.getBody().getUserData();
            Player player = (Player) fixtureA.getBody().getUserData();
            pickup.onHit(player);

        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null) return;

        //Player x Planet
        if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Player.class.isInstance(fixtureB.getBody().getUserData())) {
            Player player = (Player) fixtureB.getBody().getUserData();
            if (!player.isChained()) {
                player.leftPlanet();
            }
        } else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            Player player = (Player) fixtureA.getBody().getUserData();
            if (!player.isChained()) {
                player.leftPlanet();
            }
        }
        //Enemy x Planet
        else if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Enemy.class.isInstance(fixtureB.getBody().getUserData())) {
            Enemy enemy = (Enemy) fixtureB.getBody().getUserData();
            enemy.leftPlanet();
        } else if (Enemy.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            Enemy enemy = (Enemy) fixtureA.getBody().getUserData();
            enemy.leftPlanet();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (HealthPickup.class.isInstance(fixtureA.getBody().getUserData())) {
            if (!Planet.class.isInstance(fixtureB.getBody().getUserData())) {
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
            }
        } else if (HealthPickup.class.isInstance(fixtureB.getBody().getUserData())) {
            if (!Planet.class.isInstance(fixtureA.getBody().getUserData())) {
                contact.setEnabled(false);
            } else {
                contact.setEnabled(true);
            }

        }


    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}