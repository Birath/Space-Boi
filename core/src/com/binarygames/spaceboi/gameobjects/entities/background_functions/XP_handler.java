package com.binarygames.spaceboi.gameobjects.entities.background_functions;

import com.binarygames.spaceboi.gameobjects.entities.Player;

public class XP_handler {

    private Player player;
    private int currentXP = 0;
    private final int healthOnLevelUp = 20;
    private int level = 0;
    private int nextLevel = 100;
    private int x = 0;

    public XP_handler(Player player){
        this.player = player;


    }
    private void levelUp(){
        player.setHealth(player.getHealth() + healthOnLevelUp);
        level += 1;
    }
    public void increaseXP(int XP){
        currentXP += XP;
        if(currentXP > nextLevel){
            currentXP = currentXP - nextLevel;
            calcNextLevel();
            levelUp();
        }
    }
    private void calcNextLevel(){
        x = x+2;
        nextLevel = 100 + (int) Math.exp(x/10);
    }
}
