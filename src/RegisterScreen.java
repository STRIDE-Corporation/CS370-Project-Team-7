import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField heightField;
    private JTextField weightField;
    private JComboBox<String> goalBox;

    private JButton registerButton;
    private JButton backButton;

    private JComboBox<UserProfile.UnitPreference> unitPreferenceBox;

    public RegisterScreen() {

        setTitle("Register Account");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel heightLabel = new JLabel("Height (in):");
        JLabel weightLabel = new JLabel("Weight (lbs):");
        JLabel goalLabel = new JLabel("Goal:");
        JLabel unitLabel = new JLabel("Units:");



        usernameField = new JTextField();
        passwordField = new JPasswordField();
        heightField = new JTextField();
        weightField = new JTextField();

        goalBox = new JComboBox<>(new String[]{
                "WEIGHT_LOSS",
                "MUSCLE_GAIN",
                "MAINTENANCE"
        });

        unitPreferenceBox = new JComboBox<>(UserProfile.UnitPreference.values());
        unitPreferenceBox.setSelectedItem(UserProfile.UnitPreference.IMPERIAL);

        registerButton = new JButton("Create Account");
        backButton = new JButton("Back to Login");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(heightLabel);
        panel.add(heightField);

        panel.add(weightLabel);
        panel.add(weightField);

        panel.add(goalLabel);
        panel.add(goalBox);

        panel.add(unitLabel);
        panel.add(unitPreferenceBox);

        panel.add(registerButton);
        panel.add(backButton);

        add(panel);
    }

    // Getters

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getHeightInput() {
        return heightField.getText().trim();
    }

    public String getWeightInput() {
        return weightField.getText().trim();
    }

    public String getGoal() {
        return (String) goalBox.getSelectedItem();
    }

    public UserProfile.UnitPreference getUnitPreference() 
    {
        return (UserProfile.UnitPreference) unitPreferenceBox.getSelectedItem();
    }

    // Listeners

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    // Messages

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}