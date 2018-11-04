package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

/**
 * 2meirl4meirl
 */
public class Suicide implements Command {
    @Override
    public void run(Console console, String[] args) {
        console.getGameWorld().getPlayer().reduceHealth(console.getGameWorld().getPlayer().getHealth());
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.NONE;
    }

    @Override
    public String getHelpText() {
        return "Kills you instantly";
    }

    @Override
    public String getUsage() {
        return "me_irl";
    }
}
