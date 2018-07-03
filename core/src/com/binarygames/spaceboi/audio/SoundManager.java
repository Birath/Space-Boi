package com.binarygames.spaceboi.audio;

import com.badlogic.gdx.audio.Sound;
import com.binarygames.spaceboi.SpaceBoi;

public class SoundManager {

    private SpaceBoi game;

    public SoundManager(SpaceBoi game) {
        this.game = game;
    }

    public long play(String clip) {
        if (game.getPreferences().isSoundEnabled()) {
            Sound sound = game.getAssetManager().get(clip, Sound.class);
            return sound.play(game.getPreferences().getSoundVolume());
        }
        return -1;
    }

}
