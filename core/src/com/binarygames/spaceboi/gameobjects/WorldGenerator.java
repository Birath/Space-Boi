package com.binarygames.spaceboi.gameobjects;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Chaser;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Enemy;

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
    private int minEnemies = 0;
    private int enemyMass = 100;
    private int enemyRad = 8;


    public WorldGenerator(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }
   public void createWorld(){
        Random random = new Random();
        createPlanet(0, 0, random);
        int angleOffset = 0;

        for(int i = 0; i < NumberOfCircles; i++){
            int rad = distanceBetweenRows * (i+1) * maxRad; //Distance to circle of planets
            double circumference = 2 * rad * Math.PI;

            int NumberOfPlanets;
            if((i%2)==0){ //Number is even - every other row starting from first one
                NumberOfPlanets = random.nextInt(3);
                NumberOfPlanets = NumberOfPlanets + 1; //Prevents division by zero -- 1<=NoP<=3
            }
            else{
                NumberOfPlanets = (int) Math.floor(distanceBetweenPlanets * (circumference/(2*maxRad))); //Circumference / diameter of largest planet and some extra space
            }
            double angleBetweenPlanets = (2 * Math.PI)/NumberOfPlanets;

            if(!((i%2)==0)){ //If multiplanetrow -> offset
                angleOffset = random.nextInt(30);
                angleOffset = -15 + angleOffset; //  -15<=angleOffset<=15
            }

            for(int j = 0; j < NumberOfPlanets; j++){
                createPlanet((int) Math.round(rad * Math.cos((angleBetweenPlanets * j) + angleOffset)),
                        (int) Math.round(rad * Math.sin((angleBetweenPlanets * j) + angleOffset)), random);
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

        this.lastX = x;
        this.lastY = y;
        this.lastRad = rad;

        createEnemies(x, y, random, rad);
    }
    private void createEnemies(int x, int y, Random random, int rad){
        int numberOfEnemies = random.nextInt(maxEnemies - minEnemies);
        numberOfEnemies = numberOfEnemies + minEnemies;

        x = x + rad + enemyRad;
        y = y + enemyRad;

        for(int i = 0; i < numberOfEnemies; i++){
            Chaser chaser = new Chaser(gameWorld, x + (enemyRad * i), y, Assets.PLANET_MOON, enemyMass, enemyRad);
            gameWorld.addDynamicEntity(chaser);
        }
    }

    public int generatePlayerX(){
        return lastX + lastRad + OFFSET;
    }
    public int generatePlayerY(){
        return lastY + OFFSET;
    }
}
