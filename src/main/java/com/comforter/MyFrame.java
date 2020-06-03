package com.comforter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class MyFrame extends Frame implements WindowListener, ActionListener {
    TextField text = new TextField(20);
    Button b;
    ArrayList<String> urls = new ArrayList<>();
    MyFrame(String name) {
        super(name);
        setLayout(new FlowLayout());
        addWindowListener(this);
        b = new Button("Send");
        b.setBackground(new Color(59, 89, 182));
        b.setForeground(Color.RED);
        b.setFont(new Font("Tahoma", Font.BOLD, 12));
        JLabel names = new JLabel("Enter your comforter: ");
        add(names);
        add(text);
        add(b);
        b.addActionListener(this);
    }
    public ArrayList<String> getUrls() {
        return urls;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String url = text.getText();
        urls.add(url);
        text.setText("");
        //text.setText("Button Clicked " + numClicks + " times");
    }
    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }


}
