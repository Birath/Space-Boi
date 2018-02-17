package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.ui.GameUI;

public class GameScreen implements Screen {

    private SpaceBoi game;

    private OrthographicCamera camera;

    private GameUI gameUI;

    public GameScreen(SpaceBoi game) {
        this.game = game;

        camera = new OrthographicCamera();

        gameUI = new GameUI();

        Gdx.input.setInputProcessor(gameUI.getStage()); // TODO add multiplexer for player input
    }

    private void update(float delta) {
        camera.update();

        gameUI.act(delta);
    }

    private void draw() {
        gameUI.draw();
    }

    private void batchedDraw() {
        game.getBatch().begin();
        game.debugFont.draw(game.getBatch(), "GAME", 5, 20);
        game.getBatch().end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batchedDraw();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        gameUI.getStage().getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
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
    public void dispose() {
        gameUI.dispose();
    }
}
