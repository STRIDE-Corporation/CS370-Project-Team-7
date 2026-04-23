import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkoutHistoryController {

    private WorkoutHistoryScreen historyView;
    private MainDashBoard dashboardView;
    private UserProfile currentUser;
    private WorkoutManager workoutManager;

    public WorkoutHistoryController(WorkoutHistoryScreen historyView,
                                    MainDashBoard dashboardView,
                                    UserProfile currentUser,
                                    WorkoutManager workoutManager) {
        this.historyView = historyView;
        this.dashboardView = dashboardView;
        this.currentUser = currentUser;
        this.workoutManager = workoutManager;

        refreshHistory();

        historyView.addBackListener(new BackListener());
        historyView.addDeleteListener(new DeleteListener());
    }

    private void refreshHistory() {
        String historyText = workoutManager.getUserWorkoutHistory(currentUser.getUsername());
        historyView.setHistoryText(historyText);
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dashboardView.setVisible(true);
            historyView.dispose();
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String input = JOptionPane.showInputDialog(
                    historyView,
                    "Enter Workout ID to delete:",
                    "Delete Workout",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                return; // user canceled
            }

            input = input.trim();

            if (input.isEmpty()) {
                historyView.showError("Workout ID cannot be empty.");
                return;
            }

            int workoutId;
            try {
                workoutId = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                historyView.showError("Workout ID must be a number.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    historyView,
                    "Are you sure you want to delete workout ID " + workoutId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean deleted = workoutManager.deleteWorkout(workoutId, currentUser.getUsername());

            if (deleted) {
                historyView.showMessage("Workout deleted successfully.");
                refreshHistory();
            } else {
                historyView.showError("Workout could not be deleted.");
            }
        }
    }
}