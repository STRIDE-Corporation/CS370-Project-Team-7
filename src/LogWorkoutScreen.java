import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LogWorkoutScreen extends JFrame {

    private JTextField durationField;
    private JTextField exerciseNameField;
    private JTextField setsField;
    private JTextField repsField;

    private JTextArea exerciseListArea;

    private JButton addExerciseButton;
    private JButton finishWorkoutButton;
    private JButton backButton;

    public LogWorkoutScreen() {
        setTitle("Log Workout");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        inputPanel.add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        inputPanel.add(durationField);

        inputPanel.add(new JLabel("Exercise Name:"));
        exerciseNameField = new JTextField();
        inputPanel.add(exerciseNameField);

        inputPanel.add(new JLabel("Sets:"));
        setsField = new JTextField();
        inputPanel.add(setsField);

        inputPanel.add(new JLabel("Reps:"));
        repsField = new JTextField();
        inputPanel.add(repsField);

        addExerciseButton = new JButton("Add Exercise");
        finishWorkoutButton = new JButton("Finish Workout");
        backButton = new JButton("Back");

        inputPanel.add(addExerciseButton);
        inputPanel.add(finishWorkoutButton);

        exerciseListArea = new JTextArea();
        exerciseListArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(exerciseListArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
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

    public void clearExerciseFields() {
        exerciseNameField.setText("");
        setsField.setText("");
        repsField.setText("");
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