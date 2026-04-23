import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController {

    private RegisterScreen registerView;
    private AccountManager accountManager;
    private LoginScreen loginView;

    public RegisterController(RegisterScreen registerView, AccountManager accountManager, LoginScreen loginView) {
        this.registerView = registerView;
        this.accountManager = accountManager;
        this.loginView = loginView;

        this.registerView.addRegisterListener(new RegisterListener());
        this.registerView.addBackListener(new BackListener());
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = registerView.getUsername();
            String password = registerView.getPassword();
            String heightStr = registerView.getHeightInput();
            String weightStr = registerView.getWeightInput();
            String goalStr = registerView.getGoal();

            if (username.isEmpty() || password.isEmpty() ||
                    heightStr.isEmpty() || weightStr.isEmpty()) {
                registerView.displayErrorMessage("All fields must be filled.");
                return;
            }

            if (accountManager.usernameExists(username)) {
                registerView.displayErrorMessage("Username already exists.");
                return;
            }

            int height, weight;

            try {
                height = Integer.parseInt(heightStr);
                weight = Integer.parseInt(weightStr);
            } catch (NumberFormatException ex) {
                registerView.displayErrorMessage("Height and weight must be numbers.");
                return;
            }

            if (height <= 0 || weight <= 0) {
                registerView.displayErrorMessage("Height and weight must be positive.");
                return;
            }

            UserProfile.Goal goal = UserProfile.Goal.valueOf(goalStr);

            UserProfile user = accountManager.createAccount(
                    username, password, height, weight, goal
            );

            if (user != null) {
                registerView.displayMessage("Account created successfully!");
                registerView.dispose();
                loginView.setVisible(true);
            } else {
                registerView.displayErrorMessage("Account creation failed.");
            }
        }
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            registerView.dispose();
            loginView.setVisible(true);
        }
    }
}