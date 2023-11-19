package persistence;

import model.Exercise;
import model.GymBros;
import model.User;
import model.Workout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads workout log from data stored in a file
public class JsonReader {
    private String source;

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads gymBros from file and returns it
    public GymBros read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return readGymBros(jsonObject);
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: reads GymBros from JSON object and returns it
    private GymBros readGymBros(JSONObject jsonObject) {
        GymBros gymBros = new GymBros();
        Boolean loggedIn = jsonObject.getBoolean("loggedIn");
        User currentlyActiveUser = null;
        HashMap<String, User> users = jsonToUsernameUsers(jsonObject.getJSONArray("users"));

        if (loggedIn) {
            currentlyActiveUser = jsonToUser(jsonObject.getJSONObject("user"));
            users.put(currentlyActiveUser.getUsername(),currentlyActiveUser);
        }

        // edit from here!!

        gymBros.setCurrentlyLoggedInUser(currentlyActiveUser);
        gymBros.setLoggedIn(loggedIn);
        gymBros.setUsernameUsers(users);

        return gymBros;
    }

    // EFFECTS: reads a list of users from a JSONArray and returns it
    private List<String> jsonToUsers(JSONArray usersJson) {
        List<String> users = new ArrayList<>();

        for (Object json: usersJson) {
            users.add(json.toString());
        }
        return users;
    }

    // EFFECTS: reads a map of usernames and users from a JSONArray and returns it
    private HashMap<String, User> jsonToUsernameUsers(JSONArray usersJson) {
        HashMap<String, User> users = new HashMap<>();

        for (Object json: usersJson) {
            User user = jsonToUser((JSONObject) json);
            users.put(user.getUsername(), user);
        }

        return users;
    }

    // EFFECTS: reads a user from a JSONObject and returns it
    private User jsonToUser(JSONObject userJson) {
        String username = userJson.getString("username");
        String password = userJson.getString("password");
        String about = userJson.getString("bio");
        List<String> following = jsonToUsers(userJson.getJSONArray("following"));
        List<String> followers = jsonToUsers(userJson.getJSONArray("followers"));
        List<Workout> workoutLog = jsonToWorkoutLog(userJson.getJSONArray("workoutLog"));

        User user = new User(username, password);
        user.setPassword(password);
        user.setBio(about);
        user.setFollowing(following);
        user.setFollowers(followers);
        user.setWorkoutLog(workoutLog);

        return user;
    }

    // EFFECTS: reads a workout log from a JSONArray and returns it
    private List<Workout> jsonToWorkoutLog(JSONArray workoutLogJson) {
        List<Workout> workoutLog = new ArrayList<>();

        for (Object json: workoutLogJson) {
            Workout w = jsonToWorkout((JSONObject) json);
            workoutLog.add(w);
        }

        return workoutLog;
    }

    // EFFECTS: reads a workout from a JSONArray and returns it
    private Workout jsonToWorkout(JSONObject json) {
        Workout workout = new Workout();
        String date = json.getString("date");
        List<Exercise> exercises = jsonToExercises(json.getJSONArray("exercises"));

        workout.setExercises(exercises);
        workout.setDate(date);
        return workout;
    }

    // EFFECTS: reads a list of exercises from a JSONArray and returns it
    private List<Exercise> jsonToExercises(JSONArray exercises) {
        List<Exercise> workout = new ArrayList<>();
        for (Object json: exercises) {
            Exercise e = jsonToExercise((JSONObject) json);
            workout.add(e);
        }
        return workout;
    }

    // EFFECTS: reads an exercise from a JSONObject and returns it
    private Exercise jsonToExercise(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int reps = jsonObject.getInt("reps");
        Exercise e = new Exercise(name, reps);
        return e;
    }

}
