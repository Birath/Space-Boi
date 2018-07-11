package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class WeaponStats {

    private Stage stage;

    private ProgressBar weaponSlot;
    private ProgressBar.ProgressBarStyle weaponSlotStyle;

    private ProgressBar ammoBar;
    private ProgressBar.ProgressBarStyle ammoBarStyle;

    public WeaponStats(Stage stage, float x, float y, int ammo) {
        //Todoadd image to attributes...
        this.stage = stage;

        weaponSlotStyle = new ProgressBar.ProgressBarStyle();
        ammoBarStyle = new ProgressBar.ProgressBarStyle();

        loadWeaponSlotStyle();
        loadAmmoBarStyle();

        weaponSlot = new ProgressBar(0, 100, 0.1f, true, weaponSlotStyle);
        weaponSlot.setValue(100);
        weaponSlot.setBounds(x, y,75, 75);
        stage.addActor(weaponSlot);

        ammoBar = new ProgressBar(0, ammo, 1, false, ammoBarStyle);
        ammoBar.setValue(ammo);
        ammoBar.setBounds(x, y - ammoBar.getHeight()*2,75, 20);
        stage.addActor(ammoBar);
    }

    private void loadWeaponSlotStyle() {
        //TODO add input pixmap, the weapon image
        Pixmap pixmap = new Pixmap(75, 75, Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        weaponSlotStyle.background = drawable;

        pixmap = new Pixmap(75, 0 , Pixmap.Format.RGBA8888);
        pixmap.setColor(1,1,1,0.5f);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        weaponSlotStyle.knob = drawable;

        pixmap = new Pixmap(75, 75, Pixmap.Format.RGBA8888);
        pixmap.setColor(1,1,1,0.5f);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        weaponSlotStyle.knobBefore = drawable;
    }

    private void loadAmmoBarStyle(){
        Pixmap pixmap = new Pixmap(75, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(0,0,0,0);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ammoBarStyle.background = drawable;

        pixmap = new Pixmap(0, 20 , Pixmap.Format.RGBA8888);
        pixmap.setColor(1,1,1,0.5f);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ammoBarStyle.knob = drawable;

        pixmap = new Pixmap(75, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(1,1,1,0.5f);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ammoBarStyle.knobBefore = drawable;
    }

    public ProgressBar getWeaponSlot() {
        return weaponSlot;
    }

    public ProgressBar getAmmoBar() {
        return ammoBar;
    }
}
