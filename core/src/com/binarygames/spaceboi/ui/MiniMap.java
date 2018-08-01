package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.WorldGenerator;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public class MiniMap {

    private static final int SCALE = 4;
    private static final int MINIMAP_WIDHT = Gdx.graphics.getWidth() / 4;
    private static final int MINIMAP_HEIGHT = Gdx.graphics.getHeight() / 4;
    private static final int MINIMAP_LEFT = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4;
    private static final int MINIMAP_BOTTOM = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4;
    private static final int MINIMAP_RIGHT = Gdx.graphics.getWidth();
    private static final int MINIMAP_TOP = Gdx.graphics.getHeight();

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameWorld gameWorld;
    private Player player;

    private Pixmap pixmap;
    private Sprite minimap;
    private FrameBuffer fbo;

    private ScissorStack scissorStack;
    private Rectangle scissors;
    private Rectangle clipBounds;

    private Sprite planetMarker;

    private ShapeRenderer renderer;

    public MiniMap(SpriteBatch batch, GameWorld gameWorld) {
        this.batch = batch;
        this.gameWorld = gameWorld;
        this.player = gameWorld.getPlayer();

        renderer = new ShapeRenderer();
        scissorStack = new ScissorStack();
        scissors = new Rectangle();
        clipBounds = new Rectangle(MINIMAP_LEFT, MINIMAP_BOTTOM, MINIMAP_WIDHT, MINIMAP_HEIGHT);

        planetMarker = new Sprite(gameWorld.getGame().getAssetManager().get(Assets.MARKER_PLANET, Texture.class));

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        //camera.position.x = player.getBody().getPosition().x;
        //camera.position.y = player.getBody().getPosition().y;
        //camera.position.z = 1;
        //camera.zoom = SCALE;
        //camera.translate(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        //camera.update();
        generateMiniMap();
    }

    private void generateMiniMap() {
        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int) gameWorld.getWorldGenerator().getRadOfWorld(), (int) gameWorld.getWorldGenerator().getRadOfWorld(), false);
        pixmap = new Pixmap(MINIMAP_RIGHT, MINIMAP_HEIGHT, Pixmap.Format.RGBA8888);
        int center_of_texture = (int) gameWorld.getWorldGenerator().getRadOfWorld() / 2;
        fbo.begin();
        gameWorld.getGame().getBatch().begin();

        for (final Planet planet : gameWorld.getPlanets()) {
            //planetMarker.scale(planet.getRadius()/planetMarker.getHeight());
            Gdx.app.log("Planetcoords", String.valueOf(planet.getBody().getPosition().x*PPM) + ", " + String.valueOf(planet.getBody().getPosition().y*PPM));
            planetMarker.setSize(20, 20);
            //planetMarker.setOrigin(planet.getBody().getPosition().x * PPM + center_of_texture, planet.getBody().getPosition().y * PPM + center_of_texture);
            //planetMarker.setPosition(planet.getBody().getPosition().x * PPM + center_of_texture, planet.getBody().getPosition().y * PPM + center_of_texture);

            planetMarker.setOrigin(planet.getBody().getPosition().x * PPM + center_of_texture, planet.getBody().getPosition().y * PPM + center_of_texture);
            planetMarker.setPosition(planet.getBody().getPosition().x * PPM + center_of_texture, planet.getBody().getPosition().y * PPM + center_of_texture);

            planetMarker.draw(gameWorld.getGame().getBatch());
            //batch.draw(planetMarker, planet.getBody().getPosition().x*PPM + center_of_texture, planet.getBody().getPosition().y*PPM + center_of_texture);
            //planet.getBody().getPosition();
            //planet.getRadius();
        }
        gameWorld.getGame().getBatch().end();
        fbo.end();
        minimap = new Sprite(fbo.getColorBufferTexture());
        minimap.scale(Gdx.graphics.getWidth() / fbo.getWidth());
        minimap.setOrigin(MINIMAP_RIGHT - MINIMAP_WIDHT / 2, MINIMAP_TOP - MINIMAP_HEIGHT / 2);
        minimap.setPosition(MINIMAP_LEFT, MINIMAP_BOTTOM);
        minimap.setRotation(player.getPlayerAngle());
    }

    public void render() {
        /*
        batchMiniMap.begin();
        batchMiniMap.draw(playerTexture, player.getX() - (WIDTH/2)*SCALE + ((MINIMAP_RIGHT-MINIMAP_LEFT)/2)*SCALE, player.getY() + (HEIGHT/2)*SCALE - ((MINIMAP_TOP-MINIMAP_BOTTOM)/2)*SCALE, MARKER_SIZE, MARKER_SIZE);
        batchMiniMap.end();*/
        //gameWorld.getGame().getBatch().setProjectionMatrix(camera.combined);
        boolean isScissorsPushed = false;
        gameWorld.getGame().getBatch().begin();
        gameWorld.getGame().getBatch().flush();
        ScissorStack.calculateScissors(gameWorld.getCamera(), gameWorld.getGame().getBatch().getTransformMatrix(), clipBounds, scissors);
        if (ScissorStack.pushScissors(scissors)) {
            isScissorsPushed = true;
        }
        /*
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        renderer.circle(Gdx.graphics.getWidth()-MINIMAP_WIDHT/2, Gdx.graphics.getHeight()-MINIMAP_HEIGHT/2, 10);

        Gdx.app.log("MiniMap Debug", "Player pos:" + (String.valueOf(player.getBody().getPosition().x*PPM) + "," + (String.valueOf(player.getBody().getPosition().y*PPM))));

        for (final Planet planet : gameWorld.getPlanets()) {
            float x_difference = Math.abs(player.getBody().getPosition().x*PPM - planet.getBody().getPosition().x*PPM);
            float y_difference = Math.abs(player.getBody().getPosition().y*PPM - planet.getBody().getPosition().y*PPM);

            if ((x_difference < (1920/2)*SCALE) && (y_difference < (1080/2)*SCALE)) {
                Gdx.app.log("MiniMap Debug", "Planet pos:" + (String.valueOf(planet.getBody().getPosition().x*PPM) + "," + (String.valueOf(planet.getBody().getPosition().y*PPM))));
                renderer.setColor(Color.CYAN);
                renderer.circle(x_difference*PPM - (MINIMAP_WIDHT/2)*SCALE + ((MINIMAP_RIGHT-MINIMAP_LEFT)/2)*SCALE, y_difference*PPM - (MINIMAP_HEIGHT/2)*SCALE + ((MINIMAP_TOP-MINIMAP_BOTTOM)/2)*SCALE, planet.getRadius()*0.01f);
            }
            //planet.getBody().getPosition();
            //planet.getRadius();
        }
        renderer.flush();
        renderer.end();
        */

        minimap.draw(gameWorld.getGame().getBatch());
        //gameWorld.getGame().getBatch().flush();
        //batch.draw(minimap, MINIMAP_LEFT, MINIMAP_BOTTOM, MINIMAP_RIGHT-MINIMAP_WIDHT/2, MINIMAP_TOP-MINIMAP_HEIGHT/2, MINIMAP_WIDHT, MINIMAP_HEIGHT, 1, 1, player.getBody().getAngle());
        gameWorld.getGame().getBatch().end();
        if (isScissorsPushed) {
            ScissorStack.popScissors();
        }

        //Debug minimapgeneration

        gameWorld.getGame().getBatch().begin();
        minimap.draw(gameWorld.getGame().getBatch());
        gameWorld.getGame().getBatch().end();

    }

    public Sprite getMinimap() {
        return minimap;
    }
}
