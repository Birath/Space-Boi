package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.*;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld {

    private SpaceBoi game;
    private World world;
    private Player player;

    private List<EntityDynamic> dynamicEntities;
    private List<EntityStatic> staticEntities;

    private List<EntityDynamic> addDynamicEntities = new ArrayList<>();

    private static final double GRAVITY_CONSTANT = 6.674 * Math.pow(10, -11);

    public GameWorld(SpaceBoi game, World world) {
        this.game = game;
        this.world = world;

        dynamicEntities = new ArrayList<>();
        staticEntities = new ArrayList<>();


    }

    public void createWorld() {
        Machinegun weapon = new Machinegun(world, this);
        Player player = new Player(world, 0, 0, "playerShip.png", 500, 10, this, weapon);
        addDynamicEntity(player);
        this.player = player;

        Enemy enemy = new Enemy(world, 250, 30, "moon.png", 500, 10, this);
        addDynamicEntity(enemy);

        Planet planet1 = new Planet(world, 10, 30, "moon.png",(float) Math.pow(3 * 10, 7), 100);
        addStaticEntity(planet1);
        Planet planet2 = new Planet(world, 230, 30, "moon.png", (float) Math.pow(3 * 10, 7), 75);
        addStaticEntity(planet2);

        world.setContactListener(new EntityContactListener());

    }

    public void update(float delta) {
        for (EntityDynamic entity : dynamicEntities) {
            applyGravity(entity);
            entity.updateMovement();
        }
        world.step(delta, 6, 2);
        dynamicEntities.addAll(addDynamicEntities);
        removeBullets(dynamicEntities);
        addDynamicEntities.clear();
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        for (EntityStatic entity : staticEntities) {
            entity.render(batch, camera);
        }
        for (EntityDynamic entity : dynamicEntities) {
            entity.render(batch, camera);
        }
    }

    private void applyGravity(EntityDynamic entity) {
        Vector2 entityPos = entity.getBody().getPosition();
        ArrayList<Planet> planetsWithinRange = getPlanetsWithinGravityRange(entityPos);
        Vector2 finalGravity = new Vector2();
        for (Planet planet : planetsWithinRange) {
            float angle = MathUtils.atan2(planet.getBody().getPosition().y - entityPos.y, planet.getBody().getPosition().x - entityPos.x);
            Vector2 gravityPull = new Vector2(
                    (float) (MathUtils.cos(angle) * GRAVITY_CONSTANT * planet.getMass() * entity.getMass() / planet.getRad()),
                    (float) (MathUtils.sin(angle) * GRAVITY_CONSTANT * planet.getMass() * entity.getMass() / planet.getRad())
            );
            finalGravity.add(gravityPull);
        }
        entity.getBody().applyForceToCenter(finalGravity, true);
        /*
        Vector2 closestPlanetPos = closestPlanet.getBody().getPosition();
        float distance = closestPlanetPos.dst(entityPos);
        if (closestPlanet.getRad() * Planet.GRAVITY_RADIUS >= distance) {

            // Set constant gravity while inside a set radius

            // TODO Maybe change to not depend on planet radius. Not sure what else to use tho - Maybe remove radius and then change Gravity Constant? //Albin
            double force = GRAVITY_CONSTANT * closestPlanet.getMass() * entity.getMass() / closestPlanet.getRad();
            float forceX = MathUtils.cos(angle) * (float) force;
            float forceY = MathUtils.sin(angle) * (float) force;
            entity.getBody().applyForceToCenter(forceX, forceY, true);
        */
        // TODO move to nice place
        if (entity instanceof Player) {
            // TODO Change to toPlanet vector instead
            Vector2 playerForce = new Vector2(finalGravity);
            ((Player) entity).setPlayerAngle(playerForce.angle());
        }
    }


    private void removeBullets(List<EntityDynamic> toRemoveList) {
        Iterator<EntityDynamic> itr = toRemoveList.iterator();

        while (itr.hasNext()) {
            EntityDynamic entity = itr.next();
            if (entity instanceof Bullet) {
                if (((Bullet) entity).toRemove(player.getBody().getPosition().x, player.getBody().getPosition().y)) {
                    world.destroyBody(entity.getBody());
                    itr.remove();
                }
            }
        }
    }

    private ArrayList<Planet> getPlanetsWithinGravityRange(Vector2 bodyPos) {
        ArrayList<Planet> planetsWithinRange = new ArrayList<>();
        for (EntityStatic entityStatic : staticEntities) { //Seems like we need to have a planetList since we want to apply gravity using every object in staticEntities
            if (Planet.class.isInstance(entityStatic)) {
                Planet planet = (Planet) entityStatic;
                Vector2 planetPos = planet.getBody().getPosition();
                float distance = planetPos.dst(bodyPos);
                if (planet.getRad() * Planet.GRAVITY_RADIUS >= distance) {
                    planetsWithinRange.add(planet);
                }
            }
        }
        return planetsWithinRange;
    }

    public void addDynamicEntity(EntityDynamic entity) {
        addDynamicEntities.add(entity);
    }

    public void addStaticEntity(EntityStatic entity) {
        staticEntities.add(entity);
    }

    public Player getPlayer() {
        return player;
    }

}
