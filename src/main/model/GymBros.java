package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

public class GymBros implements Writable {

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
    public void createNewUser(User user) {
        currentlyLoggedInUser = user;
        usernameUser.put(user.getUsername(), user);
        usernamePassword.put(user.getUsername(), user.getPassword());
        EventLog.getInstance().logEvent(new Event("Added user " + user.getUsername() + " to the app."));
    }

    // EFFECTS: returns true if username exists on the app, false otherwise
    public Boolean checkUsernameWhenLoggingIn(String username) {
        return (usernameUser.containsKey(username));
    }

    // REQUIRES: username should exist in the app
    // EFFECTS: returns true if password matches the username, false otherwise
    public Boolean checkPasswordWhenLoggingIn(String username, String password) {
        return (password.equals(usernameUser.get(username).getPassword()));
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

    // EFFECTS: returns the number of users on the app
    public int getNumUsers() {
        for (Map.Entry<String, User> entry : usernameUser.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
        return usernameUser.size();
    }

    // MODIFIES: this
    // EFFECTS: logs the user out of the app
    public void logOut() {
        loggedIn = false;
        EventLog.getInstance().logEvent(new Event(currentlyLoggedInUser.getUsername() + " is logged out."));
        currentlyLoggedInUser = null;
    }


    // REQUIRES: given user is registered on the app
    // MODIFIES: this
    // EFFECTS: sets the current user to the given user
    public void setCurrentlyLoggedInUser(User currentlyLoggedInUser) {
        this.currentlyLoggedInUser = currentlyLoggedInUser;
        if (currentlyLoggedInUser != null) {
            EventLog.getInstance().logEvent(new Event(currentlyLoggedInUser.getUsername() + " is logged in."));
        }

    }

    // MODIFIES: this
    // EFFECTS: sets the loggedIn status of the user to the given boolean
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // REQUIRES: map contains registered users on the app
    // MODIFIES: this
    // EFFECTS: sets the map of usernames and users and users to the given map
    public void setUsernameUsers(HashMap<String, User> usernameUsers) {
        this.usernameUser = usernameUsers;
    }


    // EFFECTS: returns true if username and password match an existing user on the app, false otherwise
    public boolean authenticateUser(String username, String password) {
        if (checkUsernameWhenLoggingIn(username) == false) {
            return false;
        } else if (checkPasswordWhenLoggingIn(username, password) == false) {
            return false;
        } else {
            loggedIn = true;
            EventLog.getInstance().logEvent(new Event(username + " is authenticated."));
            setCurrentlyLoggedInUser(getUserWithUsername(username));
            return true;
        }
    }

    @Override
    // EFFECTS: returns GymBros as a JSONObject
    public JSONObject toJson() {
        JSONObject gymBros = new JSONObject();
        gymBros.put("loggedIn",this.loggedIn);
        if (currentlyLoggedInUser != null) {
            gymBros.put("user", currentlyLoggedInUser.toJson());
        } else {
            gymBros.put("user","null");
        }

        gymBros.put("users", usersToJson());
        return gymBros;
    }

    // EFFECTS: returns the users on the app as a JSONArray
    private JSONArray usersToJson() {
        JSONArray users = new JSONArray();

        for (User u: usernameUser.values()) {
            users.put(u.toJson());
        }

        return users;
    }

}

