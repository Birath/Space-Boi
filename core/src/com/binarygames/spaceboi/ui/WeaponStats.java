package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

public class WeaponStats {

    private Stage stage;

    private ProgressBar weaponSlot;
    private ProgressBar.ProgressBarStyle weaponSlotStyle;

    private Texture ammoIconTex;

    private int x, y, ammo;
    private Table table;

    private Image weaponIcon;

    public WeaponStats(Stage stage, int x, int y, int ammo, Texture weaponIconTex, Texture ammoIconTex, float scaling) {
        this.stage = stage;
        this.ammo = ammo;
        this.x = x;
        this.y = y;
        this.table = new Table();
        this.ammoIconTex = ammoIconTex;

        weaponSlotStyle = new ProgressBar.ProgressBarStyle();

        weaponIcon = new Image(weaponIconTex);
        weaponIcon.setScaling(Scaling.fill);
        weaponIcon.setWidth(weaponIconTex.getWidth() * scaling);
        weaponIcon.setHeight(weaponIconTex.getHeight() * scaling);

        this.y = (int) (y - weaponIcon.getHeight());

        weaponIcon.setPosition(this.x, this.y);
        weaponIcon.setOrigin(Align.center);

        int weaponReloadWidth = (int) (weaponIcon.getWidth() - (25 * scaling));
        int weaponReloadHeight = (int) (weaponIcon.getHeight() - (100 * scaling));

        loadWeaponSlotStyle(weaponReloadWidth, weaponReloadHeight);

        weaponSlot = new ProgressBar(0, 100, 0.1f, false, weaponSlotStyle);
        weaponSlot.setValue(100);
        weaponSlot.setBounds(this.x + (weaponIcon.getWidth() - weaponReloadWidth) / 2, this.y + (weaponIcon.getHeight() - weaponReloadHeight) / 2, weaponReloadWidth, weaponReloadHeight);

        //table.setFillParent(true);
        table.setHeight(weaponIcon.getHeight() / 5);
        table.setWidth(weaponIcon.getWidth());
        table.setX(weaponIcon.getX());
        table.setY(weaponIcon.getY() - (float)(table.getHeight() * 1.2));
        table.align(Align.left);
        fillAmmoBar(ammo);

        stage.addActor(table);
        stage.addActor(weaponSlot);
        stage.addActor(weaponIcon);
    }

    private void loadWeaponSlotStyle(int width, int height) {
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

    public void act(int currentAmmo) {
        if (currentAmmo < table.getCells().size) {
            table.removeActor(table.getCells().get(currentAmmo).getActor());
            table.getCells().removeIndex(currentAmmo);
        }
        if (currentAmmo == 0) {
            table.row();
        }
        if (currentAmmo > table.getCells().size) {
            fillAmmoBar(currentAmmo - table.getCells().size);
        }
    }

    private void fillAmmoBar(int amount) {
        for (int i = 0; i <= amount - 1; i++) {
            Image ammoIcon = new Image(ammoIconTex);
            ammoIcon.setScaling(Scaling.fill);
            table.add(ammoIcon).maxSize(table.getHeight() / (ammoIcon.getHeight() / ammoIcon.getWidth()), table.getHeight()).fill(false).pad(0f, ammoIcon.getWidth() / 20, 0f, ammoIcon.getWidth() / 20).left();
        }
    }

    public ProgressBar getWeaponSlot() {
        return weaponSlot;
    }

    public int getRightPositionX() {
        return (int) (this.x + weaponIcon.getWidth());
    }
}
