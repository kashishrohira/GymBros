package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GymBros {

    // fields
    private Boolean loggedIn;
    private User currentlyLoggedInUser;
    private String username;
    private String password;

    private HashMap<String, User> usernameUser;
    private HashMap<String, String> usernamePassword;
    private String date;

    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;

    // EFFECTS: creates a new instance of the GymBros application with loggedIn being false,
    // no logged-in user instantiates, the usernameUser as empty Hashmap
    public GymBros() {
        loggedIn = false;
        usernameUser = new HashMap<>();
        usernamePassword = new HashMap<>();
        currentlyLoggedInUser = null;
    }

    // EFFECTS: returns the log in state of the user
    public boolean getLoggedIn() {
        return this.loggedIn;
    }

    // EFFECTS: returns the hashmap containing usernames and user objects
    public HashMap<String, User> getUsernameUser() {
        return this.usernameUser;
    }

    // EFFECTS: returns the hashmap containing usernames and passwords
    public HashMap<String, String> getUsernamePassword() {
        return this.usernamePassword;
    }

    // EFFECTS: returns the currently logged-in user
    public User getCurrentlyLoggedInUser() {
        return this.currentlyLoggedInUser;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new user and adds the user to the list of all users on the app
    public void createNewUser(String username, String password) {
        currentlyLoggedInUser = new User(username, password);
        usernameUser.put(username, currentlyLoggedInUser);
        usernamePassword.put(username, password);
    }

    // EFFECTS: returns true if username exists on the app, false otherwise
    public Boolean checkUsernameWhenLoggingIn(String username) {
        return (usernamePassword.containsKey(username));
    }

    // REQUIRES: username should exist in the app
    // EFFECTS: returns true if password matches the username, false otherwise
    public Boolean checkPasswordWhenLoggingIn(String username, String password) {
        return (password.equals(usernamePassword.get(username)));
    }

    // EFFECTS: returns true if the username is at least one character and less than or
    //          equal to max length, false otherwise
    public Boolean checkUsernameInput(String username) {
        return (username.length() > 1 && username.length() <= MAX_USERNAME_LENGTH);
    }

    // EFFECTS: returns true if password is greater than or equal to minimum length, false otherwise
    public Boolean checkPasswordInput(String password) {
        return (password.length() >= MIN_PASSWORD_LENGTH);
    }

    // EFFECTS: returns true if username doesn't already exist in the app, false otherwise
    public boolean isUsernameUnique(String username) {
        return (!usernameUser.containsKey(username));
    }

    // EFFECTS: returns true if a user with the given username exists on the app, false otherwise
    public boolean doesUserExist(String username) {
        return (usernameUser.containsKey(username));
    }

    // REQUIRES: username exists in the app
    // EFFECTS: returns the user with the given username
    public User getUserWithUsername(String username) {
        return usernameUser.get(username);
    }



}

