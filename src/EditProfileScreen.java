import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditProfileScreen extends JFrame {

    private JLabel usernameLabel;
    private JTextField heightField;
    private JTextField weightField;
    private JComboBox<String> goalBox;

    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;

    public EditProfileScreen() {
        setTitle("Edit Account and Profile");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(SolumBaseGUI.BACKGROUND);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2),
                BorderFactory.createEmptyBorder(45, 65, 45, 65)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Edit Account and Profile", SwingConstants.CENTER);
        titleLabel.setFont(SolumBaseGUI.TITLE_FONT.deriveFont(Font.BOLD, 36f));
        titleLabel.setForeground(SolumBaseGUI.NEON_PURPLE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        cardPanel.add(titleLabel, gbc);

        usernameLabel = createValueLabel();
        addRow(cardPanel, gbc, 1, "Username:", usernameLabel);

        heightField = createStyledTextField();
        addRow(cardPanel, gbc, 2, "Height (in):", heightField);

        weightField = createStyledTextField();
        addRow(cardPanel, gbc, 3, "Weight (lbs):", weightField);

        goalBox = createStyledComboBox();
        addRow(cardPanel, gbc, 4, "Goal:", goalBox);

        saveButton = createStyledButton("Save Changes");
        cancelButton = createStyledButton("Cancel");
        deleteButton = createStyledButton("Delete Profile");

        gbc.gridy = 5;
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        cardPanel.add(saveButton, gbc);

        gbc.gridx = 1;
        cardPanel.add(cancelButton, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        cardPanel.add(deleteButton, gbc);

        outerPanel.add(cardPanel);
        add(outerPanel);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent input) {
        JLabel label = new JLabel(labelText);
        label.setFont(SolumBaseGUI.TEXT_FONT.deriveFont(Font.BOLD, 22f));
        label.setForeground(SolumBaseGUI.WHITE);

        gbc.gridy = row;
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        input.setPreferredSize(new Dimension(340, 50));
        panel.add(input, gbc);
    }

    private JLabel createValueLabel() {
        JLabel label = new JLabel();
        label.setFont(SolumBaseGUI.TEXT_FONT.deriveFont(Font.BOLD, 22f));
        label.setForeground(SolumBaseGUI.NEON_PURPLE);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        field.setForeground(SolumBaseGUI.WHITE);
        field.setCaretColor(SolumBaseGUI.WHITE);
        field.setFont(SolumBaseGUI.TEXT_FONT.deriveFont(Font.PLAIN, 22f));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        return field;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> box = new JComboBox<>(new String[]{
                "WEIGHT_LOSS",
                "WEIGHT_GAIN",
                "MAINTENANCE"
        });

        box.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        box.setForeground(SolumBaseGUI.WHITE);
        box.setFont(SolumBaseGUI.TEXT_FONT.deriveFont(Font.BOLD, 20f));
        box.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        box.setFocusable(false);

        return box;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setFont(SolumBaseGUI.BUTTON_FONT.deriveFont(Font.BOLD, 20f));
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(260, 58));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SolumBaseGUI.BUTTON_HOVER);
                button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.GLOW_STRONG, 3));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
                button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
            }
        });

        return button;
    }

    public void setProfileData(UserProfile user) {
        usernameLabel.setText(user.getUsername());
        heightField.setText(String.valueOf(user.getHeight()));
        weightField.setText(String.valueOf(user.getWeight()));
        goalBox.setSelectedItem(user.getGoal().name());
    }

    public String getHeightInput() {
        return heightField.getText().trim();
    }

    public String getWeightInput() {
        return weightField.getText().trim();
    }

    public String getGoalInput() {
        return (String) goalBox.getSelectedItem();
    }

    public void addSaveListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) { deleteButton.addActionListener(listener); }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}