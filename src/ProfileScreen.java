import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileScreen extends JFrame {

    private JLabel usernameLabel;
    private JLabel heightLabel;
    private JLabel weightLabel;
    private JLabel goalLabel;
    private JLabel unitPreferenceLabel;

    private JButton editButton;
    private JButton backButton;

    private JRadioButton imperialButton;
    private JRadioButton metricButton;

    private UserProfile currentUser;
    private AccountManager accountManager;

    public ProfileScreen(AccountManager accountManager) {
        this.accountManager = accountManager;

        setTitle("Account and Profile");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBackground(SolumBaseGUI.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        usernameLabel = createProfileLabel();
        heightLabel = createProfileLabel();
        weightLabel = createProfileLabel();
        goalLabel = createProfileLabel();
        unitPreferenceLabel = createProfileLabel();

        imperialButton = createRadioButton("Imperial");
        metricButton = createRadioButton("Metric");

        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(imperialButton);
        unitGroup.add(metricButton);

        JPanel unitPanel = new JPanel();
        unitPanel.setBackground(SolumBaseGUI.BACKGROUND);
        unitPanel.add(imperialButton);
        unitPanel.add(metricButton);

        editButton = new JButton("Edit Account and Profile");
        backButton = new JButton("Back");

        styleButton(editButton);
        styleButton(backButton);

        panel.add(usernameLabel);
        panel.add(heightLabel);
        panel.add(weightLabel);
        panel.add(goalLabel);
        panel.add(unitPreferenceLabel);
        panel.add(unitPanel);
        panel.add(editButton);
        panel.add(backButton);

        add(panel);

        imperialButton.addActionListener(e -> saveUnitPreference(UserProfile.UnitPreference.IMPERIAL));
        metricButton.addActionListener(e -> saveUnitPreference(UserProfile.UnitPreference.METRIC));
    }

    private JLabel createProfileLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(SolumBaseGUI.TEXT_FONT);
        label.setForeground(SolumBaseGUI.NEON_PURPLE);
        return label;
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton button = new JRadioButton(text);
        button.setFont(SolumBaseGUI.BUTTON_FONT);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setBackground(SolumBaseGUI.BACKGROUND);
        button.setFocusPainted(false);
        return button;
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

    public void setProfileData(UserProfile user) {
        this.currentUser = user;

        if (user.getUnitPreference() == UserProfile.UnitPreference.METRIC) {
            metricButton.setSelected(true);
        } else {
            imperialButton.setSelected(true);
        }

        updateDisplayedUnits();
    }

    private void saveUnitPreference(UserProfile.UnitPreference unitPreference) {
        if (currentUser == null) {
            return;
        }

        boolean success = accountManager.updateUnitPreference(
                currentUser.getUserID(),
                unitPreference
        );

        if (success) {
            currentUser.setUnitPreference(unitPreference);
            updateDisplayedUnits();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not save unit preference.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void updateDisplayedUnits() {
        if (currentUser == null) {
            return;
        }

        usernameLabel.setText("Username: " + currentUser.getUsername());

        if (currentUser.getUnitPreference() == UserProfile.UnitPreference.METRIC) {
            int heightCm = (int) Math.round(currentUser.getHeight() * 2.54);
            int weightKg = (int) Math.round(currentUser.getWeight() * 0.453592);

            heightLabel.setText("Height: " + heightCm + " cm");
            weightLabel.setText("Weight: " + weightKg + " kg");
            unitPreferenceLabel.setText("Display Units: Metric");
        } else {
            heightLabel.setText("Height: " + currentUser.getHeight() + " in");
            weightLabel.setText("Weight: " + currentUser.getWeight() + " lbs");
            unitPreferenceLabel.setText("Display Units: Imperial");
        }

        goalLabel.setText("Goal: " + currentUser.getGoal().name());
    }

    public void addEditListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}