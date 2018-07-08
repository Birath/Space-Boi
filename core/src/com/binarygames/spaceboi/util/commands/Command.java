package com.binarygames.spaceboi.util.commands;

import com.binarygames.spaceboi.util.Console;

public interface Command {

    void run(Console console, String[] args);

    Console.ArgumentType getArgumentType();

    String getHelpText();

    String getUsage();

}