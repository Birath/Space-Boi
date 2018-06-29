package com.binarygames.spaceboi;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    private AssetManager assetManager;

    public Assets(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void loadMenuAssets() {
        /*
            General Menu Assets
         */

        assetManager.load("menu/space_boi_menu_placeholder.jpg", Texture.class);

        assetManager.load("menu/uiskin.json", Skin.class);
    }

    public void loadGameAssets() {
        /*
            Entities
         */

        // Planets
        assetManager.load("game/entities/planets/moon.png", Texture.class);

        // Player
        assetManager.load("game/entities/player/playerShip.png", Texture.class);

        /*
            Environment
         */

        // TODO

        /*
            Particles
         */

        assetManager.load("game/particles/particle_effects.atlas", TextureAtlas.class);

        // Blood
        assetManager.load("game/particles/blood.p", ParticleEffect.class);

        /*
            UI
         */

        assetManager.load("game/ui/health_icon.png", Texture.class);
    }

    /*
        Ok hello this is code for non-hardcoded loading of files

        FileHandle fileHandle;
        fileHandle = Gdx.files.internal("game/entities/planets/");
        for (FileHandle entry : fileHandle.list()) {
            if (entry.name().endsWith(".png")) {
                assetManager.load(fileHandle.path() + "/" + entry.name(), Texture.class);
            }
        }
     */

}
