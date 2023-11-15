package ui;

import model.GymBros;
import model.User;
import ui.LoginRegistration.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFeed extends JPanel {
    private JLabel welcomeLabel;
    private JButton profileButton;
    private JButton workoutLogButton;
    private JButton logoutButton;
    private JButton addWorkoutButton;
    private JButton followUserButton;

    private User currentUser;
    private GymBros gymBros;
    private LoginPage loginPage;

    public HomeFeed(User user, GymBros gymBros, LoginPage loginPage) {
        this.currentUser = user;
        this.gymBros = gymBros;
        this.loginPage = loginPage;

        initButtons(user);

        setHomeLayout();

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProfilePage();
            }
        });

        workoutLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showWorkoutLogPage();
            }
        });

        addWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddWorkoutPage();
            }
        });

        followUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle workout log button click
                // You can navigate to the workout log page or perform any relevant action
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout(e);
            }
        });
    }

    public void initButtons(User user) {
        welcomeLabel = new JLabel("Welcome, " + user.getUsername()
                + ". You've taken the first step for a healthier, happier you!");
        profileButton = new JButton("My Profile");
        workoutLogButton = new JButton("My Workout Log");
        addWorkoutButton = new JButton("Add a new workout");
        followUserButton = new JButton("Follow other users");
        logoutButton = new JButton("Logout");
    }

    // MODIFIES: this
    // EFFECTS: sets the layout of the home feed with box layout
    public void setHomeLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setBackground(Color.BLACK);  // Set background color to black
        setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(40));
        add(welcomeLabel);
        add(Box.createVerticalStrut(20));
        add(profileButton);
        add(Box.createVerticalStrut(10));
        add(addWorkoutButton);
        add(Box.createVerticalStrut(10));
        add(workoutLogButton);
        add(Box.createVerticalStrut(10));
        add(followUserButton);
        add(Box.createVerticalStrut(10));
        add(logoutButton);
    }

    public void showWorkoutLogPage() {
        removeAll(); // Clear the content of the current panel

        WorkoutLog workoutLog = new WorkoutLog(currentUser, this);
        add(workoutLog);

        revalidate();
        repaint();
    }

    public void showProfilePage() {
        removeAll(); // Clear the content of the current panel

        ProfilePage profilePage = new ProfilePage(currentUser, this);
        add(profilePage);

        revalidate();
        repaint();
    }

    public void showAddWorkoutPage() {
        removeAll(); // Clear the content of the current panel

        // Assuming you have a method to get the currently logged-in user
        AddWorkoutPage addWorkoutPage = new AddWorkoutPage(currentUser, this);
        add(addWorkoutPage);

        revalidate();
        repaint();
    }

    public void handleLogout(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(HomeFeed.this,
                "Do you want to save your data?", "Logout",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
//            GymBrosApp.saveAccount(currentUser);
        } else if (option == JOptionPane.NO_OPTION) {
            // do nothing
        } else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            return;
        }

        logOutUser();
    }

    public void logOutUser() {
        gymBros.logOut();
        showLoginPage();
    }

    public void showLoginPage() {
        removeAll();

        // Add the components for the home page
        loginPage.setLoginPageLayout();

        // Refresh the panel
        revalidate();
        repaint();

    }


}
