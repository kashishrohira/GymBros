package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GymBrosTest {
    public GymBros testGymBros;
    public User testUser1;

    @BeforeEach
    void runBefore() {
        testGymBros = new GymBros();
    }

    @Test
    void testConstructor() {
        assertFalse(testGymBros.getLoggedIn());

        HashMap<String, String> expected1 = new HashMap<>();
        HashMap<String, User> expected2 = new HashMap<>();
        assertEquals(expected1, testGymBros.getUsernamePassword());
        assertEquals(expected2, testGymBros.getUsernameUser());
        assertEquals(null, testGymBros.getCurrentlyLoggedInUser());
    }

    @Test
    void testCreateNewUser() {
        testGymBros.createNewUser("kash","nononono");
        User expected = new User("kash", "nononono");
        assertEquals("kash", testGymBros.getCurrentlyLoggedInUser().getUsername());
        assertEquals("nononono", testGymBros.getCurrentlyLoggedInUser().getPassword());

        HashMap<String, String> expected1 = new HashMap<>();
        expected1.put("kash", "nononono");
        assertEquals(expected1, testGymBros.getUsernamePassword());

        HashMap<String, User> expected2 = new HashMap<>();
        expected2.put("kash", testGymBros.getCurrentlyLoggedInUser());
        assertEquals(expected2, testGymBros.getUsernameUser());


    }
}
