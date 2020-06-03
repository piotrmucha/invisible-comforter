package com.comforter;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class KeyListener implements NativeKeyListener {
    private MyFrame window;
    private StringBuilder charactersFromUser = new StringBuilder();
    private LocalDateTime lastTimeWhenUserUseKeyboard = LocalDateTime.now();
    KeyListener(MyFrame myWindow) {
        this.window = myWindow;
    }
    public void clearCharacters() {
        charactersFromUser = new StringBuilder();
    }
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }
    public LocalDateTime getLastTimeWhenUserUseKeyboard() {
        return  lastTimeWhenUserUseKeyboard;
    }

    public StringBuilder getCharactersFromUser() {
        return charactersFromUser;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));




    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        charactersFromUser.append(Character.toChars(nativeKeyEvent.getRawCode()));
        lastTimeWhenUserUseKeyboard = LocalDateTime.now();

    }
}
