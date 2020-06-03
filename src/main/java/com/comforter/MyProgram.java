package com.comforter;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;


import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyProgram {

    public static void main(String[] args) {

        MyFrame myWindow = new MyFrame("Invisible Comforter");
        myWindow.setSize(500,100);
        myWindow.setVisible(true);
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        KeyListener listener = new KeyListener(myWindow);
        GlobalScreen.addNativeKeyListener(listener);
        LogManager.getLogManager().reset();
        TimerExecutor executor = new TimerExecutor(listener, myWindow);
        ScheduledExecutorService executors = Executors.newScheduledThreadPool(1);
        executors.scheduleAtFixedRate(executor, 0, 10, TimeUnit.SECONDS);


    }
}
