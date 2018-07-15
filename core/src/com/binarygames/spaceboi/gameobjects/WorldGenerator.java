package com.binarygames.spaceboi.gameobjects;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Chaser;
import com.binarygames.spaceboi.gameobjects.entities.enemies.EnemyType;
import com.binarygames.spaceboi.gameobjects.entities.enemies.FlyingShip;
import com.binarygames.spaceboi.gameobjects.pickups.HealthPickup;

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


    public WorldGenerator(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

   public void createWorld(){
        Random random = new Random();
        createPlanet(0, 0, random);
        int angleOffset = 0;

        for(int circleNumber = 0; circleNumber < NumberOfCircles; circleNumber++){
            int rad = distanceBetweenRows * (circleNumber+1) * maxRad; //Distance to circle of planets
            double circumference = 2 * rad * Math.PI;

            int NumberOfPlanets;
            if((circleNumber%2)==0){ //Number is even - every other row starting from first one
                NumberOfPlanets = random.nextInt(3);
                NumberOfPlanets = NumberOfPlanets + 1; //Prevents division by zero -- 1<=NoP<=3
            }
            else{
                NumberOfPlanets = (int) Math.floor(distanceBetweenPlanets * (circumference/(2*maxRad))); //Circumference / diameter of largest planet and some extra space
            }
            double angleBetweenPlanets = (2 * Math.PI)/NumberOfPlanets;

            if(!((circleNumber%2)==0)){ //If multiplanetrow -> offset
                angleOffset = random.nextInt(30);
                angleOffset = -15 + angleOffset; //  -15<=angleOffset<=15
            }

            for(int j = 0; j < NumberOfPlanets; j++){
                lastX = (int) Math.round(rad * Math.cos((angleBetweenPlanets * j) + angleOffset));
                lastY = (int) Math.round(rad * Math.sin((angleBetweenPlanets * j) + angleOffset));
                boolean isLastPlanet = ((j == NumberOfPlanets-1));

                createPlanet(lastX, lastY, random);
                createEnemies(lastX, lastY, random, lastRad, circleNumber, isLastPlanet);
            }
        }
    }
    private void createPlanet(int x, int y, Random random){
        int rad = random.nextInt(maxRad - minRad);
        rad = rad + minRad;
        int grav = random.nextInt(maxGrav - minGrav);
        grav = grav + minGrav;

        Planet planet = new Planet(gameWorld, x, y, Assets.PLANET_MOON, (float) grav, rad); //TODO Add different sprites depending on gravity and size of planet
        gameWorld.addStaticEntity(planet);

        this.lastRad = rad; //rad from planet
    }
    private void createEnemies(int x, int y, Random random, int rad, int circleNumber, boolean isLastPlanet){
        if(isLastPlanet){
            //Last planet, do not spawn anything
        }
        else if((circleNumber%2)==0){
            //Spawn spaceship
            x = (int) (x + rad * 1.3 + EnemyType.FLYING_SHIP.getRad());
            y = y + (int) EnemyType.FLYING_SHIP.getRad();
            FlyingShip flyingship = new FlyingShip(gameWorld, x, y, Assets.PLANET_MOON, EnemyType.FLYING_SHIP.getMass(), EnemyType.FLYING_SHIP.getRad());
            gameWorld.addDynamicEntity(flyingship);
        }
        else {
            //If we are on one of the big circles of planets
            int planetType = random.nextInt(6); //from 0 to bound-1
            if (planetType < 5) {
                //Spawn Chasers
                int numberOfEnemies = random.nextInt(maxEnemies - minEnemies);
                numberOfEnemies = numberOfEnemies + minEnemies;

                int r = rad + (int) EnemyType.CHASER.getRad(); //Poolära koordinater
                double angleBetweenEnemies = 10;
                double angleDiff = (2 * Math.PI) * (angleBetweenEnemies / 360);

                for (int enemies = 0; enemies < numberOfEnemies; enemies++) {
                    Chaser chaser = new Chaser(gameWorld, (int) (x + Math.round(r * Math.cos(angleDiff * enemies))),
                            (int) (y + Math.round(r * Math.sin(angleDiff * enemies))),
                            Assets.PLANET_MOON, EnemyType.CHASER.getMass(), EnemyType.CHASER.getRad());
                    gameWorld.addDynamicEntity(chaser);
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
    }

    public int generatePlayerX(){
        return lastX + lastRad + OFFSET;
    } //TODO Might not work as inteded
    public int generatePlayerY(){
        return lastY + OFFSET;
    }
}
