package ui;

import model.GymBros;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDisplay extends UsernamePasswordInput {
    private JButton registerButton;
    private JButton cancelButton;
    private JButton loginButton;
    private LoginPage loginPage;


    // EFFECTS: constructs a new RegisterDisplay panel with specified Gymbros instance and a reference
    //          to the login Page
    public RegisterDisplay(GymBros gymbros, LoginPage loginPage) {
        super(gymbros);
        this.loginPage = loginPage;

        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");

        Dimension buttonSize = new Dimension(200, 30);
        registerButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister(e);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPage.showLoginPage(); // Switch to the login page
            }
        });

//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showLoginDisplay(); // Show the login display page
//            }
//        });

        setupLayout();
    }

    // EFFECTS: returns true if user enters valid username and password and registers successfully, false otherwise
    public boolean authenticateNewUser(String username, String password) {
        if (gymBros.checkUsernameInput(username) && gymBros.checkPasswordInput(password)
                && gymBros.isUsernameUnique(username)) {
            User newUser = new User(username, password);
            gymBros.createNewUser(newUser);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the loginButton
    public void showLoginButton() {
        loginPage.setVisible(true);
    }


    // EFFECTS: displays the LoginDisplay
    public void showLoginDisplay() {
        loginPage.showLoginDisplay(gymBros);
    }

    // MODIFIES: this, usernameField, PasswordField, loginPage
    // EFFECTS: takes the username and input from the text fields and tries to authenticate the new user
    public void handleRegister(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean authenticated = authenticateNewUser(username, password);

        if (authenticated) {
            JOptionPane.showMessageDialog(RegisterDisplay.this,
                    "Registration successful!");
            showLoginButton();
        } else {
            JOptionPane.showMessageDialog(RegisterDisplay.this,
                    "Invalid username or password. Try again.");
            clearFields();
        }
        loginPage.showLoginPage(); // Switch to the login page
    }

    // MODIFIES: this
    // EFFECTS: sets the layout with vertical box layout and adds components to the panel
    public void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(40));
        add(registerButton);
        add(Box.createVerticalStrut(20));
        add(cancelButton);
    }
}
