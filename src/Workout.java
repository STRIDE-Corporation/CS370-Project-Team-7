import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Workout {

    private int workoutId;
    private String username;
    private LocalDateTime workoutDateTime;
    private int duration;
    private List<ExerciseEntry> exercises;

    public Workout(int workoutId, String username, int duration) {
        this.workoutId = workoutId;
        this.username = username;
        this.duration = duration;
        this.workoutDateTime = LocalDateTime.now();
        this.exercises = new ArrayList<>();
    }

    public static Workout createWorkout(int workoutId, String username, int duration) {
        return new Workout(workoutId, username, duration);
    }

    public void addExercise(ExerciseEntry exercise) {
        exercises.add(exercise);
    }

    public void viewWorkout() {
        System.out.println("Workout ID: " + workoutId);
        System.out.println("Username: " + username);
        System.out.println("Date: " + workoutDateTime);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Exercises:");

        if (exercises.isEmpty()) {
            System.out.println("No exercises recorded.");
        } else {
            for (ExerciseEntry exercise : exercises) {
                System.out.println("-------------------");
                exercise.viewExercise();
            }
        }
    }

    public void editWorkout(int newDuration) {
        this.duration = newDuration;
        System.out.println("Workout updated successfully.");
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getWorkoutDateTime() {
        return workoutDateTime;
    }

    public int getDuration() {
        return duration;
    }

    public List<ExerciseEntry> getExercises() {
        return exercises;
    }
}