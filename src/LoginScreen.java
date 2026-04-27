import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton closeButton;

    public LoginScreen() {
        setTitle("Solum Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SolumBaseGUI.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 45, 30, 45));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("SOLUM", SwingConstants.CENTER);
        titleLabel.setFont(SolumBaseGUI.TITLE_FONT);
        titleLabel.setForeground(SolumBaseGUI.PURPLE);

        JLabel subtitle = new JLabel("WELCOME FITNESS ENTHUSIAST", SwingConstants.CENTER);
        subtitle.setFont(SolumBaseGUI.BUTTON_FONT.deriveFont(Font.BOLD, 14f));
        subtitle.setForeground(SolumBaseGUI.PURPLE);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        usernameField.setFont(SolumBaseGUI.TEXT_FONT);
        passwordField.setFont(SolumBaseGUI.TEXT_FONT);

        JLabel usernameLabel = createCenteredLabel("Username");
        JLabel passwordLabel = createCenteredLabel("Password");

        loginButton = new JButton("Log In");
        registerButton = new JButton("Register Account");
        closeButton = new JButton("Close App");

        styleButton(loginButton);
        styleButton(registerButton);
        styleButton(closeButton);

        // Title
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 0, 10);
        panel.add(titleLabel, gbc);

        // Subtitle
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 25, 10);
        panel.add(subtitle, gbc);

        // Username label
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 3, 10);
        panel.add(usernameLabel, gbc);

        // Username field
        gbc.gridy++;
        gbc.insets = new Insets(0, 80, 12, 80);
        panel.add(usernameField, gbc);

        // Password label
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 3, 10);
        panel.add(passwordLabel, gbc);

        // Password field
        gbc.gridy++;
        gbc.insets = new Insets(0, 80, 18, 80);
        panel.add(passwordField, gbc);

        // Buttons row
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 10, 8, 10);

        gbc.gridx = 0;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        // Close button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(8, 10, 8, 10);
        panel.add(closeButton, gbc);

        add(panel);

        pack();
        setMinimumSize(new Dimension(580, 430));
        setLocationRelativeTo(null);
    }

    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(SolumBaseGUI.TEXT_FONT);
        label.setForeground(SolumBaseGUI.BLACK);
        return label;
    }

    private void styleButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT);
        button.setBackground(SolumBaseGUI.WHITE);
        button.setForeground(SolumBaseGUI.BLACK);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 45));
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addCloseListener(ActionListener listener) {
        closeButton.addActionListener(listener);
    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}