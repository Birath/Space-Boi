package com.binarygames.spaceboi.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.binarygames.spaceboi.entities.Player;

public class PlayerInputProcessor implements InputProcessor {

    private Player player;

    public PlayerInputProcessor(Player player){
        this.player = player;
    }

    //Keyboardrelated
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W){
            player.setMoveUp(true);
        }
        if (keycode == Input.Keys.S){
            player.setMoveDown(true);
        }
        if (keycode == Input.Keys.D){
            player.setMoveRight(true);
        }
        if(keycode == Input.Keys.A){
            player.setMoveLeft(true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.W){
            player.setMoveUp(false);
        }
        if (keycode == Input.Keys.S){
            player.setMoveDown(false);
        }
        if (keycode == Input.Keys.D){
            player.setMoveRight(false);
        }
        if(keycode == Input.Keys.A){
            player.setMoveLeft(false);
        }
        return true;
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


    //Touchrelated
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
}
