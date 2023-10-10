package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseTest {
    @Test // testing Exercise constructor
    public void testConstructor() {
        Exercise testExercise = new Exercise("Squats",10);
        assertEquals("Squats", testExercise.getExerciseName());
        assertEquals(10, testExercise.getReps());
    }

}