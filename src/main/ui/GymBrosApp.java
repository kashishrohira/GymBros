package ui;

import model.Exercise;
import model.GymBros;
import model.User;
import model.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GymBrosApp {
    // register/login commands
    public static final String LOGIN_COMMAND = "/login";
    public static final String REGISTER_COMMAND = "/register";
    public static final String LOGOUT_COMMAND = "/logout";
    public static final String SAVE_ACCOUNT = "/save";
    public static final String LOAD_ACCOUNT = "/load";
    // fitness-tracking commands
    public static final String VIEW_WORKOUT_LOG_COMMAND = "/viewlog";
    public static final String ADD_EXERCISE_COMMAND = "/exercise";
    public static final String TRACKER_COMMAND = "/track";
    public static final String HOME_COMMAND = "/home";

    // profile commands
    public static final String EDIT_PROFILE_COMMAND = "/edit";
    public static final String FOLLOW_USER_COMMAND = "/follow";
    public static final String SELF_PROFILE_COMMAND = "me";
    public static final String VIEW_USER_POSTS = "/userposts";

    // other
    public static final String EXIT_COMMAND = "/exit";


    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;


    private Boolean loggedIn;
    private User currentlyLoggedInUser;
    private Scanner input;
    private HashMap<String, User> usernameUser;
    private HashMap<String, String> usernamePassword;
    private String userInput;
    private GymBros gymBros;
    private JsonWriter writer;
    private JsonReader reader;
    private String jsonUser = "./data/user1.json";

    // EFFECTS: creates a new instance of the GymBros application, with loggedIn being false,
    //          the usernameUser as empty hashmap, instantiates the Scanner to take in user input
    //          and runs the GymBros application
    public GymBrosApp() throws FileNotFoundException {
        loggedIn = false;
        usernameUser = new HashMap<>();
        input = new Scanner(System.in);
        currentlyLoggedInUser = null;
        gymBros = new GymBros();
        writer = new JsonWriter(jsonUser);
        reader = new JsonReader(jsonUser);
        runGymBros();
    }


    // EFFECTS: processes user input
    private void runGymBros() {
        boolean keepGoing1 = true;
        boolean keepGoing = true;
        String command = null;


        while (loggedIn == false) {
            displayLoginMenu();
            command = input.next();
            command = command.toLowerCase();
            processLoginCommand(command);
        }

        while (keepGoing) {
            displayNextMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals(EXIT_COMMAND)) {
                keepGoing = false;
            } else {
                processNextCommand(command);
            }
        }
    }

    // EFFECTS: processes user command
    public void processLoginCommand(String command) {
        if (command.equals(REGISTER_COMMAND)) {
            registerAccount();
        } else if (command.equals(LOGIN_COMMAND)) {
            logIntoAccount();
        } else {
            System.out.println("Selection not valid!");
        }
    }

    // EFFECTS: displays registration/login menu options to the user
    public void displayLoginMenu() {
        System.out.println("What do you want to do?");
        System.out.println("Select " + EXIT_COMMAND + " to exit the app");
        System.out.println("Select  " + REGISTER_COMMAND + " to register a new account");
        System.out.println("Select " + LOGIN_COMMAND + " to log into an existing account");
    }

    // MODIFIES: this
    // EFFECTS: registers a new account with valid username and password input from the user
    public void registerAccount() {

        System.out.println("Please enter a username less than " + MAX_USERNAME_LENGTH + " characters.");
        String username = input.next();

        System.out.println("Please enter a password greater than " + MIN_PASSWORD_LENGTH + " characters.");
        String password = input.next();

        if (gymBros.checkUsernameInput(username) && gymBros.checkPasswordInput(password)
                && gymBros.isUsernameUnique(username)) {
            User newUser = new User(username, password);
            gymBros.createNewUser(newUser);
            System.out.println("Successfully registered your account! You can now login");
            logIntoAccount();
        } else if (gymBros.checkUsernameInput(username) == false) {
            System.out.println("Please enter a valid username! Try again...");
            registerAccount();
        } else if (gymBros.checkPasswordInput(password) == false) {
            System.out.println("Please enter a valid password! Try again...");
            registerAccount();
        } else {
            System.out.println("This username is already taken! Try again...");
            registerAccount();
        }
    }


    // MODIFIES: this
    // EFFECTS: logs the user into their account if username exists and password matches
    public void logIntoAccount() {
        System.out.println("Please enter your username");
        String username = input.next();
        System.out.println("Please enter your password");
        String password = input.next();

        if (gymBros.checkUsernameWhenLoggingIn(username) == false) {
            System.out.println("This username doesn't exist!");
            logIntoAccount();
        } else if (gymBros.checkPasswordWhenLoggingIn(username, password) == false) {
            System.out.println("Incorrect password! Try again");
            logIntoAccount();
        } else {
            loggedIn = true;
            currentlyLoggedInUser = gymBros.getUserWithUsername(username);
            System.out.println("Successfully logged in!");
        }
    }




    // MODIFIES: this
    // EFFECTS: loads account from file
    private void loadAccount() {

        try {
            gymBros = reader.read();
            System.out.println("Loaded " + currentlyLoggedInUser.getUsername() + " from " + jsonUser);
        } catch (IOException io) {
            System.out.println("Unable to read from file: " + jsonUser);
        }
    }

    // EFFECTS: saves the account to file
    private void saveAccount() {
        try {
            writer.open();
            writer.writeGymBros(gymBros);
            writer.close();
            System.out.println("Saved " + currentlyLoggedInUser.getUsername() + " to " + jsonUser);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + jsonUser);
        }
    }

    // EFFECTS: displays action options for user after logging in
    public void displayNextMenu() {
        System.out.println("Choose from the options below");
        System.out.println("Select " + EDIT_PROFILE_COMMAND + " to edit your profile");
        System.out.println("Select  " + TRACKER_COMMAND + " to go into your workout history");
        System.out.println("Select " + FOLLOW_USER_COMMAND + " to follow another user");
        System.out.println("Select " + SELF_PROFILE_COMMAND + " to view your profile");
        System.out.println("Select " + SAVE_ACCOUNT + " to log out of your account");
        System.out.println("Select " + LOAD_ACCOUNT + " to load data from your account");
        System.out.println("Select " + LOGOUT_COMMAND + " to log out of your account");
    }

    // EFFECTS: processes user command
    public void processNextCommand(String command) {
        if (command.equals(EDIT_PROFILE_COMMAND)) {
            editProfile();
        } else if (command.equals(TRACKER_COMMAND)) {
            addWorkout();
        } else if (command.equals(FOLLOW_USER_COMMAND)) {
            followUser();
        } else if (command.equals(SELF_PROFILE_COMMAND)) {
            viewProfile();
        } else if (command.equals(SAVE_ACCOUNT)) {
            saveAccount();
        } else if (command.equals(LOAD_ACCOUNT)) {
            loadAccount();
        } else if (command.equals(LOGOUT_COMMAND)) {
            gymBros.logOut();
            System.out.println("You have been successfully logged out!");
            displayLoginMenu();
            command = input.next();
            command = command.toLowerCase();
            processLoginCommand(command);
        } else {
            System.out.println("Selection not valid!");
        }
    }


    // MODIFIES: this
    // EFFECTS: lets the user edit their profile
    public void editProfile() {
        String bio = null;
        System.out.println("Now you can customise your bio! Enter your new bio");
        bio = input.next();

        currentlyLoggedInUser.setBio(bio);
        System.out.println("Success! Your bio has been updated!");
    }

    // EFFECTS: processes commands related to workout log
    public void addWorkout() {
        String command = null;
        displayWorkoutMenu();
        command = input.next();
        command = command.toLowerCase();

        if (command.equals(ADD_EXERCISE_COMMAND)) {
            addExercise();
        } else if (command.equals(VIEW_WORKOUT_LOG_COMMAND)) {
            if (currentlyLoggedInUser.getWorkoutLog().isEmpty()) {
                System.out.println("Your workout log is empty");
            }  else if (command.equals(HOME_COMMAND)) {
                processNextCommand(command);
            } else {
                List<Workout> workoutLog = currentlyLoggedInUser.getWorkoutLog();
                for (Workout w: workoutLog) {
                    System.out.println(w.getDate());
                    for (Exercise e: w.getWorkoutExercises()) {
                        System.out.println(e.getExerciseName() + " " + e.getReps());
                    }
                }
            }
        }
    }

    // EFFECTS: displays workout menu options to the user
    public void displayWorkoutMenu() {
        System.out.println("Select " + ADD_EXERCISE_COMMAND + " to add an exercise to a new workout session");
        System.out.println("Select  " + VIEW_WORKOUT_LOG_COMMAND + " to view your workout log");
        System.out.println("Select " + HOME_COMMAND + " to go back to home!");
    }

    // MODIFIES: this
    // EFFECTS: adds a new exercise to the user's workout and adds the workout to the user's workout log
    public void addExercise() {
        String name = null;
        int reps = 0;
        System.out.println("Enter the exercise name");
        name = input.next();
        System.out.println("Enter the number of reps");
        reps = input.nextInt();

        Exercise exercise = new Exercise(name, reps);
        String date;
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
        LocalDateTime localDate = LocalDateTime.now();
        date = dtf.format(localDate);
        if (currentlyLoggedInUser.workoutOnDateExists(date)) {
            Workout w = currentlyLoggedInUser.getWorkoutOnDate(date);
            w.addExercise(exercise);

        } else {
            Workout workout = new Workout();
            workout.addExercise(exercise);
            currentlyLoggedInUser.addWorkoutToLog(workout);
        }
        System.out.println("Exercise has been added to new workout");
    }


    // MODIFIES: this
    // EFFECTS: if user exists, then follow the user
    public void followUser() {
        String follow = null;
        System.out.println("Enter the username of the user you want to follow");
        follow = input.next();

        if (gymBros.doesUserExist(follow)) {
            String following = follow;
            currentlyLoggedInUser.addToFollowing(following);
        } else {
            System.out.println("User does not exist! Please enter a valid username");
            followUser();
        }
    }

    // EFFECTS: displays profile of the user
    public void viewProfile() {
        String command = null;
        System.out.println("Your username is " + currentlyLoggedInUser.getUsername());
        System.out.println("Your bio is " + currentlyLoggedInUser.getBio());
        System.out.println("You are following " + currentlyLoggedInUser.getFollowing());
        System.out.println("Your followers are " + currentlyLoggedInUser.getFollowers());
    }


}
