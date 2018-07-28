package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.effects.ParticleHandler;
import com.binarygames.spaceboi.gameobjects.entities.*;
import com.binarygames.spaceboi.background_functions.XP_handler;
import com.binarygames.spaceboi.background_functions.XP_handler;
import com.binarygames.spaceboi.gameobjects.entities.enemies.EnemyType;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Spawner;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.Experience;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.GlassCannon;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.Recoil;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.Silencer;
import com.binarygames.spaceboi.gameobjects.utils.JointInfo;
import com.binarygames.spaceboi.screens.DeathScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld {

    private SpaceBoi game;
    private OrthographicCamera camera;
    private World world;
    private WorldGenerator worldGenerator;

    private ParticleHandler particleHandler;
    private XP_handler xp_handler;
    private Player player;
    private boolean shouldLerpPlayerAngle = false;

    private List<EntityDynamic> dynamicEntities;
    private List<EntityStatic> staticEntities;
    private List<Weapon> weaponList;

    private List<EntityDynamic> addDynamicEntities = new ArrayList<>();

    private Array<JointInfo> jointsToCreate = new Array<>();
    private Array<Joint> jointsToDestroy = new Array<>();

    private float targetAngle = 0;
    private int lerpSpeed = 5;
    private int currentLerpStep;
    private float rotationAmmount;
    private boolean isLerping;
    private float fromDegrees;
    private float toDegrees;
    private float timeSinceStart;
    private static final float LERP_TIME = 0.1f;
    private static final double GRAVITY_CONSTANT = 6.674 * Math.pow(10, -10);

    public GameWorld(SpaceBoi game, World world, OrthographicCamera camera) {
        this.game = game;
        this.world = world;
        this.camera = camera;

        particleHandler = new ParticleHandler(game);

        dynamicEntities = new ArrayList<>();
        staticEntities = new ArrayList<>();
        weaponList = new ArrayList<>();
    }

    public void createWorld() {
        worldGenerator = new WorldGenerator(this);
        worldGenerator.createWorld();
        Player player = new Player(this, worldGenerator.generatePlayerX(),
                worldGenerator.generatePlayerY(), Assets.PLAYER, 500, 10);

        // TODO remove temp attachment testing
        addDynamicEntity(new Experience(this, worldGenerator.generatePlayerX() + 50,
                worldGenerator.generatePlayerY(), Assets.PLANET_MOON, 500, 5));
        addDynamicEntity(new GlassCannon(this, worldGenerator.generatePlayerX() + 100,
                worldGenerator.generatePlayerY(), Assets.PLANET_MOON, 500, 5));
        addDynamicEntity(new Recoil(this, worldGenerator.generatePlayerX() + 150,
                worldGenerator.generatePlayerY(), Assets.PLANET_MOON, 500, 5));
        addDynamicEntity(new Silencer(this, worldGenerator.generatePlayerX() + 200,
                worldGenerator.generatePlayerY(), Assets.PLANET_MOON, 500, 5));

        addDynamicEntity(new Spawner(this, worldGenerator.generatePlayerX() + 100, worldGenerator.generatePlayerY(), Assets.PLANET_MOON, EnemyType.SPAWNER));

        addDynamicEntity(player);
        this.player = player;
        xp_handler = new XP_handler(player);

        world.setContactListener(new EntityContactListener(this));
    }

    public void update(float delta) {
        for (EntityDynamic entity : dynamicEntities) {
            applyGravity(entity);
            entity.update(delta);
        }
        for (Weapon weapon : weaponList) {
            if (weapon.getShooter().getHealth() > 0) {
                weapon.update(delta);
            }
        }
        rotatePlayer(delta);
        world.step(delta, 6, 2);

        removeDead(dynamicEntities);
        dynamicEntities.addAll(addDynamicEntities);
        addDynamicEntities.clear();

        createJoints();
        removeJoints();
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
        Planet closestPlanet = getClosestPlanet(planetsWithinRange, entityPos);
        if (entity.getEntityState() == ENTITY_STATE.STANDING) {
            planetsWithinRange.clear();
            planetsWithinRange.add(closestPlanet);
        }
        Vector2 finalGravity = new Vector2();
        for (Planet planet : planetsWithinRange) {
            float angle = MathUtils
                    .atan2(planet.getBody().getPosition().y - entityPos.y, planet.getBody().getPosition().x - entityPos.x);
            Vector2 gravityPull = new Vector2(
                    (float) (MathUtils.cos(angle) * GRAVITY_CONSTANT * planet.getMass() * entity.getMass() / planet.getRad()),
                    (float) (MathUtils.sin(angle) * GRAVITY_CONSTANT * planet.getMass() * entity.getMass() / planet.getRad()));
            finalGravity.add(gravityPull);
        }
        if (game.getPreferences().isGravityEnabled()) {
            if (entity.isAffectedByGravity()) {
                entity.getBody().applyForceToCenter(finalGravity, true);
            }

        }
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
        if (closestPlanet != null) {
            // TODO Change to toPlanet vector instead
            entity.setClosestPlanet(closestPlanet);
        }
    }


    private void removeDead(List<EntityDynamic> entityList) {
        Iterator<EntityDynamic> itr = entityList.iterator();
        while (itr.hasNext()) {
            EntityDynamic entity = itr.next();
            if (entity.shouldRemove(player.getBody().getPosition())) {
                entity.onRemove();
                if (!(entity instanceof Player)) {
                    world.destroyBody(entity.getBody());
                    itr.remove();
                }
            }
        }
    }

    private ArrayList<Planet> getPlanetsWithinGravityRange(Vector2 bodyPos) {
        ArrayList<Planet> planetsWithinRange = new ArrayList<>();
        for (EntityStatic entityStatic : staticEntities) {
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

    private Planet getClosestPlanet(ArrayList<Planet> planets, Vector2 entityPos) {
        float closestDistance = -1.0f;
        Planet closestPlanet = null;
        for (Planet planet : planets) {
            if (closestDistance < 0) {
                closestPlanet = planet;
                closestDistance = planet.getBody().getPosition().dst(entityPos);
            } else if (planet.getBody().getPosition().dst(entityPos) < closestDistance) {
                closestPlanet = planet;
                closestDistance = planet.getBody().getPosition().dst(entityPos);
            }
        }
        return closestPlanet;
    }

    private void createJoints() {
        ArrayIterator<JointInfo> jointInfoIterator = new ArrayIterator<>(jointsToCreate, true);
        while (jointInfoIterator.hasNext()) {
            JointInfo jointInfo = jointInfoIterator.next();
            DistanceJointDef wd = new DistanceJointDef();
            wd.bodyA = jointInfo.bodyA;
            wd.bodyB = jointInfo.bodyB;
            wd.length = wd.bodyA.getPosition().dst(wd.bodyB.getPosition());
            wd.collideConnected = false;
            world.createJoint(wd);
            jointInfoIterator.remove();
        }
    }

    private void removeJoints() {
        for (Joint joint : jointsToDestroy) {
            world.destroyJoint(joint);
            joint = null;
        }
        jointsToDestroy.clear();
    }


    public void respawnPlayer() {
        game.setScreen(new DeathScreen(game, game.getScreen()));
    }

    private void rotatePlayer(float delta) {
        Planet closestPlanet = player.getClosestPlanet();
        if (shouldLerpPlayerAngle) {
            if (currentLerpStep == lerpSpeed || player.isChained()) {
                shouldLerpPlayerAngle = false;
                currentLerpStep = 0;
                return;
            }
            float currentAngle = player.getPlayerAngle() - 90;
            player.setPlayerAngle(currentAngle + (rotationAmmount / lerpSpeed));
            currentLerpStep++;
            return;
        }

        if (closestPlanet == null) {
            return;
        }
        Vector2 relativeVector = closestPlanet.getBody().getPosition().sub(player.getBody().getPosition());
        float angleToPlanet = MathUtils.atan2(relativeVector.y, relativeVector.x) * MathUtils.radiansToDegrees;
        angleToPlanet = (angleToPlanet + 360) % 360;
        // float oldAngle = MathUtils.atan2(relativeVector.y, relativeVector.x) * MathUtils.radiansToDegrees;
        /*
        if (player.getPlayerAngle() -90 - angleToPlanet > 50) {
            Gdx.app.log("GameWorld", "Angle diff: " + (player.getPlayerAngle() - 90 - angleToPlanet));
            shouldLerpPlayerAngle = true;
            targetAngle = angleToPlanet;
            rotationAmmount = player.getPlayerAngle() - 90 - angleToPlanet;
            if (player.getPlayerAngle() - 90 > angleToPlanet) {
                rotationAmmount *= -1;
            }
            currentLerpStep = 0;
            return;
        }*/
        //Gdx.app.log("GameWorld", "Angle to planet: " + angleToPlanet);
        //Gdx.app.log("GameWorld", "Previous Angle:  " + player.getPlayerAngle());
        float lerpedAngle = 0;
        float otherInterpolation = 0;
        // http://www.blueraja.com/blog/404/how-to-use-unity-3ds-linear-interpolation-vector3-lerp-correctly
        if (isLerping) {
            timeSinceStart += delta * 5;
            float progress = timeSinceStart / LERP_TIME;
            //lerpedAngle = MathUtils.lerpAngleDeg(fromDegrees, toDegrees, progress);
            //otherInterpolation = Interpolation.linear.apply(fromDegrees, toDegrees, progress);
            lerpedAngle = MathUtils.lerpAngleDeg(fromDegrees, toDegrees, timeSinceStart);
            otherInterpolation = Interpolation.linear.apply(fromDegrees, toDegrees, timeSinceStart);
            //Gdx.app.log("GameWorld", "FromDegrees: " + fromDegrees + " ToDegrees: " + toDegrees + " Progress: " + timeSinceStart);
            if (timeSinceStart >= 1.0f) {
                isLerping = false;
            }
        } else {
            fromDegrees = player.getPlayerAngle();
            toDegrees = angleToPlanet;
            timeSinceStart = 0;
            //isLerping = true;
            lerpedAngle = MathUtils.lerpAngleDeg(fromDegrees, toDegrees, 0);
            otherInterpolation = Interpolation.linear.apply(fromDegrees, toDegrees, 0);
        }
        if (Math.abs(player.getPlayerAngle() - angleToPlanet) > 20) {
            lerpSpeed = 5;
        } else {
            lerpSpeed = 25;
        }
        lerpedAngle = MathUtils.lerpAngleDeg(player.getPlayerAngle(), angleToPlanet, delta * lerpSpeed);
        //Gdx.app.log("GameWorld", "Lerped angle:        " + lerpedAngle);
        //Gdx.app.log("GameWorld", "Linear interpolation: " + otherInterpolation);
        //Gdx.app.log("GameWorld", "Target Angle:        " + angleToPlanet);
        //player.setPlayerAngle(lerpedAngle);
        //player.setPlayerAngle(otherInerpolation);
        player.setPlayerAngle(lerpedAngle);
    }

    public ArrayList<Planet> getPlanets() {
        ArrayList<Planet> planets = new ArrayList<>();
        for (EntityStatic entityStatic : staticEntities) {
            if (Planet.class.isInstance(entityStatic)) {
                Planet planet = (Planet) entityStatic;
                planets.add(planet);
            }
        }
        return planets;
    }

    public void addWeapon(Weapon weapon) {
        weaponList.add(weapon);
    }

    public void addDynamicEntity(EntityDynamic entity) {
        addDynamicEntities.add(entity);
    }

    public void addStaticEntity(EntityStatic entity) {
        staticEntities.add(entity);
    }

    public void addJoints(JointInfo jointInfo) {
        jointsToCreate.add(jointInfo);
    }

    public void addJointToRemove(Joint joint) {
        jointsToDestroy.add(joint);
    }

    public Player getPlayer() {
        return player;
    }

    public XP_handler getXp_handler() {
        return xp_handler;
    }

    public SpaceBoi getGame() {
        return game;
    }

    public World getWorld() {
        return world;
    }

    public ParticleHandler getParticleHandler() {
        return particleHandler;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    private void dispose() {
        world.dispose();
        game.getScreen().dispose();
    }

}
