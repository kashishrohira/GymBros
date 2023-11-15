package ui;

import model.Exercise;
import model.User;
import model.Workout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WorkoutLog extends JPanel {
    private User currentUser;
    private HomeFeed homeFeed;
    private JButton backButton;
    private JComboBox<String> dateComboBox;
    private List<Workout> workoutLog;

    // REQUIRES: user and homeFeed are not null
    // EFFECTS: constructs a WorkoutLog page with given user and homeFeed
    public WorkoutLog(User user, HomeFeed homeFeed) {
        this.currentUser = user;
        this.homeFeed = homeFeed;
        this.workoutLog = user.getWorkoutLog();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        init();
        dateComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySelectedWorkout();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToHomePage();
            }
        });

        List<Workout> workoutLog = currentUser.getWorkoutLog();
        handleWorkoutLog(workoutLog);

    }

    // MODIFIES: this
    // EFFECTS: initializes layout components
    public void init() {
        dateComboBox = new JComboBox<>();
        populateDateComboBox();
        backButton = new JButton("Back to Home");
        add(dateComboBox);
        add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: if workout log is not empty then displays each workout entry, otherwise shows empty workout dialog
    public void handleWorkoutLog(List<Workout> workoutLog) {
        if (workoutLog.isEmpty()) {
            showEmptyWorkoutLog();
        } else {
            for (Workout w: workoutLog) {
                displayWorkoutEntry(w);
            }
        }
    }

    // MODIFIES: dateComboBox
    // EFFECTS: populate the combo box with dates from the user's workout log
    private void populateDateComboBox() {
        for (Workout w: workoutLog) {
            dateComboBox.addItem(w.getDate());
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the workout log for selected date
    private void displaySelectedWorkout() {
        String selectedDate = (String) dateComboBox.getSelectedItem();
        removeAll();

        for (Workout workout : workoutLog) {
            if (workout.getDate().equals(selectedDate)) {
                displayWorkoutEntry(workout);
                break; // Stop after finding the selected workout
            }
        }

        add(backButton);

        // Refresh the panel
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: displays workout entry for given workout including name and reps for each exercise
    public void displayWorkoutEntry(Workout workout) {
        JPanel workoutPanel = new JPanel();
        workoutPanel.setLayout(new BoxLayout(workoutPanel, BoxLayout.Y_AXIS));

        JLabel dateLabel = new JLabel("Date: " + workout.getDate());
        workoutPanel.add(dateLabel);

        for (Exercise e: workout.getWorkoutExercises()) {
            JLabel exerciseLabel = new JLabel(e.getExerciseName() + ": " + e.getReps() + " reps");
            workoutPanel.add(exerciseLabel);
        }

        workoutPanel.add(Box.createVerticalStrut(20));
        add(workoutPanel);
    }

    // EFFECTS: displays an information dialog indicating that workout log is empty
    public void showEmptyWorkoutLog() {
        int option = JOptionPane.showOptionDialog(this,
                "Your workout log is empty. This is the beginning of your fitness journey <3",
                "Workout Log", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            backToHomePage();
        }
    }

    // MODIFIES: this
    // EFFECTS: sets current page to homeFeed layout
    public void backToHomePage() {
        removeAll();

        // Add the components for the home page
        homeFeed.setHomeLayout();

        // Refresh the panel
        revalidate();
        repaint();
    }
}
