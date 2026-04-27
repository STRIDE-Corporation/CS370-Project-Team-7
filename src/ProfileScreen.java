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

        setTitle("Profile");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        usernameLabel = new JLabel();
        heightLabel = new JLabel();
        weightLabel = new JLabel();
        goalLabel = new JLabel();
        unitPreferenceLabel = new JLabel();

        imperialButton = new JRadioButton("Imperial");
        metricButton = new JRadioButton("Metric");

        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(imperialButton);
        unitGroup.add(metricButton);

        JPanel unitPanel = new JPanel();
        unitPanel.add(imperialButton);
        unitPanel.add(metricButton);

        editButton = new JButton("Edit Profile");
        backButton = new JButton("Back");

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