package com.binarygames.spaceboi.util.commands;

import com.badlogic.gdx.Gdx;
import com.binarygames.spaceboi.gameobjects.bodies.BaseBody;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;
import com.binarygames.spaceboi.util.Console;

import java.lang.reflect.InvocationTargetException;

public class SpawnAttachment implements Command {
    @Override
    public void run(Console console, String[] args) {
        if (args == null || args.length == 0) {
            console.echo("Invalid amount of arguments. ");
            return;
        }
        if (args.length == 1){
            int index = Integer.valueOf(args[0]);
            try {
                console.getGameWorld().addDynamicEntity(WeaponAttachment.getAttachment(console.getGameWorld(), console.getGameWorld().getPlayer().getBody().getPosition().x * BaseBody.PPM + 100, console.getGameWorld().getPlayer().getBody().getPosition().y * BaseBody.PPM, index));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                Gdx.app.error("SpawnRandomAttachment", "Failed to spawn random attachment", e);
            }
        } else if (args.length == 2) {
            int index = Integer.valueOf(args[0]);
            int x = Integer.valueOf(args[1]);
            try {
                WeaponAttachment attachment = WeaponAttachment.getAttachment(console.getGameWorld(), console.getGameWorld().getPlayer().getBody().getPosition().x * BaseBody.PPM + x, console.getGameWorld().getPlayer().getBody().getPosition().y * BaseBody.PPM, index);
                console.getGameWorld().addDynamicEntity(attachment);
                console.echo("Spawned: " + attachment.getName());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                Gdx.app.error("SpawnRandomAttachment", "Failed to spawn random attachment", e);
            }
        } else if (args.length == 3) {
            int index = Integer.valueOf(args[0]);
            int x = Integer.valueOf(args[1]);
            int y = Integer.valueOf(args[2]);
            try {
                WeaponAttachment attachment = WeaponAttachment.getAttachment(console.getGameWorld(), console.getGameWorld().getPlayer().getBody().getPosition().x * BaseBody.PPM + x, console.getGameWorld().getPlayer().getBody().getPosition().y * BaseBody.PPM + y, index);
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
        return "attachment index [x[x y]]";
    }
}
