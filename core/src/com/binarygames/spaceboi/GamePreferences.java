package com.binarygames.spaceboi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

public final class GamePreferences {

    private static final String PREF_MUSIC_VOLUME = "music.volume";
    private static final String PREF_SOUND_VOLUME = "sound.volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";

    private static final String DEBUG_PREF_GRAVITY_ENABLED = "gravity.enabled";

    private static final String PREF_FULLSCREEN_ENABLED = "fullscreen.enabled";
    private static final String PREF_VSYNC = "vsync.enabled";

    private static final String PREF_KEY_SHOOT = "key.shoot";
    private static final String PREF_KEY_SHOOT_IS_KEYBOARD = "key.shoot";

    private static final String PREF_SHOW_INTRO_TEXT = "tutorial.enabled";

    private static final String PREF_NAME = "spaceboi";
    private static final float DEFAULT_VOLUME = 0.5f;

    private SpaceBoi game;

    public GamePreferences(SpaceBoi game) {
        this.game = game;
    }

    private Preferences getPreferences() {
        return Gdx.app.getPreferences(PREF_NAME);
    }

    public float getMusicVolume() {
        return getPreferences().getFloat(PREF_MUSIC_VOLUME, DEFAULT_VOLUME);
    }

    public void setMusicVolume(float volume) {
        getPreferences().putFloat(PREF_MUSIC_VOLUME, volume);
        getPreferences().flush();

        game.getMusicManager().setVolume(volume);
    }

    public boolean isMusicEnabled() {
        return getPreferences().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPreferences().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPreferences().flush();

        if (musicEnabled) {
            game.getMusicManager().play(Assets.BACKGROUND_MUSIC);
        } else {
            game.getMusicManager().stop();
        }
    }

    public float getSoundVolume() {
        return getPreferences().getFloat(PREF_SOUND_VOLUME, DEFAULT_VOLUME);
    }

    public void setSoundVolume(float soundVolume) {
        getPreferences().putFloat(PREF_SOUND_VOLUME, soundVolume);
        getPreferences().flush();
    }

    public boolean isSoundEnabled() {
        return getPreferences().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEnabled(boolean soundEnabled) {
        getPreferences().putBoolean(PREF_SOUND_ENABLED, soundEnabled);
        getPreferences().flush();
    }

    public boolean isFullscreenEnabled() {
        return getPreferences().getBoolean(PREF_FULLSCREEN_ENABLED, false);
    }

    public void setFullscreenEnable(boolean fullscreenEnabled) {
        getPreferences().putBoolean(PREF_FULLSCREEN_ENABLED, fullscreenEnabled);
        getPreferences().flush();
    }

    public boolean isVsyncEnabled() {
        return getPreferences().getBoolean(PREF_VSYNC, false);
    }

    public void setVsync(boolean vsync) {
        getPreferences().putBoolean(PREF_VSYNC, vsync);
        getPreferences().flush();
    }

    public boolean isGravityEnabled() {
        return getPreferences().getBoolean(DEBUG_PREF_GRAVITY_ENABLED, true);
    }

    public void setGravityEnabled(boolean gravityEnabled) {
        getPreferences().putBoolean(DEBUG_PREF_GRAVITY_ENABLED, gravityEnabled);
        getPreferences().flush();
    }

    public int getKeyShoot() {
        return getPreferences().getInteger(PREF_KEY_SHOOT, Input.Buttons.LEFT);
    }

    public boolean isKeyShootKeyboard() {
        return getPreferences().getBoolean(PREF_KEY_SHOOT_IS_KEYBOARD, false);
    }

    public void setKeyShoot(int key) {
        getPreferences().putInteger(PREF_KEY_SHOOT, key);
        getPreferences().flush();
    }

    public void setKeyShootKeyboard(boolean isKeyboard) {
        getPreferences().putBoolean(PREF_KEY_SHOOT_IS_KEYBOARD, isKeyboard);
        getPreferences().flush();
    }

    public boolean isTutorialEnabled() {
        return getPreferences().getBoolean(PREF_SHOW_INTRO_TEXT, true);
    }

    public void setTutorialEnabled(boolean tutorialEnabled) {
        getPreferences().putBoolean(PREF_SHOW_INTRO_TEXT, tutorialEnabled);
        getPreferences().flush();
    }

}

