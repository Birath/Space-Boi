package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public class Gravity implements Command {

    @Override
    public void run(Console console, String[] args) {
        if (args != null) {
            int value = Integer.valueOf(args[0]);
            if (value == 0) {
                console.getGame().getPreferences().setGravityEnabled(false);
            } else if (value == 1) {
                console.getGame().getPreferences().setGravityEnabled(true);
            } else {
                console.echo("Invalid argument " + value);
            }
            console.echo("Gravity: " + console.getGame().getPreferences().isGravityEnabled());
        }
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.OPTIONAL;
    }

    @Override
    public String getHelpText() {
        return "Toggles gravity";
    }

    @Override
    public String getUsage() {
        return "god [1/0]";
    }
}
