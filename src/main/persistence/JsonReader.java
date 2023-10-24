package persistence;

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

    // CHANGE!!!!!
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
    private List<User> jsonToUsers(JSONArray usersJson) {
        List<User> users = new ArrayList<User>();

        for (Object json: usersJson) {
            User user = jsonToUser((JSONObject) json);
            users.add(user);
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

    private User jsonToUser(JSONObject userJson) {
        String username = userJson.getString("username");
        String password = userJson.getString("password");
        String about = userJson.getString("bio");
        List<User> following = jsonToUsers(userJson.getJSONArray("following"));
        List<User> followers = jsonToUsers(userJson.getJSONArray("followers"));

        User user = new User(username, password);
        user.setPassword(password);
        user.setBio(about);
        user.setFollowing(following);
        user.setFollowers(followers);

        return user;
    }

}
