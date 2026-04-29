import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WorkoutHistoryScreen extends JFrame {

    private JTextPane historyPane;
    private JButton backButton;
    private JButton deleteButton;

    public WorkoutHistoryScreen() {
        setTitle("Workout History");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 750));
        setResizable(true);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        historyPane = new JTextPane();
        historyPane.setContentType("text/html");
        historyPane.setEditable(false);
        historyPane.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        historyPane.setForeground(SolumBaseGUI.WHITE);
        historyPane.setCaretColor(SolumBaseGUI.WHITE);
        historyPane.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JScrollPane scrollPane = new JScrollPane(historyPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(SolumBaseGUI.BACKGROUND);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 25, 10));

        deleteButton = new JButton("Delete Workout");
        backButton = new JButton("Back");

        styleButton(deleteButton);
        styleButton(backButton);

        bottomPanel.add(deleteButton);
        bottomPanel.add(backButton);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        button.setPreferredSize(new Dimension(220, 50));

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

    public void setHistoryText(String historyText) {
        historyPane.setText(formatHistoryAsHtml(historyText));
        historyPane.setCaretPosition(0);
    }

    private String formatHistoryAsHtml(String rawText) {
        String[] workouts = rawText.split("=======================");
        StringBuilder html = new StringBuilder();

        html.append("<html><body style='")
                .append("font-family: Orbitron, monospace;")
                .append("background-color: #191925;")
                .append("color: white;")
                .append("font-size: 13px;")
                .append("'>");

        for (String workout : workouts) {
            String cleanedWorkout = workout.trim();

            if (cleanedWorkout.isEmpty()) {
                continue;
            }

            String formatted = cleanedWorkout
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\n", "<br>");

            formatted = formatted
                    .replace("Workout ID:", "<span style='color:#A05AFF; font-size:18px; font-weight:bold;'>Workout ID:</span>")
                    .replace("Date:", "<br><span style='color:#A05AFF; font-weight:bold;'>Date:</span>")
                    .replace("Total Duration:", "<br><span style='color:#A05AFF; font-weight:bold;'>Total Duration:</span>")
                    .replace("Estimated Total Calories Burned:", "<br><span style='color:#A05AFF; font-weight:bold;'>Estimated Total Calories Burned:</span>")
                    .replace("Notes:", "<br><br><span style='color:#A05AFF; font-weight:bold;'>Notes:</span>")
                    .replace("Exercises:", "<br><br><span style='color:#A05AFF; font-weight:bold;'>Exercises:</span>")
                    .replace("Exercise:", "<br><span style='color:#A05AFF;'>Exercise:</span>")
                    .replace("Sets:", "<span style='color:#A05AFF;'>Sets:</span>")
                    .replace("Reps:", "<span style='color:#A05AFF;'>Reps:</span>")
                    .replace("Duration:", "<span style='color:#A05AFF;'>Duration:</span>")
                    .replace("Estimated Calories Burned:", "<span style='color:#A05AFF;'>Estimated Calories Burned:</span>")
                    .replace("-------------------", "");

            html.append("<div style='")
                    .append("border: 2px solid #A05AFF;")
                    .append("background-color: #1E1E2D;")
                    .append("padding: 18px;")
                    .append("margin: 18px;")
                    .append("'>")
                    .append(formatted)
                    .append("</div>");
        }

        html.append("</body></html>");

        return html.toString();
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}