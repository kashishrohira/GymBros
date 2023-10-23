package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Workout is a list of exercises
public class Workout {
    List<Exercise> workout;
    private String date;
    private DateTimeFormatter dtf;

    // EFFECTS: constructs a new Workout with an empty list of Exercises
    //          and stores local date of when the workout was created
    public Workout() {
        this.workout = new ArrayList<Exercise>();
        this.dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
        LocalDateTime localDate = LocalDateTime.now();
        this.date = dtf.format(localDate);
    }

    // MODIFIES: this
    // EFFECTS: adds Exercise to the list of exercises in workout
    public void addExercise(Exercise exercise) {
        this.workout.add(exercise);
    }

    // MODIFIES: this
    // EFFECTS: removes Exercise from the list of exercises in workout
    public void removeExercise(Exercise exercise) {
        this.workout.remove(exercise);
    }

    // EFFECTS: returns the number of exercises in workout
    public int size() {
        return workout.size();
    }

    // REQUIRES: i < workout.size()
    // EFFECTS: returns the Exercise at index i in workout
    public Exercise get(int i) {
        return workout.get(i);
    }

    // EFFECTS: returns the date when the workout was created
    public String getDate() {
        return this.date;
    }

    // EFFECTS: returns the list of exercises in this workout
    public List<Exercise> getWorkoutExercises() {
        return this.workout;
    }

    // DOCUMENTATION?????????????????????????????????
    public JSONObject ToJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("exercises", exercisesToJson());
        // how to put exercises???????????????????????? CHECK DUMBASS
        return json;
    }

    // EFFECTS: returns exercises in this workout as a JSON array
    public JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exercise e: workout) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
