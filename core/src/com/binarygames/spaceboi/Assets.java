package com.binarygames.spaceboi;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    // Locations to all game assets. Use these addresses when creating new objects from assets.
    // This could be divided up into subdivisions to make access clearer.

    // Menu assets

    // Images
    public static final String MENU_BACKGROUND_IMAGE = "menu/space_boi_menu_placeholder.jpg";

    // Audio
    public static final String MENU_BACKGROUND_MUSIC = "menu/Flume - Helix.mp3";

    // Data
    public static final String MENU_UI_SKIN = "menu/uiskin.json";

    /*
        Entity assets
     */

    // Planets
    public static final String PLANET_MOON = "game/entities/planets/moon.png";
    public static final String LAUNCH_PAD = "game/entities/planets/launch_pad.png";

    // Player
    public static final String PLAYER = "game/entities/player/norrland.jpg";
    public static final String PLAYER_WALK_ANIMATION = "game/entities/player/walk_animation/walk_animation_atlas.png";
    public static final String PLAYER_PICKUP_HEALTH = "game/entities/player/health_pickup.mp3"; // https://freesound.org/people/SilverIllusionist/sounds/411172/
    public static final String PLAYER_FOOTSTEP = "game/entities/player/footstep.wav"; // https://freesound.org/people/Yoyodaman234/sounds/166507/

    // Weapons
    public static final String WEAPON_SHOTGUN_SHOT = "game/entities/weapons/shotgun/shot.mp3"; // Free Firearms library
    public static final String WEAPON_SHOTGUN_PUMP = "game/entities/weapons/shotgun/pump.mp3"; // Free Firearms library
    public static final String WEAPON_SHOTGUN_SHOT_PUMP = "game/entities/weapons/shotgun/shot_pump.wav"; // Free Firearms library
    public static final String WEAPON_SHOTGUN_RELOAD = "game/entities/weapons/shotgun/reload.wav"; // https://freesound.org/people/lensflare8642/sounds/145209/
    public static final String WEAPON_MACHINEGUN_SHOT = "game/entities/weapons/machinegun/Ratatata.mp3";
    public static final String WEAPON_GRENADELAUNCHER_EXPLOSION = "game/entities/weapons/grenadelauncher/seismic_charge.wav"; // Stolen from Mickey Mouse
    public static final String WEAPON_GRENADELAUNCHER_SHOT = "game/entities/weapons/grenadelauncher/shot.mp3"; // https://freesound.org/people/LeMudCrab/sounds/163458/

    /*
        Environment
     */

    // TODO

    /*
        Particles
     */

    public static final String PARTICLE_ATLAS = "game/particles/particle_effects.atlas";

    // Blood
    public static final String PARTICLE_BLOOD = "game/particles/blood.p";

    /*
        UI
     */

    public static final String UI_HEALTH_ICON = "game/ui/health_icon.png";
    public static final String UI_SILENCER_ICON = "game/ui/silencer_icon.jpg";
    public static final String UI_EXPERIENCE_ICON = "game/ui/experience_icon.jpg";

    private AssetManager assetManager;

    public Assets(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void loadMenuAssets() {
        /*
            General Menu Assets
         */

        assetManager.load(MENU_BACKGROUND_IMAGE, Texture.class);

        assetManager.load(MENU_UI_SKIN, Skin.class);

        assetManager.load(MENU_BACKGROUND_MUSIC, Music.class);
    }

    public void loadGameAssets() {
        /*
            Entities
         */

        // Planets
        assetManager.load(PLANET_MOON, Texture.class);
        assetManager.load(LAUNCH_PAD, Texture.class);

        // Player
        assetManager.load(PLAYER, Texture.class);
        assetManager.load(PLAYER_WALK_ANIMATION, Texture.class);
        assetManager.load(PLAYER_PICKUP_HEALTH, Sound.class);
        assetManager.load(PLAYER_FOOTSTEP, Sound.class);

        // Weapons
        assetManager.load(WEAPON_SHOTGUN_SHOT, Sound.class);
        assetManager.load(WEAPON_SHOTGUN_PUMP, Sound.class);
        assetManager.load(WEAPON_SHOTGUN_SHOT_PUMP, Sound.class);
        assetManager.load(WEAPON_SHOTGUN_RELOAD, Sound.class);
        assetManager.load(WEAPON_MACHINEGUN_SHOT, Sound.class);
        assetManager.load(WEAPON_GRENADELAUNCHER_EXPLOSION, Sound.class);
        assetManager.load(WEAPON_GRENADELAUNCHER_SHOT, Sound.class);

        /*
            Environment
         */

        // TODO

        /*
            Particles
         */

        assetManager.load(PARTICLE_ATLAS, TextureAtlas.class);

        // Blood
        assetManager.load(PARTICLE_BLOOD, ParticleEffect.class);

        /*
            UI
         */

        assetManager.load(UI_HEALTH_ICON, Texture.class);
        assetManager.load(UI_SILENCER_ICON, Texture.class);
        assetManager.load(UI_EXPERIENCE_ICON, Texture.class);
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
