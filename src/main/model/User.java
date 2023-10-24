package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class User implements Writable {
    // Constants
    public static final String defaultBio = "default bio";

    // Fields
    private final String userName;
    private String password;
    private String bio;
    private List<String> following;
    private List<String> followers;

    private List<Workout> workoutLog;


    // EFFECTS: creates a user with the given username and password
    //          with a default bio, no followers, no following and
    //          an empty workout log
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.bio = defaultBio;
        this.followers = new ArrayList<String>();
        this.following = new ArrayList<String>();
        this.workoutLog = new ArrayList<Workout>();
    }

    // EFFECTS: returns the user's username
    public String getUsername() {
        return this.userName;
    }

    // EFFECTS: returns the user's password
    public String getPassword() {
        return this.password;
    }

    // EFFECTS: returns the user's bio
    public String getBio() {
        return this.bio;
    }

    // EFFECTS: returns the list of users that this user is following
    //          in the order they were added
    public List<String> getFollowing() {
        return this.following;
    }

    // EFFECTS: returns the list of users followers of this user
    //          in the order they followed
    public List<String> getFollowers() {
        return this.followers;
    }

    // EFFECTS: returns the user's workout log
    public List<Workout> getWorkoutLog() {
        return this.workoutLog;
    }

    // MODIFIES: this, user
    // EFFECTS: adds the given user to this user's following list if not already there
    //          and adds this user to the given user's followers
    public void addToFollowing(User user) {
        if (!this.following.contains(user)) {
            this.following.add(user.getUsername());
            user.addFollower(this);
        }
    }

    // MODIFIES: this, user
    // EFFECTS: adds the given user to this user's followers if not already there
    //          and adds this user to the given user's following list
    public void addFollower(User user) {
        if (!this.followers.contains(user)) {
            this.followers.add(user.getUsername());
            user.addToFollowing(this);
        }
    }

    // MODIFIES, this, user
    // EFFECTS: if this user's following contains given user, then removes given user from following
    //          and removes this from given user's followers
    public void removeFromFollowing(User user) {
        if (this.following.contains(user)) {
            this.following.remove(user);
            user.removeFollower(this);
        }
    }

    // MODIFIES, this, user
    // EFFECTS: if this user's followers contains given user, then removes given user from followers
    //          and removes this user from given user's following list
    public void removeFollower(User user) {
        if (this.followers.contains(user)) {
            this.followers.remove(user);
            user.removeFromFollowing(this);
        }
    }

    // EFFECTS: returns the list of usernames of users that this user follows
    public List<String> getFollowingUsernames() {
        List<String> followingList = new ArrayList<>();
        for (String u: this.following) {
            followingList.add(u);
        }
        return followingList;
    }

    // EFFECTS: returns the list of followers of this user
    public List<String> getFollowersUsernames() {
        List<String> followerList = new ArrayList<>();
        for (String u: this.followers) {
            followerList.add(u);
        }
        return followerList;
    }


    // REQUIRES: length is at least minimum password length
    // MODIFIES: this
    // EFFECTS: sets user's password to given string
    public void setPassword(String password) {
        this.password = password;
    }

    // REQUIRES: bio is at least 1 character
    // MODIFIES: this
    // EFFECTS: sets the user's bio to given string
    public void setBio(String bio) {
        this.bio = bio;
    }

    // MODIFIES: this
    // EFFECTS: adds given workout to the workout log of this user
    public void addWorkoutToLog(Workout w) {
        workoutLog.add(w);
    }

    // REQUIRES: date should be in format MMMM dd, YYYY eg, October 10, 2023
    // EFFECTS: returns true if a workout exists on the given date
    public boolean workoutOnDateExists(String date) {
        if (workoutLog.isEmpty()) {
            return false;
        }
        for (Workout w : this.workoutLog) {
            if (w.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }


    // EFFECTS: returns workout on given date if it exists, null otherwise
    public Workout getWorkoutOnDate(String date) {
        for (Workout w: workoutLog) {
            if (w.getDate().equals(date)) {
                return w;
            }
        }
        return null;
    }

    // started from here

    // EFFECTS: sets the user's workout log to the given workout log
    public void setWorkoutLog(List<Workout> workoutLog) {
        this.workoutLog = workoutLog;
    }

    // MODIFIES: this
    // EFFECTS: sets the user's following to the given list of users
    public void setFollowing(List<String> following) {
        this.following = following;
    }

    // MODIFIES: this
    // EFFECTS: sets the user's followers to the given list of users
    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    @Override
    public JSONObject toJson() {
        JSONObject user = new JSONObject();
        user.put("username", userName);
        user.put("password", password);
        user.put("bio", bio);
        user.put("following", usersToJson(following));
        user.put("followers", usersToJson(followers));
        user.put("workoutLog", workoutLogToJson());
        return user;
    }

    // EFFECTS: returns the user's workout log as a JSONArray
    public JSONArray workoutLogToJson() {
        JSONArray workoutLog = new JSONArray();

        for (Workout w: this.workoutLog) {
            workoutLog.put(w.toJson());
        }

        return workoutLog;
    }

    // EFFECTS: returns the user's following/follower list as a JSONArray
    public JSONArray usersToJson(List<String> users) {
        JSONArray usersJson = new JSONArray();

        for (String u: users) {
            usersJson.put(u);
        }
        return usersJson;
    }

}
