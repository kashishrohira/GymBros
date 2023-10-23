package model;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class JsonTest {

    public void checkUser(User actual, User expected) {
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getBio(), actual.getBio());
        assertEquals(expected.getFollowing(), actual.getFollowing());
        assertEquals(expected.getFollowers(), actual.getFollowers());
        assertEquals(expected.getPassword(), actual.getPassword());
    }
}
