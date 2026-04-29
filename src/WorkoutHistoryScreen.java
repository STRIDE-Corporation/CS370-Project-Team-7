import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WorkoutHistoryScreen extends JFrame {

    private JTextArea historyArea;
    private JButton backButton;
    private JButton deleteButton;

    public WorkoutHistoryScreen() {
        setTitle("Workout History");

        // Keeps Workout History full-size like the dashboard
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 750));
        setResizable(true);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyArea.setFont(SolumBaseGUI.TEXT_FONT);
        historyArea.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        historyArea.setForeground(SolumBaseGUI.WHITE);
        historyArea.setCaretColor(SolumBaseGUI.WHITE);
        historyArea.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 1));

        JScrollPane scrollPane = new JScrollPane(historyArea);
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
        historyArea.setText(historyText);
        historyArea.setCaretPosition(0);
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