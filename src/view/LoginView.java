package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private JTextField nameField;
    private JButton loginButton;

    public LoginView() {
        setLayout(new GridBagLayout());
        setBackground(Theme.BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Willkommen beim QuizMaster");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT_COLOR);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel label = new JLabel("Bitte Namen eingeben:");
        label.setFont(Theme.FONT_REGULAR);
        label.setForeground(Theme.TEXT_SECONDARY_COLOR);

        nameField = new JTextField(15);
        nameField.setFont(Theme.FONT_INPUT);
        nameField.setBackground(Theme.SURFACE_COLOR);
        nameField.setForeground(Theme.TEXT_COLOR);
        nameField.setCaretColor(Theme.TEXT_COLOR);
        nameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        loginButton = new ModernButton("Anmelden");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);
        gbc.gridy = 1;
        add(label, gbc);
        gbc.gridy = 2;
        add(nameField, gbc);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(loginButton, gbc);
    }

    public String getUsername() {
        return nameField.getText().trim();
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}