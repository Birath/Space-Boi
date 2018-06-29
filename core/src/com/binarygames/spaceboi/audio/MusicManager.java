package com.binarygames.spaceboi.audio;

import com.badlogic.gdx.audio.Music;
import com.binarygames.spaceboi.SpaceBoi;

public class MusicManager {

    private SpaceBoi game;

    private Music music;

    public MusicManager(SpaceBoi game) {
        this.game = game;
    }

    public void play(String track) {
        if (game.getPreferences().isMusicEnabled()) {
            music = game.getAssetManager().get(track, Music.class);
            music.setVolume(game.getPreferences().getMusicVolume());
            music.play();
        }

    }

    public void pause() {
        if (music != null) {
            music.pause();
        }
    }

    public void resume() {
        if (music != null && game.getPreferences().isMusicEnabled()) {
            music.play();
        }
    }

    public void stop() {
        if (music != null) {
            music.stop();
        }
    }

    public void setLooping(boolean looping) {
        music.setLooping(looping);
    }

    public boolean isPlaying() {
        return music.isPlaying();
    }

    public void setVolume(float volume) {
        if (music != null) {
            music.setVolume(volume);
        }
    }

}
