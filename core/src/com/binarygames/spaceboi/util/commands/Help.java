package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public class Help implements Command {

    @Override
    public void run(Console console, String[] args) {
        if (console.getCommands().containsKey(args[0])) {
            Command command = console.getCommands().get(args[0]);
            console.echo(command.getUsage() + " - " + command.getHelpText());
        } else {
            console.echo(args[0] + " is no a valid command");
        }
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.REQUIRED;
    }

    @Override
    public String getHelpText() {
        return "view the help text of a command";
    }

    @Override
    public String getUsage() {
        return "help [command]";
    }

}
