import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileScreen extends JFrame {

    private JLabel usernameLabel;
    private JLabel heightLabel;
    private JLabel weightLabel;
    private JLabel goalLabel;

    private JButton editButton;
    private JButton backButton;

    public ProfileScreen() {
        setTitle("Profile");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        usernameLabel = new JLabel();
        heightLabel = new JLabel();
        weightLabel = new JLabel();
        goalLabel = new JLabel();
        
        editButton = new JButton("Edit Profile");
        backButton = new JButton("Back");

        panel.add(usernameLabel);
        panel.add(heightLabel);
        panel.add(weightLabel);
        panel.add(goalLabel);
        panel.add(editButton);
        panel.add(backButton);

        add(panel);
    }

    public void setProfileData(UserProfile user) {
        usernameLabel.setText("Username: " + user.getUsername());
        heightLabel.setText("Height: " + user.getHeight() + " in");
        weightLabel.setText("Weight: " + user.getWeight() + " lbs");
        goalLabel.setText("Goal: " + user.getGoal().name());
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    public void addBackListener(ActionListener listener) { 
        backButton.addActionListener(listener); 
    }
}
