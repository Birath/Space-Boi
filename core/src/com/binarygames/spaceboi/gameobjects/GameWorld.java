package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.*;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

import javax.swing.text.html.parser.Entity;
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

        Planet planet1 = new Planet(world, 10, 30, "moon.png",(float) Math.pow(3 * 10, 7), 100);
        addStaticEntity(planet1);
        Planet planet2 = new Planet(world, 230, 30, "moon.png", (float) Math.pow(3 * 10, 7), 75);
        addStaticEntity(planet2);

        world.setContactListener(new GameConctatListener());

    }

    public void update(float delta) {
        applyGravity(delta, dynamicEntities);
        for (EntityDynamic entity : dynamicEntities) {
            entity.updateMovement();
        }
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

    public void applyGravity(float delta, List<EntityDynamic> dynamicObjectList) {
        for (EntityDynamic entity : dynamicObjectList) {

            Vector2 entityPos = entity.getBody().getPosition();
            Planet closestPlanet = getClosestPlanet(entityPos);

            Vector2 closestPlanetPos = closestPlanet.getBody().getPosition();
            float distance = closestPlanetPos.dst(entityPos);
            if (closestPlanet.getRad() * 3 >= distance) {
                float angle = MathUtils.atan2(closestPlanetPos.y - entityPos.y, closestPlanetPos.x - entityPos.x);
                // Set constant gravity while inside a set radius

                // TODO Maybe change to not depend on planet radius. Not sure what else to use tho - Maybe remove radius and then change Gravity Constant? //Albin
                double force = GRAVITY_CONSTANT * closestPlanet.getMass() * entity.getMass() / closestPlanet.getRad();
                float forceX = MathUtils.cos(angle) * (float) force;
                float forceY = MathUtils.sin(angle) * (float) force;
                entity.getBody().applyForceToCenter(forceX, forceY, true);

                // TODO move to nice place
                if (entity instanceof Player) {
                    Vector2 playerForce = new Vector2(forceX, forceY);
                    ((Player) entity).setPlayerAngle(playerForce.angle());
                }
            }
        }
        world.step(delta, 6, 2);
    }

    private void removeBullets(List<EntityDynamic> toRemoveList){
        Iterator<EntityDynamic> itr = toRemoveList.iterator();

        while(itr.hasNext()){
            EntityDynamic entity = itr.next();
            if (entity instanceof Bullet){
                if(((Bullet) entity).toRemove(player.getBody().getPosition().x, player.getBody().getPosition().y)){
                    world.destroyBody(entity.getBody());
                    itr.remove();
                }
            }
        }
    }

    private Planet getClosestPlanet(Vector2 bodyPos) {
        float closestDistance = 0.0f;
        Planet closestPlanet = null;
        for (EntityStatic entityStatic : staticEntities) { //Seems like we need to have a planetList since we want to apply gravity using every object in staticEntities
            if (Planet.class.isInstance(entityStatic)) {
                Planet planet = (Planet) entityStatic;
                Vector2 planetPos = planet.getBody().getPosition();
                float distance = planetPos.dst(bodyPos) - planet.getRad();
                if (closestDistance == 0.0f) {
                    closestDistance = distance;
                    closestPlanet = planet;
                } else if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPlanet = planet;
                }
            }
        }
        return closestPlanet;
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
