import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainDashBoard extends JFrame {

    private JButton logWorkoutButton;
    private JButton viewHistoryButton;
    private JButton profileButton;
    private JButton logoutButton;

    public MainDashBoard(String username) {
        setTitle("Solum - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(SolumBaseGUI.BACKGROUND);

        SolumBaseGUI.styleFrameFullscreen(this);

        logWorkoutButton = new JButton("Log Workout");
        viewHistoryButton = new JButton("Workout History");
        profileButton = new JButton("Account and Profile");
        logoutButton = new JButton("Log Out");

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        headerPanel.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));

        JLabel welcomeLabel = new JLabel("Welcome back, " + username + "!");
        welcomeLabel.setFont(SolumBaseGUI.TITLE_FONT.deriveFont(32f));
        welcomeLabel.setForeground(SolumBaseGUI.NEON_PURPLE);

        headerPanel.add(welcomeLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(SolumBaseGUI.BACKGROUND);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 40, 15, 40));

        JLabel statsTitle = new JLabel("STATS");
        statsTitle.setFont(SolumBaseGUI.TITLE_FONT.deriveFont(Font.BOLD, 20f));
        statsTitle.setForeground(SolumBaseGUI.NEON_PURPLE);
        statsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel statsRow = new JPanel(new GridLayout(1, 3, 20, 0));
        statsRow.setBackground(SolumBaseGUI.BACKGROUND);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsRow.add(createStatBox("Minutes", "45"));
        statsRow.add(createStatBox("Calories", "320"));
        statsRow.add(createStatBox("Workouts", "3"));

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        gridPanel.setBackground(SolumBaseGUI.BACKGROUND);
        gridPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 360));
        gridPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        gridPanel.add(createClickableCard("Today's Workout", () -> logWorkoutButton.doClick()));
        gridPanel.add(createClickableCard("Personalized Plan", () ->
                JOptionPane.showMessageDialog(this,
                        "Personalized Plan:\nBased on your goal, Solum recommends a weekly workout plan.",
                        "Personalized Plan",
                        JOptionPane.INFORMATION_MESSAGE)
        ));
        gridPanel.add(createClickableCard("Goal Progress", () ->
                JOptionPane.showMessageDialog(this,
                        "Goal Progress:\nProgress tracking module will display user goal completion.",
                        "Goal Progress",
                        JOptionPane.INFORMATION_MESSAGE)
        ));
        gridPanel.add(createClickableCard("Visual Diagrams", () ->
                JOptionPane.showMessageDialog(this,
                        "Visual Diagrams:\nFuture charts may include workout trends, muscle distribution, and progress graphs.",
                        "Visual Diagrams",
                        JOptionPane.INFORMATION_MESSAGE)
        ));

        JPanel recentPanel = createClickableCard("Recent Workouts", () -> viewHistoryButton.doClick());
        recentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        recentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainContent.add(statsTitle);
        mainContent.add(Box.createVerticalStrut(8));
        mainContent.add(statsRow);
        mainContent.add(Box.createVerticalStrut(20));
        mainContent.add(gridPanel);
        mainContent.add(Box.createVerticalStrut(20));
        mainContent.add(recentPanel);

        add(mainContent, BorderLayout.CENTER);

        JPanel bottomNav = new JPanel(new BorderLayout());
        bottomNav.setBackground(SolumBaseGUI.BACKGROUND);
        bottomNav.setBorder(BorderFactory.createEmptyBorder(5, 35, 20, 35));

        JPanel navButtonsPanel = new JPanel(new GridLayout(1, 3, 25, 0));
        navButtonsPanel.setBackground(SolumBaseGUI.BACKGROUND);

        styleDashboardButton(logWorkoutButton);
        styleDashboardButton(viewHistoryButton);
        styleDashboardButton(profileButton);

        navButtonsPanel.add(logWorkoutButton);
        navButtonsPanel.add(viewHistoryButton);
        navButtonsPanel.add(profileButton);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutPanel.setBackground(SolumBaseGUI.BACKGROUND);

        styleDashboardButton(logoutButton);
        logoutButton.setPreferredSize(new Dimension(140, 38));

        logoutPanel.add(logoutButton);

        bottomNav.add(navButtonsPanel, BorderLayout.CENTER);
        bottomNav.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
        bottomNav.add(logoutPanel, BorderLayout.EAST);

        add(bottomNav, BorderLayout.SOUTH);
    }

    private JPanel createStatBox(String title, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        panel.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(SolumBaseGUI.TEXT_FONT.deriveFont(13f));
        titleLabel.setForeground(SolumBaseGUI.NEON_PURPLE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(SolumBaseGUI.TITLE_FONT.deriveFont(17f));
        valueLabel.setForeground(SolumBaseGUI.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(valueLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createClickableCard(String title, Runnable action) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        panel.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(SolumBaseGUI.BUTTON_FONT.deriveFont(18f));
        titleLabel.setForeground(SolumBaseGUI.NEON_PURPLE);

        panel.add(titleLabel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(SolumBaseGUI.BUTTON_HOVER);
                panel.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.GLOW_STRONG, 3));
            }

            public void mouseExited(MouseEvent e) {
                panel.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
                panel.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
            }

            public void mousePressed(MouseEvent e) {
                panel.setBackground(SolumBaseGUI.GLOW_STRONG);
                titleLabel.setForeground(SolumBaseGUI.BACKGROUND);
            }

            public void mouseReleased(MouseEvent e) {
                panel.setBackground(SolumBaseGUI.BUTTON_HOVER);
                titleLabel.setForeground(SolumBaseGUI.NEON_PURPLE);
                action.run();
            }
        });

        return panel;
    }

    private void styleDashboardButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT.deriveFont(15f));
        button.setFocusPainted(false);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(200, 42));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(SolumBaseGUI.BUTTON_HOVER);
                button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.GLOW_STRONG, 3));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
                button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
            }
        });
    }

    public void addLogWorkoutListener(ActionListener listener) {
        logWorkoutButton.addActionListener(listener);
    }

    public void addViewHistoryListener(ActionListener listener) {
        viewHistoryButton.addActionListener(listener);
    }

    public void addProfileListener(ActionListener listener) {
        profileButton.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}