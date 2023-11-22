package ui;

import model.Exercise;
import model.User;
import model.Workout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddWorkoutPage extends JPanel {
    private User currentUser;
    private HomeFeed homeFeed;
    private String exerciseName;
    private String repsText;
    private int reps;

    private JTextField exerciseNameField;
    private JTextField repsField;
    private JButton logExerciseButton;
    private JButton backButton;
    private JLabel dateLabel;


    // REQUIRES: user and homeFeed are not null
    // EFFECTS: constructs AddWorkoutPage with the given user and homeFeed
    public AddWorkoutPage(User user, HomeFeed homeFeed) {
        this.currentUser = user;
        this.homeFeed = homeFeed;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        init();

        logExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logExercise();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToHomePage();
            }
        });

        setAddWorkoutLayout();
    }

    // EFFECTS: initializes the label, text fields and buttons
    public void init() {
        dateLabel = new JLabel("Date: " + getCurrentDate());
        exerciseNameField = new JTextField("Exercise Name");
        repsField = new JTextField("0");

        logExerciseButton = new JButton("Log Exercise");
        backButton = new JButton("Back to Home");
    }

    // EFFECTS: returns the local date formatted as "MMMM dd, YYYY"
    public String getCurrentDate() {
        String date;
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
        LocalDateTime localDate = LocalDateTime.now();
        date = dtf.format(localDate);
        return date;
    }

    // MODIFIES: this
    // effects: adds components to the layout including label, text fields and buttons
    public void setAddWorkoutLayout() {
        add(Box.createVerticalStrut(20));
        add(dateLabel);
        add(Box.createVerticalStrut(10));
        add(new JLabel("Exercise Name:"));
        add(exerciseNameField);
        add(Box.createVerticalStrut(10));
        add(new JLabel("Reps:"));
        add(repsField);
        add(Box.createVerticalStrut(20));
        add(logExerciseButton);
        add(Box.createVerticalStrut(10));
        add(backButton);
    }

    // MODIFIES: currentUser
    // EFFECTS: validates input and logs the exercise to the workout log of the current user
    public void logExercise() {
        getFields();
        if (reps < 0) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a positive number of reps",
                    "Log Exercise", JOptionPane.ERROR_MESSAGE);
        } else if (!exerciseName.isEmpty() && !repsText.isEmpty()) {

            Exercise exercise = new Exercise(exerciseName, reps);

            if (currentUser.workoutOnDateExists(getCurrentDate())) {
                Workout w = currentUser.getWorkoutOnDate(getCurrentDate());
                w.addExercise(exercise);
            } else {
                Workout workout = new Workout();
                workout.addExercise(exercise);
                currentUser.addWorkoutToLog(workout);
            }
            JOptionPane.showMessageDialog(this,
                    "Exercise logged successfully!", "Log Exercise", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please enter both exercise name and number of reps.",
                    "Log Exercise", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: retrieves the exercise name and reps from the text fields
    public void getFields() {
        exerciseName = exerciseNameField.getText();
        repsText = repsField.getText();
        reps = Integer.parseInt(repsText);
    }

    // MODIFIES: this
    // EFFECTS: clears the exercise name and reps text fields
    public void clearFields() {
        exerciseNameField.setText("");
        repsField.setText("");
    }

    // MODIFIES: this
    // EFFECTS: sets the panel layout to homeFeed layout
    public void backToHomePage() {
        removeAll();

        // Add the components for the home page
        homeFeed.setHomeLayout();

        // Refresh the panel
        revalidate();
        repaint();
    }
}
