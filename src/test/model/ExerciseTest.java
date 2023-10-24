package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseTest {
    public Exercise testExercise;

    @Test // testing Exercise constructor
    public void testConstructor() {
        testExercise = new Exercise("Squats",10);
        assertEquals("Squats", testExercise.getExerciseName());
        assertEquals(10, testExercise.getReps());
    }

    @Test
    public void testSetExerciseName() {
        testExercise = new Exercise("squat",10);
        testExercise.setExerciseName("lunge");
        assertEquals("lunge", testExercise.getExerciseName());
    }

    @Test
    public void testSetExerciseReps() {
        testExercise = new Exercise("squat",10);
        testExercise.setExerciseReps(20);
        assertEquals(20, testExercise.getReps());
    }

}