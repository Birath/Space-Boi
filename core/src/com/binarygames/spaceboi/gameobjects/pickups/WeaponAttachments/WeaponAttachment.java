package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.badlogic.gdx.Gdx;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.pickups.Pickup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class WeaponAttachment extends Pickup {

    private static final float ATTACHMENT_MASS = 300;
    private static final float ATTACHMENT_RADIUS = 7;
    private static Random random = new Random();

    private static final List<Class<? extends WeaponAttachment>> WEAPON_ATTACHMENTS = Arrays.asList(
        BioDamage.class,
        Experience.class,
        GlassCannon.class,
        LifeSteal.class,
        MechDamage.class,
        Recoil.class,
        Silencer.class,
        Slow.class
    );


    public WeaponAttachment(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    public static WeaponAttachment getRandomAttachment(GameWorld gameWorld, float x ,float y) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends WeaponAttachment> attachmentClass = WeaponAttachment.WEAPON_ATTACHMENTS.get(random.nextInt(WeaponAttachment.WEAPON_ATTACHMENTS.size()));
        String asset;
        try {
            Gdx.app.log("WeaponAttachment", "Name:" + attachmentClass.getSimpleName());
            asset = WeaponAttachmentAsset.valueOf(attachmentClass.getSimpleName()).getAsset();
        } catch (IllegalArgumentException e) {
            Gdx.app.error("RandomAttachment", "Error when spawing random attachment", e);
            asset = Assets.PLANET_MOON;
        }
        // Gets the weapon attachment constructor
        Constructor<? extends WeaponAttachment> attachementConstructor = attachmentClass.getConstructor(GameWorld.class, float.class, float.class, String.class, float.class, float.class);// GameWorld, x, y, path, mass, radius
        // TODO Add picture based on class name
        return attachementConstructor.newInstance(gameWorld, x, y, asset, ATTACHMENT_MASS, ATTACHMENT_RADIUS);
    }

    public static WeaponAttachment getAttachment(GameWorld gameWorld, float x ,float y, int attachmentIndex) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<? extends WeaponAttachment> attachmentClass = WeaponAttachment.WEAPON_ATTACHMENTS.get(attachmentIndex);
        String asset;
        try {
            Gdx.app.log("WeaponAttachment", "Name:" + attachmentClass.getSimpleName());
            asset = WeaponAttachmentAsset.valueOf(attachmentClass.getSimpleName()).getAsset();
        } catch (IllegalArgumentException e) {
            Gdx.app.error("RandomAttachment", "Erroer when spawing random attachment", e);
            asset = Assets.PLANET_MOON;
        }
        // Gets the weapon attachment constructor
        Constructor<? extends WeaponAttachment> attachementConstructor = attachmentClass.getConstructor(GameWorld.class, float.class, float.class, String.class, float.class, float.class);// GameWorld, x, y, path, mass, radius
        // TODO Add picture based on class name
        return attachementConstructor.newInstance(gameWorld, x, y, asset, ATTACHMENT_MASS, ATTACHMENT_RADIUS);
    }

    @Override
    public void onRemove() {
        
    }

    @Override
    public void onHit(Player player) {
        player.addToInventory(this);
        gameWorld.getGame().getSoundManager().play(Assets.ATTACHMENT_PICK_1);
        //gameWorld.getGame().getSoundManager().play(Assets.ATTACHMENT_PICK_2);
        remove = true;
    }

    public abstract void applyAttachment(Weapon weapon);

    public abstract void removeAttachment(Weapon weapon);

    public abstract boolean isEquipped();

    public abstract void setEquipped(boolean equipped);

    public abstract String getIcon();

    public abstract String getName();

    public abstract String getDescription();

}
