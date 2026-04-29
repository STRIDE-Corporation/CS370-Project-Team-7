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
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(SolumBaseGUI.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("SOLUM", SwingConstants.CENTER);
        titleLabel.setFont(SolumBaseGUI.TITLE_FONT);
        titleLabel.setForeground(SolumBaseGUI.NEON_PURPLE);

        JLabel subtitle = new JLabel("WELCOME FITNESS ENTHUSIAST", SwingConstants.CENTER);
        subtitle.setFont(SolumBaseGUI.BUTTON_FONT.deriveFont(Font.BOLD, 14f));
        subtitle.setForeground(SolumBaseGUI.NEON_PURPLE);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        styleInputField(usernameField);
        styleInputField(passwordField);

        JLabel usernameLabel = createCenteredLabel("Username");
        JLabel passwordLabel = createCenteredLabel("Password");

        loginButton = new JButton("Log In");
        registerButton = new JButton("Register Account");
        closeButton = new JButton("Close App");

        styleButton(loginButton);
        styleButton(registerButton);
        styleButton(closeButton);

        // Layout

        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 0, 10);
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 30, 10);
        panel.add(subtitle, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 5, 10);
        panel.add(usernameLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 120, 15, 120);
        panel.add(usernameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 5, 10);
        panel.add(passwordLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 120, 25, 120);
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(closeButton, gbc);

        add(panel);

        // 🔥 THIS is the key (bigger + consistent across app)
        SolumBaseGUI.styleFrame(this);
    }

    private JLabel createCenteredLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
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
        button.setPreferredSize(new Dimension(220, 50));

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

    // Getters

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // Listeners

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