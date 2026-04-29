import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditProfileScreen extends JFrame {

    private JLabel usernameLabel;
    private JComboBox<Integer> heightBox;
    private JComboBox<Integer> weightBox;
    private JComboBox<String> goalBox;

    private JButton saveButton;
    private JButton cancelButton;

    public EditProfileScreen() {
        setTitle("Edit Account and Profile");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 750));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        JLabel title = new JLabel("Edit Account and Profile", SwingConstants.CENTER);
        title.setFont(SolumBaseGUI.TITLE_FONT);
        title.setForeground(SolumBaseGUI.NEON_PURPLE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 20));
        panel.setBackground(SolumBaseGUI.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        panel.add(createLabel("Username:"));
        usernameLabel = createValueLabel("");
        panel.add(usernameLabel);

        panel.add(createLabel("Height (in):"));
        heightBox = new JComboBox<>(createNumberRange(48, 84));
        styleComponent(heightBox);
        panel.add(heightBox);

        panel.add(createLabel("Weight (lbs):"));
        weightBox = new JComboBox<>(createNumberRange(70, 400));
        styleComponent(weightBox);
        panel.add(weightBox);

        panel.add(createLabel("Goal:"));
        goalBox = new JComboBox<>(new String[]{
                "WEIGHT_LOSS",
                "WEIGHT_GAIN",
                "MAINTENANCE"
        });
        styleComponent(goalBox);
        panel.add(goalBox);

        add(panel, BorderLayout.CENTER);

        saveButton = new JButton("Save Changes");
        cancelButton = new JButton("Back");

        styleButton(saveButton);
        styleButton(cancelButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(SolumBaseGUI.BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SolumBaseGUI.TEXT_FONT);
        label.setForeground(SolumBaseGUI.NEON_PURPLE);
        return label;
    }

    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SolumBaseGUI.TEXT_FONT);
        label.setForeground(SolumBaseGUI.WHITE);
        return label;
    }

    private void styleComponent(JComponent component) {
        component.setFont(SolumBaseGUI.TEXT_FONT);
        component.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        component.setForeground(SolumBaseGUI.WHITE);
        component.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));
        component.setPreferredSize(new Dimension(250, 40));

        if (component instanceof JComboBox<?> box) {
            box.setFocusable(false);
            box.setMaximumRowCount(10);
        }
    }

    private void styleButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        button.setPreferredSize(new Dimension(250, 50));

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

    private Integer[] createNumberRange(int start, int end) {
        Integer[] nums = new Integer[end - start + 1];

        for (int i = start; i <= end; i++) {
            nums[i - start] = i;
        }

        return nums;
    }

    public void setProfileData(UserProfile user) {
        usernameLabel.setText(user.getUsername());
        heightBox.setSelectedItem(user.getHeight());
        weightBox.setSelectedItem(user.getWeight());
        goalBox.setSelectedItem(user.getGoal().name());
    }

    public String getHeightInput() {
        return heightBox.getSelectedItem().toString();
    }

    public String getWeightInput() {
        return weightBox.getSelectedItem().toString();
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

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}