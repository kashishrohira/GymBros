package model;

import java.util.Date;

// Represents an exercise with name, number of repetitions and date performed
public class Exercise {
    String exerciseName;
    int reps;

    // REQUIRES: name has non-zero length, reps > 0
    // EFFECTS: Constructs an exercise with given name, reps and date
    public Exercise(String name, int reps) {
        this.exerciseName = name;
        this.reps = reps;
    }

    // EFFECTS: returns the name of the exercise
    public String getExerciseName() {
        return this.exerciseName;
    }

    // EFFECTS: returns the number of reps of the exercise
    public int getReps() {
        return this.reps;
    }




}
