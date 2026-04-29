import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LogWorkoutScreen extends JFrame {

    private JTextField durationField;
    private JTextField exerciseNameField;
    private JTextField setsField;
    private JTextField repsField;
    private JTextField caloriesField;

    private JTextArea exerciseListArea;
    private JTextArea notesArea;

    private JButton addExerciseButton;
    private JButton finishWorkoutButton;
    private JButton backButton;

    public LogWorkoutScreen() {
        setTitle("Log Workout");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 750));
        setResizable(true);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 12, 12));
        inputPanel.setBackground(SolumBaseGUI.BACKGROUND);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 15, 35));

        durationField = new JTextField();
        exerciseNameField = new JTextField();
        setsField = new JTextField();
        repsField = new JTextField();
        caloriesField = new JTextField();

        styleInputField(durationField);
        styleInputField(exerciseNameField);
        styleInputField(setsField);
        styleInputField(repsField);
        styleInputField(caloriesField);

        inputPanel.add(createLabel("Duration (min):"));
        inputPanel.add(durationField);

        inputPanel.add(createLabel("Exercise Name:"));
        inputPanel.add(exerciseNameField);

        inputPanel.add(createLabel("Sets:"));
        inputPanel.add(setsField);

        inputPanel.add(createLabel("Reps:"));
        inputPanel.add(repsField);

        inputPanel.add(createLabel("Calories Burned:"));
        inputPanel.add(caloriesField);

        notesArea = new JTextArea(3, 20);
        notesArea.setFont(SolumBaseGUI.TEXT_FONT);
        notesArea.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        notesArea.setForeground(SolumBaseGUI.WHITE);
        notesArea.setCaretColor(SolumBaseGUI.WHITE);
        notesArea.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));

        JScrollPane notesScroll = new JScrollPane(notesArea);

        inputPanel.add(createLabel("Workout Notes:"));
        inputPanel.add(notesScroll);

        addExerciseButton = new JButton("Add Exercise");
        finishWorkoutButton = new JButton("Finish Workout");
        backButton = new JButton("Back");

        styleButton(addExerciseButton);
        styleButton(finishWorkoutButton);
        styleButton(backButton);

        inputPanel.add(addExerciseButton);
        inputPanel.add(finishWorkoutButton);

        exerciseListArea = new JTextArea();
        exerciseListArea.setEditable(false);
        exerciseListArea.setFont(SolumBaseGUI.TEXT_FONT);
        exerciseListArea.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        exerciseListArea.setForeground(SolumBaseGUI.WHITE);
        exerciseListArea.setCaretColor(SolumBaseGUI.WHITE);
        exerciseListArea.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));

        JScrollPane scrollPane = new JScrollPane(exerciseListArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(SolumBaseGUI.BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        bottomPanel.add(backButton);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SolumBaseGUI.TEXT_FONT);
        label.setForeground(SolumBaseGUI.NEON_PURPLE);
        return label;
    }

    private void styleInputField(JTextField field) {
        field.setFont(SolumBaseGUI.TEXT_FONT);
        field.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        field.setForeground(SolumBaseGUI.WHITE);
        field.setCaretColor(SolumBaseGUI.WHITE);
        field.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));
    }

    private void styleButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        button.setPreferredSize(new Dimension(210, 45));

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

    public String getDuration() {
        return durationField.getText().trim();
    }

    public String getExerciseName() {
        return exerciseNameField.getText().trim();
    }

    public String getSets() {
        return setsField.getText().trim();
    }

    public String getReps() {
        return repsField.getText().trim();
    }

    public String getCaloriesBurned() {
        return caloriesField.getText().trim();
    }

    public String getNotes() { return notesArea.getText().trim(); }

    public void clearExerciseFields() {
        durationField.setText("");
        exerciseNameField.setText("");
        setsField.setText("");
        repsField.setText("");
        caloriesField.setText("");
    }

    public void appendExercise(String text) {
        exerciseListArea.append(text + "\n");
    }

    public void addAddExerciseListener(ActionListener listener) {
        addExerciseButton.addActionListener(listener);
    }

    public void addFinishWorkoutListener(ActionListener listener) {
        finishWorkoutButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}