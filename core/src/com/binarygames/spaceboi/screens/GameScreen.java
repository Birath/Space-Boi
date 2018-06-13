package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.PLAYER_STATE;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.input.PlayerInputProcessor;
import com.binarygames.spaceboi.ui.FrameRate;
import com.binarygames.spaceboi.ui.GameUI;

import java.util.ArrayList;
import java.util.List;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseStaticBody.WORLD_TO_BOX;

public class GameScreen implements Screen {

    private SpaceBoi game;

    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Viewport viewport;
    private World world;

    private PlayerInputProcessor inputProcessor;

    private GameUI gameUI;

    private GameWorld gameWorld;

    private Player player;

    private List<EntityDynamic> entities = new ArrayList<>();
    private List<Planet> planets = new ArrayList<>();

    private FrameRate frameRate;

    //TEMPSTUFF(har tillhörande imports ovan)
    private Texture img;

    public GameScreen(SpaceBoi game) {
        //TEMPSTUFF - Laddar in bild
        img = new Texture("playerShip.png");

        //Framerate
        frameRate = new FrameRate();

        this.game = game;
        // Mysko skit för att få scalingen av debuggkameran rätt
        camera = new OrthographicCamera();
        //box2dCamera = new OrthographicCamera(Gdx.graphics.getWidth() * WORLD_TO_BOX, Gdx.graphics.getHeight() * WORLD_TO_BOX);
        // Vet inte riktigt vad jag håller på med här, men det verkar funka // Björn
        viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH * WORLD_TO_BOX, SpaceBoi.VIRTUAL_HEIGHT * WORLD_TO_BOX, camera);
        viewport.apply();

        //camera = new OrthographicCamera();

        gameUI = new GameUI();
        world = new World(new Vector2(0f, 0f), true);

        gameWorld = new GameWorld(game, world);

        //Entities:
        gameWorld.createWorld();
        player = gameWorld.getPlayer();
        //Input processor och multiplexer, hanterar användarens input
        inputProcessor = new PlayerInputProcessor(player, camera);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameUI.getStage());
        multiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(multiplexer);

        debugRenderer = new Box2DDebugRenderer();


    }

    private void update(float delta) {
        frameRate.update();
        gameWorld.update(delta);
        gameUI.act(delta);

        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        camera.update();

        batchedDraw();
        draw();
    }

    private void draw() {
        gameUI.draw();
    }

    private void batchedDraw() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        debugRenderer.render(world, camera.combined);

        game.getBatch().begin();

        gameWorld.render(game.getBatch(), camera);

        game.getBatch().draw(player.getSprite(), player.getX(), player.getY()); // TODO remove

        game.debugFont.draw(game.getBatch(), "GAME", 5, 20);
        game.getBatch().end();
    }

    @Override public void show() {

    }


    @Override public void render(float delta) {
        update(delta);
        frameRate.render();
    }

    @Override public void resize(int width, int height) {
        gameUI.getStage().getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void hide() {

    }


    @Override public void dispose() {
        gameUI.dispose();
        frameRate.dispose();
    }

    public void appendEntity(EntityDynamic entity) {
        entities.add(entity);
    }
}
