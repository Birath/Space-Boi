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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public class WorldGenerator {
    private static final int OFFSET = 10;
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
    private int lastRad = 0;
    private Planet lastPlanet;

    private static final int MAX_ENEMIES = 5;
    private static final int MIN_ENEMIES = 1;

    private float radOfWorld;

    private static final int LAUNCHPAD_RANGE = (MAX_RAD /PPM) * 6;

    private static final float LAUNCH_PAD_OFFSET = 0.2f;

    private List<Planet> singleRowPlanets = new ArrayList<>();


    public WorldGenerator(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void createWorld() {
        //Create middle planet
        Random random = new Random();
        createPlanet(0, 0, random);


        int angleOffset = 0;
        boolean isMultiPlanetRow;

        for (int circleNumber = 0; circleNumber < NUMBER_OF_CIRCLES; circleNumber++) {
            float rad = DISTANCE_BETWEEN_ROWS * (circleNumber + 1) * MAX_RAD; //Distance to circle of planets
            float circumference = 2 * rad * MathUtils.PI;
            isMultiPlanetRow = circleNumber % 2 != 0; //Number is even - every other row starting from first one

            int numberOfPlanetsInRow;
            // Boss row
            if(!isMultiPlanetRow){
                numberOfPlanetsInRow = circleNumber + 1;
            }
            // Normal rows
            else{
                numberOfPlanetsInRow = (int) Math.floor(DISTANCE_BETWEEN_PLANETS * (circumference/(2* MAX_RAD))); //Circumference / diameter of largest planet and some extra space
            }
            double angleBetweenPlanets = (2 * Math.PI) / numberOfPlanetsInRow;

            if(isMultiPlanetRow){ //If singleplanetrow -> offset
                angleOffset = random.nextInt(30);
                angleOffset = -15 + angleOffset; //  -15<=planetOffset<=15
            }

            for(int j = 0; j < numberOfPlanetsInRow; j++){
                lastX = (int) Math.round(rad * Math.cos((angleBetweenPlanets * j) + angleOffset));
                lastY = (int) Math.round(rad * Math.sin((angleBetweenPlanets * j) + angleOffset));

                boolean isLastPlanet = ((j == numberOfPlanetsInRow-1) && (circleNumber == NUMBER_OF_CIRCLES -1));
                createPlanet(lastX, lastY, random);
                createEnemies(lastX, lastY, random, lastRad, circleNumber, isLastPlanet);

                if(isMultiPlanetRow) { //If multiplanetrow -> spawn ordinary launchpads
                    //Create launchpad to next
                    int prevX = (int) Math.round(rad * Math.cos((angleBetweenPlanets * (j - 1)) + angleOffset));
                    int prevY = (int) Math.round(rad * Math.sin((angleBetweenPlanets * (j - 1)) + angleOffset));
                    float angleToPrevPlanet = MathUtils.atan2(prevY - lastY, prevX - lastX);
                    createLaunchPad(lastX, lastY, lastRad, angleToPrevPlanet + LAUNCH_PAD_OFFSET);

                    //Create launchpad to prev
                    int nextX = (int) Math.round(rad * Math.cos((angleBetweenPlanets * (j + 1)) + angleOffset));
                    int nextY = (int) Math.round(rad * Math.sin((angleBetweenPlanets * (j + 1)) + angleOffset));
                    float angleToNextPlanet = MathUtils.atan2(nextY - lastY, nextX - lastX);
                    createLaunchPad(lastX, lastY, lastRad, angleToNextPlanet);
                }
                else{
                    singleRowPlanets.add(lastPlanet);
                }
            }
        }
        createLaunchPadBetweenRows();
    }

    private void createPlanet(int x, int y, Random random) {
        int rad = random.nextInt(MAX_RAD - MIN_RAD);
        rad += MIN_RAD;
        int grav = random.nextInt(MAX_GRAV - MIN_GRAV);
        grav += MIN_GRAV;

        Planet planet = new Planet(gameWorld, x, y, Assets.PLANET_MOON, (float) grav, rad); //TODO Add different sprites depending on gravity and size of planet
        gameWorld.addStaticEntity(planet);

        this.lastRad = rad; //rad from planet
        this.lastPlanet = planet;
    }

    private void createEnemies(int x, int y, Random random, int rad, int circleNumber, boolean isLastPlanet) {
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
        }
        else if((circleNumber%2)==0){
            //Spawn spaceship
            x = (int) (x + rad * 1.3 + EnemyType.FLYING_SHIP.getRad());
            y += (int) EnemyType.FLYING_SHIP.getRad();
            FlyingShip flyingship = new FlyingShip(gameWorld, x, y, Assets.PLANET_MOON);
            gameWorld.addDynamicEntity(flyingship);
        }
        else {
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
            }else if (planetType < 5) {
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
    private void createLaunchPadBetweenRows(){
        for(Planet singleRowPlanet : singleRowPlanets){
            Vector2 pos = singleRowPlanet.getBody().getPosition();

            ArrayList<Planet> planetList = getPlanetsWithinRange(pos);
            for(Planet planet : planetList){
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
        return lastX + lastRad + OFFSET;
    }

    public int generatePlayerY() {
        return lastY + OFFSET;
    }
    private ArrayList<Planet> getPlanetsWithinRange(Vector2 pos) {
        ArrayList<Planet> planetsWithinRange = new ArrayList<>();
        for (Planet planet : gameWorld.getPlanets()) {
            Vector2 planetPos = planet.getBody().getPosition();
            float distance = planetPos.dst(pos);
            Gdx.app.log("WorldGenerator", "Range" + LAUNCHPAD_RANGE);
            Gdx.app.log("WorldGenerator", "distance: " + distance);

            if (LAUNCHPAD_RANGE >= distance && distance > 2) { //seem to need error margin for the second part
                planetsWithinRange.add(planet); }
            }
        return planetsWithinRange;
    }

    public float getRadOfWorld() {
        return radOfWorld;
    }
}
