import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardController {

    private MainDashBoard dashboardView;
    private UserProfile currentUser;
    private WorkoutManager workoutManager;
    private AccountManager accountManager;

    public DashboardController(MainDashBoard dashboardView,
                               UserProfile currentUser,
                               WorkoutManager workoutManager,
                               AccountManager accountManager) {

        this.dashboardView = dashboardView;
        this.currentUser = currentUser;
        this.workoutManager = workoutManager;
        this.accountManager = accountManager;

        this.dashboardView.addLogoutListener(new LogoutListener());
        this.dashboardView.addProfileListener(new ProfileListener());
        this.dashboardView.addViewHistoryListener(new HistoryListener());
        this.dashboardView.addLogWorkoutListener(new LogWorkoutListener());
    }

    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            LoginScreen loginScreen = new LoginScreen();
            new LoginController(loginScreen, accountManager, workoutManager);

            loginScreen.setVisible(true);
            dashboardView.dispose();
        }
    }

    private class ProfileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProfileScreen profileScreen = new ProfileScreen();
            new ProfileController(profileScreen, dashboardView, currentUser);

            profileScreen.setVisible(true);
            dashboardView.setVisible(false);
        }
    }

    private class HistoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            WorkoutHistoryScreen historyScreen = new WorkoutHistoryScreen();
            new WorkoutHistoryController(historyScreen, dashboardView, currentUser, workoutManager);

            historyScreen.setVisible(true);
            dashboardView.setVisible(false);
        }
    }

    private class LogWorkoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            LogWorkoutScreen screen = new LogWorkoutScreen();
            new LogWorkoutController(
                    screen,
                    dashboardView,
                    workoutManager,
                    currentUser
            );

            screen.setVisible(true);
            dashboardView.setVisible(false);
        }
    }
}