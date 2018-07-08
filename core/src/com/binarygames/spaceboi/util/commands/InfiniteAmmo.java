package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public class InfiniteAmmo implements Command {

    @Override
    public void run(Console console, String[] args) {
        if (args != null) {
            int value = Integer.valueOf(args[0]);
            if (value == 0) {
                console.getGameWorld().getPlayer().setInfiniteAmmo(false);
            } else if (value == 1) {
                console.getGameWorld().getPlayer().setInfiniteAmmo(true);
            } else {
                console.echo("Invalid argument " + value);
            }
        }
        console.echo("Infinite ammo: " + console.getGameWorld().getPlayer().hasInfiniteAmmo());
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.OPTIONAL;
    }

    @Override
    public String getHelpText() {
        return "Toggles infinite ammo";
    }

    @Override
    public String getUsage() {
        return "infiniteammo [1/0]";
    }
}
