package view;

import model.Question;
import model.User;

import javax.swing.*;
import java.awt.*;

public class EditorView extends JPanel {
    private JTabbedPane tabbedPane;

    // --- KOMPONENTEN FÜR FRAGEN ---
    private JList<Question> questionList;
    private DefaultListModel<Question> questionListModel;
    private JTextField qText;
    private JTextField[] qAnswers;
    private JComboBox<Integer> qCorrectBox;
    private JButton btnAddQ, btnUpdateQ, btnDeleteQ, btnClearQ;

    // --- KOMPONENTEN FÜR USER ---
    private JList<User> userList;
    private DefaultListModel<User> userListModel;
    private JTextField uName;
    private JSpinner uScore;
    private JCheckBox uIsEditor;
    private JButton btnAddU, btnUpdateU, btnDeleteU, btnClearU;

    private JButton btnBack;

    public EditorView() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Theme.SURFACE_COLOR);
        tabbedPane.setForeground(Theme.TEXT_COLOR);

        // Tab 1: Fragen
        JPanel questionsPanel = createQuestionsPanel();
        tabbedPane.addTab("Fragen verwalten", questionsPanel);

        // Tab 2: User
        JPanel usersPanel = createUsersPanel();
        tabbedPane.addTab("Benutzer verwalten", usersPanel);

        add(tabbedPane, BorderLayout.CENTER);

        btnBack = new ModernButton("Zurück zum Hauptmenü");
        add(btnBack, BorderLayout.SOUTH);
    }

    private JPanel createQuestionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BACKGROUND_COLOR);

        // Liste Links
        questionListModel = new DefaultListModel<>();
        questionList = new JList<>(questionListModel);
        questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionList.setBackground(Theme.SURFACE_COLOR);
        questionList.setForeground(Theme.TEXT_COLOR);

        JScrollPane listScroll = new JScrollPane(questionList);
        listScroll.setPreferredSize(new Dimension(200, 0));
        panel.add(listScroll, BorderLayout.WEST);

        // Formular Rechts
        JPanel form = new JPanel(new GridLayout(8, 2, 5, 5));
        form.setBackground(Theme.BACKGROUND_COLOR);

        qText = createStyledTextField();
        qAnswers = new JTextField[4];
        for (int i = 0; i < 4; i++)
            qAnswers[i] = createStyledTextField();

        qCorrectBox = new JComboBox<>(new Integer[] { 0, 1, 2, 3 });
        qCorrectBox.setBackground(Theme.SURFACE_COLOR);
        qCorrectBox.setForeground(Theme.TEXT_COLOR);

        form.add(createLabel("Frage:"));
        form.add(qText);
        form.add(createLabel("Antwort A (0):"));
        form.add(qAnswers[0]);
        form.add(createLabel("Antwort B (1):"));
        form.add(qAnswers[1]);
        form.add(createLabel("Antwort C (2):"));
        form.add(qAnswers[2]);
        form.add(createLabel("Antwort D (3):"));
        form.add(qAnswers[3]);
        form.add(createLabel("Korrekter Index:"));
        form.add(qCorrectBox);

        // Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Theme.BACKGROUND_COLOR);

        btnAddQ = new ModernButton("Neu Hinzufügen");
        btnUpdateQ = new ModernButton("Speichern (Ändern)");
        btnDeleteQ = new ModernButton("Löschen");
        btnClearQ = new ModernButton("Formular leeren");

        btnUpdateQ.setEnabled(false); // Erst aktiv wenn ausgewählt
        btnDeleteQ.setEnabled(false);

        btnPanel.add(btnClearQ);
        btnPanel.add(btnAddQ);
        btnPanel.add(btnUpdateQ);
        btnPanel.add(btnDeleteQ);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Theme.BACKGROUND_COLOR);
        centerPanel.add(form, BorderLayout.NORTH);
        centerPanel.add(btnPanel, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.BACKGROUND_COLOR);

        // Liste Links
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setBackground(Theme.SURFACE_COLOR);
        userList.setForeground(Theme.TEXT_COLOR);

        JScrollPane scroll = new JScrollPane(userList);
        scroll.setPreferredSize(new Dimension(200, 0));
        panel.add(scroll, BorderLayout.WEST);

        // Formular Rechts
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.setBackground(Theme.BACKGROUND_COLOR);

        uName = createStyledTextField();
        uScore = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 10));
        // Style Spinner mostly relies on LookAndFeel but we can try to set colors
        // For simplicity, we just leave it or try to access its editor

        uIsEditor = new JCheckBox("Ist Admin/Editor?");
        uIsEditor.setBackground(Theme.BACKGROUND_COLOR);
        uIsEditor.setForeground(Theme.TEXT_COLOR);

        form.add(createLabel("Benutzername:"));
        form.add(uName);
        form.add(createLabel("Highscore:"));
        form.add(uScore);
        form.add(createLabel("Rechte:"));
        form.add(uIsEditor);

        // Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Theme.BACKGROUND_COLOR);

        btnAddU = new ModernButton("Neu Hinzufügen");
        btnUpdateU = new ModernButton("Speichern (Ändern)");
        btnDeleteU = new ModernButton("Löschen");
        btnClearU = new ModernButton("Formular leeren");

        btnUpdateU.setEnabled(false);
        btnDeleteU.setEnabled(false);

        btnPanel.add(btnClearU);
        btnPanel.add(btnAddU);
        btnPanel.add(btnUpdateU);
        btnPanel.add(btnDeleteU);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Theme.BACKGROUND_COLOR);
        centerPanel.add(form, BorderLayout.NORTH);
        centerPanel.add(btnPanel, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }

    // --- Getter & Helper ---
    public JList<Question> getQuestionList() {
        return questionList;
    }

    public DefaultListModel<Question> getQuestionListModel() {
        return questionListModel;
    }

    public JList<User> getUserList() {
        return userList;
    }

    public DefaultListModel<User> getUserListModel() {
        return userListModel;
    }

    public JButton getBtnBack() {
        return btnBack;
    }

    // Fragen Formular Getter
    public String getQText() {
        return qText.getText();
    }

    public String getQAnswer(int i) {
        return qAnswers[i].getText();
    }

    public int getQCorrect() {
        return (int) qCorrectBox.getSelectedItem();
    }

    public JButton getBtnAddQ() {
        return btnAddQ;
    }

    public JButton getBtnUpdateQ() {
        return btnUpdateQ;
    }

    public JButton getBtnDeleteQ() {
        return btnDeleteQ;
    }

    public JButton getBtnClearQ() {
        return btnClearQ;
    }

    public void setQuestionForm(Question q) {
        qText.setText(q.getText());
        for (int i = 0; i < 4; i++)
            qAnswers[i].setText(q.getAnswers()[i]);
        qCorrectBox.setSelectedItem(q.getCorrectIndex());
        btnUpdateQ.setEnabled(true);
        btnDeleteQ.setEnabled(true);
        btnAddQ.setEnabled(false);
    }

    public void clearQuestionForm() {
        qText.setText("");
        for (int i = 0; i < 4; i++)
            qAnswers[i].setText("");
        qCorrectBox.setSelectedIndex(0);
        questionList.clearSelection();
        btnUpdateQ.setEnabled(false);
        btnDeleteQ.setEnabled(false);
        btnAddQ.setEnabled(true);
    }

    // User Formular Getter
    public String getUName() {
        return uName.getText();
    }

    public int getUScore() {
        return (Integer) uScore.getValue();
    }

    public boolean getUIsEditor() {
        return uIsEditor.isSelected();
    }

    public JButton getBtnAddU() {
        return btnAddU;
    }

    public JButton getBtnUpdateU() {
        return btnUpdateU;
    }

    public JButton getBtnDeleteU() {
        return btnDeleteU;
    }

    public JButton getBtnClearU() {
        return btnClearU;
    }

    public void setUserForm(User u) {
        uName.setText(u.getUsername());
        uScore.setValue(u.getUserHighscore());
        uIsEditor.setSelected(u.isEditor());
        btnUpdateU.setEnabled(true);
        btnDeleteU.setEnabled(true);
        btnAddU.setEnabled(false);
    }

    public void clearUserForm() {
        uName.setText("");
        uScore.setValue(0);
        uIsEditor.setSelected(false);
        userList.clearSelection();
        btnUpdateU.setEnabled(false);
        btnDeleteU.setEnabled(false);
        btnAddU.setEnabled(true);
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setFont(Theme.FONT_INPUT);
        tf.setBackground(Theme.SURFACE_COLOR);
        tf.setForeground(Theme.TEXT_COLOR);
        tf.setCaretColor(Theme.TEXT_COLOR);
        tf.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return tf;
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Theme.TEXT_COLOR);
        l.setFont(Theme.FONT_REGULAR);
        return l;
    }
}