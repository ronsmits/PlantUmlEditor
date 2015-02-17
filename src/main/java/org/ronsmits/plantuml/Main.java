package org.ronsmits.plantuml;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ron on 17-2-15.
 */
public class Main extends JFrame {

    List<TextListener> listenerList = new ArrayList<TextListener>();

    Main() {
        initUI();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
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
//        setSize(640, 480);
        setLocationRelativeTo(null);
        add(new TextEditor(listenerList), BorderLayout.WEST);
        Drawing drawing = new Drawing();
        add(drawing, BorderLayout.CENTER);
        listenerList.add(drawing);
        pack();
    }
}
