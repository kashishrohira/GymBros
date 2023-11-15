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

    public WorkoutLog(User user, HomeFeed homeFeed) {
        this.currentUser = user;
        this.homeFeed = homeFeed;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        backButton = new JButton("Back to Home");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToHomePage();
            }
        });

        List<Workout> workoutLog = currentUser.getWorkoutLog();
        if (workoutLog.isEmpty()) {
            showEmptyWorkoutLog();
        } else {
            for (Workout w: workoutLog) {
                displayWorkoutEntry(w);
            }
            add(backButton);
        }

    }

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

    public void showEmptyWorkoutLog() {
        int option = JOptionPane.showOptionDialog(this,
                "Your workout log is empty. This is the beginning of your fitness journey <3",
                "Workout Log", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            backToHomePage();
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
