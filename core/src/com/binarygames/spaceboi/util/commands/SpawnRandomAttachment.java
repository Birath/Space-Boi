package com.binarygames.spaceboi.util.commands;

import com.badlogic.gdx.Gdx;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.bodies.BaseBody;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;
import com.binarygames.spaceboi.util.Console;

import java.lang.reflect.InvocationTargetException;

public class SpawnRandomAttachment implements Command {
    @Override
    public void run(Console console, String[] args) {
        if (args == null) {
            try {
                console.getGameWorld().addDynamicEntity(WeaponAttachment.getRandomAttachment(console.getGameWorld(), console.getGameWorld().getPlayer().getBody().getPosition().x * BaseBody.PPM + 100, console.getGameWorld().getPlayer().getBody().getPosition().y * BaseBody.PPM));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        } else if (args.length == 1){
            int x = Integer.valueOf(args[0]);
            try {
                console.getGameWorld().addDynamicEntity(WeaponAttachment.getRandomAttachment(console.getGameWorld(), console.getGameWorld().getPlayer().getBody().getPosition().x * BaseBody.PPM + x, console.getGameWorld().getPlayer().getBody().getPosition().y * BaseBody.PPM));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                Gdx.app.error("SpawnRandomAttachment", "Failed to spawn random attachment", e);
            }
        } else if (args.length == 2) {
            int x = Integer.valueOf(args[0]);
            int y = Integer.valueOf(args[1]);

            try {
                WeaponAttachment attachment = WeaponAttachment.getRandomAttachment(console.getGameWorld(), console.getGameWorld().getPlayer().getBody().getPosition().x * BaseBody.PPM + x, console.getGameWorld().getPlayer().getBody().getPosition().y * BaseBody.PPM + y);
                console.getGameWorld().addDynamicEntity(attachment);
                console.echo("Spawned: " + attachment.getName());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                Gdx.app.error("SpawnRandomAttachment", "Failed to spawn random attachment", e);
            }
        } else {
            console.echo("Invalid amount of arguments. ");
        }
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.OPTIONAL;
    }

    @Override
    public String getHelpText() {
        return "Spawns a random attachment at the input coordinates relative to the player";
    }

    @Override
    public String getUsage() {
        return "randomAttachment [x[x y]]";
    }
}
