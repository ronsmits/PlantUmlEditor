package org.ronsmits.plantuml;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by ron on 17-2-15.
 */
public class TextEditor extends JPanel implements TextListener {

    private final List<TextListener> listeners;
    private final JButton button;
    private JTextArea area;
    private Action wrapper = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            notifyListeners(area.getText());
            area.insert("\n", area.getCaretPosition());
        }
    };

    /**
     * Constructor for an empty drawing.
     *
     * @param listeners
     */
    TextEditor(List<TextListener> listeners) {
        this(listeners, "@startuml\n\n@enduml\n");
    }

    /**
     * Constructor with a loaded drawing.
     * @param listeners
     * @param text
     */
    TextEditor(List<TextListener> listeners, String text) {
        this.listeners = listeners;
        setLayout(new BorderLayout(5, 5));
        area = new JTextArea(text, 0, 30);
        add(area);
        button = new JButton("update");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyListeners(area.getText());
            }
        });
        add(button, BorderLayout.SOUTH);

        setupEnterKey(area);
    }

    private void notifyListeners(String text) {
        for (TextListener listener : listeners)
            listener.textUpdated(text);
    }

    private void setupEnterKey(JTextArea area) {
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        Object actionKey = area.getInputMap(WHEN_FOCUSED).get(enter);
        area.getActionMap().put(actionKey, wrapper);
    }

    /**
     * called when a file is loaded. Will set the text and update the graphics.
     *
     * @param text content that is changed
     */
    @Override
    public void textUpdated(String text) {
        area.setText(text);
        button.doClick();
    }
}
