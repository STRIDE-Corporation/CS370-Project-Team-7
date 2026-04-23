import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Initialize database
            DatabaseHandler db = new DatabaseHandler();
            db.initializeDatabase();

            // Initialize managers
            AccountManager accountManager = new AccountManager(db);
            WorkoutManager workoutManager = new WorkoutManager(db);

            // Launch UI
            LoginScreen loginScreen = new LoginScreen();
            new LoginController(loginScreen, accountManager, workoutManager);

            loginScreen.setVisible(true);
        });
    }
}