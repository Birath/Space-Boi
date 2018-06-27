package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.input.PlayerInputProcessor;
import com.binarygames.spaceboi.ui.FrameRate;
import com.binarygames.spaceboi.ui.GameUI;

import java.util.ArrayList;
import java.util.List;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public class GameScreen implements Screen {

    private SpaceBoi game;

    private static final int GAME_RUNNING = 0;
    private static final int GAME_PAUSED = 1;
    public int state;

    private InGameMenuScreen inGameMenuScreen;

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

    private float lastAngle;

    private boolean interpolateRotation = false;
    private final int interpolateCount = 30;
    private int currentInterpolateCount;

    private final float whenToInterpolate = 10;
    private float angleToInterpolate;

    public GameScreen(SpaceBoi game) {
        //Framerate
        frameRate = new FrameRate();

        this.game = game;
        // Mysko skit för att få scalingen av debuggkameran rätt
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        //box2dCamera = new OrthographicCamera(Gdx.graphics.getWidth() * WORLD_TO_BOX, Gdx.graphics.getHeight() * WORLD_TO_BOX);
        // Vet inte riktigt vad jag håller på med här, men det verkar funka // Björn

        //viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH * WORLD_TO_BOX, SpaceBoi.VIRTUAL_HEIGHT * WORLD_TO_BOX, camera);
        //viewport.apply();

        //camera = new OrthographicCamera();

        gameUI = new GameUI(game);
        inGameMenuScreen = new InGameMenuScreen(this, game);
        world = new World(new Vector2(0f, 0f), true);

        gameWorld = new GameWorld(game, world, camera);

        //Entities:
        gameWorld.createWorld();
        player = gameWorld.getPlayer();
        //Input processor och multiplexer, hanterar användarens input
        inputProcessor = new PlayerInputProcessor(player, camera, world, gameWorld, this);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameUI.getStage());
        multiplexer.addProcessor(inGameMenuScreen.getStage());
        multiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(multiplexer);

        debugRenderer = new Box2DDebugRenderer();

        state = GAME_RUNNING;
    }

    private void update(float delta) {

        switch (state) {
            case GAME_RUNNING:
                gameWorld.update(delta);
                gameUI.act(delta);

                // set camera rotation
                if (interpolateRotation) {
                    if (currentInterpolateCount == interpolateCount) {
                        interpolateRotation = false;
                        System.out.println("TOINTERPOLATE: " + angleToInterpolate);
                        System.out.println("GOALANGLE: " + angleToInterpolate + lastAngle);
                        System.out.println("CAMANGLE: " + getCameraRotation());
                        System.out.println("CURRENTANGLE: " + player.getPlayerAngle());
                        lastAngle = player.getPlayerAngle();
                    } else {
                        camera.rotate(angleToInterpolate / interpolateCount);
                        currentInterpolateCount++;
                    }
                } else {
                    // float angleDiff = lastAngle - player.getPlayerAngle();
                    // System.out.println("Player angle: " + player.getPlayerAngle());
                    // System.out.println("Camera angle " + getCameraRotation());
                    // System.out.println("Angle diff: " + MathUtils.ceil(player.getPlayerAngle() - getCameraRotation()));
                    // System.out.println();

                    float angleDiff = angleDifference(player.getPlayerAngle(), lastAngle);
                    //int angleDiff = MathUtils.ceil(player.getPlayerAngle() - getCameraRotation());
                    if (Math.abs(angleDiff) >= whenToInterpolate) {
                        //angleToInterpolate = Math.abs(angleDiff) + 180;
                        angleToInterpolate = angleDiff;
                        currentInterpolateCount = 0;
                        interpolateRotation = true;
                        lastAngle = player.getPlayerAngle();
                    } else {
                        lastAngle = player.getPlayerAngle();
                        camera.rotate(angleDiff);
                        //camera.rotate(Math.abs(angleDiff) + 180);
                    }
                }

        /* float angleDiff = lastAngle - player.getPlayerAngle();
        lastAngle = player.getPlayerAngle();
        camera.rotate(angleDiff); */

                camera.position.set(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM, 0);
                camera.update();
                game.getBatch().setProjectionMatrix(camera.combined);
                batchedDraw(delta);
                draw();
                break;

            case GAME_PAUSED:
                inGameMenuScreen.act(delta);
                draw();
                break;

        }
        frameRate.update();
    }

    private void draw() {

        switch (state) {
            case GAME_RUNNING:
                gameUI.draw();
                break;
            case GAME_PAUSED:
                inGameMenuScreen.draw();
                break;
        }
    }

    private void batchedDraw(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        //debugRenderer.render(world, camera.combined.scl(PPM)); TODO fix matching rotation on debug rendering

        game.getBatch().begin();
        gameWorld.render(game.getBatch(), camera);
        gameWorld.getParticleHandler().updateAndDrawEffects(game.getBatch(), delta);
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
        inGameMenuScreen.getStage().getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        camera.setToOrtho(false, width / 2, height / 2);
        //viewport.update();
    }

    @Override
    public void pause() {
        if (state == GAME_RUNNING) {
            state = GAME_PAUSED;
            inGameMenuScreen.createBlurredBackground();
        }
    }

    @Override
    public void resume() {
        if (state == GAME_PAUSED) state = GAME_RUNNING;
    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        gameUI.dispose();
        inGameMenuScreen.dispose();
        frameRate.dispose();
    }

    public void appendEntity(EntityDynamic entity) {
        entities.add(entity);
    }

    public float getCameraRotation() {
        float camAngle = -(float) Math.atan2(camera.up.x, camera.up.y) * MathUtils.radiansToDegrees + 180;
        return camAngle;
    }

    private float angleDifference(float angle1, float angle2) {
        float diff = (angle2 - angle1 + 180) % 360 - 180;
        return diff < -180 ? diff + 360 : diff;
    }

}
