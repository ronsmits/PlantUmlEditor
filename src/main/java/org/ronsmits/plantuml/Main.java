package org.ronsmits.plantuml;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.EventQueue.invokeLater;

/**
 * Main entry point for the program.
 */
class Main extends JFrame {

    private List<TextListener> listenerList = new ArrayList<>();

    private Main() {
        initUI();
    }

    public static void main(String[] args) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
                main.setVisible(true);
            }
        });
    }

    private void initUI() {
        setTitle("PlantUML Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 480);
        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        TextEditor textEditor = new TextEditor(listenerList);
        add(textEditor, BorderLayout.WEST);
        Drawing drawing = new Drawing();
        add(drawing, BorderLayout.CENTER);
        listenerList.add(drawing);
        MenuBar menuBar = new MenuBar(textEditor);
        listenerList.add(menuBar);
        setJMenuBar(menuBar);
        pack();
    }
}
