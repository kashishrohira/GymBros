package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            GymBros gymBros = new GymBros();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGymbros() {
        try {
            GymBros gymbros = new GymBros();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGymbros.json");
            writer.open();
            writer.writeGymBros(gymbros);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGymbros.json");
            gymbros = reader.read();
            assertEquals(0, gymbros.getNumUsers());
        } catch (IOException e) {
            fail("IOException should not have been thrown!");
        }
    }

    @Test
    void testWriterGeneralGymbros() {
        try {
            GymBros gymBros = new GymBros();
            gymBros.setLoggedIn(true);
            User user = new User("user1", "abababab");
            gymBros.createNewUser(user);
            gymBros.setCurrentlyLoggedInUser(user);

            User user2 = new User("user2", "123123123");
            gymBros.createNewUser(user2);

            user2.setBio("cool");
            Workout workout = new Workout();
            Exercise exercise = new Exercise("Squat", 20);
            workout.addExercise(exercise);
            user2.addWorkoutToLog(workout);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGymbros.json");
            writer.open();
            writer.writeGymBros(gymBros);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGymbros.json");
            gymBros = reader.read();

            assertEquals(2, gymBros.getNumUsers());
            assertEquals("cool", gymBros.getCurrentlyLoggedInUser().getBio());
            assertEquals(0, gymBros.getCurrentlyLoggedInUser().getFollowers().size());
            assertEquals("user2", gymBros.getCurrentlyLoggedInUser().getUsername());
            assertEquals("123123123", gymBros.getCurrentlyLoggedInUser().getPassword());
            assertEquals(1, gymBros.getCurrentlyLoggedInUser().getWorkoutLog().size());

            String date;
            DateTimeFormatter dtf;
            dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
            LocalDateTime localDate = LocalDateTime.now();
            date = dtf.format(localDate);
            assertEquals(date, gymBros.getCurrentlyLoggedInUser().getWorkoutLog().get(0).getDate());

        } catch (IOException e) {
            fail("IOException should not have been thrown!");
        }
    }
}
