package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class SettingsTab {

    Table table;

    public void show() {
        table.setVisible(true);
    }

    public void hide() {
        table.setVisible(false);
    }

    public Table getTable() {
        return table;
    }

}
