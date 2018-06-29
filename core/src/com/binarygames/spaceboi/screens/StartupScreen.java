package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.binarygames.spaceboi.SpaceBoi;

public class StartupScreen extends BaseScreen implements Screen {

    private Image startupImage;

    public StartupScreen(SpaceBoi game) {
        super(game, null);

        stage.clear();

        // Game startup image
        startupImage = new Image(new Texture(Gdx.files.internal("menu/startup_screen/startup.jpg")));
        startupImage.setOrigin(startupImage.getWidth() / 2, startupImage.getHeight() / 2);
        startupImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(startupImage);

        // Loading label // TODO add text similar to loading screen?
        Label startupLabel = new Label("Launching SpaceBoi,\nplease fasten your seatbelts...", titleStyle);
        startupLabel.setX(stage.getWidth() / 2 - startupLabel.getWidth() / 2);
        startupLabel.setY(stage.getHeight() / 2 + startupLabel.getHeight() / 2);
        stage.addActor(startupLabel);
    }

    private void update(float delta) {
        // Load menu and sound assets
        if (game.getAssetManager().update()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        stage.act(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        update(delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        // TODO dispose of startup image
    }
}
