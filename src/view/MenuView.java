package view;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JPanel {
    private JButton btnSingle;
    private JButton btnMulti;
    private JButton btnHighscore;
    private JButton btnEditor;
    private JButton btnCredits;
    private JButton btnExit;
    private JLabel userLabel;

    public MenuView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        setBackground(Theme.BACKGROUND_COLOR);

        JLabel title = new JLabel("Hauptmenü");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        userLabel = new JLabel("Nicht angemeldet");
        userLabel.setFont(Theme.FONT_REGULAR);
        userLabel.setForeground(Theme.TEXT_SECONDARY_COLOR);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSingle = createButton("Einzelspieler starten");
        btnMulti = createButton("Mehrspieler (Hot-Seat)");
        btnHighscore = createButton("Highscores");
        btnEditor = createButton("Editor-Modus");
        btnCredits = createButton("Credits");
        btnExit = createButton("Beenden");

        btnEditor.setVisible(false); // Default versteckt

        add(title);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(userLabel);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(btnSingle);

        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnMulti);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnHighscore);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnCredits);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnEditor);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnExit);
    }

    private JButton createButton(String text) {
        ModernButton btn = new ModernButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 50));
        return btn;
    }

    public void setUsername(String name, boolean isEditor) {
        userLabel.setText("Angemeldet als: " + name + (isEditor ? " (Editor)" : ""));
        userLabel.setToolTipText("Klicken zum Ausloggen");
        btnEditor.setVisible(isEditor);
    }

    // Getter
    public JButton getBtnSingle() {
        return btnSingle;
    }

    public JButton getBtnMulti() {
        return btnMulti;
    }

    public JButton getBtnHighscore() {
        return btnHighscore;
    }

    public JButton getBtnEditor() {
        return btnEditor;
    }

    public JButton getBtnCredits() {
        return btnCredits;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public JLabel getUserLabel() {
        return userLabel;
    }
}