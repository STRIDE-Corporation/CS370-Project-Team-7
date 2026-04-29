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
                String durationText = view.getDuration();
                String caloriesText = view.getCaloriesBurned();

                if (exercise.isEmpty()) {
                    view.showError("Exercise name required.");
                    return;
                }

                if (durationText.isEmpty()) {
                    view.showError("Exercise duration is required.");
                    return;
                }

                if (caloriesText.isEmpty()) {
                    view.showError("Calories burned is required.");
                    return;
                }

                int sets = Integer.parseInt(view.getSets());
                int reps = Integer.parseInt(view.getReps());
                int duration = Integer.parseInt(durationText);
                int caloriesBurned = Integer.parseInt(caloriesText);

                if (!ValidationUtils.isValidExerciseName(exercise)) {
                    view.showError("Exercise name must be between 1 and 50 characters.");
                    return;
                }

                if (!ValidationUtils.isValidSets(sets)) {
                    view.showError("Sets must be between 1 and 20.");
                    return;
                }

                if (!ValidationUtils.isValidReps(reps)) {
                    view.showError("Reps must be between 1 and 100.");
                    return;
                }

                if (!ValidationUtils.isValidDuration(duration)) {
                    view.showError("Duration must be between 1 and 300 minutes.");
                    return;
                }

                if (!ValidationUtils.isValidCalories(caloriesBurned)) {
                    view.showError("Calories burned must be between 100 and 1000.");
                    return;
                }

                if (!workoutStarted) {
                    String notes = view.getNotes();
                    currentWorkout = workoutManager.addWorkout(currentUser.getUsername(), notes);

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
                        duration,
                        caloriesBurned
                );

                workoutManager.addExerciseToWorkout(
                        currentWorkout.getWorkoutId(),
                        entry
                );

                view.appendExercise(
                        "Exercise: " + exercise +
                                " | Sets: " + sets +
                                " | Reps: " + reps +
                                " | Duration: " + duration + " min" +
                                " | Calories: " + caloriesBurned
                );

                view.clearExerciseFields();

            } catch (NumberFormatException ex) {
                view.showError("Please enter valid numbers.");
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
}