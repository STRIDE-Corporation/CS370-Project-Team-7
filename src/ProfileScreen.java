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

    public ProfileScreen() {
        setTitle("Profile");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        usernameLabel = new JLabel();
        heightLabel = new JLabel();
        weightLabel = new JLabel();
        goalLabel = new JLabel();
        unitPreferenceLabel = new JLabel();

        editButton = new JButton("Edit Profile");
        backButton = new JButton("Back");

        panel.add(usernameLabel);
        panel.add(heightLabel);
        panel.add(weightLabel);
        panel.add(goalLabel);
        panel.add(unitPreferenceLabel);
        panel.add(editButton);
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

        goalLabel.setText("Goal: " + user.getGoal().name());
        unitPreferenceLabel.setText("Display Units: " + user.getUnitPreference());
    }

    public void addEditListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}