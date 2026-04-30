import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;
import java.awt.*;

public class StatsScreen extends JFrame {

    private JPanel chartPanel;
    private JLabel insightLabel;
    private JTextPane planArea;
    private JButton backButton;

    public StatsScreen() {
        setTitle("Workout Stats");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        insightLabel = new JLabel("", SwingConstants.CENTER);
        insightLabel.setFont(SolumBaseGUI.TEXT_FONT);
        insightLabel.setForeground(SolumBaseGUI.NEON_PURPLE);
        insightLabel.setOpaque(true);
        insightLabel.setBackground(SolumBaseGUI.BACKGROUND);
        insightLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(insightLabel, BorderLayout.NORTH);

        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(SolumBaseGUI.BACKGROUND);
        chartPanel.setOpaque(true);
        add(chartPanel, BorderLayout.CENTER);

        planArea = new JTextPane();
        planArea.setEditable(false);
        planArea.setBackground(SolumBaseGUI.FIELD_BACKGROUND);
        planArea.setForeground(SolumBaseGUI.WHITE);
        planArea.setCaretColor(SolumBaseGUI.WHITE);
        planArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JScrollPane planScroll = new JScrollPane(planArea);
        planScroll.setPreferredSize(new Dimension(390, 0));
        planScroll.setBackground(SolumBaseGUI.BACKGROUND);
        planScroll.getViewport().setBackground(SolumBaseGUI.FIELD_BACKGROUND);

        TitledBorder planBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2),
                "Personalized Plan"
        );
        planBorder.setTitleColor(SolumBaseGUI.WHITE);
        planBorder.setTitleFont(SolumBaseGUI.TEXT_FONT.deriveFont(Font.BOLD, 14f));
        planScroll.setBorder(planBorder);

        add(planScroll, BorderLayout.EAST);

        backButton = new JButton("Back");
        styleButton(backButton);

        JPanel bottom = new JPanel();
        bottom.setBackground(SolumBaseGUI.BACKGROUND);
        bottom.setOpaque(true);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 14, 10));
        bottom.add(backButton);
        add(bottom, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(210, 45));
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));

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

    public void setChart(JPanel panel) {
        panel.setBackground(SolumBaseGUI.BACKGROUND);
        panel.setOpaque(true);

        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public void setInsight(String text) {
        insightLabel.setText(text);
    }

    public void setPlanText(String text) {
        planArea.setText("");

        StyledDocument doc = planArea.getStyledDocument();

        Style headerStyle = planArea.addStyle("headerStyle", null);
        StyleConstants.setForeground(headerStyle, SolumBaseGUI.NEON_PURPLE);
        StyleConstants.setBold(headerStyle, true);
        StyleConstants.setFontFamily(headerStyle, SolumBaseGUI.TEXT_FONT.getFamily());
        StyleConstants.setFontSize(headerStyle, 13);

        Style bodyStyle = planArea.addStyle("bodyStyle", null);
        StyleConstants.setForeground(bodyStyle, SolumBaseGUI.WHITE);
        StyleConstants.setFontFamily(bodyStyle, SolumBaseGUI.TEXT_FONT.getFamily());
        StyleConstants.setFontSize(bodyStyle, 13);

        try {
            String[] lines = text.split("\n");

            for (String line : lines) {
                if (isPlanHeader(line)) {
                    doc.insertString(doc.getLength(), line + "\n", headerStyle);
                } else {
                    doc.insertString(doc.getLength(), line + "\n", bodyStyle);
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private boolean isPlanHeader(String line) {
        return line.startsWith("Goal:")
                || line.startsWith("Latest workout analysis:")
                || line.startsWith("Next workout target:")
                || line.startsWith("Target range:")
                || line.startsWith("Weekly suggestion:")
                || line.startsWith("Recommendation:");
    }

    public void addBackListener(java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }
}