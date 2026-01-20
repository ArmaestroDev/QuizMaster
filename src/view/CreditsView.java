package view;

import javax.swing.*;
import java.awt.*;

public class CreditsView extends JPanel {

    private JTextArea textArea;
    private JButton backButton;

    public CreditsView() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);

        JLabel title = new JLabel("Credits", SwingConstants.CENTER);
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_COLOR);
        add(title, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(Theme.SURFACE_COLOR);
        textArea.setForeground(Theme.TEXT_COLOR);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        textArea.setText("QuizMaster erstellt von\nDenis Lomovtsev und Armando Monte");

        backButton = new ModernButton("Zurück zum Menü");
        add(backButton, BorderLayout.SOUTH);
    }

    public JButton getBackButton() {
        return backButton;
    }
}
