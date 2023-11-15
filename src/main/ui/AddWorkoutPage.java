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

    private JTextField exerciseNameField;
    private JTextField repsField;
    private JButton logExerciseButton;
    private JButton backButton;
    private JLabel dateLabel;

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

    public void init() {
        dateLabel = new JLabel("Date: " + getCurrentDate());
        exerciseNameField = new JTextField("Exercise Name");
        repsField = new JTextField("0");

        logExerciseButton = new JButton("Log Exercise");
        backButton = new JButton("Back to Home");
    }

    public String getCurrentDate() {
        String date;
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
        LocalDateTime localDate = LocalDateTime.now();
        date = dtf.format(localDate);
        return date;
    }

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

    public void logExercise() {
        String exerciseName = exerciseNameField.getText();
        String repsText = repsField.getText();

        if (!exerciseName.isEmpty() && !repsText.isEmpty()) {
            int reps = Integer.parseInt(repsText);

            Exercise exercise = new Exercise(exerciseName, reps);
            boolean exists = currentUser.workoutOnDateExists(getCurrentDate());

            if (exists) {
                Workout w = currentUser.getWorkoutOnDate(getCurrentDate());
                w.addExercise(exercise);
            } else {
                Workout workout = new Workout();
                workout.addExercise(exercise);
                currentUser.addWorkoutToLog(workout);
            }
            JOptionPane.showMessageDialog(this,
                    "Exercise logged successfully!", "Log Exercise", JOptionPane.INFORMATION_MESSAGE);
            exerciseNameField.setText("");
            repsField.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please enter both exercise name and number of reps.",
                    "Log Exercise", JOptionPane.ERROR_MESSAGE);
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
