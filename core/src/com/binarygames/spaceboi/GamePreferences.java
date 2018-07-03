package com.binarygames.spaceboi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class GamePreferences {

    private static final String PREF_MUSIC_VOLUME = "music.volume";
    private static final String PREF_SOUND_VOLUME = "sound.volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_NAME = "spaceboi";
    public static final float DEFAULT_VOLUME = 0.5f;

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
            game.getMusicManager().play(Assets.MENU_BACKGROUND_MUSIC);
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
}

