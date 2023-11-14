package ui;

import model.User;

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

    public HomeFeed(User user) {
        this.currentUser = user;

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
                // Handle logout button click
                // You can perform logout actions, such as switching to the login page
            }
        });
    }

    public void initButtons(User user) {
        welcomeLabel = new JLabel("Welcome, " + user.getUsername());
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
}
