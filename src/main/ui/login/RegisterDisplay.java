package ui.login;

import model.GymBros;
import model.User;
import ui.UsernamePasswordInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDisplay extends UsernamePasswordInput {
    private JButton registerButton;
    private JButton cancelButton;
    private JButton loginButton;
    private LoginPage loginPage;


    // REQUIRES: gymBros and loginPage are not null
    // EFFECTS: constructs a new RegisterDisplay panel with specified gymBros instance and a reference
    //          to the login Page
    public RegisterDisplay(GymBros gymbros, LoginPage loginPage) {
        super(gymbros);
        this.loginPage = loginPage;

        init();

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister(e);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPage(); // Switch to the login page
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginDisplay(); // Show the login display page
            }
        });

        setupLayout();
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons for registerDisplay
    public void init() {
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        loginButton = new JButton("Login to your account");
        loginButton.setVisible(false);

        Dimension buttonSize = new Dimension(200, 30);
        registerButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);
        loginButton.setPreferredSize(buttonSize);
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
        registerButton.setVisible(false);
    }


    // EFFECTS: displays the LoginDisplay
    public void showLoginDisplay() {
        loginPage.showLoginDisplay(gymBros);
    }

    // MODIFIES: this
    // EFFECTS: sets the current panel to loginPage layout
    public void showLoginPage() { // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        loginPage.removeAll();

        // Add the components for the home page
        loginPage.setLoginPageLayout();

        // Refresh the panel
        loginPage.revalidate();
        loginPage.repaint();
    }

    // MODIFIES: this, usernameField, PasswordField, loginPage
    // EFFECTS: takes the username and input from the text fields and tries to authenticate the new user
    public void handleRegister(ActionEvent e) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean authenticated = authenticateNewUser(username, password);

        if (authenticated) {
            int option = JOptionPane.showOptionDialog(RegisterDisplay.this,
                    "Registration successful! Click Login to log in.",
                    "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, new Object[]{"Login"}, null);
            if (option == 0) {
                showLoginDisplay();
            }
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
        add(Box.createVerticalStrut(20));
        add(loginButton);
    }


}
