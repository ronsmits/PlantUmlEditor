package org.ronsmits.plantuml;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by ron on 18-2-15.
 */
public class MenuBar extends JMenuBar implements TextListener {

    private final TextListener listener;
    private String currentGraph = "";

    MenuBar(TextListener textListener) {
        listener = textListener;
        setupFileMenu();
        setupAboutMenu();
    }

    @Override
    public void textUpdated(String text) {
        currentGraph = text;
        System.out.println(currentGraph);
    }

    private void setupAboutMenu() {
        JMenu aboutMenu = new JMenu("About");
        add(aboutMenu);
        JMenuItem menuItem = makeMenuItem("about", KeyEvent.VK_A, "about this program");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebPage("http://plantuml.sourceforge.net");
            }
        });
        aboutMenu.add(menuItem);
        JMenuItem github = makeMenuItem("source", KeyEvent.VK_S, "the source on github");
        github.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebPage("https://github.com/ronsmits/PlantUmlEditor");
            }
        });
        aboutMenu.add(github);

    }

    private void setupFileMenu() {
        JMenu fileMenu = new JMenu("File");
        add(fileMenu);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(setupLoadMenu());
        fileMenu.add(setupSaveMenu());
        fileMenu.add(savePngMenu());
        fileMenu.add(setupExitMenu());
    }

    private JMenuItem savePngMenu() {
        JMenuItem menuItem = makeMenuItem("save graphics", KeyEvent.VK_G, "Save as PNG file");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage bufferedImage = Utils.createImage(currentGraph);
                try {
                    saveBufferedImage(bufferedImage);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        return menuItem;
    }

    private void saveBufferedImage(BufferedImage bufferedImage) throws IOException {
        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as PNG file");
        int option = fileChooser.showSaveDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIO.write(bufferedImage, "png", file);
        }
    }

    private JMenuItem setupLoadMenu() {
        JMenuItem menuItem = makeMenuItem("Load", KeyEvent.VK_L, "load file");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("load a file");
                int option = fileChooser.showOpenDialog(frame);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File fileToLoad = fileChooser.getSelectedFile();
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null) {
                            currentGraph += currentLine + "\n";
                        }
                        listener.textUpdated(currentGraph);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        return menuItem;
    }

    private JMenuItem setupSaveMenu() {
        JMenuItem menuItem = makeMenuItem("Save", KeyEvent.VK_S, "Save file");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        return menuItem;
    }

    private JMenuItem makeMenuItem(String title, int keyEvent, String tooltipText) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.setMnemonic(keyEvent);
        menuItem.setToolTipText(tooltipText);
        return menuItem;
    }

    private void saveFile() {
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file as");
        int option = fileChooser.showSaveDialog(parentFrame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("saving to " + fileToSave);
            try (FileWriter fw = new FileWriter(fileToSave)) {
                fw.write(currentGraph);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JMenuItem setupExitMenu() {
        JMenuItem eMenuItem = makeMenuItem("Exit", KeyEvent.VK_E, "Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        return eMenuItem;
    }

    private void openWebPage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
}
