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
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // default spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== TITLE =====
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("SOLUM", SwingConstants.CENTER);
        titleLabel.setFont(SolumBaseGUI.TITLE_FONT);
        titleLabel.setForeground(SolumBaseGUI.PURPLE);
        panel.add(titleLabel, gbc);

        // ===== SUBTITLE (NEW) =====
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 15, 10); // 🔥 spacing tweak here

        JLabel subtitle = new JLabel("WELCOME FITNESS ENTHUSIAST", SwingConstants.CENTER);
        subtitle.setFont(SolumBaseGUI.TEXT_FONT.deriveFont(12f));
        subtitle.setForeground(SolumBaseGUI.PURPLE);
        panel.add(subtitle, gbc);

        // Reset spacing for rest
        gbc.insets = new Insets(10, 10, 10, 10);

        // ===== USERNAME =====
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(SolumBaseGUI.TEXT_FONT);
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(18);
        panel.add(usernameField, gbc);

        // ===== PASSWORD =====
        gbc.gridy++;
        gbc.gridx = 0;

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(SolumBaseGUI.TEXT_FONT);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(18);
        panel.add(passwordField, gbc);

        // ===== BUTTONS =====
        loginButton = new JButton("Log In");
        registerButton = new JButton("Register Account");
        closeButton = new JButton("Close App");

        styleButton(loginButton);
        styleButton(registerButton);
        styleButton(closeButton);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        // ===== CLOSE BUTTON CENTERED =====
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(closeButton, gbc);

        add(panel);

        pack();
        setMinimumSize(new Dimension(520, 330));
        setLocationRelativeTo(null);
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