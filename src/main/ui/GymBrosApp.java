package ui;

import model.GymBros;
import model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GymBrosApp {
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

    private Boolean loggedIn;
    private User currentlyLoggedInUser;
    private Scanner input;
    private HashMap<String, User> usernamePasswords;
    private String userInput;
    private GymBros gymBros;

    // EFFECTS: creates a new instance of the GymBros application, with loggedIn being false,
    //          the usernamePasswords as empty hashmap, instantiates the Scanner to take in user input
    //          and runs the GymBros application
    public GymBrosApp() {
        loggedIn = false;
        usernamePasswords = new HashMap<>();
        input = new Scanner(System.in);
        currentlyLoggedInUser = null;
        gymBros = new GymBros();
        runGymBros();
    }

    // EFFECTS: processes user input
    private void runGymBros() {
        boolean keepGoing = true;
        String command = null;
        displayMenu();
        command = input.next();
        command = command.toLowerCase();

        if (command.equals(EXIT_COMMAND)) {
            keepGoing = false;
        } else {
            processCommand(command);
        }
    }

    // EFFECTS: processes user command
    public void processCommand(String command) {
        if (command.equals(REGISTER_COMMAND)) {
            registerAccount();
        } else if (command.equals(LOGIN_COMMAND)) {
            logIntoAccount();
        } else {
            System.out.println("Selection not valid!");
        }
    }

    // EFFECTS: displays menu options to the user
    public void displayMenu() {
        System.out.println("What do you want to do?");
        System.out.println("Select " + EXIT_COMMAND + " to exit the app");
        System.out.println("Select  " + REGISTER_COMMAND + " to register a new account");
        System.out.println("Select " + LOGIN_COMMAND + " to log into an existing account");
    }

    // EFFECTS: registers a new account with valid username and password input from the user
    public void registerAccount() {

        System.out.println("Please enter a username less than " + MAX_USERNAME_LENGTH + " characters.");
        String username = input.nextLine();

        System.out.println("Please enter a password greater than " + MIN_PASSWORD_LENGTH + " characters.");
        String password = input.nextLine();

        if (checkUsernameInput(username) && checkPasswordInput(password)) {
            usernamePasswords.put(username, new User(username, password));
            System.out.println("Successfully registered your account! Log in by typing " + LOGIN_COMMAND);
        }
    }

    public Boolean checkUsernameInput(String username) {
        return (username.length() > 1 && username.length() > MAX_USERNAME_LENGTH);
    }

    public Boolean checkPasswordInput(String password) {
        return (password.length() > MIN_PASSWORD_LENGTH);
    }

    public String logIntoAccount() {
        System.out.println("Please enter your username");
        String username = input.nextLine();
        System.out.println("Please enter your password");
        String password = input.nextLine();

        if (checkPasswordWhenLoggingIn(username)) {
            loggedIn = true;
            currentlyLoggedInUser = usernamePasswords.get(username);
            gymBros.login(username);
            System.out.println("Successfully logged in!");
        }
        return NEXT_ACTION_COMMAND;
    }

    public Boolean checkPasswordWhenLoggingIn(String username) {
        if (userInput.equals(EXIT_COMMAND)) {
            return false;
        } else if (!gymBros.getUsernamePasswords().containsKey(username)) {
            System.out.println("This username doesn't exist!");
            return false;
        } else if (!userInput.equals(gymBros.getUsernamePasswords().get(username).getPassword())) {
            System.out.println("Incorrect password!");
            return false;
        } else {
            return true;
        // else if (userInput.equals(gymBros.getUsernamePasswords().get(username).getPassword())) {
           // return true;
        }
        // return true; // ???
    }

}
