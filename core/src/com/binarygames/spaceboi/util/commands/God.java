package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public class God implements Command {

    @Override
    public void run(Console console, String[] args) {
        if (args != null) {
            int value = Integer.valueOf(args[0]);
            if (value == 0) {
                console.getGameWorld().getPlayer().setGod(false);
            } else if (value == 1) {
                console.getGameWorld().getPlayer().setGod(true);
            } else {
                console.echo("Invalid argument " + value);
            }
        }
        console.echo("God mode:" + console.getGameWorld().getPlayer().isGod());
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.OPTIONAL;
    }

    @Override
    public String getHelpText() {
        return "Toggles god mode";
    }

    @Override
    public String getUsage() {
        return "god [1/0]";
    }
}
