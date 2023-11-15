package ui.LoginRegistration;

import model.GymBros;
import model.User;
import ui.HomeFeed;
import ui.UsernamePasswordInput;

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
                    gymBros.setCurrentlyLoggedInUser(gymBros.getUserWithUsername(username));
                    JOptionPane.showMessageDialog(LoginDisplay.this, "Successfully logged in!");
                    showHomePage();
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

    private void showHomePage() {
        removeAll(); // Clear the content of the current panel

        // Assuming you have a method to get the currently logged-in user
        User loggedInUser = gymBros.getCurrentlyLoggedInUser();
        HomeFeed homePage = new HomeFeed(loggedInUser, gymBros, loginPage);

        add(homePage); // Add the home page to the current panel
        revalidate();
        repaint();
    }
}
