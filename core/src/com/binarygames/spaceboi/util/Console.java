package com.binarygames.spaceboi.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.input.ConsoleInputProcessor;
import com.binarygames.spaceboi.screens.GameScreen;

public class Console {

    private SpaceBoi game;
    private GameScreen gameScreen;

    private boolean isVisible = false;

    private InputMultiplexer consoleMultiplexer;
    private ConsoleInputProcessor inputProcessor;
    private Stage stage;

    private TextField inputField;
    private TextField.TextFieldStyle inputFieldStyle;

    private String input = "";

    private int consolePadding = 15;
    private float blinkTime = 0.5f; // Blink time in seconds
    private float currentBlinkTime;
    private char blinkChar = '_';
    private boolean blinkCharIsVisible = true;

    public Console(SpaceBoi game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;

        inputProcessor = new ConsoleInputProcessor(this);
        stage = new Stage();
        consoleMultiplexer = new InputMultiplexer();
        consoleMultiplexer.addProcessor(inputProcessor);
        consoleMultiplexer.addProcessor(stage);

        inputFieldStyle = new TextField.TextFieldStyle();
        inputFieldStyle.font = SpaceBoi.font.getLabelFont();
        inputFieldStyle.fontColor = Color.WHITE;

        inputField = new TextField("", inputFieldStyle);
        inputField.setX(consolePadding);
        inputField.setY(stage.getHeight() - inputField.getHeight() - consolePadding);
        inputField.setWidth(stage.getWidth() - 2 * consolePadding);
        inputField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                System.out.println("Key: " + c);
                //input += c;
            }
        });
        stage.addActor(inputField);
        stage.setKeyboardFocus(inputField);
    }

    public void update(float delta) {
        //setFieldText();

        /*
        if (currentBlinkTime >= blinkTime) {
            blinkCharIsVisible = !blinkCharIsVisible;
            currentBlinkTime = 0;
        } else {
            currentBlinkTime += delta;
        }
        */
    }

    public void render() {
        stage.draw();
    }

    public void show() {
        Gdx.input.setInputProcessor(consoleMultiplexer);
        clearInput();
        isVisible = true;
    }

    public void hide() {
        Gdx.input.setInputProcessor(gameScreen.getMultiplexer());
        clearInput();
        isVisible = false;
    }

    private void clearInput() {
        inputField.setText("");
        inputField.appendText(">");
    }

    /*

    private void setFieldText() {
        if (blinkCharIsVisible) {
            inputField.setText(">" + input + blinkChar);

        } else {
            inputField.setText(">" + input);

        }
    }

    */

    public boolean isVisible() {
        return isVisible;
    }

}
