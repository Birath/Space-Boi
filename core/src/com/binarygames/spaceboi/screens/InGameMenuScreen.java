package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.binarygames.spaceboi.SpaceBoi;


public class InGameMenuScreen implements Screen {

    private SpaceBoi game;

    private Stage stage;


    public void InGameMenuScreen(final SpaceBoi game){
        this.game = game;

    }

    public void show(){

    }

    public void render(float delta){

    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose(){
        stage.dispose();
    }

}
