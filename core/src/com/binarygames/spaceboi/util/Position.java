package com.binarygames.spaceboi.util;

import com.badlogic.gdx.math.Vector2;

public class Position implements Command {

    @Override
    public void run(Console console, String[] args) {
        Vector2 playerPos = console.getGameWorld().getPlayer().getBody().getPosition();
        console.echo("X: " + playerPos.x + " Y: " + playerPos.y);
    }
}
