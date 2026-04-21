import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 1. Initialize the View
                LoginScreen loginScreen = new LoginScreen();

                // 2. Initialize the Controller and pass the View to it
                // (This wires the button to the logic we just wrote!)
                LoginController loginController = new LoginController(loginScreen);

                // 3. Launch the Application
                loginScreen.setVisible(true);
            }
        });
    }
}