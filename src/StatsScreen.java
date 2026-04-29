import javax.swing.*;
import java.awt.*;

public class StatsScreen extends JFrame {

    private JPanel chartPanel;
    private JButton backButton;

    public StatsScreen() {
        setTitle("Workout Stats");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        chartPanel = new JPanel(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);

        backButton = new JButton("Back");
        JPanel bottom = new JPanel();
        bottom.add(backButton);

        add(bottom, BorderLayout.SOUTH);
    }

    public void setChart(JPanel panel) {
        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
    }

    public void addBackListener(java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }
}