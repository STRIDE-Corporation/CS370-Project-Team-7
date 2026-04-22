import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginScreen loginView;
    // private DatabaseManager dbManager; // We will uncomment this when the DB is ready

    public LoginController(LoginScreen loginView /*, DatabaseManager dbManager */) {
        this.loginView = loginView;
        // this.dbManager = dbManager;

        // Tell the View what to do when the button is clicked
        this.loginView.addLoginListener(new LoginListener());
    }

    // Inner class that handles the actual button click event
    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // mock authentication, implement DB access for password and username
            // Later, this will be: if(dbManager.authenticate(username, password))
            if (username.equals("William") && password.equals("")) {

                // 1. Success! Open the Main Dashboard and pass the username
                MainDashBoard dashboard = new MainDashBoard(username);
                dashboard.setVisible(true);

                // 2. Close the Login Screen
                loginView.dispose();

            } else {
                // 3. Failure! Tell the View to show an error
                loginView.displayErrorMessage("Invalid username or password.");
            }
        }
    }
}
