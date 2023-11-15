package ui;

import model.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static java.awt.AWTEventMulticaster.add;

public class ProfilePage extends JPanel {
    private JLabel usernameLabel;
    private JLabel bioLabel;
    private JButton followingButton;
    private JButton followersButton;
    private JButton backButton;
    private JButton editBioButton;

    private User currentUser;
    private HomeFeed homeFeed;

    public ProfilePage(User user, HomeFeed homeFeed) {
        this.currentUser = user;
        this.homeFeed = homeFeed;

        init(user);

        setProfileLayout();

        followingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFollowingList();
            }
        });

        followersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFollowersList();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToHomePage();
            }
        });

        editBioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBio();
            }
        });
    }

    public void init(User user) {
        usernameLabel = new JLabel("Your username is " + user.getUsername());
        bioLabel = new JLabel("Your bio is " + user.getBio());
        followingButton = new JButton("Following");
        followersButton = new JButton("Followers");
        backButton = new JButton("Back to Home");
        editBioButton = new JButton("Click here to edit your bio!");
    }

    // MODIFIES: this
    // EFFECTS: sets the layout of the profile page using box layout
    public void setProfileLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components
        add(Box.createVerticalStrut(40));
        add(usernameLabel);
        add(Box.createVerticalStrut(10));
        add(bioLabel);
        add(Box.createVerticalStrut(20));
        add(followingButton);
        add(Box.createVerticalStrut(10));
        add(followersButton);
        add(Box.createVerticalStrut(20));
        add(editBioButton);
        add(Box.createVerticalStrut(20));
        add(backButton);

    }


    private void showFollowingList() {
        List<String> followingList = currentUser.getFollowing();

        if (followingList.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "You are not following anyone yet. Must be lonely :(",
                    "Following List", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String followingMessage = "Users you are following:\n" + String.join(", ", followingList);
            JOptionPane.showMessageDialog(this, followingMessage, "Following List",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void showFollowersList() {
        List<String> followersList = currentUser.getFollowers();

        if (followersList.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nobody follows you yet! Be more interesting",
                    "Following List", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String followingMessage = "Users you are following:\n" + String.join(", ", followersList);
            JOptionPane.showMessageDialog(this, followingMessage, "Followers List",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void editBio() {
        String newBio = JOptionPane.showInputDialog(this, "Enter your new bio:");
        if (newBio != null) {  // User clicked OK
            currentUser.setBio(newBio);
            bioLabel.setText("Your bio is " + newBio);
        }
    }

    public void backToHomePage() {
        removeAll();

        // Add the components for the home page
        homeFeed.setHomeLayout();

        // Refresh the panel
        revalidate();
        repaint();
    }
}
