package com.binarygames.spaceboi.gameobjects.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.binarygames.spaceboi.SpaceBoi;

public class ParticleHandler {

    private SpaceBoi game;

    private ParticleEffectPool bloodEffectPool;
    private Array<ParticleEffectPool.PooledEffect> effects;

    public ParticleHandler(SpaceBoi game) {
        this.game = game;

        effects = new Array();

        TextureAtlas effectsAtlas = game.getAssetManager().get("game/particles/particle_effects.atlas", TextureAtlas.class);

        // ParticleEffect bloodEffect = new ParticleEffect();
        // bloodEffect.load(Gdx.files.internal("particles/blood.p"), effectsAtlas);
        ParticleEffect bloodEffect = game.getAssetManager().get("game/particles/blood.p", ParticleEffect.class);
        bloodEffect.loadEmitterImages(effectsAtlas);
        bloodEffect.scaleEffect(0.1f);
        bloodEffectPool = new ParticleEffectPool(bloodEffect, 1, 50);
    }

    public void updateAndDrawEffects(SpriteBatch batch, float delta) {
        for (int i = effects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.draw(batch, delta);
            if (effect.isComplete()) {
                effect.free();
                effects.removeIndex(i);
            }
        }
    }

    public void addEffect(EffectType type, float x, float y) {
        ParticleEffectPool.PooledEffect effect = null;
        switch (type) {
            case BLOOD:
                effect = bloodEffectPool.obtain();
                break;
        }
        effect.setPosition(x, y);
        effects.add(effect);
    }

    public enum EffectType {
        BLOOD
    }

}
