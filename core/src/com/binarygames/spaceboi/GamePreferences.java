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

    public GamePreferences() {}

    private Preferences getPrefernces() {
        return Gdx.app.getPreferences(PREF_NAME);
    }

    public float getMusicVolume() {
        return getPrefernces().getFloat(PREF_MUSIC_VOLUME, DEFAULT_VOLUME);
    }

    public void setMusicVolume(float volume) {
        getPrefernces().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefernces().flush();
    }

    public boolean isMusicEnabed() {
        return getPrefernces().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefernces().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefernces().flush();

    }

    public float getSoundVolume() {
        return getPrefernces().getFloat(PREF_SOUND_VOLUME, DEFAULT_VOLUME);
    }

    public void setSoundVolume(float soundVolume) {
        getPrefernces().putFloat(PREF_SOUND_VOLUME, soundVolume);
        getPrefernces().flush();
    }

    public boolean isSoundEnabled() {
        return getPrefernces().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEnabled(boolean soundEnabled) {
        getPrefernces().putBoolean(PREF_SOUND_ENABLED, soundEnabled);
        getPrefernces().flush();
    }
}

