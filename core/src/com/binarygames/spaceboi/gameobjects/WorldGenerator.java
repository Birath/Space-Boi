package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.entities.LaunchPad;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.enemies.EnemyType;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Shooter;

import java.util.Random;

public class WorldGenerator {
    private static final int OFFSET = 10;
    private GameWorld gameWorld;

    private final int maxRad = 300;
    private final int minRad = 250;

    private final int maxGrav = (int) (3 * Math.pow(10, 9));
    private final int minGrav = (int) (2 * Math.pow(10, 9));

    private int NumberOfCircles = 6;
    private int distanceBetweenRows = 3;
    private double distanceBetweenPlanets = 0.7; //Smaller gives larger dist

    private int lastX = 0;
    private int lastY = 0;
    private int lastRad = 0;

    private int maxEnemies = 5;
    private int minEnemies = 1;

    private float radOfWorld;


    public WorldGenerator(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void createWorld() {
        Random random = new Random();
        createPlanet(0, 0, random);
        float angleOffset = 0;
        int angleBetweenMultiRowPlanets = 0;
        int lastNumberOfPlanets = 1;
        boolean isMultiPlanetRow = false;

        for (int circleNumber = 0; circleNumber < NumberOfCircles; circleNumber++) {
            int rad = distanceBetweenRows * (circleNumber + 1) * maxRad; //Distance to circle of planets
            double circumference = 2 * rad * Math.PI;
            isMultiPlanetRow = (!((circleNumber%2)==0));

            int NumberOfPlanets;
            if(!isMultiPlanetRow){ //Number is even - every other row starting from first one
                NumberOfPlanets = random.nextInt(3);
                NumberOfPlanets = NumberOfPlanets + 1; //Prevents division by zero -- 1<=NoP<=3
            }
            else{
                NumberOfPlanets = (int) Math.floor(distanceBetweenPlanets * (circumference/(2*maxRad))); //Circumference / diameter of largest planet and some extra space
                lastNumberOfPlanets = NumberOfPlanets;
            }
            double angleBetweenPlanets = (2 * Math.PI) / NumberOfPlanets;

            if(!((circleNumber%2)==0)){ //If singleplanetrow -> offset
                angleBetweenMultiRowPlanets = (360)/lastNumberOfPlanets;
                System.out.println("anglebetweenmultirowplanets " + angleBetweenMultiRowPlanets);


                int planetOffset = random.nextInt(5);
                planetOffset = 2 + planetOffset; //  2<=planetOffset<=7
                angleOffset = planetOffset * angleBetweenMultiRowPlanets;
                System.out.println("angleoffset " + angleOffset);

                angleOffset = (float) (2 * Math.PI) * (angleOffset/360); //rads
                System.out.println("angleoffset " + angleOffset);
            }

            for(int j = 0; j < NumberOfPlanets; j++){
                if(isMultiPlanetRow) {
                    lastX = (int) Math.round(rad * Math.cos((angleBetweenPlanets * j) + angleOffset));
                    lastY = (int) Math.round(rad * Math.sin((angleBetweenPlanets * j) + angleOffset));
                }
                else{
                    lastX = (int) Math.round(rad * Math.cos((angleBetweenMultiRowPlanets * j) + angleOffset));
                    lastY = (int) Math.round(rad * Math.sin((angleBetweenMultiRowPlanets * j) + angleOffset));
                }



                boolean isLastPlanet = ((j == NumberOfPlanets-1) && (circleNumber == NumberOfCircles-1));
                createPlanet(lastX, lastY, random);
                createEnemies(lastX, lastY, random, lastRad, circleNumber, isLastPlanet);

                if(isMultiPlanetRow) { //If multiplanetrow -> spawn ordinary launchpads
                    int prevX = (int) Math.round(rad * Math.cos((angleBetweenPlanets * (j - 1)) + angleOffset));
                    int prevY = (int) Math.round(rad * Math.sin((angleBetweenPlanets * (j - 1)) + angleOffset));
                    float angleToPrevPlanet = MathUtils.atan2(prevY - lastY, prevX - lastX);
                    createLaunchPad(lastX, lastY, lastRad, angleToPrevPlanet);

                    int nextX = (int) Math.round(rad * Math.cos((angleBetweenPlanets * (j + 1)) + angleOffset));
                    int nextY = (int) Math.round(rad * Math.sin((angleBetweenPlanets * (j + 1)) + angleOffset));
                    float angleToNextPlanet = MathUtils.atan2(nextY - lastY, nextX - lastX);
                    createLaunchPad(lastX, lastY, lastRad, angleToNextPlanet);
                    if(j==0){
                        createLaunchPad2(lastX, lastY, lastRad, 0); //maybe math.pi instead of 0
                    }
                }
                else{
                    createLaunchPad2(lastX, lastY, lastRad, 0);
                    createLaunchPad2(lastX, lastY, lastRad, (float) Math.PI);
                }
            }
        }
    }

    private void createPlanet(int x, int y, Random random) {
        int rad = random.nextInt(maxRad - minRad);
        rad = rad + minRad;
        int grav = random.nextInt(maxGrav - minGrav);
        grav = grav + minGrav;

        Planet planet = new Planet(gameWorld, x, y, Assets.PLANET_MOON, (float) grav, rad); //TODO Add different sprites depending on gravity and size of planet
        gameWorld.addStaticEntity(planet);

        this.lastRad = rad; //rad from planet
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
        /*
        else if((circleNumber%2)==0){
            //Spawn spaceship
            x = (int) (x + rad * 1.3 + EnemyType.FLYING_SHIP.getRad());
            y = y + (int) EnemyType.FLYING_SHIP.getRad();
            FlyingShip flyingship = new FlyingShip(gameWorld, x, y, Assets.PLANET_MOON);
            gameWorld.addDynamicEntity(flyingship);
        }
        else {
            //If we are on one of the big circles of planets
            int planetType = random.nextInt(6); //from 0 to bound-1
            if (planetType < 3) {
                //Spawn Chasers
                int numberOfEnemies = random.nextInt(maxEnemies - minEnemies);
                numberOfEnemies = numberOfEnemies + minEnemies;

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
            } else if (planetType >= 5) {
                //Spawn healtpacks
                int numberOfHealthPacks = random.nextInt(4);
                numberOfHealthPacks = numberOfHealthPacks + 4;

                int r = rad + (int) EnemyType.CHASER.getRad(); //Poolära koordinater - should not be chaser.rad on healthpack
                double angleDiff = 2 * Math.PI / numberOfHealthPacks;

                for (int healthPacks = 0; healthPacks < numberOfHealthPacks; healthPacks++) {
                    HealthPickup pickup = new HealthPickup(gameWorld, (int) (x + Math.round(r * Math.cos(angleDiff * healthPacks))),
                            (int) (y + Math.round(r * Math.sin(angleDiff * healthPacks))),
                            Assets.UI_HEALTH_ICON, 300, 5);
                    gameWorld.addDynamicEntity(pickup);
                }
            }
        }
        */
    }

    private void createLaunchPad(int x, int y, int planetRadius, float angle) {
        int padX = x + (int) (planetRadius * Math.cos(angle));
        int padY = y + (int) (planetRadius * Math.sin(angle));
        gameWorld.addStaticEntity(new LaunchPad(gameWorld, padX, padY, Assets.LAUNCH_PAD, 0, 20, 4, angle));
    }
    private void createLaunchPad2(int x, int y, int planetRadius, float angle) {
        Vector2 toPlanetFromOrigo = new Vector2(x,y);
        toPlanetFromOrigo.setLength2(1).scl(planetRadius);
        int padX = x + (int) Math.cos(toPlanetFromOrigo.rotate(angle).x);
        int padY = y + (int) Math.sin(toPlanetFromOrigo.rotate(angle).y);
        gameWorld.addStaticEntity(new LaunchPad(gameWorld, padX, padY, Assets.LAUNCH_PAD, 0, 20, 4, angle));
    }

    public int generatePlayerX() {
        return lastX + lastRad + OFFSET;
    } //TODO Might not work as inteded

    public int generatePlayerY() {
        return lastY + OFFSET;
    }
}
