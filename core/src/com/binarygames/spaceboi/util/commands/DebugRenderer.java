package com.binarygames.spaceboi.util.commands;

import com.badlogic.gdx.math.Interpolation;
import com.binarygames.spaceboi.util.Console;

public class DebugRenderer implements Command {
    @Override
    public void run(Console console, String[] args) {
        if (args != null) {
            int value = Integer.valueOf(args[0]);
            if (value == 0) {
                console.getGameScreen().setDebugRendererIsEnabled(false);
            } else if (value == 1) {
                console.getGameScreen().setDebugRendererIsEnabled(true);
                console.echo("Workaround: Resize the window to fix stuff");
            } else {
                console.echo("Invalid argument " + value);
            }
        }
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.OPTIONAL;
    }

    @Override
    public String getHelpText() {
        return "Toggels the debug renderer";
    }

    @Override
    public String getUsage() {
        return "debug [1/0]";
    }
}
