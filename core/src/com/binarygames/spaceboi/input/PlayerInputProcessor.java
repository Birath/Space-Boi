package com.binarygames.spaceboi.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.screens.GameScreen;

public class PlayerInputProcessor implements InputProcessor {

    private Player player;
    private Camera camera;
    private World world;
    private GameWorld gameWorld;
    private GameScreen gameScreen;

    public PlayerInputProcessor(Player player, Camera camera, World world, GameWorld gameWorld, GameScreen gameScreen) {
        this.player = player;
        this.camera = camera;
        this.world = world;
        this.gameWorld = gameWorld;
        this.gameScreen = gameScreen;
    }

    //Keyboardrelated
    @Override
    public boolean keyDown(int keycode) {
        //WASD Movement
        //possibly move to another inputprocessor ui
        if ((gameScreen.state == 1) && (keycode == Input.Keys.ESCAPE)) {
            gameScreen.resume();
            return true;
        }
        if (keycode == Input.Keys.ESCAPE) {
            gameScreen.pause();
            return true;
        }
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
        //Numbers
        if (character == '1') {
            player.setWeapon(0);
            System.out.println("Equipped " + player.getWeapon().toString());
            return true;
        }
        if (character == '2') {
            player.setWeapon(1);
            System.out.println("Equipped " + player.getWeapon().toString());
            return true;
        }
        if (character == '3') {
            player.setWeapon(2);
            System.out.println("Equipped " + player.getWeapon().toString());
            return true;
        }

        if (character == '§' && gameScreen.getConsole().isConsoleEnabled()) {
            gameScreen.getConsole().show();
        }
        if (Character.toLowerCase(character) == 'r') {
            player.setToReloadTrue();
        }

        if (Character.toLowerCase(character) == 'i') {
            gameScreen.getInventoryUI().show();
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
        return false;
    }
}
