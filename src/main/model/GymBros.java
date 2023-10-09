package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GymBros {
    public static final String VIEW_WORKOUT_LOG_COMMAND = "/viewlog";
    public static final String MAKE_POST_COMMAND = "/post";
    public static final String LOGOUT_COMMAND = "/logout";
    public static final String LOGIN_COMMAND = "/login";
    public static final String HOME_COMMAND = "/home";
    public static final String VIEW_USER_COMMAND = "/user";
    public static final String REGISTER_COMMAND = "/register";
    public static final String EXIT_COMMAND = "/exit";
    public static final String NEXT_ACTION_COMMAND = "action";
    public static final String CREATE_COMMUNITY_COMMAND = "/create";
    public static final String HELP_COMMAND = "/help";
    public static final String EDIT_PROFILE_COMMAND = "/edit";
    public static final String SELF_PROFILE_COMMAND = "me";
    public static final String VIEW_USER_POSTS = "/userposts";

    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;

    public static final List<String> EXIT_FEED_COMMANDS =
            Arrays.asList(MAKE_POST_COMMAND, LOGIN_COMMAND, LOGOUT_COMMAND,
                    HOME_COMMAND, VIEW_USER_COMMAND, REGISTER_COMMAND, EXIT_COMMAND,
                    CREATE_COMMUNITY_COMMAND);

    // fields
    private Boolean loggedIn;
    private User currentlyLoggedInUser;

    private HashMap<String, User> usernamePasswords;

    // EFFECTS: creates a new instance of the GymBros application with loggedIn being false,
    // no logged-in user instantiates, the usernamePasswords as empty Hashmap
    public GymBros() {
        loggedIn = false;
        usernamePasswords = new HashMap<>();
        currentlyLoggedInUser = null;
    }

    // EFFECTS: true if user is logged in, false otherwise
    public Boolean getLoggedIn() {
        return this.loggedIn;
    }

    // EFFECTS: logs the user out of the app and clears the active feed
    public void logOut() {
        loggedIn = false;
        currentlyLoggedInUser = null;
    }

    // REQUIRES: given user has already been registered with a valid username and password
    // MODIFIES: this
    // EFFECTS: adds the given username and user to the map of registered users
    public void addUser(String username, User user) {
        this.usernamePasswords.put(username, user);
    }

    // EFFECTS: returns the registered users on this app
    public HashMap<String, User> getUsernamePasswords() {
        return this.usernamePasswords;
    }

    // MODIFIES: this
    // EFFECTS: logs the user into the app
    public void login(String username) {
        loggedIn = true;
        currentlyLoggedInUser = usernamePasswords.get(username);
    }

    // EFFECTS: returns the currently logged-in user
    public User getCurrentUser() {
        return this.currentlyLoggedInUser;
    }

    // REQUIRES: currentlyLoggedInUser != null, and loggedIn is True
    // MODIFIES: this
    // EFFECTS: changes the currently logged-in user's bio to the new bio
    public void updateBio(String newBio) {
        currentlyLoggedInUser.setBio(newBio);
    }

    // MODIFIES: this
    // EFFECTS: sets the loggedIn status to the given boolean
    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // REQUIRES: given user is a registered user on GymBros
    // MODIFIES: this
    // EFFECTS: sets the current user to the given user
    public void setCurrentlyLoggedInUser(User currentlyLoggedInUser) {
        this.currentlyLoggedInUser = currentlyLoggedInUser;
    }

    // REQUIRES: map contains registered users on GymBros
    //           each username must match with the corresponding user
    // MODIFIES: this
    // EFFECTS: sets the map of usernames and users to the given map
    public void setUsernamePasswords(HashMap<String, User> usernamePasswords) {
        this.usernamePasswords = usernamePasswords;
    }
}
