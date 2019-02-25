package com.binarygames.spaceboi.util.commands;

import com.badlogic.gdx.Gdx;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;
import com.binarygames.spaceboi.util.Console;

import java.lang.reflect.InvocationTargetException;

public class FillInventory implements Command {
    @Override
    public void run(Console console, String[] args) {
        for (int i = 0; i < 9; i++) {
            try {
                console.getGameWorld().getPlayer().addToInventory(WeaponAttachment.getRandomAttachment(console.getGameWorld(), 0, 0));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                Gdx.app.error("FillInventory", "Failed to add random attachment to inventory", e);
            }
        }
    }

    @Override
    public Console.ArgumentType getArgumentType() {
            return Console.ArgumentType.OPTIONAL;
        }

    @Override
    public String getHelpText() {
        return "Add randomn attachment to inventory";
    }

    @Override
    public String getUsage() {
        return "fillinventory";
    }
}
