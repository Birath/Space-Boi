package com.binarygames.spaceboi.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.input.ConsoleInputProcessor;
import com.binarygames.spaceboi.screens.GameScreen;
import com.binarygames.spaceboi.util.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Console {

    private SpaceBoi game;
    private GameScreen gameScreen;
    private GameWorld gameWorld;

    private boolean isVisible = false;

    private InputMultiplexer consoleMultiplexer;
    private ConsoleInputProcessor inputProcessor;
    private Stage stage;

    private TextField inputField;
    private TextField.TextFieldStyle inputFieldStyle;

    private Label outputLabel;
    private Label.LabelStyle outputLabelStyle;

    private boolean outputVisible = false;
    private float outputTime = 3f;
    private float currentOutputTime = 0;

    // private String input = "";

    private int consolePadding = 15;
    private float blinkTime = 0.5f; // Blink time in seconds
    private float currentBlinkTime;
    private char blinkChar = '_';
    private boolean blinkCharIsVisible = true;

    private Map<String, Command> commands;

    public Console(SpaceBoi game, GameScreen gameScreen, GameWorld gameWorld) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.gameWorld = gameWorld;

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
        inputField.setTextFieldListener((textField, c) -> {
            /*
            if (!Character.isWhitespace(c)) {

                System.out.println("Key: " + c);
            } else {
                System.out.println("FAK");
            }
            */
        });
        stage.addActor(inputField);
        stage.setKeyboardFocus(inputField);

        outputLabelStyle = new Label.LabelStyle();
        outputLabelStyle.font = SpaceBoi.font.getLabelFont();
        outputLabelStyle.fontColor = Color.WHITE;

        outputLabel = new Label("", outputLabelStyle);
        outputLabel.setPosition(consolePadding, inputField.getY() - outputLabel.getHeight() - consolePadding);
        stage.addActor(outputLabel);

        commands = new HashMap<>();
        commands.put("pos", new Position());
        commands.put("zoom", new Zoom());
        commands.put("help", new Help());
        commands.put("god", new God());
        commands.put("gravity", new Gravity());
    }

    public void update(float delta) {
        if (outputVisible) {
            if (currentOutputTime > outputTime) {
                outputVisible = false;
                outputLabel.setText("");
            } else {
                currentOutputTime += delta;
            }
        }

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

    public void parse() {
        String[] commandString = inputField.getText().split("\\s+");
        String command = commandString[0];
        command = command.substring(1).toLowerCase();

        if (commands.containsKey(command)) {
            switch (commands.get(command).getArgumentType()) {
                case REQUIRED:
                    if (commandString.length > 1) {
                        String[] args = Arrays.copyOfRange(commandString, 1, commandString.length);
                        commands.get(command).run(this, args);
                    } else {
                        echo(commands.get(command).getUsage());
                    }
                    break;
                case OPTIONAL:
                    if (commandString.length > 1) {
                        String[] args = Arrays.copyOfRange(commandString, 1, commandString.length);
                        System.out.println(args.length);
                        commands.get(command).run(this, args);
                    } else {
                        commands.get(command).run(this, null);
                    }
                    break;
                case NONE:
                    commands.get(command).run(this, null);
            }
        } else {
            echo(command + " is not a valid command u scrub");
        }

        clearInput();
    }

    public void echo(String out) {
        System.out.println(out);
        outputLabel.setText(out);
        outputVisible = true;
        currentOutputTime = 0;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public SpaceBoi getGame() {
        return game;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public enum ArgumentType {
        REQUIRED, OPTIONAL, NONE
    }
}
