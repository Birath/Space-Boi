package com.binarygames.spaceboi;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    // Locations to all game assets. Use these addresses when creating new objects from assets.
    // This could be divided up into subdivisions to make access clearer.

    // Menu assets

    // Images
    public static final String MENU_BACKGROUND_IMAGE = "menu/space_boi_menu_placeholder.jpg";
    public static final String WIN_BACKGROUND = "menu/win_screen/win_background.png";

    // Audio


    // Data
    public static final String MENU_UI_SKIN = "menu/uiskin.json";


    public static final String HEALTHBAR_SKIN = "menu/healthbar.json";

    // Fonts
    public static final String MAIN_FONT = "menu/JosefinSans-SemiBold.ttf";
    public static final String TITLE_FONT = "title-font.ttf";
    public static final String LABEL_FONT = "label-font.ttf";

    // Font parameters


    /*
        Entity assets
     */

    // Enemies

    // Flyging ship
    public static final String MISSILE_LAUNCH = "game/entities/enemies/flying_ship/missile_launch.wav"; // https://freesound.org/people/Audionautics/sounds/171655/
    public static final String MISSILE_HIT = "game/entities/enemies/flying_ship/missile_hit.wav"; // https://freesound.org/people/studiomandragore/sounds/401628/
    public static final String RICOCHET1 = "game/entities/enemies/flying_ship/ricochet1.wav"; // https://freesound.org/people/aust_paul/sounds/30932/
    public static final String RICOCHET2 = "game/entities/enemies/flying_ship/ricochet2.wav"; // https://freesound.org/people/aust_paul/sounds/30932/
    public static final String RICOCHET3 = "game/entities/enemies/flying_ship/ricochet3.wav"; // https://freesound.org/people/aust_paul/sounds/30932/
    public static final String RICOCHET4 = "game/entities/enemies/flying_ship/ricochet4.wav"; // https://freesound.org/people/aust_paul/sounds/30932/
    public static final String WEAPON_MISSILE = "game/entities/enemies/flying_ship/missile.png";
    public static final String FLYINGSHIP = "game/entities/enemies/flying_ship/flyingship.png";
    public static final String FLYINGSHIP_CANNON = "game/entities/enemies/flying_ship/flyingship_cannon.png";

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
    public static final String PIRATE = "game/entities/enemies/pirate/pirate.png";
    public static final String PIRATE_WALK_ANIMATION = "game/entities/enemies/pirate/shooter_running.png";
    public static final String SHOOTER_WEAPON = "game/entities/enemies/pirate/shooter_arms.png";

    // End boss
    public static final String END_BOSS1 = "game/entities/enemies/end_boss/Bring_me_his_corpse.wav";
    public static final String END_BOSS_WALK_ANIMATION = "game/entities/enemies/end_boss/finalboss_spritesheet.png";
    public static final String END_BOSS_CHARGE_ANIMATION = "game/entities/enemies/end_boss/finalboss_charge_spritesheet.png";
    public static final String END_BOSS_PREPCHARGE_ANIMATION = "game/entities/enemies/end_boss/finalboss_preparecharge_spritesheet.png";
    public static final String END_BOSS_SHOOT_ANIMATION = "game/entities/enemies/end_boss/finalboss_shooting_spritesheet.png";
    public static final String END_BOSS_CLANK = "game/entities/enemies/end_boss/metalic_clank.mp3"; // https://freesound.org/people/soundmary/sounds/194996/
    public static final String END_BOSS_SHOOT_SOUND = "game/entities/enemies/end_boss/cannon_shot.ogg"; // https://freesound.org/people/SamsterBirdies/sounds/467883/

    // Dog
    public static final String DOG1 = "game/entities/enemies/dog/dog4.wav"; // https://www.freesoundeffects.com/free-track/dog4-89462/
    public static final String DOG2 = "game/entities/enemies/dog/dog2_weird.wav"; // https://www.freesoundeffects.com/free-track/dog2-89460/
    public static final String DOG3 = "game/entities/enemies/dog/dog-2.wav"; // https://www.freesoundeffects.com/free-track/dog-2-89458/
    public static final String DOG = "game/entities/enemies/dog/enemy_dog.png";
    public static final String DOG_RUNNING_ANIMATION = "game/entities/enemies/dog/dog_running_spritesheet.png";
    public static final String DOG_ATTACKING_ANIMATION = "game/entities/enemies/dog/dog_attacking_spritesheet.png";

    // Planets
    public static final String PLANET_MOON = "game/entities/planets/moon.png";
    public static final String PLANET1 = "game/entities/planets/planet1.png";
    public static final String PLANET2 = "game/entities/planets/planet2.png";
    public static final String PLANET3 = "game/entities/planets/planet3.png";
    public static final String PLANET4 = "game/entities/planets/planet4.png";
    public static final String PLANET5 = "game/entities/planets/planet5.png";
    public static final String PLANET6 = "game/entities/planets/planet6.png";
    public static final String ATMOSPHERE = "game/entities/planets/atmosphere.png";
    public static final String LAUNCH_PAD = "game/entities/planets/launch_pad.png";
    public static final String LAUNCH_PAD_SOUND = "game/entities/planets/launch_pad_sound.wav"; // https://freesound.org/people/Kinoton/sounds/351256/

    // Player
    public static final String PLAYER = "game/entities/player/norrland.jpg";
    //public static final String PLAYER_WALK_ANIMATION = "game/entities/player/walk_animation/walk_animation_atlas.png";
    public static final String PLAYER_RUNNING_ANIMATION = "game/entities/player/walk_animation/player_running_spritesheet.png";
    public static final String PLAYER_PICKUP_HEALTH = "game/entities/player/health_pickup.mp3"; // https://freesound.org/people/SilverIllusionist/sounds/411172/
    public static final String PLAYER_FOOTSTEP = "game/entities/player/footstep.wav"; // https://freesound.org/people/Yoyodaman234/sounds/166507/

    // Weapons
    public static final String WEAPON_SHOTGUN_SHOT = "game/entities/weapons/shotgun/shot.mp3"; // Free Firearms library
    public static final String WEAPON_SHOTGUN_PUMP = "game/entities/weapons/shotgun/pump.mp3"; // Free Firearms library
    public static final String WEAPON_SHOTGUN_SHOT_PUMP = "game/entities/weapons/shotgun/shot_pump.wav"; // Free Firearms library
    public static final String WEAPON_SHOTGUN_RELOAD = "game/entities/weapons/shotgun/reload.wav"; // https://freesound.org/people/lensflare8642/sounds/145209/
    public static final String WEAPON_SHOTGUN = "game/entities/weapons/shotgun/shotgun_w_arms.png"; //https://pixabay.com/en/shotgun-gun-rifle-weapon-hunter-48282/
    // public static final String WEAPON_MACHINEGUN_SHOT = "game/entities/weapons/machinegun/Ratatata.mp3";
    public static final String WEAPON_MACHINEGUN_SHOT = "game/entities/weapons/machinegun/shot.wav";
    public static final String WEAPON_MACHINEGUN_RELOAD = "game/entities/weapons/machinegun/reload.wav"; // https://freesound.org/people/davdud101/sounds/145475/
    public static final String WEAPON_MACHINEGUN = "game/entities/weapons/machinegun/ak-47.png"; // https://vector.me/browse/282635/ak47_assault_rifle
    public static final String WEAPON_MACHINEGUN_ARMS = "game/entities/weapons/machinegun/ak-47_w_arms.png";

    public static final String WEAPON_BULLET = "game/entities/weapons/enemy_bullet.png";
    public static final String PLAYER_BULLET = "game/entities/weapons/player_bullet.png";

    public static final String WEAPON_GRENADELAUNCHER_EXPLOSION = "game/entities/weapons/grenadelauncher/explosion.wav"; // https://freesound.org/people/eardeer/sounds/390182/
    public static final String WEAPON_GRENADELAUNCHER_SHOT = "game/entities/weapons/grenadelauncher/shot.mp3"; // https://freesound.org/people/LeMudCrab/sounds/163458/
    public static final String WEAPON_GRENADELAUNCHER_RELOAD = "game/entities/weapons/grenadelauncher/reload.wav"; // https://freesound.org/people/JacquelineKohrs150067/sounds/326677/
    public static final String WEAPON_GRENADE = "game/entities/weapons/grenadelauncher/grenade.png";
    public static final String WEAPON_GRENADELAUNCHER = "game/entities/weapons/grenadelauncher/grenade_launcher_w_arms.png";

    /*
        Environment
     */
    //Audio
    public static final String BACKGROUND_MUSIC = "game/enviroment/sound_track.mp3";

    public static final String ATTACHMENT_PICK_1 = "game/enviroment/attachmentpickup1.wav"; // https://freesound.org/people/rhodesmas/sounds/320654/
    public static final String ATTACHMENT_PICK_2 = "game/enviroment/attachmentpickup2.wav"; // https://freesound.org/people/Dpoggioli/sounds/213607/
    public static final String WORLD_BACKGROUND = "game/enviroment/world_background2.png"; // http://wwwtyro.github.io/procedural.js/space/

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
    public static final String UI_ASSAULT_RIFLE = "game/ui/assault.png";
    public static final String UI_ASSAULT_RIFLE_AMMO = "game/ui/assault_ammo.png";
    public static final String UI_GRENADE_LAUNCHER = "game/ui/grenade_launcher.png";
    public static final String UI_GRENADE_LAUNCHER_AMMO = "game/ui/grenade_launcher_ammo.png";
    public static final String UI_HEALTH_BAR = "game/ui/health.png";
    public static final String UI_OXYGEN_BAR = "game/ui/oxygen.png";
    public static final String UI_SHOTGUN = "game/ui/shotgun.png";
    public static final String UI_SHOTGUN_AMMO = "game/ui/shotgun_ammo.png";

    public static final String UI_EMPTY_ATTACHMENT = "game/ui/empty_attachment.png";
    public static final String UI_COMPASS_ARROW = "game/ui/compass_arrow.png";
    public static final String UI_COMPASS_OUTLINE = "game/ui/compass_outline.png";

    // PICKUPS
    public static final String PICKUP_HEALTH = "game/entities/pickups/beercanhealth.png";
    public static final String ARMOR_PIERCING = "game/entities/pickups/armor_piercing_icon.png";
    public static final String BIO_DAMAGE_ICON = "game/entities/pickups/bio_damage_icon.png";
    public static final String XP_BOOST_ICON = "game/entities/pickups/xp_boost_icon.png";
    public static final String SLOW_ICON = "game/entities/pickups/slow_icon.png";
    public static final String GLASS_CANNON_ICON = "game/entities/pickups/glass_cannon_icon.png";
    public static final String SILENCER_ICON = "game/entities/pickups/silencer_icon.png";
    public static final String RECOIL_ICON = "game/entities/pickups/recoil_icon.png";
    public static final String LIFE_STEAL_ICON = "game/entities/pickups/life_steal_icon.png";

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

        assetManager.load(WIN_BACKGROUND, Texture.class);

        assetManager.load(MENU_UI_SKIN, Skin.class);

        assetManager.load(HEALTHBAR_SKIN, Skin.class);

        // Load fonts
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter titleFontParams = new FreeTypeFontLoaderParameter();

        titleFontParams.fontFileName = MAIN_FONT;
        titleFontParams.fontParameters.size = 50;
        assetManager.load(TITLE_FONT, BitmapFont.class, titleFontParams);
        FreeTypeFontLoaderParameter labelFontParams = new FreeTypeFontLoaderParameter();

        labelFontParams.fontFileName = MAIN_FONT;
        labelFontParams.fontParameters.size = 35;
        assetManager.load(LABEL_FONT, BitmapFont.class, labelFontParams);
    }

    public void loadGameAssets() {


        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.genMipMaps = true;
        textureParameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        textureParameter.magFilter = Texture.TextureFilter.MipMapLinearLinear;
        //textureParameter.minFilter = Texture.TextureFilter.Linear;

        /*
            Entities
         */

        // Enemies

        // Flyging ship
        assetManager.load(MISSILE_LAUNCH, Sound.class);
        assetManager.load(MISSILE_HIT, Sound.class);
        assetManager.load(RICOCHET1, Sound.class);
        assetManager.load(RICOCHET2, Sound.class);
        assetManager.load(RICOCHET3, Sound.class);
        assetManager.load(RICOCHET4, Sound.class);
        assetManager.load(WEAPON_MISSILE, Texture.class, textureParameter);
        assetManager.load(FLYINGSHIP_CANNON, Texture.class, textureParameter);
        assetManager.load(FLYINGSHIP, Texture.class, textureParameter);

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
        assetManager.load(PIRATE, Texture.class, textureParameter);
        assetManager.load(PIRATE_WALK_ANIMATION, Texture.class, textureParameter);
        assetManager.load(SHOOTER_WEAPON, Texture.class, textureParameter);

        //End boss
        assetManager.load(END_BOSS1, Sound.class);
        assetManager.load(END_BOSS_WALK_ANIMATION, Texture.class, textureParameter);
        assetManager.load(END_BOSS_CHARGE_ANIMATION, Texture.class, textureParameter);
        assetManager.load(END_BOSS_PREPCHARGE_ANIMATION, Texture.class, textureParameter);
        assetManager.load(END_BOSS_SHOOT_ANIMATION, Texture.class, textureParameter);
        assetManager.load(END_BOSS_CLANK, Sound.class);
        assetManager.load(END_BOSS_SHOOT_SOUND, Sound.class);

        // Dog
        assetManager.load(DOG1, Sound.class);
        assetManager.load(DOG2, Sound.class);
        assetManager.load(DOG3, Sound.class);
        assetManager.load(DOG, Texture.class, textureParameter);
        assetManager.load(DOG_RUNNING_ANIMATION, Texture.class, textureParameter);
        assetManager.load(DOG_ATTACKING_ANIMATION, Texture.class, textureParameter);

        // Planets
        assetManager.load(PLANET_MOON, Texture.class, textureParameter);
        assetManager.load(PLANET1, Texture.class, textureParameter);
        assetManager.load(PLANET2, Texture.class, textureParameter);
        assetManager.load(PLANET3, Texture.class, textureParameter);
        assetManager.load(PLANET4, Texture.class, textureParameter);
        assetManager.load(PLANET5, Texture.class, textureParameter);
        assetManager.load(PLANET6, Texture.class, textureParameter);
        assetManager.load(ATMOSPHERE, Texture.class, textureParameter);
        assetManager.load(LAUNCH_PAD, Texture.class, textureParameter);
        assetManager.load(LAUNCH_PAD_SOUND, Sound.class);

        // Player
        assetManager.load(PLAYER, Texture.class, textureParameter);
        assetManager.load(PLAYER_RUNNING_ANIMATION, Texture.class, textureParameter);
        assetManager.load(PLAYER_PICKUP_HEALTH, Sound.class);
        assetManager.load(PLAYER_FOOTSTEP, Sound.class);

        // Weapons
        assetManager.load(WEAPON_SHOTGUN_SHOT, Sound.class);
        assetManager.load(WEAPON_SHOTGUN_PUMP, Sound.class);
        assetManager.load(WEAPON_SHOTGUN_SHOT_PUMP, Sound.class);
        assetManager.load(WEAPON_SHOTGUN_RELOAD, Sound.class);
        assetManager.load(WEAPON_SHOTGUN, Texture.class);

        assetManager.load(WEAPON_MACHINEGUN_SHOT, Sound.class);
        assetManager.load(WEAPON_MACHINEGUN_RELOAD, Sound.class);
        assetManager.load(WEAPON_MACHINEGUN, Texture.class);
        assetManager.load(WEAPON_MACHINEGUN_ARMS, Texture.class);

        assetManager.load(WEAPON_BULLET, Texture.class, textureParameter);
        assetManager.load(PLAYER_BULLET, Texture.class, textureParameter);


        assetManager.load(WEAPON_GRENADELAUNCHER_EXPLOSION, Sound.class);
        assetManager.load(WEAPON_GRENADELAUNCHER_SHOT, Sound.class);
        assetManager.load(WEAPON_GRENADELAUNCHER_RELOAD, Sound.class);
        assetManager.load(WEAPON_GRENADE, Texture.class, textureParameter);
        assetManager.load(WEAPON_GRENADELAUNCHER, Texture.class, textureParameter);

        /*
            Environment
         */
        //Audio
        assetManager.load(BACKGROUND_MUSIC, Music.class);

        assetManager.load(ATTACHMENT_PICK_1, Sound.class);
        assetManager.load(ATTACHMENT_PICK_2, Sound.class);
        assetManager.load(WORLD_BACKGROUND, Texture.class);

        /*
            Particles
         */
        assetManager.load(PARTICLE_ATLAS, TextureAtlas.class);

        assetManager.load(PARTICLE_BLOOD, ParticleEffect.class);
        assetManager.load(PARTICLE_FIRE, ParticleEffect.class);

        /*
            UI
         */
        assetManager.load(UI_ASSAULT_RIFLE, Texture.class, textureParameter);
        assetManager.load(UI_ASSAULT_RIFLE_AMMO, Texture.class, textureParameter);
        assetManager.load(UI_GRENADE_LAUNCHER, Texture.class, textureParameter);
        assetManager.load(UI_GRENADE_LAUNCHER_AMMO, Texture.class, textureParameter);
        assetManager.load(UI_HEALTH_BAR, Texture.class, textureParameter);
        assetManager.load(UI_OXYGEN_BAR, Texture.class, textureParameter);
        assetManager.load(UI_SHOTGUN, Texture.class, textureParameter);
        assetManager.load(UI_SHOTGUN_AMMO, Texture.class, textureParameter);

        assetManager.load(UI_EMPTY_ATTACHMENT, Texture.class, textureParameter);

        TextureParameter compassArrowParamter = new TextureParameter();
        compassArrowParamter.minFilter = TextureFilter.Linear;
        compassArrowParamter.magFilter = TextureFilter.Linear;
        assetManager.load(UI_COMPASS_ARROW, Texture.class, compassArrowParamter);

        assetManager.load(UI_COMPASS_OUTLINE, Texture.class, textureParameter);

        /*
            PICKUPS
        */
        assetManager.load(PICKUP_HEALTH, Texture.class, textureParameter);
        assetManager.load(ARMOR_PIERCING, Texture.class, textureParameter);
        assetManager.load(BIO_DAMAGE_ICON, Texture.class, textureParameter);
        assetManager.load(XP_BOOST_ICON, Texture.class, textureParameter);
        assetManager.load(SLOW_ICON, Texture.class, textureParameter);
        assetManager.load(GLASS_CANNON_ICON, Texture.class, textureParameter);
        assetManager.load(SILENCER_ICON, Texture.class, textureParameter);
        assetManager.load(RECOIL_ICON, Texture.class, textureParameter);
        assetManager.load(LIFE_STEAL_ICON, Texture.class, textureParameter);

        // Minimap markers
        assetManager.load(MARKER_PLANET, Texture.class, textureParameter);
    }

    /*
        Ok hello this is code for non-hardcoded loading of files

        FileHandle fileHandle;
        fileHandle = Gdx.files.internal("game/entities/planets/");
        for (FileHandle entry : fileHandle.list()) {
            if (entry.name().endsWith(".png")) {
                assetManager.load(fileHandle.path() + "/" + entry.name(), Texture.class, textureParameter);
            }
        }
     */

}
