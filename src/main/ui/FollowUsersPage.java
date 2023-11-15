package ui;

import model.GymBros;
import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FollowUsersPage extends JPanel {
    private User currentUser;
    private HomeFeed homeFeed;
    private GymBros gymBros;
    private JTextField usernameField;
    private JButton searchButton;
    private JButton backButton;

    public FollowUsersPage(User currentUser, HomeFeed homeFeed, GymBros gymBros) {
        this.currentUser = currentUser;
        this.homeFeed = homeFeed;
        this.gymBros = gymBros;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        init();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchForUser();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToHomePage();
            }
        });

        add(new JLabel("Enter username to follow:"));
        add(usernameField);
        add(searchButton);
        add(backButton);
    }

    public void init() {
        usernameField = new JTextField(20);
        searchButton = new JButton("Search");
        backButton = new JButton("Back to Home");
    }

    private void searchForUser() {
        String usernameToFollow = usernameField.getText();

        boolean exists = gymBros.doesUserExist(usernameToFollow);

        if (exists) {
            User userToFollow = gymBros.getUserWithUsername(usernameToFollow);
            followUser(usernameToFollow);
            displayUserProfile(userToFollow);
        } else {
            showUserNotFoundDialog();
        }
    }

    private void displayUserProfile(User user) {
        removeAll();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel topLabel = new JLabel("You are viewing " + user.getUsername() + "'s profile!");
        add(topLabel);

        JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
        JLabel bioLabel = new JLabel("Bio: " + user.getBio());
        JLabel followingLabel = new JLabel("Following: " + String.join(", ", user.getFollowing()));
        JLabel followersLabel = new JLabel("Followers: " + String.join(", ", user.getFollowers()));

        // Add labels to the panel
        add(usernameLabel);
        add(bioLabel);
        add(followingLabel);
        add(followersLabel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToHomePage();
            }
        });
        add(backButton);

        revalidate();
        repaint();
    }

    private void followUser(String username) {
        currentUser.addToFollowing(username);
        User u = gymBros.getUserWithUsername(username);

        String successMessage = "You are now following " + username;

        if (u.getFollowers().isEmpty() || !u.getFollowers().contains(currentUser)) {
            u.addFollower(currentUser.getUsername());
            JOptionPane.showMessageDialog(null, successMessage,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "You are already following " + username,
                    "Already following", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showUserNotFoundDialog() {
        JOptionPane.showMessageDialog(this,
                "User not found. Please enter a valid username.",
                "User Not Found", JOptionPane.ERROR_MESSAGE);
    }

    private void backToHomePage() {
        removeAll();

        // Add the components for the home page
        homeFeed.setHomeLayout();

        // Refresh the panel
        revalidate();
        repaint();
    }


}
