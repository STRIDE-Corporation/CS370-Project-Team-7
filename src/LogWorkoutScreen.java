import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LogWorkoutScreen extends JFrame {

    private JComboBox<Integer> workoutDurationBox;
    private JComboBox<Integer> caloriesBurnedBox;
    private JComboBox<String> workoutSplitBox;
    private JComboBox<String> moodEnergyBox;
    private JComboBox<String> difficultyBox;
    private JTextArea workoutNotesArea;

    private JTextField exerciseNameField;
    private JTextField setsField;
    private JTextField repsField;
    private JTextField weightField;
    private JTextField restTimeField;
    private JComboBox<String> muscleGroupBox;
    private JTextField distanceField;
    private JTextField paceSpeedField;
    private JTextField inclineResistanceField;
    private JTextArea exerciseNotesArea;

    private JTextArea exerciseListArea;

    private JButton addExerciseButton;
    private JButton finishWorkoutButton;
    private JButton backButton;

    public LogWorkoutScreen() {
        setTitle("Log Workout");

        // Keeps Log Workout full-size like the dashboard
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 750));
        setResizable(true);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        JPanel mainInputPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        mainInputPanel.setBackground(SolumBaseGUI.BACKGROUND);
        mainInputPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));

        JPanel workoutPanel = createSectionPanel("Workout Info");
        JPanel exercisePanel = createSectionPanel("Exercise Info");

        workoutDurationBox = new JComboBox<>(createNumberRange(1, 2400));
        caloriesBurnedBox = new JComboBox<>(createNumberRange(1, 1000));

        workoutSplitBox = new JComboBox<>(new String[]{
                "Chest", "Back", "Legs", "Shoulders", "Arms",
                "Core", "Cardio", "Full Body", "Push", "Pull",
                "Upper Body", "Lower Body"
        });

        moodEnergyBox = new JComboBox<>(new String[]{
                "Energized", "Good", "Okay", "Tired",
                "Stressed", "Sore", "Unmotivated", "Focused"
        });

        difficultyBox = new JComboBox<>(new String[]{
                "Easy", "Moderate", "Hard", "Very Hard", "Max Effort"
        });

        workoutNotesArea = new JTextArea(2, 20);

        addWorkoutRow(workoutPanel, "Duration (min):", workoutDurationBox);
        addWorkoutRow(workoutPanel, "Calories Burned:", caloriesBurnedBox);
        addWorkoutRow(workoutPanel, "Workout Split:", workoutSplitBox);
        addWorkoutRow(workoutPanel, "Mood/Energy:", moodEnergyBox);
        addWorkoutRow(workoutPanel, "Difficulty:", difficultyBox);
        addWorkoutArea(workoutPanel, "Workout Notes:", workoutNotesArea);

        exerciseNameField = new JTextField();
        setsField = new JTextField();
        repsField = new JTextField();
        weightField = new JTextField();
        restTimeField = new JTextField();

        muscleGroupBox = new JComboBox<>(new String[]{
                "Chest", "Back", "Legs", "Shoulders", "Arms",
                "Core", "Cardio", "Full Body"
        });

        distanceField = new JTextField();
        paceSpeedField = new JTextField();
        inclineResistanceField = new JTextField();
        exerciseNotesArea = new JTextArea(2, 20);

        addWorkoutRow(exercisePanel, "Exercise Name:", exerciseNameField);
        addWorkoutRow(exercisePanel, "Sets:", setsField);
        addWorkoutRow(exercisePanel, "Reps:", repsField);
        addWorkoutRow(exercisePanel, "Weight:", weightField);
        addWorkoutRow(exercisePanel, "Rest Time (sec):", restTimeField);
        addWorkoutRow(exercisePanel, "Muscle Group:", muscleGroupBox);
        addWorkoutRow(exercisePanel, "Distance:", distanceField);
        addWorkoutRow(exercisePanel, "Pace/Speed:", paceSpeedField);
        addWorkoutRow(exercisePanel, "Incline/Resistance:", inclineResistanceField);
        addWorkoutArea(exercisePanel, "Exercise Notes:", exerciseNotesArea);

        mainInputPanel.add(workoutPanel);
        mainInputPanel.add(exercisePanel);

        exerciseListArea = new JTextArea();
        exerciseListArea.setEditable(false);
        exerciseListArea.setFont(SolumBaseGUI.TEXT_FONT);
        exerciseListArea.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        exerciseListArea.setForeground(SolumBaseGUI.WHITE);
        exerciseListArea.setCaretColor(SolumBaseGUI.WHITE);
        exerciseListArea.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));

        JScrollPane scrollPane = new JScrollPane(exerciseListArea);
        scrollPane.setPreferredSize(new Dimension(800, 120));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2),
                "Added Exercises"
        ));

        addExerciseButton = new JButton("Add Exercise");
        finishWorkoutButton = new JButton("Finish Workout");
        backButton = new JButton("Back");

        styleButton(addExerciseButton);
        styleButton(finishWorkoutButton);
        styleButton(backButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(SolumBaseGUI.BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        bottomPanel.add(addExerciseButton);
        bottomPanel.add(finishWorkoutButton);
        bottomPanel.add(backButton);

        add(mainInputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private Integer[] createNumberRange(int start, int end) {
        Integer[] numbers = new Integer[end - start + 1];

        for (int i = start; i <= end; i++) {
            numbers[i - start] = i;
        }

        return numbers;
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 8));
        panel.setBackground(SolumBaseGUI.BACKGROUND);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2),
                title
        ));
        return panel;
    }

    private void addWorkoutRow(JPanel panel, String labelText, JComponent input) {
        JLabel label = createLabel(labelText);

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(SolumBaseGUI.BACKGROUND);
        labelPanel.add(label, BorderLayout.NORTH);

        styleComponent(input);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(SolumBaseGUI.BACKGROUND);
        inputPanel.add(input, BorderLayout.NORTH);

        panel.add(labelPanel);
        panel.add(inputPanel);
    }

    private void addWorkoutArea(JPanel panel, String labelText, JTextArea area) {
        JLabel label = createLabel(labelText);

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(SolumBaseGUI.BACKGROUND);
        labelPanel.add(label, BorderLayout.NORTH);

        styleTextArea(area);

        JScrollPane areaScrollPane = new JScrollPane(area);
        areaScrollPane.setPreferredSize(new Dimension(220, 60));
        areaScrollPane.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));

        JPanel areaPanel = new JPanel(new BorderLayout());
        areaPanel.setBackground(SolumBaseGUI.BACKGROUND);
        areaPanel.add(areaScrollPane, BorderLayout.NORTH);

        panel.add(labelPanel);
        panel.add(areaPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SolumBaseGUI.TEXT_FONT);
        label.setForeground(SolumBaseGUI.NEON_PURPLE);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        return label;
    }

    private void styleComponent(JComponent component) {
        component.setFont(SolumBaseGUI.TEXT_FONT);
        component.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        component.setForeground(SolumBaseGUI.WHITE);
        component.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));
        component.setPreferredSize(new Dimension(220, 38));

        if (component instanceof JTextField textField) {
            textField.setCaretColor(SolumBaseGUI.WHITE);
        }

        if (component instanceof JComboBox<?> comboBox) {
            comboBox.setFocusable(false);
            comboBox.setMaximumRowCount(10);
        }
    }

    private void styleTextArea(JTextArea area) {
        area.setFont(SolumBaseGUI.TEXT_FONT);
        area.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        area.setForeground(SolumBaseGUI.WHITE);
        area.setCaretColor(SolumBaseGUI.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    private void styleButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        button.setPreferredSize(new Dimension(190, 45));

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

    public String getWorkoutDuration() {
        return workoutDurationBox.getSelectedItem().toString();
    }

    public String getCaloriesBurned() {
        return caloriesBurnedBox.getSelectedItem().toString();
    }

    public String getWorkoutSplit() {
        return workoutSplitBox.getSelectedItem().toString();
    }

    public String getMoodEnergyLevel() {
        return moodEnergyBox.getSelectedItem().toString();
    }

    public String getDifficultyRating() {
        return difficultyBox.getSelectedItem().toString();
    }

    public String getWorkoutNotes() {
        return workoutNotesArea.getText().trim();
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

    public String getWeight() {
        return weightField.getText().trim();
    }

    public String getRestTime() {
        return restTimeField.getText().trim();
    }

    public String getMuscleGroup() {
        return muscleGroupBox.getSelectedItem().toString();
    }

    public String getDistance() {
        return distanceField.getText().trim();
    }

    public String getPaceSpeed() {
        return paceSpeedField.getText().trim();
    }

    public String getInclineResistance() {
        return inclineResistanceField.getText().trim();
    }

    public String getExerciseNotes() {
        return exerciseNotesArea.getText().trim();
    }

    public void clearExerciseFields() {
        exerciseNameField.setText("");
        setsField.setText("");
        repsField.setText("");
        weightField.setText("");
        restTimeField.setText("");
        distanceField.setText("");
        paceSpeedField.setText("");
        inclineResistanceField.setText("");
        exerciseNotesArea.setText("");
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