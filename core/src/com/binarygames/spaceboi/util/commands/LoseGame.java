package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public class LoseGame implements Command {
    @Override
    public void run(Console console, String[] args) {
        console.getGameWorld().endGame();
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.NONE;
    }

    @Override
    public String getHelpText() {
        return "Lose the game";
    }

    @Override
    public String getUsage() {
        return "losegame";
    }
}
