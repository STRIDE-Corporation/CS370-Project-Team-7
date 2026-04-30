import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LogWorkoutScreen extends JFrame {

    private JTextField durationField;
    private JTextField exerciseNameField;
    private JTextField setsField;
    private JTextField repsField;
    private JLabel caloriesEstimateLabel;

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

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 12, 12));
        inputPanel.setBackground(SolumBaseGUI.BACKGROUND);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 15, 35));

        durationField = new JTextField();
        exerciseNameField = new JTextField();
        setsField = new JTextField();
        repsField = new JTextField();
        caloriesEstimateLabel = createPurpleValueLabel("0");

        styleInputField(durationField);
        styleInputField(exerciseNameField);
        styleInputField(setsField);
        styleInputField(repsField);

        addDurationLiveUpdate();

        inputPanel.add(createLabel("Duration (min):"));
        inputPanel.add(durationField);

        inputPanel.add(createLabel("Exercise Name:"));
        inputPanel.add(exerciseNameField);

        inputPanel.add(createLabel("Sets:"));
        inputPanel.add(setsField);

        inputPanel.add(createLabel("Reps:"));
        inputPanel.add(repsField);

        inputPanel.add(createLabel("Estimated Calories Burned:"));
        inputPanel.add(caloriesEstimateLabel);

        addExerciseButton = new JButton("Add Exercise");
        finishWorkoutButton = new JButton("Finish Workout");

        styleButton(addExerciseButton);
        styleButton(finishWorkoutButton);

        inputPanel.add(addExerciseButton);
        inputPanel.add(finishWorkoutButton);

        exerciseListArea = new JTextArea();
        exerciseListArea.setEditable(false);
        exerciseListArea.setFont(SolumBaseGUI.TEXT_FONT);
        exerciseListArea.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        exerciseListArea.setForeground(SolumBaseGUI.WHITE);
        exerciseListArea.setCaretColor(SolumBaseGUI.WHITE);
        exerciseListArea.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));

        JScrollPane exerciseScrollPane = new JScrollPane(exerciseListArea);
        exerciseScrollPane.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        exerciseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel notesPanel = new JPanel(new BorderLayout(10, 10));
        notesPanel.setBackground(SolumBaseGUI.BACKGROUND);
        notesPanel.setBorder(BorderFactory.createEmptyBorder(10, 35, 10, 35));

        JLabel notesLabel = createLabel("Workout Notes:");

        notesArea = new JTextArea(4, 20);
        notesArea.setFont(SolumBaseGUI.TEXT_FONT);
        notesArea.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        notesArea.setForeground(SolumBaseGUI.WHITE);
        notesArea.setCaretColor(SolumBaseGUI.WHITE);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setText("- ");
        notesArea.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));

        notesArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    notesArea.append("\n- ");
                }
            }
        });

        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        notesScrollPane.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));

        notesPanel.add(notesLabel, BorderLayout.NORTH);
        notesPanel.add(notesScrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        styleButton(backButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(SolumBaseGUI.BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel backPanel = new JPanel();
        backPanel.setBackground(SolumBaseGUI.BACKGROUND);
        backPanel.add(backButton);

        bottomPanel.add(notesPanel, BorderLayout.CENTER);
        bottomPanel.add(backPanel, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        add(exerciseScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addDurationLiveUpdate() {
        durationField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateCaloriesFromDuration();
            }

            public void removeUpdate(DocumentEvent e) {
                updateCaloriesFromDuration();
            }

            public void changedUpdate(DocumentEvent e) {
                updateCaloriesFromDuration();
            }
        });
    }

    private void updateCaloriesFromDuration() {
        try {
            String durationText = durationField.getText().trim();

            if (durationText.isEmpty()) {
                setCaloriesEstimate(0);
                return;
            }

            int duration = Integer.parseInt(durationText);

            if (duration <= 0) {
                setCaloriesEstimate(0);
                return;
            }

            setCaloriesEstimate(duration * 6);

        } catch (NumberFormatException e) {
            setCaloriesEstimate(0);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SolumBaseGUI.TEXT_FONT);
        label.setForeground(SolumBaseGUI.NEON_PURPLE);
        return label;
    }

    private JLabel createPurpleValueLabel(String text) {
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

    public String getNotes() {
        return notesArea.getText().trim();
    }

    public void setCaloriesEstimate(int calories) {
        caloriesEstimateLabel.setText(String.valueOf(calories));
    }

    public void clearExerciseFields() {
        durationField.setText("");
        exerciseNameField.setText("");
        setsField.setText("");
        repsField.setText("");
        setCaloriesEstimate(0);
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