import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController {

    private LoginScreen loginView;
    private AccountManager accountManager;
    private WorkoutManager workoutManager;

    public LoginController(LoginScreen loginView, AccountManager accountManager, WorkoutManager workoutManager) {
        this.loginView = loginView;
        this.accountManager = accountManager;
        this.workoutManager = workoutManager;

        this.loginView.addLoginListener(new LoginListener());
        this.loginView.addRegisterListener(new RegisterListener());
        this.loginView.addCloseListener(new CloseListener());
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername().trim();
            String password = loginView.getPassword();

            if (username.isEmpty() || password.isEmpty()) {
                loginView.displayErrorMessage("Username and password cannot be empty.");
                return;
            }

            UserProfile user = accountManager.login(username, password);

            if (user != null) {
                MainDashBoard dashboard = new MainDashBoard(user.getUsername());

                // this is number 3
                new DashboardController(dashboard, user, workoutManager, accountManager);

                dashboard.setVisible(true);
                loginView.dispose();
            } else {
                loginView.displayErrorMessage("Invalid username or password.");
            }
        }
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterScreen registerScreen = new RegisterScreen();
            new RegisterController(registerScreen, accountManager, loginView);

            registerScreen.setVisible(true);
            loginView.setVisible(false);
        }
    }

    private class CloseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            int confirm = JOptionPane.showConfirmDialog(
                    loginView,
                    "Are you sure you want to exit?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}