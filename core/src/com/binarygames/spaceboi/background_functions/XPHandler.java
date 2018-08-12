package com.binarygames.spaceboi.background_functions;

import com.binarygames.spaceboi.gameobjects.entities.Player;

public class XPHandler {

    private Player player;
    private int currentXP = 0;
    private final int healthOnLevelUp = 20;
    private int level = 0;
    private int nextLevel = 100;
    private int x = 0;

    public XPHandler(Player player){
        this.player = player;

    }
    private void levelUp(){
        player.increaseMaxHealth(healthOnLevelUp);
        level += 1;
    }
    public void increaseXP(int xp){
        currentXP += xp;
        while (currentXP >= nextLevel) {
            currentXP -= nextLevel;
            calcNextLevel();
            levelUp();
        }
    }
    private void calcNextLevel(){
        x += 1;
        nextLevel = 100 + (int) Math.exp(x/2) + 3*x;
    }
    public int getLevel(){
        return level;
    }
    public int getCurrentXP(){
        return currentXP;
    }
    public int getNextLevel(){
        return nextLevel;
    }
}
