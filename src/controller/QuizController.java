package controller;

import model.GameModel;
import model.Question;
import model.User;
import view.MainFrame;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import view.EditorView;
import view.Theme;
import view.ModernButton;

/**
 * Verbindet Model und View (Observer Pattern) und steuert die Interaktion.
 */
public class QuizController implements Observer {
    private GameModel model;
    private MainFrame view;

    public QuizController(GameModel model, MainFrame view) {
        this.model = model;
        this.view = view;
        this.model.addObserver(this);
        initController();
    }

    private void initController() {
        view.getLoginView().getLoginButton().addActionListener(e -> {
            String name = view.getLoginView().getUsername();
            if (!name.isEmpty()) {
                if (model.userExists(name)) {
                    model.login(name);
                } else {
                    showModernConfirmationDialog("User nicht gefunden",
                            "Dieser User existiert nicht. Möchten Sie einen neuen User anlegen?",
                            () -> model.register(name));
                }
            }
        });

        view.getMenuView().getBtnSingle().addActionListener(e -> model.startSinglePlayer());
        view.getMenuView().getBtnMulti().addActionListener(e -> {
            String p2 = JOptionPane.showInputDialog("Name Spieler 2:");
            if (p2 != null && !p2.trim().isEmpty()) {
                if (!model.userExists(p2)) {
                    showModernConfirmationDialog("User nicht gefunden",
                            "Dieser User existiert nicht. Möchten Sie einen neuen User anlegen?",
                            () -> model.register(p2));
                }
                model.startMultiplayer(p2);
            }
        });

        view.getMenuView().getBtnHighscore().addActionListener(e -> {
            Collections.sort(model.getAllUsers());
            view.getHighscoreView().setHighscores(model.getAllUsers());
            view.showCard("HIGHSCORE");
        });

        view.getMenuView().getBtnEditor().addActionListener(e -> {
            refreshEditorLists(); // Listen laden beim Öffnen
            view.showCard("EDITOR");
        });

        view.getMenuView().getBtnCredits().addActionListener(e -> {
            view.showCard("CREDITS");
        });

        view.getMenuView().getBtnExit().addActionListener(e -> System.exit(0));
        view.getMenuView().getUserLabel().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                model.logout();
            }
        });

        view.getHighscoreView().getBackButton().addActionListener(e -> view.showCard("MENU"));
        view.getCreditsView().getBackButton().addActionListener(e -> view.showCard("MENU"));
        for (int i = 0; i < 4; i++) {
            final int idx = i;
            view.getGameView().getAnswerButtons()[i].addActionListener(e -> model.answerQuestion(idx));
        }

        initEditorLogic();
    }

    private void initEditorLogic() {
        EditorView ed = view.getEditorView();

        ed.getBtnBack().addActionListener(e -> view.showCard("MENU"));
        // Liste Klick
        ed.getQuestionList().addListSelectionListener(e -> {
            Question q = ed.getQuestionList().getSelectedValue();
            if (q != null)
                ed.setQuestionForm(q);
        });

        // Leeren
        ed.getBtnClearQ().addActionListener(e -> ed.clearQuestionForm());

        // Hinzufügen
        ed.getBtnAddQ().addActionListener(e -> {
            String txt = ed.getQText();
            String[] ans = { ed.getQAnswer(0), ed.getQAnswer(1), ed.getQAnswer(2), ed.getQAnswer(3) };
            if (txt.isEmpty() || ans[0].isEmpty())
                return;
            model.addQuestion(txt, ans, ed.getQCorrect());
            ed.clearQuestionForm();
            refreshEditorLists();
        });

        // Löschen
        ed.getBtnDeleteQ().addActionListener(e -> {
            Question q = ed.getQuestionList().getSelectedValue();
            if (q != null) {
                model.deleteQuestion(q);
                ed.clearQuestionForm();
                refreshEditorLists();
            }
        });

        // Update
        ed.getBtnUpdateQ().addActionListener(e -> {
            int idx = ed.getQuestionList().getSelectedIndex();
            if (idx >= 0) {
                String txt = ed.getQText();
                String[] ans = { ed.getQAnswer(0), ed.getQAnswer(1), ed.getQAnswer(2), ed.getQAnswer(3) };
                model.updateQuestion(idx, txt, ans, ed.getQCorrect());
                ed.clearQuestionForm();
                refreshEditorLists();
            }
        });

        ed.getUserList().addListSelectionListener(e -> {
            User u = ed.getUserList().getSelectedValue();
            if (u != null)
                ed.setUserForm(u);
        });

        ed.getBtnAddU().addActionListener(e -> {
            String name = ed.getUName();
            if (name.isEmpty())
                return;
            model.addUser(name, ed.getUScore(), ed.getUIsEditor());
            ed.clearUserForm();
            refreshEditorLists();
        });

        ed.getBtnDeleteU().addActionListener(e -> {
            User u = ed.getUserList().getSelectedValue();
            if (u != null) {
                model.deleteUser(u);
                ed.clearUserForm();
                refreshEditorLists();
            }
        });

        ed.getBtnUpdateU().addActionListener(e -> {
            int idx = ed.getUserList().getSelectedIndex();
            if (idx >= 0) {
                model.updateUser(idx, ed.getUName(), ed.getUScore(), ed.getUIsEditor());
                ed.clearUserForm();
                refreshEditorLists();
            }
        });
    }

    private void refreshEditorLists() {
        EditorView ed = view.getEditorView();

        // Fragen neu laden
        ed.getQuestionListModel().clear();
        for (Question q : model.getQuestionList()) {
            ed.getQuestionListModel().addElement(q);
        }

        // User neu laden
        ed.getUserListModel().clear();
        for (User u : model.getUserList()) {
            ed.getUserListModel().addElement(u);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String msg = (String) arg;
            switch (msg) {
                case "LOGIN_SUCCESS":
                    view.getMenuView().setUsername(model.getCurrentUser().getUsername(),
                            model.getCurrentUser().isEditor());
                    view.showCard("MENU");
                    break;
                case "GAME_START":
                    view.getGameView().setScore(model.getScoreString());
                    view.getGameView().setTurnLabel(model.getCurrentPlayerName());
                    view.showCard("GAME");
                    view.getGameView().setProgress(model.getProgressString());
                    break;
                case "UPDATE_SCORES":
                    view.getGameView().setScore(model.getScoreString());
                    view.getGameView().setTurnLabel(model.getCurrentPlayerName());
                    break;
                case "GAME_OVER":
                    JOptionPane.showMessageDialog(view, "Spiel vorbei! " + model.getScoreString());
                    view.showCard("MENU");
                    break;
                case "NEW_HIGHSCORE":
                    JOptionPane.showMessageDialog(view, "Neuer Highscore!");
                    break;
                case "DATA_CHANGED":
                    refreshEditorLists();
                    break;
                case "LOGOUT":
                    view.showCard("LOGIN");
                    break;
            }
        } else if (arg instanceof Question) {
            view.getGameView().showQuestion((Question) arg);
            view.getGameView().setProgress(model.getProgressString());
        }
    }

    public void showModernConfirmationDialog(String title, String message, Runnable onConfirm) {
        JDialog dialog = new JDialog(view, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Theme.BACKGROUND_COLOR);

        JLabel lblMsg = new JLabel("<html><center>" + message + "</center></html>", SwingConstants.CENTER);
        lblMsg.setFont(Theme.FONT_REGULAR);
        lblMsg.setForeground(Theme.TEXT_COLOR);
        lblMsg.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialog.add(lblMsg, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Theme.BACKGROUND_COLOR);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        ModernButton btnYes = new ModernButton("Ja");
        ModernButton btnNo = new ModernButton("Nein");

        btnYes.addActionListener(e -> {
            onConfirm.run();
            dialog.dispose();
        });

        btnNo.addActionListener(e -> dialog.dispose());

        btnPanel.add(btnYes);
        btnPanel.add(btnNo);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(view);
        dialog.setSize(400, 200);
        dialog.setVisible(true);
    }
}