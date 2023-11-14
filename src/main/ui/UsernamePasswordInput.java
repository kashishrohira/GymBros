package ui;

import model.GymBros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class UsernamePasswordInput extends JPanel {
    protected GymBros gymBros;
    protected JTextField usernameField;
    protected JPasswordField passwordField;

    public UsernamePasswordInput(GymBros gymBros) {
        this.gymBros = gymBros;
        Dimension dimension = new Dimension(200, 50);
        // setting the layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        // Create and add components
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(Box.createVerticalStrut(30));
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField();
        usernameField.setPreferredSize(dimension);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(Box.createVerticalStrut(30));
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(dimension);
        add(passwordField, gbc);

        setBorder(BorderFactory.createEmptyBorder(100, 0, 50, 0));
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
