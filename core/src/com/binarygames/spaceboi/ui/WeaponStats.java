package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class WeaponStats {

    private Stage stage;

    private ProgressBar weaponSlot;
    private ProgressBar.ProgressBarStyle weaponSlotStyle;

    private ProgressBar ammoBar;
    private ProgressBar.ProgressBarStyle ammoBarStyle;

    //private Array<Image> ammunition;

    public WeaponStats(Stage stage, float x, float y, int ammo, Texture weaponIconTex, float scaling) {
        this.stage = stage;

        weaponSlotStyle = new ProgressBar.ProgressBarStyle();
        ammoBarStyle = new ProgressBar.ProgressBarStyle();

        Image weaponIcon = new Image(weaponIconTex);
        weaponIcon.setScaling(Scaling.fill);
        weaponIcon.setWidth(weaponIconTex.getWidth() * scaling);
        weaponIcon.setHeight(weaponIconTex.getHeight() * scaling);
        weaponIcon.setPosition(x, y);
        weaponIcon.setOrigin(Align.center);

        int weaponReloadWidth = (int) (weaponIcon.getWidth() - (25 * scaling));
        int weaponReloadHeight = (int) (weaponIcon.getHeight() - (100 * scaling));

        loadWeaponSlotStyle(weaponReloadWidth, weaponReloadHeight);
        loadAmmoBarStyle();

        weaponSlot = new ProgressBar(0, 100, 0.1f, false, weaponSlotStyle);
        weaponSlot.setValue(100);
        weaponSlot.setBounds(x + (weaponIcon.getWidth() - weaponReloadWidth) / 2, y + (weaponIcon.getHeight() - weaponReloadHeight) / 2, weaponReloadWidth, weaponReloadHeight);

        //ammunition = new Array<Image>(ammoIconTex);

        ammoBar = new ProgressBar(0, ammo, 1, false, ammoBarStyle);
        ammoBar.setValue(ammo);
        ammoBar.setBounds(x, y - ammoBar.getHeight() * 2, weaponIcon.getWidth(), 20);

        stage.addActor(weaponSlot);
        stage.addActor(weaponIcon);
        stage.addActor(ammoBar);
    }

    private void loadWeaponSlotStyle(int width, int height) {
        //TODO add input pixmap, the weapon image
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        weaponSlotStyle.background = drawable;

        pixmap = new Pixmap(0, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0xe08500ff);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        weaponSlotStyle.knob = drawable;

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(0xe08500ff);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        weaponSlotStyle.knobBefore = drawable;
    }

    private void loadAmmoBarStyle() {
        Pixmap pixmap = new Pixmap(75, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ammoBarStyle.background = drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 0.5f);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ammoBarStyle.knob = drawable;

        pixmap = new Pixmap(75, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 0.5f);
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
