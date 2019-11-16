package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public class SetXp implements Command {

    @Override
    public void run(Console console, String[] args) {
        if (args != null) {
            int value = Integer.parseInt(args[0]);
            if (value > 0) {
                console.getGameWorld().getXp_handler().setXP(value);
            }
            console.echo("Invalid argument " + value);
        }
        console.echo("XP: " + console.getGameWorld().getXp_handler().getCurrentXP());
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.REQUIRED;
    }

    @Override
    public String getHelpText() {
        return "Set player experience";
    }

    @Override
    public String getUsage() {
        return "setxp [amount]";
    }
}
