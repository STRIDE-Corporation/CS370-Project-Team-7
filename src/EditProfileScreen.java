import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditProfileScreen extends JFrame {

    private JLabel usernameLabel;
    private JTextField heightField;
    private JTextField weightField;
    private JComboBox<String> goalBox;

    private JButton saveButton;
    private JButton cancelButton;

    public EditProfileScreen() {
        setTitle("Solum - Edit Profile");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Username:"));
        usernameLabel = new JLabel();
        panel.add(usernameLabel);

        panel.add(new JLabel("Height (in):"));
        heightField = new JTextField();
        panel.add(heightField);

        panel.add(new JLabel("Weight (lbs):"));
        weightField = new JTextField();
        panel.add(weightField);

        panel.add(new JLabel("Goal:"));
        goalBox = new JComboBox<>(new String[]{ "WEIGHT_LOSS", "MUSCLE_GAIN", "MAINTENANCE" });
        panel.add(goalBox);

        saveButton = new JButton("Save Changes");
        cancelButton = new JButton("Cancel");

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);
    }

    public void setProfileData(UserProfile user) {
        usernameLabel.setText(user.getUsername());
        heightField.setText(String.valueOf(user.getHeight()));
        weightField.setText(String.valueOf(user.getWeight()));
        goalBox.setSelectedItem(user.getGoal().name());
    }

    public String getHeightInput() { return heightField.getText().trim(); }
    public String getWeightInput() { return weightField.getText().trim(); }
    public String getGoalInput() { return (String) goalBox.getSelectedItem(); }

    public void addSaveListener(ActionListener listener) { saveButton.addActionListener(listener); }
    public void addCancelListener(ActionListener listener) { cancelButton.addActionListener(listener); }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
    public void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
}