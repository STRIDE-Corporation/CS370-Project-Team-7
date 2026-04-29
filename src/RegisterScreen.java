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

    public RegisterScreen() {
        setTitle("Register Account");
        setSize(560, 430);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        JPanel panel = new JPanel(new GridLayout(7, 2, 12, 12));
        panel.setBackground(SolumBaseGUI.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel usernameLabel = createLabel("Username:");
        JLabel passwordLabel = createLabel("Password:");
        JLabel heightLabel = createLabel("Height (in):");
        JLabel weightLabel = createLabel("Weight (lbs):");
        JLabel goalLabel = createLabel("Goal:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        heightField = new JTextField();
        weightField = new JTextField();

        styleInputField(usernameField);
        styleInputField(passwordField);
        styleInputField(heightField);
        styleInputField(weightField);

        goalBox = new JComboBox<>(new String[]{
                "WEIGHT_LOSS",
                "WEIGHT_GAIN",
                "MAINTENANCE"
        });
        goalBox.setFont(SolumBaseGUI.TEXT_FONT);
        goalBox.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        goalBox.setForeground(SolumBaseGUI.WHITE);
        goalBox.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));

        registerButton = new JButton("Create Account");
        backButton = new JButton("Back to Login");

        styleButton(registerButton);
        styleButton(backButton);

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

        panel.add(registerButton);
        panel.add(backButton);

        add(panel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SolumBaseGUI.TEXT_FONT);
        label.setForeground(SolumBaseGUI.NEON_PURPLE);
        return label;
    }

    private void styleInputField(JTextField field) {
        field.setFont(SolumBaseGUI.TEXT_FONT);
        field.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        field.setForeground(SolumBaseGUI.WHITE);
        field.setCaretColor(SolumBaseGUI.WHITE);
        field.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));
    }

    private void styleButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SolumBaseGUI.BUTTON_HOVER);
                button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.GLOW_STRONG, 3));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
                button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
            }
        });
    }

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

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}