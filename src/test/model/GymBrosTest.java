package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.WildcardType;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class GymBrosTest {
    public GymBros testGymBros;
    public Workout testWorkout1;
    public Workout testWorkout2;

    @BeforeEach
    void runBefore() {
        testGymBros = new GymBros();
        testWorkout1 = new Workout();
        testWorkout2 = new Workout();
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
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);
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

    @Test // username does not exist
    void testCheckUsernameWhenLoggingInDoesNotExist() {
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);
        assertFalse(testGymBros.checkUsernameWhenLoggingIn("no"));
    }

    @Test // username exists
    void testCheckUsernameWhenLoggingInExists() {
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);
        assertTrue(testGymBros.checkUsernameWhenLoggingIn("kash"));
    }

    @Test // incorrect password
    void testCheckPasswordWhenLoggingIn() {
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);
        assertTrue(testGymBros.checkPasswordWhenLoggingIn("kash", "nononono"));
        assertFalse(testGymBros.checkPasswordWhenLoggingIn("kash", "nonono"));
    }

    @Test
    void testCheckUsernameInputLength1() {
        String username = "a";
        assertFalse(testGymBros.checkUsernameInput(username));
    }

    @Test
    void testCheckUsernameInputLengthGreaterThanMax() {
        String username = "1234567890123456789012";
        assertFalse(testGymBros.checkUsernameInput(username));
    }

    @Test
    void testCheckUsernameInputLengthEqualToMax() {
        String username = "12345678901234567890";
        assertTrue(testGymBros.checkUsernameInput(username));
    }

    @Test
    void testCheckUsernameInputLength() {
        String username = "kashish";
        assertTrue(testGymBros.checkUsernameInput(username));
    }

    @Test
    void testCheckPasswordInputLengthLessThanMin() {
        String password = "no";
        assertFalse(testGymBros.checkPasswordInput(password));
    }

    @Test
    void testCheckPasswordInputLengthGreaterThanMin() {
        String password = "nonononono";
        assertTrue(testGymBros.checkPasswordInput(password));
    }

    @Test
    void testCheckPasswordInputLengthEqualToMin() {
        String password = "nononono";
        assertTrue(testGymBros.checkPasswordInput(password));
    }

    @Test // not unique username
    void testIsUsernameUniqueNotUnique() {
        String username = "kash";
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);
        assertFalse(testGymBros.isUsernameUnique(username));
    }

    @Test // unique username
    void testIsUsernameUnique() {
        String username = "kash";
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);
        assertTrue(testGymBros.isUsernameUnique(username));
    }

    @Test // user does not exist
    void testDoesUserExistDoesNotExist() {
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);
        assertFalse(testGymBros.doesUserExist("kashish"));
    }

    @Test // user exists
    void testDoesUserExist() {
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);
        assertTrue(testGymBros.doesUserExist("kash"));
    }

    @Test
    void testGetUserWithUsername() {
        User newUser = new User("kash", "nononono");
        testGymBros.createNewUser(newUser);

        assertEquals("kash", testGymBros.getUserWithUsername("kash").getUsername());
        assertEquals("nononono", testGymBros.getUserWithUsername("kash").getPassword());
    }

    @Test
    void testLogOut() {
        testGymBros.logOut();
        assertFalse(testGymBros.getLoggedIn());
        assertNull(testGymBros.getCurrentlyLoggedInUser());
    }

}
