package com.binarygames.spaceboi.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.GrenadeLauncher;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;

public class PlayerInputProcessor implements InputProcessor {

    private Player player;
    private Camera camera;
    private World world;
    private GameWorld gameWorld;

    public PlayerInputProcessor(Player player, Camera camera, World world, GameWorld gameWorld) {
        this.player = player;
        this.camera = camera;
        this.world = world;
        this.gameWorld = gameWorld;
    }

    //Keyboardrelated
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            player.setMoveUp(true);
            return true;
        }
        if (keycode == Input.Keys.S) {
            player.setMoveDown(true);
            return true;
        }
        if (keycode == Input.Keys.D) {
            player.setMoveRight(true);
            return true;
        }
        if (keycode == Input.Keys.A) {
            player.setMoveLeft(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            player.setMoveUp(false);
            return true;
        }
        if (keycode == Input.Keys.S) {
            player.setMoveDown(false);
            return true;
        }
        if (keycode == Input.Keys.D) {
            player.setMoveRight(false);
            return true;
        }
        if (keycode == Input.Keys.A) {
            player.setMoveLeft(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == 'k') {
            if (player.getWeapon() instanceof Machinegun) {
                player.setWeapon(new GrenadeLauncher(world, gameWorld));
                System.out.println("Weapon set to GrenadeLauncher");
            } else {
                player.setWeapon(new Machinegun(world, gameWorld));
                System.out.println("Weapon set to Machinegun");
            }
            return true;
        }
        return false;
    }


    //Mouserelated
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    //Touchrelated - And clicking!
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            player.setMouseHeld(true);

            Vector3 mouseCoords = getMouseCoords(screenX, screenY);
            player.setMouseCoords(mouseCoords.x, mouseCoords.y);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            player.setMouseHeld(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (player.isMouseHeld()) {
            Vector3 mouseCoords = getMouseCoords(screenX, screenY);
            player.setMouseCoords(mouseCoords.x, mouseCoords.y);
            return true;
        }

        return false;
    }

    private Vector3 getMouseCoords(int screenX, int screenY) {
        Vector3 mouseCoords = new Vector3(screenX, screenY, 0);
        camera.unproject(mouseCoords);
        return mouseCoords;
    }
}
