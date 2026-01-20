package main;

import controller.QuizController;
import model.GameModel;
import view.MainFrame;

import javax.swing.*;

/**
 * Hauptklasse zum Starten der Anwendung.
 */
public class QuizApp {
    public static void main(String[] args) {
        // Swing GUI im Event Dispatch Thread starten
        SwingUtilities.invokeLater(() -> {
            try {
                GameModel model = new GameModel();
                MainFrame view = new MainFrame();
                new QuizController(model, view);

                view.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Kritischer Fehler: " + e.getMessage());
            }
        });
    }
}