package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.entities.Player;
import com.binarygames.spaceboi.input.PlayerInputProcessor;
import com.binarygames.spaceboi.ui.GameUI;

public class GameScreen implements Screen {

    private SpaceBoi game;

    private OrthographicCamera camera;

    private PlayerInputProcessor inputProcessor;

    private GameUI gameUI;

    private Player player;

    //TEMPSTUFF(har tillhörande imports ovan)
    private Texture img;

    public GameScreen(SpaceBoi game) {
        //TEMPSTUFF
        img = new Texture("playerShip.png");

        this.game = game;

        camera = new OrthographicCamera();

        gameUI = new GameUI();

        player = new Player(0,0);

        inputProcessor = new PlayerInputProcessor(player);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameUI.getStage());
        multiplexer.addProcessor(inputProcessor);

        Gdx.input.setInputProcessor(multiplexer);
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
        game.getBatch().draw(img, player.getX(), player.getY());
        game.debugFont.draw(game.getBatch(), "GAME", 5, 20);
        game.getBatch().end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        player.updateMovement(); //TEMP
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
