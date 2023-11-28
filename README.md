# GymBros

## Introduction
GymBros is a fitness social network and tracking application 
that seamlessly combines **fitness tracking** and **social networking**,
creating a vibrant community of fitness enthusiasts. The application aims 
to provide users with a user-friendly and engaging experience to 
log their workouts, set fitness goals, see their friends' progress 
and connect with like-minded individuals.

When a new user creates a profile, there are a number of 
actions that can be done including:
- Fitness tracking - users can log their workouts including,
exercise type, number of reps etc.
- Follow other users - this enables them to see others' 
workout updates and progress
- Goals and challenges - users can set fitness goals and 
participate in challenges with other users to achieve goals together

## Target Audience
GymBros is intended to cater to users of all fitness levels, 
from beginners seeking guidance to seasoned athletes and 
passionate fitness enthusiasts sharing their expertise.

## Motivation for the project
This project deeply interests me as a fitness enthusiast who seeks
connections with like-minded individuals. Witnessing others'
fitness progress motivates and inspires me to continue pushing my own
boundaries. Hence, developing a fitness social network and tracker allows me to
combine my love for fitness with technology and community-building.

## User Stories
- As a user, I want tp be able to create a new account on the app
- As a user, I want to be able to log in to my account using my username and password
- As a user, I want to be able to create a workout session and record the local date when it was created
- As a user, I want to be able to add an exercise to my workout session
- As a user, I want to be able to add a workout session to my workout log
- As a user, I want to be able to view my workout history(log) 
- As a user, I want to be able to customize my profile with a bio
- As a user, I want to be able to follow other users 
- As a user, I want to be able to see the list of people I'm following
- As a user, I want to be able to view workout updates from people I follow
- As a user, I want to be able to join a fitness challenge
- As a user, I want to be able to earn a fitness achievement badge for reaching a significant milestone
- As a user, I want to be able to search for other users using their unique username
- As a user, I want to be able to like and comment under posts
- As a user, when I select the exit option from the application menu, I want to be reminded to save my workout log to 
 file and have the option to do so or not


## Instructions for grader
- You can generate the first required action related to adding a workout to your workout log by clicking the "Add a new workout" button
- You can generate the second required action related to adding a workout to your workout log by selecting a date from the drop-down menu and getting the subset of workouts on that date
- You can locate my visual component at the start of the application when you click "Load from file"
- You can save the state of my application by clicking on exit from the login page and then selecting yes when prompted to save to file.
- You can reload the state of my application by clicking "Load from file" when starting the app again and logging into your account

## Phase 4: Task 3
If I had more time, this is how I would refactor my project:
- I would make it so that when the user navigates between pages, instead of repainting the whole frame, I could just modify
certain elements on the screen
- I would combine the AddWorkoutPage and WorkoutLog page so that the user can view the existing workout log while adding a new exercise
- I would create an abstract class that has the 'Back to Home' button and make all the appropriate classes extend this class in order to 
avoid code duplication when creating the button for every page.