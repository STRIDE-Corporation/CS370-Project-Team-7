import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    // 1. Declare UI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginScreen() {
        // 2. Window Setup
        setTitle("Solum - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window on the screen

        // 3. Create the Main Panel with GridBagLayout for centering
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adds padding between elements

        // --- TITLE & WELCOME TEXT ---

        JLabel titleLabel = new JLabel("Solum");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28)); // Large bold font
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Spans across both columns
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        JLabel welcomeLabel = new JLabel("Welcome back! Please log in.");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 20, 5); // Extra bottom padding below subtitle
        mainPanel.add(welcomeLabel, gbc);

        // --- LOGIN FORM ---

        // Reset padding for the form fields
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1; // Back to single column
        gbc.anchor = GridBagConstraints.EAST; // Align labels to the right

        // Username Row
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Align text field to the left
        mainPanel.add(usernameField, gbc);

        // Password Row
        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordField, gbc);

        // --- BUTTON ---

        loginButton = new JButton("Log In");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5); // Extra top padding
        mainPanel.add(loginButton, gbc);

        // Add the completed panel to the frame
        add(mainPanel);
    }

    // 4. Getters for the Controller to extract data

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        // getPassword() returns a char array for security, converting to String here
        return new String(passwordField.getPassword());
    }

    // 5. Method to attach the Controller's logic to the button

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    // Method to display error messages to the user
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
}