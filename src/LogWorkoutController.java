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

                if (exercise.isEmpty()) {
                    view.showError("Exercise name required.");
                    return;
                }

                int sets = Integer.parseInt(view.getSets());
                int reps = Integer.parseInt(view.getReps());

                if (sets <= 0 || reps <= 0) {
                    view.showError("Sets and reps must be positive.");
                    return;
                }

                if (!workoutStarted) {
                    if (durationText.isEmpty()) {
                        view.showError("Duration is required.");
                        return;
                    }

                    int duration = Integer.parseInt(durationText);

                    if (duration <= 0) {
                        view.showError("Duration must be positive.");
                        return;
                    }

                    currentWorkout = workoutManager.addWorkout(
                            currentUser.getUsername(),
                            duration
                    );

                    if (currentWorkout == null) {
                        view.showError("Failed to create workout.");
                        return;
                    }

                    workoutStarted = true;
                }

                workoutManager.addExerciseToWorkout(
                        currentWorkout.getWorkoutId(),
                        exercise,
                        sets,
                        reps
                );

                view.appendExercise("Exercise: " + exercise + " | Sets: " + sets + " | Reps: " + reps);
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