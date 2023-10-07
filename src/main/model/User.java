package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User {
    // Constants
    public static final String defaultBio = " ";

    // Fields
    private final String userName;
    private String password;
    private String bio;
    private List<User> following;
    private List<User> followers;

    private List<Workout> workoutLog;


    // EFFECTS: creates a user with the given username and password
    //          with a default bio, no followers, no following and
    //          an empty workout log
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.bio = defaultBio;
        this.followers = new ArrayList<User>();
        this.following = new ArrayList<User>();
        this.workoutLog = new ArrayList<Workout>();
    }

    // EFFECTS: returns the user's username
    public String getUsername() {
        return this.userName;
    }

    // EFFECTS: returns the user's password
    public String getPassword() {
        return this.password;
    }

    // EFFECTS: returns the user's bio
    public String getBio() {
        return this.bio;
    }

    // EFFECTS: returns the list of users that this user is following
    //          in the order they were added
    public List<User> getFollowing() {
        return this.following;
    }

    // EFFECTS: returns the list of users followers of this user
    //          in the order they followed
    public List<User> getFollowers() {
        return this.followers;
    }

    // EFFECTS: returns the user's workout log
    public List<Workout> getWorkoutLog() {
        return this.workoutLog;
    }

    // MODIFIES: this !!!!!!!!!!!!!!!!!!!!!!!!!! (ADD THIS USER TO THE GIVEN USER'S FOLLOWERS)
    // EFFECTS: adds the given user to the following list of this user
    public void addToFollowing(User user) {
        this.following.add(user);
    }

    // MODIFIES: this !!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // EFFECTS: adds the given user to this user's followers
    public void addFollower(User user) {
        this.followers.add(user);
    }

    // MODIFIES, this, user????????????????
    // EFFECTS: removes the given user from this user's following list
    public void removeFromFollowing(User user) {
        this.following.remove(user);
    }

    // MODIFIES, this, user????????????????
    // EFFECTS: removes the given user from this user's following list
    public void removeFollower(User user) {
        this.followers.remove(user);
    }

    // MODIFIES: this
    // EFFECTS: adds given workout to the user's workout log
    public void addWorkout(Workout workout) {
        this.workoutLog.add(workout);
    }

    // REQUIRES: !!!
    // MODIFIES: this
    // EFFECTS: sets user's password to given string
    public void setPassword(String password) {
        this.password = password;
    }

    // MODIFIES: this
    // EFFECTS: sets the user's bio to given string
    public void setBio(String bio) {
        this.bio = bio;
    }

}
