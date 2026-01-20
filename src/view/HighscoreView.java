package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighscoreView extends JPanel {
    private JTextArea textArea;
    private JButton backButton;

    public HighscoreView() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);

        JLabel title = new JLabel("Highscores", SwingConstants.CENTER);
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_COLOR);
        add(title, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(Theme.SURFACE_COLOR);
        textArea.setForeground(Theme.TEXT_COLOR);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        backButton = new ModernButton("Zurück zum Menü");
        add(backButton, BorderLayout.SOUTH);
    }

    public void setHighscores(List<User> users) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s\n", "Name", "Punkte"));
        sb.append("--------------------------------\n");
        for (User u : users) {
            sb.append(String.format("%-20s %-10d\n", u.getUsername(), u.getUserHighscore()));
        }
        textArea.setText(sb.toString());
    }

    public JButton getBackButton() {
        return backButton;
    }
}