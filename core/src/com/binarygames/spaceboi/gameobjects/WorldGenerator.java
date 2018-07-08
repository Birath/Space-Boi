package com.binarygames.spaceboi.gameobjects;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Chaser;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Enemy;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Flyingship;

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


    //Hardcoded enemystats
    private int chaserRad = 8;
    private int chaserMass = 100;

    private int shipRad = 20;
    private int shipMass = 300;


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

                createPlanet(lastX, lastY, random);
                createEnemies(lastX, lastY, random, lastRad, circleNumber);
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
    private void createEnemies(int x, int y, Random random, int rad, int circleNumber){
        if((circleNumber%2)==0){
            //Spawn spaceship
            x = (int) (x + rad * 1.3 + shipRad);
            y = y + shipRad;
            Flyingship flyingship = new Flyingship(gameWorld, x, y, Assets.PLANET_MOON, shipMass, shipRad);
            gameWorld.addDynamicEntity(flyingship);
        }
        else{
            //Spawn Chasers
            int numberOfEnemies = random.nextInt(maxEnemies - minEnemies);
            numberOfEnemies = numberOfEnemies + minEnemies;

            x = x + rad + chaserRad;
            y = y + chaserRad;

            for(int enemies = 0; enemies < numberOfEnemies; enemies++){
                Chaser chaser = new Chaser(gameWorld, x + (chaserRad * enemies), y, Assets.PLANET_MOON, chaserMass, chaserRad);
                gameWorld.addDynamicEntity(chaser);
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
