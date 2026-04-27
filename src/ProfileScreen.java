import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileScreen extends JFrame {

    private JLabel usernameLabel;
    private JLabel heightLabel;
    private JLabel weightLabel;
    private JLabel goalLabel;
    private JLabel unitPreferenceLabel;

    private JButton backButton;

    public ProfileScreen() {
        setTitle("Profile");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        usernameLabel = new JLabel();
        heightLabel = new JLabel();
        weightLabel = new JLabel();
        goalLabel = new JLabel();
        unitPreferenceLabel = new JLabel();

        backButton = new JButton("Back");

        panel.add(usernameLabel);
        panel.add(heightLabel);
        panel.add(weightLabel);
        panel.add(goalLabel);
        panel.add(unitPreferenceLabel);
        panel.add(backButton);

        add(panel);
    }

    public void setProfileData(UserProfile user) {
        usernameLabel.setText("Username: " + user.getUsername());

        if (user.getUnitPreference() == UserProfile.UnitPreference.METRIC) {
            int heightCm = (int) Math.round(user.getHeight() * 2.54);
            int weightKg = (int) Math.round(user.getWeight() * 0.453592);

            heightLabel.setText("Height: " + heightCm + " cm");
            weightLabel.setText("Weight: " + weightKg + " kg");
        } else {
            heightLabel.setText("Height: " + user.getHeight() + " in");
            weightLabel.setText("Weight: " + user.getWeight() + " lbs");
        }

        goalLabel.setText("Goal: " + user.getGoal());
        unitPreferenceLabel.setText("Display Units: " + user.getUnitPreference());
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}