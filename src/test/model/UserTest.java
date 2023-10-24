package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    public User testUser1;
    public User testUser2;
    public User testUser3;
    public Workout testWorkout1;
    public Workout testWorkout2;
    public Workout testWorkout3;

    @BeforeEach
    void runBefore() {
        testUser1 = new User("user1", "abcd");
        testUser2 = new User("user2","efgh");
        testUser3 = new User("user3", "123");
        testWorkout1 = new Workout();
        testWorkout2 = new Workout();
        testWorkout3 = new Workout();
    }

    @Test
    void testConstructor() {
        assertEquals("user1", testUser1.getUsername());
        assertEquals("abcd", testUser1.getPassword());
        assertEquals("default bio", testUser1.getBio());
        assertTrue(testUser1.getFollowing().isEmpty());
    }

    @Test // add single user to following
    void testAddToFollowingSingleUser() {
        testUser1.addToFollowing(testUser2.getUsername());
        assertEquals(1, testUser1.getFollowing().size());
        assertEquals(testUser2.getUsername(), testUser1.getFollowing().get(0));
    }

    @Test // add multiple users to following
    void testAddToFollowingMultipleUsers() {
        testUser1.addToFollowing(testUser2.getUsername());
        testUser1.addToFollowing(testUser3.getUsername());
        assertEquals(2, testUser1.getFollowing().size());
        assertEquals(testUser2.getUsername(), testUser1.getFollowing().get(0));
        assertEquals(testUser3.getUsername(), testUser1.getFollowing().get(1));
    }

    @Test // adding a user that is already in the following list
    public void testAddToFollowingAlreadyFollowingUser() {
        testUser1.addToFollowing(testUser3.getUsername());
        testUser1.addToFollowing(testUser2.getUsername());
        testUser1.addToFollowing(testUser2.getUsername());
        assertEquals(2, testUser1.getFollowing().size());
        assertEquals(testUser3.getUsername(), testUser1.getFollowing().get(0));
        assertEquals(testUser2.getUsername(), testUser1.getFollowing().get(1));
    }

    @Test // add single follower
    void testAddFollowerSingleUser() {
        testUser1.addFollower(testUser2.getUsername());
        assertEquals(1, testUser1.getFollowers().size());
        assertEquals(testUser2.getUsername(), testUser1.getFollowers().get(0));
    }

    @Test // add multiple followers
    void testAddFollowerMultipleUsers() {
        testUser1.addFollower(testUser2.getUsername());
        testUser1.addFollower(testUser3.getUsername());
        assertEquals(2, testUser1.getFollowers().size());
        assertEquals(testUser2.getUsername(), testUser1.getFollowers().get(0));
        assertEquals(testUser3.getUsername(), testUser1.getFollowers().get(1));
    }

    @Test // adding a user that is already in the following list
    public void testAddToFollowersAlreadyFollower() {
        testUser1.addFollower(testUser3.getUsername());
        testUser1.addFollower(testUser2.getUsername());
        testUser1.addFollower(testUser2.getUsername());
        assertEquals(2, testUser1.getFollowers().size());
        assertEquals(testUser3.getUsername(), testUser1.getFollowers().get(0));
        assertEquals(testUser2.getUsername(), testUser1.getFollowers().get(1));
    }

    @Test
    void testRemoveFromFollowingSingleUser() {
        testUser1.addToFollowing(testUser2.getUsername());
        testUser1.addToFollowing(testUser3.getUsername());
        testUser1.removeFromFollowing(testUser2.getUsername());
        assertEquals(1, testUser1.getFollowing().size());
        assertEquals(testUser3.getUsername(), testUser1.getFollowing().get(0));
    }

    @Test
    void testRemoveFromFollowingMultipleUsers() {
        testUser1.addToFollowing(testUser2.getUsername());
        testUser1.addToFollowing(testUser3.getUsername());
        testUser1.removeFromFollowing(testUser2.getUsername());
        testUser1.removeFromFollowing(testUser3.getUsername());
        assertEquals(0, testUser1.getFollowing().size());
        assertTrue(testUser1.getFollowing().isEmpty());
    }

    @Test
    void testRemoveFromFollowingNotFollowing() {
        testUser1.addToFollowing(testUser2.getUsername());
        testUser1.addToFollowing(testUser3.getUsername());
        testUser1.removeFromFollowing(testUser2.getUsername());
        testUser1.removeFromFollowing(testUser3.getUsername());
        testUser1.removeFromFollowing(testUser2.getUsername());
        assertEquals(0, testUser1.getFollowing().size());
        assertTrue(testUser1.getFollowing().isEmpty());
    }

    @Test // remove single follower
    void testRemoveSingleFollower() {
        testUser1.addFollower(testUser2.getUsername());
        testUser1.addFollower(testUser3.getUsername());
        testUser1.removeFollower(testUser2.getUsername());
        assertEquals(1, testUser1.getFollowers().size());
        assertEquals(testUser3.getUsername(), testUser1.getFollowers().get(0));
    }

    @Test // remove multiple followers
    void testRemoveMultipleFollowers() {
        testUser1.addFollower(testUser2.getUsername());
        testUser1.addFollower(testUser3.getUsername());
        testUser1.removeFollower(testUser2.getUsername());
        testUser1.removeFollower(testUser3.getUsername());
        assertEquals(0, testUser1.getFollowers().size());
        assertTrue(testUser1.getFollowers().isEmpty());
    }

    @Test // remove multiple followers
    void testRemoveFollowerNotFollower() {
        testUser1.addFollower(testUser2.getUsername());
        testUser1.removeFollower(testUser3.getUsername());
        assertEquals(1, testUser1.getFollowers().size());
        assertEquals(testUser2.getUsername(), testUser1.getFollowers().get(0));
        assertFalse(testUser1.getFollowers().isEmpty());
    }

    @Test
    void testGetFollowingUsernames() {
        assertTrue(testUser1.getFollowing().isEmpty());
        testUser1.addToFollowing(testUser2.getUsername());
        testUser1.addToFollowing(testUser3.getUsername());
        List<String> expected = new ArrayList<>();
        expected.add("user2");
        expected.add("user3");
        assertEquals(expected, testUser1.getFollowing());
    }

    @Test
    void testGetFollowersUsernames() {
        assertTrue(testUser1.getFollowers().isEmpty());
        testUser1.addFollower(testUser2.getUsername());
        testUser1.addFollower(testUser3.getUsername());
        List<String> expected = new ArrayList<>();
        expected.add("user2");
        expected.add("user3");
        assertEquals(expected, testUser1.getFollowers());
    }

    @Test // adding single workout to workout log
    void testAddSingleWorkout() {
        testUser1.addWorkoutToLog(testWorkout1);
        assertEquals(1, testUser1.getWorkoutLog().size());
        assertEquals(testWorkout1, testUser1.getWorkoutLog().get(0));
    }

    @Test // add multiple workouts to workout log
    void testAddMultipleWorkouts() {
        testUser1.addWorkoutToLog(testWorkout1);
        testUser1.addWorkoutToLog(testWorkout2);
        assertEquals(2, testUser1.getWorkoutLog().size());
        assertEquals(testWorkout1, testUser1.getWorkoutLog().get(0));
        assertEquals(testWorkout2, testUser1.getWorkoutLog().get(1));
    }

    @Test
    void testSetPassword() {
        assertEquals("abcd", testUser1.getPassword());
        testUser1.setPassword("yaynewpassword");
        assertEquals("yaynewpassword", testUser1.getPassword());
        testUser1.setPassword("strongpasswordyay");
        assertEquals("strongpasswordyay", testUser1.getPassword());
    }

    @Test
    void testSetBio() {
        assertEquals(User.defaultBio, testUser2.getBio());
        testUser2.setBio("yas queen");
        assertEquals("yas queen", testUser2.getBio());
        testUser2.setBio("slayyy");
        assertEquals("slayyy", testUser2.getBio());
    }

    @Test
    void testAddWorkoutToLogSingleWorkout() {
        testUser1.addWorkoutToLog(testWorkout1);
        assertEquals(1, testUser1.getWorkoutLog().size());
        assertEquals(testWorkout1, testUser1.getWorkoutLog().get(0));
    }

    @Test
    void testAddWorkoutToLogMultipleWorkouts() {
        testUser1.addWorkoutToLog(testWorkout1);
        testUser1.addWorkoutToLog(testWorkout2);
        assertEquals(2, testUser1.getWorkoutLog().size());
        assertEquals(testWorkout1, testUser1.getWorkoutLog().get(0));
        assertEquals(testWorkout2, testUser1.getWorkoutLog().get(1));
    }

    @Test
    void testWorkoutOnDateExistsFalse() {
        testUser1.addWorkoutToLog(testWorkout1);
        assertFalse(testUser1.workoutOnDateExists("October 11, 2023"));
    }

    @Test
    void testWorkoutOnDateExistsTrue() {
        testUser1.addWorkoutToLog(testWorkout1);
        String date;
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
        LocalDateTime localDate = LocalDateTime.now();
        date = dtf.format(localDate);
        assertTrue(testUser1.workoutOnDateExists(date));
    }

    @Test
    void testWorkoutOnDateExistsEmptyWorkout() {
        assertFalse(testUser1.workoutOnDateExists("October 10, 2023"));
    }

    @Test
    void testGetWorkoutOnDate() {
        testUser1.addWorkoutToLog(testWorkout3);
        String date;
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
        LocalDateTime localDate = LocalDateTime.now();
        date = dtf.format(localDate);
        assertEquals(testWorkout3, testUser1.getWorkoutOnDate(date));
    }

    @Test
    void testGetWorkoutOnDateNull() {
        String date;
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY");
        LocalDateTime localDate = LocalDateTime.now();
        date = dtf.format(localDate);
        assertEquals(null, testUser1.getWorkoutOnDate(date));
    }
}
