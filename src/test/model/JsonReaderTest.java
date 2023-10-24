package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
    // Constants
    public static final String emptyGymBros = "./data/testReaderEmptyGymbros.json";
    public static final String gymBrosWithData = "./data/testReaderGymbrosWithData.json";

    // Fields
    private GymBros testGymBros;
    private User testUser1;
    private User testUser2;

    @BeforeEach
    void runBefore() {
        testGymBros = new GymBros();
        testUser1 = new User("user1", "12345678");
        testUser2 = new User("user2", "abababab");
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            GymBros gymbros = reader.read();
            fail("IO exception expected!");
        } catch (IOException e){
            // pass
        }
    }

    @Test
    void testReaderEmptyGymbrosNotLoggedIn() {
        try {
            JsonWriter writer = new JsonWriter(emptyGymBros);
            writer.open();
            writer.writeGymBros(testGymBros);
            writer.close();

            JsonReader reader = new JsonReader(emptyGymBros);
            testGymBros = reader.read();

            assertFalse(testGymBros.getLoggedIn());
            assertNull(testGymBros.getCurrentlyLoggedInUser());
            assertTrue(testGymBros.getUsernameUser().isEmpty());
            assertTrue(testGymBros.getUsernamePassword().isEmpty());
        } catch (IOException ioe) {
            fail("IOException should not have been thrown.");
        }
    }

    @Test
    void testReaderGymbrosWithData() {
        try {
            JsonReader reader = new JsonReader(gymBrosWithData);
            testGymBros = reader.read();

            assertTrue(testGymBros.getLoggedIn());
            initAssertUser();
            HashMap<String, User> users = new HashMap<>();
            users.put("user1", testUser1);
            assertEquals("user1", users.get("user1").getUsername());
            assertEquals("abababab", users.get("user1").getPassword());
            assertEquals(1, users.get("user1").getWorkoutLog().size());
        } catch (IOException e){
            fail("IOException should not have been thrown");
        }
    }

    void initAssertUser() {
        String username = "user1";
        String password = "abababab";
        String bio = "cool";
        Workout workout = new Workout();
        Exercise exercise = new Exercise("squats", 10);
        workout.addExercise(exercise);

        testUser1.setPassword(password);
        testUser1.setBio(bio);
        testUser1.addWorkoutToLog(workout);
    }
}
