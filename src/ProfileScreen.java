import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileScreen extends JFrame {

    private JLabel usernameLabel;
    private JLabel heightLabel;
    private JLabel weightLabel;
    private JLabel goalLabel;

    private JButton backButton;

    public ProfileScreen() {
        setTitle("Profile");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        usernameLabel = new JLabel();
        heightLabel = new JLabel();
        weightLabel = new JLabel();
        goalLabel = new JLabel();

        backButton = new JButton("Back");

        panel.add(usernameLabel);
        panel.add(heightLabel);
        panel.add(weightLabel);
        panel.add(goalLabel);
        panel.add(backButton);

        add(panel);
    }

    public void setProfileData(UserProfile user) {
        usernameLabel.setText("Username: " + user.getUsername());
        heightLabel.setText("Height: " + user.getHeight());
        weightLabel.setText("Weight: " + user.getWeight());
        goalLabel.setText("Goal: " + user.getGoal());
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}