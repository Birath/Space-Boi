package com.binarygames.spaceboi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.binarygames.spaceboi.SpaceBoi;

public class DesktopLauncher {
    public static void main(String[] arg) {
        System.setProperty("user.name", "bjorn");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        // TODO load video settings on launch
        config.width = 1280;
        config.height = 720;
        config.samples = 4;
        config.vSyncEnabled = false;
        config.foregroundFPS = 250;
        config.backgroundFPS = 250;
        config.title = "SPACE-NORTHERNERS REVENGE";

        new LwjglApplication(new SpaceBoi(), config);
    }
}
