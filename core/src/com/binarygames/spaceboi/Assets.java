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

    public static final String HEALTHBAR_SKIN = "menu/healthbar.json";

    /*
        Entity assets
     */

    // Enemies

    // Flyging ship
    public static final String MISSILE_LAUNCH = "game/entities/enemies/flying_ship/missile_launch.wav"; // https://freesound.org/people/Audionautics/sounds/171655/

    // Pirate
    public static final String PIRATE_ATTACK1 = "game/entities/enemies/pirate/attack/Filthy_landlubber.wav";
    public static final String PIRATE_ATTACK2 = "game/entities/enemies/pirate/attack/Get_em.wav";
    public static final String PIRATE_ATTACK3 = "game/entities/enemies/pirate/attack/Plunder.wav";
    public static final String PIRATE_ATTACK4 = "game/entities/enemies/pirate/attack/Walk_the_plank.wav";
    public static final String PIRATE_OUCH1 = "game/entities/enemies/pirate/damage_taken/Ouch1.wav";
    public static final String PIRATE_OUCH2 = "game/entities/enemies/pirate/damage_taken/Ouch2.wav";
    public static final String PIRATE_OUCH3 = "game/entities/enemies/pirate/damage_taken/Ouch3.wav";
    public static final String PIRATE_OUCH4 = "game/entities/enemies/pirate/damage_taken/Ouch4.wav";
    public static final String PIRATE_DEATH = "game/entities/enemies/pirate/death/Parleyyy.wav";

    // End boss
    public static final String END_BOSS1 = "game/entities/enemies/end_boss/Bring_me_his_corpse.wav";

    // Dog
    public static final String DOG1 = "game/entities/enemies/dog/dog4.wav"; // https://www.freesoundeffects.com/free-track/dog4-89462/
    public static final String DOG2 = "game/entities/enemies/dog/dog2_weird.wav"; // https://www.freesoundeffects.com/free-track/dog2-89460/
    public static final String DOG3 = "game/entities/enemies/dog/dog-2.wav"; // https://www.freesoundeffects.com/free-track/dog-2-89458/

    // Planets
    public static final String PLANET_MOON = "game/entities/planets/moon.png";
    public static final String LAUNCH_PAD = "game/entities/planets/launch_pad.png";
    public static final String LAUNCH_PAD_SOUND = "game/entities/planets/launch_pad_sound.wav"; // https://freesound.org/people/Kinoton/sounds/351256/

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

    // public static final String WEAPON_MACHINEGUN_SHOT = "game/entities/weapons/machinegun/Ratatata.mp3";
    public static final String WEAPON_MACHINEGUN_SHOT = "game/entities/weapons/machinegun/shot.wav";
    public static final String WEAPON_MACHINEGUN_RELOAD = "game/entities/weapons/machinegun/reload.wav"; // https://freesound.org/people/davdud101/sounds/145475/

    public static final String WEAPON_GRENADELAUNCHER_EXPLOSION = "game/entities/weapons/grenadelauncher/explosion.wav"; // https://freesound.org/people/eardeer/sounds/390182/
    public static final String WEAPON_GRENADELAUNCHER_SHOT = "game/entities/weapons/grenadelauncher/shot.mp3"; // https://freesound.org/people/LeMudCrab/sounds/163458/
    public static final String WEAPON_GRENADELAUNCHER_RELOAD = "game/entities/weapons/grenadelauncher/reload.wav"; // https://freesound.org/people/JacquelineKohrs150067/sounds/326677/

    /*
        Environment
     */
    public static final String ATTACHMENT_PICK_1 = "game/enviroment/attachmentpickup1.wav"; // https://freesound.org/people/rhodesmas/sounds/320654/
    public static final String ATTACHMENT_PICK_2 = "game/enviroment/attachmentpickup2.wav"; // https://freesound.org/people/Dpoggioli/sounds/213607/

    // TODO

    /*
        Particles
     */

    public static final String PARTICLE_ATLAS = "game/particles/particle_effects.atlas";

    // Blood
    public static final String PARTICLE_BLOOD = "game/particles/blood.p";
    public static final String PARTICLE_FIRE = "game/particles/fire.p";

    /*
        UI
     */

    public static final String UI_EMPTY_ATTACHMENT = "game/ui/empty_attachment.png";
    public static final String UI_HEALTH_ICON = "game/ui/health_icon.png";
    public static final String UI_SILENCER_ICON = "game/ui/silencer_icon.jpg";
    public static final String UI_EXPERIENCE_ICON = "game/ui/experience_icon.jpg";
    public static final String UI_RECOIL_ICON = "game/ui/recoil_icon.jpg";
    public static final String UI_GLASS_CANNON_ICON = "game/ui/glass_cannon_icon.png";
    public static final String UI_BIO_DAMAGE_ICON = "game/ui/glass_cannon_icon.png";
    public static final String UI_MECH_DAMAGE_ICON = "game/ui/glass_cannon_icon.png";
    public static final String UI_LIFESTEAL_ICON = "game/ui/glass_cannon_icon.png";
    public static final String UI_SLOW_ICON = "game/ui/glass_cannon_icon.png";

    // PICKUPS
    public static final String PICKUP_HEALTH = "game/entities/pickups/norrlands_health.png";

    // Minimap markers
    public static final String MARKER_PLANET = "game/ui/minimap_markers/planet_marker.png";

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

        assetManager.load(HEALTHBAR_SKIN, Skin.class);

        assetManager.load(MENU_BACKGROUND_MUSIC, Music.class);
    }

    public void loadGameAssets() {
        /*
            Entities
         */

        // Enemies

        // Flyging ship
        assetManager.load(MISSILE_LAUNCH, Sound.class);

        // Pirate
        assetManager.load(PIRATE_ATTACK1, Sound.class);
        assetManager.load(PIRATE_ATTACK2, Sound.class);
        assetManager.load(PIRATE_ATTACK3, Sound.class);
        assetManager.load(PIRATE_ATTACK4, Sound.class);
        assetManager.load(PIRATE_OUCH1, Sound.class);
        assetManager.load(PIRATE_OUCH2, Sound.class);
        assetManager.load(PIRATE_OUCH3, Sound.class);
        assetManager.load(PIRATE_OUCH4, Sound.class);
        assetManager.load(PIRATE_DEATH, Sound.class);

        //End boss
        assetManager.load(END_BOSS1, Sound.class);

        // Dog
        assetManager.load(DOG1, Sound.class);
        assetManager.load(DOG2, Sound.class);
        assetManager.load(DOG3, Sound.class);

        // Planets
        assetManager.load(PLANET_MOON, Texture.class);
        assetManager.load(LAUNCH_PAD, Texture.class);
        assetManager.load(LAUNCH_PAD_SOUND, Sound.class);

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
        assetManager.load(WEAPON_MACHINEGUN_RELOAD, Sound.class);

        assetManager.load(WEAPON_GRENADELAUNCHER_EXPLOSION, Sound.class);
        assetManager.load(WEAPON_GRENADELAUNCHER_SHOT, Sound.class);
        assetManager.load(WEAPON_GRENADELAUNCHER_RELOAD, Sound.class);

        /*
            Environment
         */

        assetManager.load(ATTACHMENT_PICK_1, Sound.class);
        assetManager.load(ATTACHMENT_PICK_2, Sound.class);

        // TODO

        /*
            Particles
         */

        assetManager.load(PARTICLE_ATLAS, TextureAtlas.class);

        assetManager.load(PARTICLE_BLOOD, ParticleEffect.class);
        assetManager.load(PARTICLE_FIRE, ParticleEffect.class);

        /*
            UI
         */

        assetManager.load(UI_EMPTY_ATTACHMENT, Texture.class);
        assetManager.load(UI_HEALTH_ICON, Texture.class);
        assetManager.load(UI_SILENCER_ICON, Texture.class);
        assetManager.load(UI_EXPERIENCE_ICON, Texture.class);
        assetManager.load(UI_RECOIL_ICON, Texture.class);
        assetManager.load(UI_GLASS_CANNON_ICON, Texture.class);

        /*
            PICKUPS
        */
        assetManager.load(PICKUP_HEALTH, Texture.class);

        // Minimap markers
        assetManager.load(MARKER_PLANET, Texture.class);
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
