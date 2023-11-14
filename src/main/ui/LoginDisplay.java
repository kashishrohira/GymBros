package ui;

import model.GymBros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDisplay extends UsernamePasswordInput {
    private JButton loginButton;
    private JButton cancelButton;
    private LoginPage loginPage;

    public LoginDisplay(GymBros gymBros, LoginPage loginPage) {
        super(gymBros);
        this.loginPage = loginPage;

        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = passwordField.getText();
                boolean authenticated = gymBros.authenticateUser(username, password);

                if (authenticated) {
                    JOptionPane.showMessageDialog(LoginDisplay.this, "Successfully logged in!");
                } else {
                    JOptionPane.showMessageDialog(LoginDisplay.this,
                            "Incorrect username or password. Try again.");
                    clearFields();
                }

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPage.showLoginPage(); // Switch to the login page
            }
        });

        loginButton.setPreferredSize(new Dimension(200, 30));

        cancelButton.setPreferredSize(new Dimension(200, 30));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(40));
        add(loginButton);
        add(Box.createVerticalStrut(20));
        add(cancelButton);
    }



}
