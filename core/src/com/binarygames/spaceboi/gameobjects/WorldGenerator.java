package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.entities.LaunchPad;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Chaser;
import com.binarygames.spaceboi.gameobjects.entities.enemies.EnemyType;
import com.binarygames.spaceboi.gameobjects.entities.enemies.FlyingShip;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Shooter;
import com.binarygames.spaceboi.gameobjects.pickups.HealthPickup;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public class WorldGenerator {
    private static final int OFFSET = 10;
    public static final int ATTACHMENT_MASS = 300;
    private GameWorld gameWorld;

    private static final int MAX_RAD = 450;
    private static final int MIN_RAD = 375;


    private static final int MAX_GRAV = (int) (3 * Math.pow(10, 9));
    private static final int MIN_GRAV = (int) (2 * Math.pow(10, 9));

    private static final int NUMBER_OF_CIRCLES = 6;
    private static final float DISTANCE_BETWEEN_ROWS = 4.0f;
    private static final float DISTANCE_BETWEEN_PLANETS = 0.5f; //Smaller gives larger dist

    private int lastX = 0;
    private int lastY = 0;
    private Planet lastPlanet = null;

    private static final int MAX_ENEMIES = 5;
    private static final int MIN_ENEMIES = 1;

    private float radOfWorld;

    private static final int LAUNCHPAD_RANGE = (MAX_RAD / PPM) * 5;

    private static final float LAUNCH_PAD_OFFSET = 0.2f;

    private List<Planet> singleRowPlanets = new ArrayList<>();
    private final Random random = new Random();


    public WorldGenerator(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void createWorld() {
        //Create middle planet
        createPlanet(0, 0);

        int angleOffset = 0;

        for (int circleNumber = 0; circleNumber < NUMBER_OF_CIRCLES; circleNumber++) {
            float rowRadius = DISTANCE_BETWEEN_ROWS * (circleNumber + 1) * MAX_RAD; //Distance to circle of planets

            //Number is even - every other row starting from first one
            boolean isMultiPlanetRow = circleNumber % 2 != 0;

            int numberOfPlanetsInRow;
            // Boss row
            if (!isMultiPlanetRow) {
                numberOfPlanetsInRow = circleNumber + 1;
            }
            // Normal rows
            else {
                float circumference = 2 * rowRadius * MathUtils.PI;
                numberOfPlanetsInRow = (int) Math.floor(DISTANCE_BETWEEN_PLANETS * (circumference / (2 * MAX_RAD))); //Circumference / diameter of largest planet and some extra space
            }

            float angleBetweenPlanets = (2 * MathUtils.PI) / numberOfPlanetsInRow;

            if (isMultiPlanetRow) { //If singleplanetrow -> offset
                angleOffset = random.nextInt(30);
                angleOffset = -15 + angleOffset; //  -15<=planetOffset<=15
            }
            int attachmentPlanetNumber = random.nextInt(numberOfPlanetsInRow);
            for (int planetNumber = 0; planetNumber < numberOfPlanetsInRow; planetNumber++) {

                boolean shouldSpawnAttachment = planetNumber == attachmentPlanetNumber;
                boolean isLastPlanet = (planetNumber == numberOfPlanetsInRow - 1) && (circleNumber == NUMBER_OF_CIRCLES - 1);

                spawnPlanetAndEntities(angleOffset, isMultiPlanetRow, circleNumber, rowRadius, isLastPlanet, angleBetweenPlanets, planetNumber, shouldSpawnAttachment);
            }
        }
        createLaunchPadBetweenRows();
    }

    private void spawnPlanetAndEntities(int angleOffset, boolean isMultiPlanetRow, int circleNumber, float rowRadius, boolean isLastPlanet, float angleBetweenPlanets, int planetNumber, boolean shouldSpawnAttachment) {
        int currentX = (int) Math.round(rowRadius * Math.cos((angleBetweenPlanets * planetNumber) + angleOffset));
        int currentY = (int) Math.round(rowRadius * Math.sin((angleBetweenPlanets * planetNumber) + angleOffset));

        Planet planet = createPlanet(currentX, currentY);
        int planetRadius = (int) planet.getRadius();
        createEnemies(currentX, currentY, planetRadius, circleNumber, isLastPlanet);

        if (isMultiPlanetRow) { //If multiplanetrow -> spawn ordinary launchpads

            try {
                spawnAttachment(new Vector2(currentX, currentY), planetRadius);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                Gdx.app.error("WorldGenerator", "Failed to spawnAttachment", e);
            }

            //Create launchpad to next
            int prevX = (int) Math.round(rowRadius * Math.cos((angleBetweenPlanets * (planetNumber - 1)) + angleOffset));
            int prevY = (int) Math.round(rowRadius * Math.sin((angleBetweenPlanets * (planetNumber - 1)) + angleOffset));
            float angleToPrevPlanet = MathUtils.atan2(prevY - currentY, prevX - currentX);
            createLaunchPad(currentX, currentY, planetRadius, angleToPrevPlanet + LAUNCH_PAD_OFFSET);

            //Create launchpad to prev
            int nextX = (int) Math.round(rowRadius * Math.cos((angleBetweenPlanets * (planetNumber + 1)) + angleOffset));
            int nextY = (int) Math.round(rowRadius * Math.sin((angleBetweenPlanets * (planetNumber + 1)) + angleOffset));
            float angleToNextPlanet = MathUtils.atan2(nextY - currentY, nextX - currentX);
            createLaunchPad(currentX, currentY, planetRadius, angleToNextPlanet);
        } else {
            singleRowPlanets.add(planet);
        }
        if (isLastPlanet) {
            lastX = currentX;
            lastY = currentY;
            lastPlanet = planet;
        }
    }

    private Planet createPlanet(int x, int y) {
        int planetRadius = random.nextInt(MAX_RAD - MIN_RAD);
        planetRadius += MIN_RAD;
        int grav = random.nextInt(MAX_GRAV - MIN_GRAV);
        grav += MIN_GRAV;

        Planet planet = new Planet(gameWorld, x, y, Assets.PLANET_MOON, grav, planetRadius); //TODO Add different sprites depending on gravity and size of planet
        gameWorld.addStaticEntity(planet);

        return planet;
    }

    private void spawnAttachment(Vector2 planetPosition, float planetRadius) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Class<? extends WeaponAttachment> attachmentClass = WeaponAttachment.WEAPON_ATTACHMENTS.get(random.nextInt(WeaponAttachment.WEAPON_ATTACHMENTS.size()));
        Constructor<? extends WeaponAttachment> attachementConstructor = attachmentClass.getConstructor(GameWorld.class, float.class, float.class, String.class, float.class, float.class);// GameWorld, x, y, path, mass, radius
        // TODO Add picture based on class name
        WeaponAttachment attachment = attachementConstructor.newInstance(gameWorld, planetPosition.x + planetRadius, planetPosition.y + planetRadius, Assets.PLANET_MOON, ATTACHMENT_MASS, 7);
        gameWorld.addDynamicEntity(attachment);
    }

    private void createEnemies(int x, int y, int rad, int circleNumber, boolean isLastPlanet) {
        if (isLastPlanet) {
            //Last planet aka spawn planet
            int numberOfShooters = 1;

            int r = rad + (int) EnemyType.SHOOTER.getRad(); //Poolära koordinater
            double angleBetweenEnemies = 5;
            double angleDiff = (2 * Math.PI) * (angleBetweenEnemies / 360);
            for (int shooters = 0; shooters < numberOfShooters; shooters++) {
                Shooter shooter = new Shooter(gameWorld, (int) (x + Math.round(r * Math.cos(angleDiff * shooters + 30))),
                    (int) (y + Math.round(r * Math.sin(angleDiff * shooters + 30))), Assets.PLANET_MOON);
                gameWorld.addDynamicEntity(shooter);
            }

            //Calculating for other parts of the code
            Vector2 toPlanet = new Vector2(x, y);
            radOfWorld = toPlanet.len() + Planet.GRAVITY_RADIUS;
        } else if ((circleNumber % 2) == 0) {
            //Spawn spaceship
            x = (int) (x + rad * 1.3 + EnemyType.FLYING_SHIP.getRad());
            y += (int) EnemyType.FLYING_SHIP.getRad();
            FlyingShip flyingship = new FlyingShip(gameWorld, x, y, Assets.PLANET_MOON);
            gameWorld.addDynamicEntity(flyingship);
        } else {
            //If we are on one of the big circles of planets
            int planetType = random.nextInt(6); //from 0 to bound-1
            if (planetType < 3) {
                //Spawn Chasers
                int numberOfEnemies = random.nextInt(MAX_ENEMIES - MIN_ENEMIES);
                numberOfEnemies += MIN_ENEMIES;

                int r = rad + (int) EnemyType.CHASER.getRad(); //Poolära koordinater
                double angleBetweenEnemies = 10;
                double angleDiff = (2 * Math.PI) * (angleBetweenEnemies / 360);

                for (int enemies = 0; enemies < numberOfEnemies; enemies++) {
                    Chaser chaser = new Chaser(gameWorld, (int) (x + Math.round(r * Math.cos(angleDiff * enemies))),
                        (int) (y + Math.round(r * Math.sin(angleDiff * enemies))),
                        Assets.PLANET_MOON);
                    gameWorld.addDynamicEntity(chaser);
                }
            } else if (planetType < 5) {
                //Spawn Shooters
                int numberOfShooters = 2;

                int r = rad + (int) EnemyType.SHOOTER.getRad(); //Poolära koordinater
                double angleBetweenEnemies = 5;
                double angleDiff = (2 * Math.PI) * (angleBetweenEnemies / 360);

                for (int shooters = 0; shooters < numberOfShooters; shooters++) {
                    Shooter shooter = new Shooter(gameWorld, (int) (x + Math.round(r * Math.cos(angleDiff * shooters))),
                        (int) (y + Math.round(r * Math.sin(angleDiff * shooters))), Assets.PLANET_MOON);
                    gameWorld.addDynamicEntity(shooter);
                }
            } else if (planetType == 5) {
                //Spawn healtpacks
                int numberOfHealthPacks = random.nextInt(4);
                numberOfHealthPacks += 4;

                int r = rad + (int) EnemyType.CHASER.getRad(); //Poolära koordinater - should not be chaser.rad on healthpack
                double angleDiff = 2 * Math.PI / numberOfHealthPacks;

                for (int healthPacks = 0; healthPacks < numberOfHealthPacks; healthPacks++) {
                    HealthPickup pickup = new HealthPickup(gameWorld, (int) (x + Math.round(r * Math.cos(angleDiff * healthPacks))),
                        (int) (y + Math.round(r * Math.sin(angleDiff * healthPacks))),
                        Assets.PICKUP_HEALTH, 300, 7);
                    gameWorld.addDynamicEntity(pickup);
                }
            }
        }
    }

    private void createLaunchPadBetweenRows() {
        for (Planet singleRowPlanet : singleRowPlanets) {
            Vector2 pos = singleRowPlanet.getBody().getPosition();

            ArrayList<Planet> planetList = getPlanetsWithinRange(pos);
            for (Planet planet : planetList) {
                float angleToPlanet = MathUtils.atan2(pos.y - planet.getBody().getPosition().y, pos.x - planet.getBody().getPosition().x);
                createLaunchPad((int) (PPM * planet.getBody().getPosition().x), (int) (PPM * planet.getBody().getPosition().y), (int) planet.getRadius(), angleToPlanet);

                float reverseAngleToPlanet = MathUtils.atan2(planet.getBody().getPosition().y - pos.y, planet.getBody().getPosition().x - pos.x);
                createLaunchPad((int) (PPM * pos.x), (int) (PPM * pos.y), (int) singleRowPlanet.getRadius(), reverseAngleToPlanet + LAUNCH_PAD_OFFSET);
            }
        }
    }

    private void createLaunchPad(int x, int y, int planetRadius, float angle) {
        int padX = x + (int) (planetRadius * Math.cos(angle));
        int padY = y + (int) (planetRadius * Math.sin(angle));
        gameWorld.addStaticEntity(new LaunchPad(gameWorld, padX, padY, Assets.LAUNCH_PAD, 0, 20, 4, angle));
    }

    public int generatePlayerX() {
        return (int) (lastX + lastPlanet.getRadius() + OFFSET);
    }

    public int generatePlayerY() {
        return lastY + OFFSET;
    }

    private ArrayList<Planet> getPlanetsWithinRange(Vector2 pos) {
        ArrayList<Planet> planetsWithinRange = new ArrayList<>();
        for (Planet planet : gameWorld.getPlanets()) {
            Vector2 planetPos = planet.getBody().getPosition();
            float distance = planetPos.dst(pos);
            if (LAUNCHPAD_RANGE >= distance && distance > 2) { //seem to need error margin for the second part
                planetsWithinRange.add(planet);
            }
        }
        return planetsWithinRange;
    }

    public float getRadOfWorld() {
        return radOfWorld;
    }
}
