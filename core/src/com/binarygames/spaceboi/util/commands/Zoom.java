package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public class Zoom implements Command {

    @Override
    public void run(Console console, String[] args) {
        if (args == null) {
            console.echo("Zoom: " + console.getGameWorld().getCamera().zoom);
        } else {
            console.getGameWorld().getCamera().zoom = Float.valueOf(args[0]);
            console.echo("Zoom set to " + Float.valueOf(args[0]));
        }
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.OPTIONAL;
    }

    @Override
    public String getHelpText() {
        return "Sets or prints the cameras zoom level";
    }

    @Override
    public String getUsage() {
        return "zoom [ZOOMLEVEL]";
    }

}
