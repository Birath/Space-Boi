package com.binarygames.spaceboi.util.commands;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Chaser;
import com.binarygames.spaceboi.gameobjects.entities.enemies.FinalBoss;
import com.binarygames.spaceboi.gameobjects.entities.enemies.FlyingShip;
import com.binarygames.spaceboi.gameobjects.entities.enemies.Shooter;
import com.binarygames.spaceboi.util.Console;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public class SpawnEnemy implements Command {

    @Override
    public void run(Console console, String[] args) {

        Vector2 position = console.getGameWorld().getPlayer().getBody().getPosition();
        position.scl(PPM);
        switch (args[0].toUpperCase()) {
            case "CHASER":
                Chaser chaser = new Chaser(console.getGameWorld(), position.x + 10, position.y + 10, Assets.DOG);
                console.getGameWorld().addDynamicEntity(chaser);

                console.echo("chaser");
                break;
            case "SHOOTER":
                Shooter shooter = new Shooter(console.getGameWorld(), position.x + 10, position.y + 10, Assets.PIRATE);
                console.getGameWorld().addDynamicEntity(shooter);

                console.echo("shooter");
                break;
            case "FLYINGSHIP":
                FlyingShip flyingship = new FlyingShip(console.getGameWorld(), position.x + 150, position.y, Assets.FLYINGSHIP);
                console.getGameWorld().addDynamicEntity(flyingship);

                console.echo("flying ship");
                break;
            case "FINALBOSS":
                FinalBoss finalBoss = new FinalBoss(console.getGameWorld(), position.x + 10, position.y + 10, Assets.PLAYER);
                console.getGameWorld().addDynamicEntity(finalBoss);

                console.echo("finalboss");
                break;
            default:
                console.echo("Invalid argument " + args[0]);
        }
    }

    @Override
    public Console.ArgumentType getArgumentType() {
        return Console.ArgumentType.REQUIRED;
    }

    @Override
    public String getHelpText() {
        return "Spawn enemy nearby. Enemy types: chaser, shooter, flyingship, finalboss";
    }

    @Override
    public String getUsage() {
        return "spawnenemy [enemy type]";
    }
}
