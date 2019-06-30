package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public class WinGame implements Command {
    @Override
    public void run(Console console, String[] args) {
        console.getGameWorld().winGame();
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.NONE;
    }

    @Override
    public String getHelpText() {
        return "Wins the game";
    }

    @Override
    public String getUsage() {
        return "wingame";
    }
}
