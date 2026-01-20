package view;

import model.Question;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private JLabel questionLabel;
    private JButton[] answerButtons;
    private JLabel scoreLabel;
    private JLabel turnLabel; // Für Multiplayer "Spieler X ist dran"
    private JLabel progressLabel;

    public GameView() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);

        // Header
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.setOpaque(false);

        scoreLabel = new JLabel("Punkte: 0", SwingConstants.CENTER);
        scoreLabel.setFont(Theme.FONT_REGULAR);
        scoreLabel.setForeground(Theme.TEXT_COLOR);

        turnLabel = new JLabel("", SwingConstants.CENTER);
        turnLabel.setFont(Theme.FONT_HEADER);
        turnLabel.setForeground(Theme.ACCENT_COLOR);

        // Fragen-Fortschritt
        progressLabel = new JLabel("Frage: - / -", SwingConstants.CENTER);
        progressLabel.setFont(Theme.FONT_REGULAR);
        progressLabel.setForeground(Theme.TEXT_SECONDARY_COLOR);

        topPanel.add(turnLabel);
        topPanel.add(scoreLabel);
        topPanel.add(progressLabel);
        add(topPanel, BorderLayout.NORTH);

        // Center: Frage
        questionLabel = new JLabel("Frage lädt...", SwingConstants.CENTER);
        questionLabel.setFont(Theme.FONT_HEADER);
        questionLabel.setForeground(Theme.TEXT_COLOR);
        add(questionLabel, BorderLayout.CENTER);

        // Bottom: Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        answerButtons = new ModernButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new ModernButton("");
            buttonPanel.add(answerButtons[i]);
        }
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void showQuestion(Question q) {
        questionLabel.setText("<html><div style='text-align: center;'>" + q.getText() + "</div></html>");
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(q.getAnswers()[i]);
            answerButtons[i].setBackground(null); // Reset Farbe
        }
    }

    public void setScore(String scoreText) {
        scoreLabel.setText(scoreText);
    }

    public void setTurnLabel(String text) {
        turnLabel.setText(text);
    }

    public JButton[] getAnswerButtons() {
        return answerButtons;
    }

    public void setProgress(String text) {
        progressLabel.setText(text);
    }
}