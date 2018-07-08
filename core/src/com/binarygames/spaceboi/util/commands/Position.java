package com.binarygames.spaceboi.util.commands;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.util.Console;

public class Position implements Command {

    @Override
    public void run(Console console, String[] args) {
        Vector2 playerPos = console.getGameWorld().getPlayer().getBody().getPosition();
        console.echo("X: " + playerPos.x + " Y: " + playerPos.y);
    }
}
