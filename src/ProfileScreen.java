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
                BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Account and Profile", SwingConstants.CENTER);
        titleLabel.setFont(SolumBaseGUI.TITLE_FONT.deriveFont(Font.BOLD, 36f));
        titleLabel.setForeground(SolumBaseGUI.NEON_PURPLE);

        gbc.gridy = 0;
        cardPanel.add(titleLabel, gbc);

        usernameLabel = createProfileLabel();
        heightLabel = createProfileLabel();
        weightLabel = createProfileLabel();
        goalLabel = createProfileLabel();
        unitPreferenceLabel = createProfileLabel();

        gbc.gridy = 1;
        cardPanel.add(usernameLabel, gbc);

        gbc.gridy = 2;
        cardPanel.add(heightLabel, gbc);

        gbc.gridy = 3;
        cardPanel.add(weightLabel, gbc);

        gbc.gridy = 4;
        cardPanel.add(goalLabel, gbc);

        gbc.gridy = 5;
        cardPanel.add(unitPreferenceLabel, gbc);

        imperialButton = createRadioButton("Imperial");
        metricButton = createRadioButton("Metric");

        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(imperialButton);
        unitGroup.add(metricButton);

        JPanel unitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        unitPanel.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        unitPanel.add(imperialButton);
        unitPanel.add(metricButton);

        gbc.gridy = 6;
        cardPanel.add(unitPanel, gbc);

        editButton = new JButton("Edit Account and Profile");
        backButton = new JButton("Back");

        styleButton(editButton);
        styleButton(backButton);

        gbc.gridy = 7;
        cardPanel.add(editButton, gbc);

        gbc.gridy = 8;
        cardPanel.add(backButton, gbc);

        outerPanel.add(cardPanel);
        add(outerPanel);

        imperialButton.addActionListener(e -> saveUnitPreference(UserProfile.UnitPreference.IMPERIAL));
        metricButton.addActionListener(e -> saveUnitPreference(UserProfile.UnitPreference.METRIC));
    }

    private JLabel createProfileLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(SolumBaseGUI.TEXT_FONT.deriveFont(Font.PLAIN, 24f));
        label.setForeground(SolumBaseGUI.NEON_PURPLE);
        return label;
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton button = new JRadioButton(text);
        button.setFont(SolumBaseGUI.BUTTON_FONT.deriveFont(Font.PLAIN, 18f));
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        button.setFocusPainted(false);
        return button;
    }

    private void styleButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT.deriveFont(Font.BOLD, 20f));
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(420, 55));
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