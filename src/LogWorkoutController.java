import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogWorkoutController {

    private LogWorkoutScreen view;
    private MainDashBoard dashboardView;
    private WorkoutManager workoutManager;
    private UserProfile currentUser;

    private Workout currentWorkout;
    private boolean workoutStarted = false;

    public LogWorkoutController(LogWorkoutScreen view,
                                MainDashBoard dashboardView,
                                WorkoutManager workoutManager,
                                UserProfile currentUser) {

        this.view = view;
        this.dashboardView = dashboardView;
        this.workoutManager = workoutManager;
        this.currentUser = currentUser;

        this.view.addAddExerciseListener(new AddExerciseListener());
        this.view.addFinishWorkoutListener(new FinishWorkoutListener());
        this.view.addBackListener(new BackListener());
    }

    private class AddExerciseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String exercise = view.getExerciseName();

                if (exercise.isEmpty()) {
                    view.showError("Exercise name required.");
                    return;
                }

                int workoutDuration = parseRequiredInt(view.getWorkoutDuration(), "Workout duration is required.");
                int caloriesBurned = parseOptionalInt(view.getCaloriesBurned(), 0);

                int sets = parseOptionalInt(view.getSets(), 0);
                int reps = parseOptionalInt(view.getReps(), 0);
                double weight = parseOptionalDouble(view.getWeight(), 0.0);
                int restTime = parseOptionalInt(view.getRestTime(), 0);
                double distance = parseOptionalDouble(view.getDistance(), 0.0);

                String workoutSplit = view.getWorkoutSplit();
                String moodEnergy = view.getMoodEnergyLevel();
                String difficulty = view.getDifficultyRating();
                String workoutNotes = view.getWorkoutNotes();

                String muscleGroup = view.getMuscleGroup();
                String paceSpeed = view.getPaceSpeed();
                String inclineResistance = view.getInclineResistance();
                String exerciseNotes = view.getExerciseNotes();

                if (workoutDuration <= 0) {
                    view.showError("Workout duration must be positive.");
                    return;
                }

                if (!workoutStarted) {
                    currentWorkout = workoutManager.addWorkout(
                            currentUser.getUsername(),
                            workoutDuration,
                            caloriesBurned,
                            workoutSplit,
                            moodEnergy,
                            difficulty,
                            workoutNotes
                    );

                    if (currentWorkout == null) {
                        view.showError("Failed to create workout.");
                        return;
                    }

                    workoutStarted = true;
                }

                ExerciseEntry entry = new ExerciseEntry(
                        0,
                        exercise,
                        sets,
                        reps,
                        weight,
                        restTime,
                        muscleGroup,
                        distance,
                        paceSpeed,
                        inclineResistance,
                        exerciseNotes
                );

                workoutManager.addExerciseToWorkout(
                        currentWorkout.getWorkoutId(),
                        entry
                );

                view.appendExercise(
                        "Exercise: " + exercise +
                        " | Sets: " + sets +
                        " | Reps: " + reps +
                        " | Weight: " + weight +
                        " | Rest: " + restTime + " sec" +
                        " | Muscle: " + muscleGroup +
                        " | Distance: " + distance +
                        " | Pace/Speed: " + paceSpeed +
                        " | Incline/Resistance: " + inclineResistance
                );

                view.clearExerciseFields();

            } catch (NumberFormatException ex) {
                view.showError("Please enter valid numbers.");
            } catch (IllegalArgumentException ex) {
                view.showError(ex.getMessage());
            }
        }
    }

    private class FinishWorkoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!workoutStarted) {
                view.showError("Add at least one exercise before finishing the workout.");
                return;
            }

            view.showMessage("Workout saved successfully!");
            dashboardView.setVisible(true);
            view.dispose();
        }
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dashboardView.setVisible(true);
            view.dispose();
        }
    }

    private int parseRequiredInt(String text, String errorMessage) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return Integer.parseInt(text.trim());
    }

    private int parseOptionalInt(String text, int defaultValue) {
        if (text == null || text.trim().isEmpty()) {
            return defaultValue;
        }

        return Integer.parseInt(text.trim());
    }

    private double parseOptionalDouble(String text, double defaultValue) {
        if (text == null || text.trim().isEmpty()) {
            return defaultValue;
        }

        return Double.parseDouble(text.trim());
    }
}