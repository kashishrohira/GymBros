package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutTest {
    Workout testWorkout;
    Exercise testExercise1;
    Exercise testExercise2;

    @BeforeEach
    void runBefore() {
        testWorkout = new Workout();
        testExercise1 = new Exercise("squats", 20);
        testExercise2 = new Exercise("lunges", 10);
    }
    @Test
    public void testConstructor() {
        assertEquals(0, testWorkout.size());
    }

    @Test // addExercise for single exercise
    public void testAddExerciseSingleExercise() {
        testWorkout.addExercise(testExercise1);
        assertEquals(1, testWorkout.size());
        assertEquals(testExercise1, testWorkout.get(0));
    }

    @Test // addExercise for multiple exercises
    public void testAddExerciseMultipleExercises() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(2, testWorkout.size());
        assertEquals(testExercise1, testWorkout.get(0));
        assertEquals(testExercise2, testWorkout.get(1));
    }

    @Test // removeExercise for single exercise
    public void testRemoveExerciseSingleExercise() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        testWorkout.removeExercise(testExercise1);
        assertEquals(1, testWorkout.size());
        assertEquals(testExercise2, testWorkout.get(0));
    }

    @Test // removeExercise for multiple exercises
    public void testRemoveExerciseMultipleExercises() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        testWorkout.removeExercise(testExercise1);
        testWorkout.removeExercise(testExercise2);
        assertEquals(0, testWorkout.size());
    }

    @Test // size for empty workout
    public void testSizeEmptyWorkout() {
        assertEquals(0, testWorkout.size());
    }

    @Test // size for multiple exercises in workout
    public void testSizeMultipleExercises() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(testExercise1, testWorkout.get(0));
        assertEquals(testExercise2, testWorkout.get(1));
        assertEquals(2, testWorkout.size());
    }

    @Test // get for multiple exercises
    public void testGetMultipleExercises() {
        testWorkout.addExercise(testExercise1);
        testWorkout.addExercise(testExercise2);
        assertEquals(testExercise1, testWorkout.get(0));
        assertEquals(testExercise2, testWorkout.get(1));
    }

    @Test
    public void testGetDate() {
        assertEquals("October 07, 2023", testWorkout.getDate());
    }
}
