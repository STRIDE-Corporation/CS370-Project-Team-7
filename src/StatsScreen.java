import javax.swing.*;
import java.awt.*;

public class StatsScreen extends JFrame {

    private JPanel chartPanel;
    private JLabel insightLabel;
    private JTextArea planArea;
    private JButton backButton;

    public StatsScreen() {
        setTitle("Workout Stats");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        insightLabel = new JLabel("", SwingConstants.CENTER);
        insightLabel.setFont(SolumBaseGUI.TEXT_FONT);
        insightLabel.setForeground(SolumBaseGUI.NEON_PURPLE);
        insightLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(insightLabel, BorderLayout.NORTH);

        chartPanel = new JPanel(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);

        planArea = new JTextArea();
        planArea.setEditable(false);
        planArea.setLineWrap(true);
        planArea.setWrapStyleWord(true);
        planArea.setFont(SolumBaseGUI.TEXT_FONT.deriveFont(13f));
        planArea.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        planArea.setForeground(SolumBaseGUI.WHITE);
        planArea.setCaretColor(SolumBaseGUI.WHITE);
        planArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane planScroll = new JScrollPane(planArea);
        planScroll.setPreferredSize(new Dimension(390, 0));
        planScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2),
                "Personalized Plan"
        ));

        add(planScroll, BorderLayout.EAST);

        backButton = new JButton("Back");
        JPanel bottom = new JPanel();
        bottom.add(backButton);
        add(bottom, BorderLayout.SOUTH);
    }

    public void setChart(JPanel panel) {
        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public void setInsight(String text) {
        insightLabel.setText(text);
    }

    public void setPlanText(String text) {
        planArea.setText(text);
    }

    public void addBackListener(java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }
}