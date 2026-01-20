package view;

import javax.swing.*;
import java.awt.*;

/**
 * Das Hauptfenster, das alle Views (Login, Menu, Game, etc.) beinhaltet.
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Sub-Views
    private LoginView loginView;
    private MenuView menuView;
    private GameView gameView;
    private HighscoreView highscoreView;
    private EditorView editorView;
    private CreditsView creditsView;

    public MainFrame() {
        setTitle("QuizMaster - Software Engineering I");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Theme.BACKGROUND_COLOR);

        // Panels initialisieren
        loginView = new LoginView();
        menuView = new MenuView();
        gameView = new GameView();
        highscoreView = new HighscoreView();
        editorView = new EditorView();
        creditsView = new CreditsView();

        // Dem CardLayout hinzufügen
        mainPanel.add(loginView, "LOGIN");
        mainPanel.add(menuView, "MENU");
        mainPanel.add(gameView, "GAME");
        mainPanel.add(highscoreView, "HIGHSCORE");
        mainPanel.add(editorView, "EDITOR");
        mainPanel.add(creditsView, "CREDITS");

        add(mainPanel);

        // Start mit Login
        showCard("LOGIN");
    }

    public void showCard(String name) {
        cardLayout.show(mainPanel, name);
    }

    // Getter für die Controller-Verknüpfung
    public LoginView getLoginView() {
        return loginView;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public GameView getGameView() {
        return gameView;
    }

    public HighscoreView getHighscoreView() {
        return highscoreView;
    }

    public EditorView getEditorView() {
        return editorView;
    }

    public CreditsView getCreditsView() {
        return creditsView;
    }
}