package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.input.PlayerInputProcessor;
import com.binarygames.spaceboi.ui.FrameRate;
import com.binarygames.spaceboi.ui.GameUI;
import com.binarygames.spaceboi.ui.InventoryUI;
import com.binarygames.spaceboi.ui.MiniMap;
import com.binarygames.spaceboi.util.Console;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public class GameScreen implements Screen {

    public static final int CAMERA_LERP_ACCELERATION = 25;
    private CameraRotator cameraRotator;
    private SpaceBoi game;

    private static final int GAME_RUNNING = 0;
    private static final int GAME_PAUSED = 1;
    public int state;

    private InGameMenuScreen inGameMenuScreen;

    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Viewport viewport;
    private World world;

    private GameUI gameUI;

    private MiniMap miniMap;

    private Console console;

    private InventoryUI inventoryUI;

    private GameWorld gameWorld;

    private Player player;
    private InputMultiplexer multiplexer;

    private FrameRate frameRate;

    private float lastAngle;

    private boolean interpolateRotation = false;
    private final int interpolateCount = 30;
    private int currentInterpolateCount;

    private final float whenToInterpolate = 30;
    private float angleToInterpolate;


    private boolean debugRendererIsEnabled = false;
    private float startCameraAngle = 0;

    public GameScreen(SpaceBoi game) {
        //Framerate
        frameRate = new FrameRate();

        this.game = game;

        //Music
        game.getMusicManager().play(Assets.BACKGROUND_MUSIC);

        // Mysko skit för att få scalingen av debuggkameran rätt
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        cameraRotator = new CameraRotator(camera);
        //box2dCamera = new OrthographicCamera(Gdx.graphics.getWidth() * WORLD_TO_BOX, Gdx.graphics.getHeight() * WORLD_TO_BOX);
        // Vet inte riktigt vad jag håller på med här, men det verkar funka // Björn
        //viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH * WORLD_TO_BOX, SpaceBoi.VIRTUAL_HEIGHT * WORLD_TO_BOX, camera);
        //viewport.apply();


        //camera = new OrthographicCamera();

        inGameMenuScreen = new InGameMenuScreen(this, game);
        world = new World(new Vector2(0f, 0f), true);

        gameWorld = new GameWorld(game, world, camera);
        startCameraAngle = getCameraRotation();

        //Entities:
        gameWorld.createWorld();
        player = gameWorld.getPlayer();

        //UI
        gameUI = new GameUI(game, player, gameWorld.getXp_handler());
        miniMap = new MiniMap(game.getBatch(), gameWorld);
        //gameUI.debugMinimap(miniMap.getMinimap());

        console = new Console(game, this, gameWorld);
        inventoryUI = new InventoryUI(this, gameWorld);

        PlayerInputProcessor inputProcessor = new PlayerInputProcessor(player, camera, world, gameWorld, this);
        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameUI.getStage());
        multiplexer.addProcessor(inputProcessor);

        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

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
                        /*
                        System.out.println("TOINTERPOLATE: " + angleToInterpolate);
                        System.out.println("GOALANGLE: " + angleToInterpolate + lastAngle);
                        System.out.println("CAMANGLE: " + getCameraRotation());
                        System.out.println("CURRENTANGLE: " + player.getPlayerAngle());
                        lastAngle = player.getPlayerAngle();
                        */
                    } else {
                        camera.rotate(angleToInterpolate / interpolateCount);
                        Gdx.app.log("GameScreen", "Current camera angle: " + getCameraRotation());
                        Gdx.app.log("GameScreen", "Target angle:  " + player.getPlayerAngle());
                        currentInterpolateCount++;
                    }
                } else {
                    // float angleDiff = lastAngle - player.getPlayerAngle();
                    // System.out.println("Player angle: " + player.getPlayerAngle());
                    // System.out.println("Camera angle " + getCameraRotation());
                    // System.out.println("Angle diff: " + MathUtils.ceil(player.getPlayerAngle() - getCameraRotation()));
                    // System.out.println();

                    float angleDiff = angleDifference(player.getPlayerAngle(), lastAngle);
                    float rotationAmount = getCameraRotation() - player.getPlayerAngle() + 90;
                    //float angleDiff = player.getPlayerAngle() - getCameraRotation();
                    //Gdx.app.log("GameScreen", "Camera angle: " + getCameraRotation());
                    //Gdx.app.log("GameScreen", "Player angle: " + player.getPlayerAngle() + 270);

                    //Gdx.app.log("GameScreen", "Target Angle: " + (getCameraRotation() + rotationAmount));
                    float lerpedAngle = MathUtils.lerpAngleDeg(getCameraRotation(), getCameraRotation() + rotationAmount, delta * 10);
                    //Gdx.app.log("GameScreen", "Lerped angle: " + lerpedAngle);
                    //System.out.println();
                    //if (Math.abs(rotationAmount) >= whenToInterpolate) {
                    //    cameraRotator.startRotation(getCameraRotation(), rotationAmount, 0.5f);
                    //}
                    float lerpedAmount = MathUtils.lerp(getCameraRotation(), getCameraRotation() + rotationAmount, delta);
                    //Gdx.app.log("GameScreen", "Rotate amnt:  " + rotationAmount);
                    //Gdx.app.log("GameScreen", "Lerped amnt:  " + lerpedAmount);
                    //camera.rotate(lerpedAmount);
                    // TODO: 2018-07-10 Clean up this mess
                    if (!debugRendererIsEnabled) {
                        camera.rotate(rotationAmount);
                    }


                    if (Math.abs(rotationAmount) >= whenToInterpolate) {
                        //angleToInterpolate = (Math.abs(player.getPlayerAngle() - getCameraRotation()) + 180);
                        //camera.rotate((Math.abs(player.getPlayerAngle() - getCameraRotation()) + 180));
                        //angleToInterpolate = angleDiff;
                        currentInterpolateCount = 0;
                        //interpolateRotation = true;
                        lastAngle = player.getPlayerAngle();
                        //cameraRotator.startRotation(rotationAmount, 0.5f);
                    } else {
                        lastAngle = player.getPlayerAngle();


                        //camera.rotate(angleDiff);
                        //Gdx.app.log("GameScreen", "AngleDiff: " + angleDiff);
                        //camera.rotate((Math.abs(angleDiff) + 180));
                        //Gdx.app.log("GameScreen", "CameraAngle: " + getCameraRotation());
                    }
                }

        /* float angleDiff = lastAngle - player.getPlayerAngle();
        lastAngle = player.getPlayerAngle();
        camera.rotate(angleDiff); */
                camera.position.lerp(new Vector3(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM, 0), CAMERA_LERP_ACCELERATION * delta);
                //camera.position.set(new Vector3(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM, 0));
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

        if (console.isVisible()) {
            console.update(delta);
        }
        if (inventoryUI.isVisible()) {
            inventoryUI.update(delta);
        }
    }

    private void draw() {
        switch (state) {
            case GAME_RUNNING:
                //miniMap.render();
                gameUI.draw();
                break;
            case GAME_PAUSED:
                inGameMenuScreen.draw();
                break;
        }
        if (console.isVisible()) {
            console.render();
        }
        if (inventoryUI.isVisible()) {
            inventoryUI.render();
        }
    }

    private void batchedDraw(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        if (debugRendererIsEnabled) {
            debugRenderer.render(world, camera.combined.scl(PPM));// TODO fix matching rotation on debug rendering
        }

        game.getBatch().begin();
        if (!debugRendererIsEnabled) {
            gameWorld.render(game.getBatch(), camera);
        }
        gameWorld.getParticleHandler().updateAndDrawEffects(game.getBatch(), delta);
        game.getBatch().end();
    }

    @Override
    public void show() {
        //Input processor och multiplexer, hanterar användarens input
        // Flyttades så att inputsen uppdateras efter settingsscreen
        if (state == GAME_RUNNING) {

            Gdx.input.setInputProcessor(multiplexer);
        } else {
            Gdx.input.setInputProcessor(inGameMenuScreen.getStage());
        }


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
            System.out.println("Pausing game");
            inGameMenuScreen.createBlurredBackground();
            Gdx.input.setInputProcessor(inGameMenuScreen.getStage());
        }
    }

    @Override
    public void resume() {
        if (state == GAME_PAUSED) {
            Gdx.input.setInputProcessor(multiplexer);
            state = GAME_RUNNING;
            inGameMenuScreen.reset();
        }
    }

    @Override
    public void hide() {

    }


    @Override
    public void dispose() {
        gameUI.dispose();
        inGameMenuScreen.dispose();
        frameRate.dispose();
        inventoryUI.dispose();
    }

    public float getCameraRotation() {
        float camAngle = -(float) Math.atan2(camera.up.x, camera.up.y) * MathUtils.radiansToDegrees + 180;
        return camAngle;
    }

    private float angleDifference(float angle1, float angle2) {
        float diff = (angle2 - angle1 + 180) % 360 - 180;
        return diff < -180 ? diff + 360 : diff;
    }

    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

    public Console getConsole() {
        return console;
    }

    public InventoryUI getInventoryUI() {
        return inventoryUI;
    }

    public void setDebugRendererIsEnabled(boolean debugRendererIsEnabled) {
        if (!debugRendererIsEnabled) {
            //camera.rotate(getCameraRotation() - startCameraAngle);

        }
        this.debugRendererIsEnabled = debugRendererIsEnabled;
    }


}
