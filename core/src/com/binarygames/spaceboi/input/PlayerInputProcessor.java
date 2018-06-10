package com.binarygames.spaceboi.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public class PlayerInputProcessor implements InputProcessor {

    private Player player;
    private Camera camera;

    public PlayerInputProcessor(Player player, Camera camera)
    {
        this.player = player;
        this.camera = camera;
    }

    //Keyboardrelated
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE){
            player.setMoveUp(true);
            return true;
        }
        if (keycode == Input.Keys.S){
            player.setMoveDown(true);
            return true;
        }
        if (keycode == Input.Keys.D){
            player.setMoveRight(true);
            return true;
        }
        if(keycode == Input.Keys.A){
            player.setMoveLeft(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE){
            player.setMoveUp(false);
            return true;
        }
        if (keycode == Input.Keys.S){
            player.setMoveDown(false);
            return true;
        }
        if (keycode == Input.Keys.D){
            player.setMoveRight(false);
            return true;
        }
        if(keycode == Input.Keys.A){
            player.setMoveLeft(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
        if (button == Input.Buttons.LEFT){
            player.setMouseHeld(true);

            Vector3 vec = new Vector3(screenX, screenY, 0);
            camera.unproject(vec);
            player.setMouseXAndMouseY(vec.x, vec.y);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT){
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
