package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public class MiniMap {

    private static final int SCALE = 4;
    private static final int MINIMAP_WIDHT = Gdx.graphics.getWidth()/4;
    private static final int MINIMAP_HEIGHT = Gdx.graphics.getHeight()/4;
    private static final int MINIMAP_X = Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/4;
    private static final int MINIMAP_Y = Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameWorld gameWorld;
    private Player player;

    private ShapeRenderer renderer;

    public MiniMap(SpriteBatch batch, GameWorld gameWorld) {
        this.batch = batch;
        this.gameWorld = gameWorld;
        this.player = gameWorld.getPlayer();

        renderer = new ShapeRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        camera.position.x = player.getBody().getPosition().x;
        camera.position.y = player.getBody().getPosition().y;
        camera.position.z = 1;
        camera.zoom = SCALE;
        //camera.translate(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        camera.update();
    }

    private void generateMiniMap() {
        //Pixmap pixmap = new Pixmap();
        for (final Planet planet : gameWorld.getPlanets()) {

            //planet.getBody().getPosition();
            //planet.getRadius();
        }
    }

    public void render() {
        /*
        batchMiniMap.begin();
        batchMiniMap.draw(playerTexture, player.getX() - (WIDTH/2)*SCALE + ((MINIMAP_RIGHT-MINIMAP_LEFT)/2)*SCALE, player.getY() + (HEIGHT/2)*SCALE - ((MINIMAP_TOP-MINIMAP_BOTTOM)/2)*SCALE, MARKER_SIZE, MARKER_SIZE);
        batchMiniMap.end();*/
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        renderer.circle(Gdx.graphics.getWidth()-MINIMAP_WIDHT/2, Gdx.graphics.getHeight()-MINIMAP_HEIGHT/2, 10);

        for (final Planet planet : gameWorld.getPlanets()) {
            float x_difference = Math.abs(player.getBody().getPosition().x*PPM - planet.getBody().getPosition().x*PPM);
            float y_difference = Math.abs(player.getBody().getPosition().y*PPM - planet.getBody().getPosition().y*PPM);

            if ((x_difference < 1920*2) && (y_difference < 1080*2)) {
                renderer.setColor(Color.CYAN);
                renderer.circle(planet.getBody().getPosition().x*PPM, planet.getBody().getPosition().y*PPM, planet.getRadius()*0.01f);
            }
            //planet.getBody().getPosition();
            //planet.getRadius();
        }
        renderer.flush();
        renderer.end();
        batch.end();
    }
}
