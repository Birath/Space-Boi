package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.input.PlayerInputProcessor;
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

    private Player player;

    private List<Planet> planets = new ArrayList<Planet>();

    //TEMPSTUFF(har tillhörande imports ovan)
    private Texture img;

    public GameScreen(SpaceBoi game) {
        //TEMPSTUFF - Laddar in bild
        img = new Texture("playerShip.png");

        this.game = game;
        // Mysko skit för att få scalingen av debuggkameran rätt
        camera = new OrthographicCamera(SpaceBoi.VIRTUAL_WIDTH * 0.1f, SpaceBoi.VIRTUAL_HEIGHT * 0.1f);
        //box2dCamera = new OrthographicCamera(Gdx.graphics.getWidth() * WORLD_TO_BOX, Gdx.graphics.getHeight() * WORLD_TO_BOX);
        // Vet inte riktigt vad jag håller på med här, men det verkar funka // Björn
        viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH * WORLD_TO_BOX, SpaceBoi.VIRTUAL_HEIGHT * WORLD_TO_BOX);
        viewport.apply();

        //camera = new OrthographicCamera();

        gameUI = new GameUI();
        world = new World(new Vector2(0f, 0f), true);

        //Entities:
        planets.add(new Planet(world, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight(), 1000000000, Gdx.graphics.getHeight()));
        planets.add(new Planet(world,  Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight() / 2, 1000000000,  Gdx.graphics.getHeight()));
        player = new Player(world, 0,0, "playerShip.png", 10000, 100);

        //Input processor och multiplexer, hanterar användarens input
        inputProcessor = new PlayerInputProcessor(player);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameUI.getStage());
        multiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(multiplexer);

        debugRenderer = new Box2DDebugRenderer();


    }

    private void update(float delta) {
        updatePlayerPhysics();
        camera.position.set(player.getBody().getPosition().x , player.getBody().getPosition().y , 0);
        camera.update();

        player.updateMovement();

        gameUI.act(delta);
    }

    private void draw() {
        gameUI.draw();
    }

    private void batchedDraw() {
        game.getBatch().begin();
        game.getBatch().draw(player.getSprite(), player.getX(), player.getY());
        game.debugFont.draw(game.getBatch(), "GAME", 5, 20);
        game.getBatch().end();
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        update(delta);
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
    public void updatePlayerPhysics(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        debugRenderer.render(world, camera.combined);
        Vector2 playerPos = player.getBody().getPosition();
        float closestDistance = 0f;
        Planet closestPlanet = null;
        for (Planet planet: planets) {
            Vector2 planetPos = planet.getBody().getPosition();
            float distance = planetPos.dst(playerPos);
            if (closestDistance == 0f) {
                closestDistance = distance;
                closestPlanet = planet;
            }
            else if (distance < closestDistance) {
                closestDistance = distance;
                closestPlanet = planet;
            }
        }
        Vector2 closestPos = closestPlanet.getBody().getPosition();
        float distance = closestPos.dst(playerPos);

        float angle = MathUtils.atan2(closestPos.y - playerPos.y, closestPos.x - playerPos.x);

        double force  = Planet.CONSTANT * closestPlanet.getMass() * player.getMass() / Math.pow(distance, 1.1);
        float forceX = MathUtils.cos(angle) * (float) force;
        float forceY = MathUtils.sin(angle) * (float) force;
        player.getBody().applyForceToCenter(forceX, forceY, true);

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        batchedDraw();
        draw();
    }

    @Override
    public void dispose() {
        gameUI.dispose();
    }
}
