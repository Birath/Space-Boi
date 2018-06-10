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
        camera = new OrthographicCamera(SpaceBoi.VIRTUAL_WIDTH * 0.1f, SpaceBoi.VIRTUAL_HEIGHT * 0.1f);
        //box2dCamera = new OrthographicCamera(Gdx.graphics.getWidth() * WORLD_TO_BOX, Gdx.graphics.getHeight() * WORLD_TO_BOX);
        // Vet inte riktigt vad jag håller på med här, men det verkar funka // Björn
        viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH * WORLD_TO_BOX, SpaceBoi.VIRTUAL_HEIGHT * WORLD_TO_BOX);
        viewport.apply();

        //camera = new OrthographicCamera();

        gameUI = new GameUI();
        world = new World(new Vector2(0f, 0f), true);

        gameWorld = new GameWorld(game, world);

        //Entities:
        player = new Player(world, 0, 0, "playerShip.png", 10000, 100);
        gameWorld.addDynamicEntity(player);

        Planet planet1 = new Planet(world, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight(), "moon.png", 50000000000f, Gdx.graphics.getHeight());
        planets.add(planet1);
        gameWorld.addStaticEntity(planet1);
        Planet planet2 = new Planet(world, Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight() / 2, "moon.png", 50000000000f, Gdx.graphics.getHeight());
        planets.add(planet2);
        gameWorld.addStaticEntity(planet2);

        //Input processor och multiplexer, hanterar användarens input
        inputProcessor = new PlayerInputProcessor(player, camera);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameUI.getStage());
        multiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(multiplexer);

        debugRenderer = new Box2DDebugRenderer();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                for (Planet planet : planets) {
                    if (contact.getFixtureA().getBody().equals(planet.getBody()) ||
                            contact.getFixtureB().getBody().equals(planet.getBody()) &&
                                    contact.getFixtureA().getBody().equals(player.getBody()) ||
                            contact.getFixtureB().getBody().equals(player.getBody())
                            ) {
                        System.out.println("Touching");
                        player.setPlayerState(PLAYER_STATE.STANDING);
                        if (contact.getFixtureB().getBody().getUserData() == null) //TODO Hack fix later
                            player.setPlanetBody(contact.getFixtureB().getBody());
                        else player.setPlanetBody(contact.getFixtureA().getBody());
                    }

                }
            }

            @Override
            public void endContact(Contact contact) {
                System.out.println("Not touching");
                player.setPlayerState(PLAYER_STATE.JUMPING);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    private void update(float delta) {
        updatePlayerPhysics();
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        camera.update();

        frameRate.update();
        gameWorld.update(delta);
        gameUI.act(delta);

        batchedDraw();
        draw();
    }

    private void draw() {
        gameUI.draw();
    }

    private void batchedDraw() {
        game.getBatch().begin();

        gameWorld.render(game.getBatch(), camera);

        game.getBatch().draw(player.getSprite(), player.getX(), player.getY()); // TODO remove

        game.debugFont.draw(game.getBatch(), "GAME", 5, 20);
        game.getBatch().end();
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        update(delta);
        frameRate.render();
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

    public void updatePlayerPhysics() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        debugRenderer.render(world, camera.combined);
        Vector2 playerPos = player.getBody().getPosition();
        float closestDistance = 0f;
        Planet closestPlanet = null;
        for (Planet planet : planets) {
            Vector2 planetPos = planet.getBody().getPosition();
            float distance = planetPos.dst(playerPos);
            if (closestDistance == 0f) {
                closestDistance = distance;
                closestPlanet = planet;
            } else if (distance < closestDistance) {
                closestDistance = distance;
                closestPlanet = planet;
            }
        }
        Vector2 closestPos = closestPlanet.getBody().getPosition();
        float distance = closestPos.dst(playerPos);
        // https://gamedev.stackexchange.com/questions/15708/how-can-i-implement-gravity
        float angle = MathUtils.atan2(closestPos.y - playerPos.y, closestPos.x - playerPos.x);

        double force = Planet.CONSTANT * closestPlanet.getMass() * player.getMass() / Math.pow(distance, 1.1);
        float forceX = MathUtils.cos(angle) * (float) force;
        float forceY = MathUtils.sin(angle) * (float) force;
        player.getBody().applyForceToCenter(forceX, forceY, true);

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    @Override
    public void dispose() {
        gameUI.dispose();
        frameRate.dispose();
    }
}
