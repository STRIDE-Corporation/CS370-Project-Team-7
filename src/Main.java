import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Main {
    public static void main(String[] args) {

        //consistent gui with macOS and windows
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //code we had before upgrade, is onwards.
        SwingUtilities.invokeLater(() -> {

            //initialize database
            DatabaseHandler db = new DatabaseHandler();
            db.initializeDatabase();

            //initialize managers
            AccountManager accountManager = new AccountManager(db);
            WorkoutManager workoutManager = new WorkoutManager(db);

            //launch ui
            LoginScreen loginScreen = new LoginScreen();
            new LoginController(loginScreen, accountManager, workoutManager);

            loginScreen.setVisible(true);
        });
    }
}