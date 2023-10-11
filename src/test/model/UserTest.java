package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    public User testUser1;
    public User testUser2;
    public User testUser3;
    public Workout testWorkout1;
    public Workout testWorkout2;

    @BeforeEach
    void runBefore() {
        testUser1 = new User("user1", "abcd");
        testUser2 = new User("user2","efgh");
        testUser3 = new User("user3", "123");
        testWorkout1 = new Workout();
        testWorkout2 = new Workout();
    }

    @Test
    void testConstructor() {
        assertEquals("user1", testUser1.getUsername());
        assertEquals("abcd", testUser1.getPassword());
        assertEquals(" ", testUser1.getBio());
        assertTrue(testUser1.getFollowing().isEmpty());
    }

    @Test // add single user to following
    void testAddToFollowingSingleUser() {
        testUser1.addToFollowing(testUser2);
        assertEquals(1, testUser1.getFollowing().size());
        assertEquals(testUser2, testUser1.getFollowing().get(0));
    }

    @Test // add multiple users to following
    void testAddToFollowingMultipleUsers() {
        testUser1.addToFollowing(testUser2);
        testUser1.addToFollowing(testUser3);
        assertEquals(2, testUser1.getFollowing().size());
        assertEquals(testUser2, testUser1.getFollowing().get(0));
        assertEquals(testUser3, testUser1.getFollowing().get(1));
    }

    @Test // adding a user that is already in the following list
    public void testAddToFollowingAlreadyFollowingUser() {
        testUser1.addToFollowing(testUser3);
        testUser1.addToFollowing(testUser2);
        testUser1.addToFollowing(testUser2);
        assertEquals(2, testUser1.getFollowing().size());
        assertEquals(testUser3, testUser1.getFollowing().get(0));
        assertEquals(testUser2, testUser1.getFollowing().get(1));
    }

    @Test // add single follower
    void testAddFollowerSingleUser() {
        testUser1.addFollower(testUser2);
        assertEquals(1, testUser1.getFollowers().size());
        assertEquals(testUser2, testUser1.getFollowers().get(0));
    }

    @Test // add multiple followers
    void testAddFollowerMultipleUsers() {
        testUser1.addFollower(testUser2);
        testUser1.addFollower(testUser3);
        assertEquals(2, testUser1.getFollowers().size());
        assertEquals(testUser2, testUser1.getFollowers().get(0));
        assertEquals(testUser3, testUser1.getFollowers().get(1));
    }

    @Test
    void testRemoveFromFollowingSingleUser() {
        testUser1.addToFollowing(testUser2);
        testUser1.addToFollowing(testUser3);
        testUser1.removeFromFollowing(testUser2);
        assertEquals(1, testUser1.getFollowing().size());
        assertEquals(testUser3, testUser1.getFollowing().get(0));
    }

    @Test
    void testRemoveFromFollowingMultipleUsers() {
        testUser1.addToFollowing(testUser2);
        testUser1.addToFollowing(testUser3);
        testUser1.removeFromFollowing(testUser2);
        testUser1.removeFromFollowing(testUser3);
        assertEquals(0, testUser1.getFollowing().size());
        assertTrue(testUser1.getFollowing().isEmpty());
    }

    @Test // remove single follower
    void testRemoveSingleFollower() {
        testUser1.addFollower(testUser2);
        testUser1.addFollower(testUser3);
        testUser1.removeFollower(testUser2);
        assertEquals(1, testUser1.getFollowers().size());
        assertEquals(testUser3, testUser1.getFollowers().get(0));
    }

    @Test // remove multiple followers
    void testRemoveMultipleFollowers() {
        testUser1.addFollower(testUser2);
        testUser1.addFollower(testUser3);
        testUser1.removeFollower(testUser2);
        testUser1.removeFollower(testUser3);
        assertEquals(0, testUser1.getFollowers().size());
        assertTrue(testUser1.getFollowers().isEmpty());
    }

    @Test
    void testGetFollowingUsernames() {
        assertTrue(testUser1.getFollowingUsernames().isEmpty());
        testUser1.addToFollowing(testUser2);
        testUser1.addToFollowing(testUser3);
        List<String> expected = new ArrayList<>();
        expected.add("user2");
        expected.add("user3");
        assertEquals(expected, testUser1.getFollowingUsernames());
    }

    @Test
    void testGetFollowersUsernames() {
        assertTrue(testUser1.getFollowersUsernames().isEmpty());
        testUser1.addFollower(testUser2);
        testUser1.addFollower(testUser3);
        List<String> expected = new ArrayList<>();
        expected.add("user2");
        expected.add("user3");
        assertEquals(expected, testUser1.getFollowersUsernames());
    }

    @Test // adding single workout to workout log
    void testAddSingleWorkout() {
        testUser1.addWorkout(testWorkout1);
        assertEquals(1, testUser1.getWorkoutLog().size());
        assertEquals(testWorkout1, testUser1.getWorkoutLog().get(0));
    }

    @Test // add multiple workouts to workout log
    void testAddMultipleWorkouts() {
        testUser1.addWorkout(testWorkout1);
        testUser1.addWorkout(testWorkout2);
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
        assertTrue(testUser1.workoutOnDateExists("October 10, 2023"));
    }

    @Test
    void testWorkoutOnDateExistsEmptyWorkout() {
        assertFalse(testUser1.workoutOnDateExists("October 10, 2023"));
    }

    @Test
    void testGetWorkoutOnDate() {
        testUser1.addWorkoutToLog(testWorkout1);
        assertEquals(testWorkout1, testUser1.getWorkoutOnDate("October 10, 2023"));
    }

    @Test
    void testGetWorkoutOnDateNull() {
        testUser1.addWorkoutToLog(testWorkout1);
        assertEquals(null, testUser1.getWorkoutOnDate("October 11, 2023"));
    }
}
