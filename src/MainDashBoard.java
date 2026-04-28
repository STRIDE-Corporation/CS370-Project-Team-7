import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        headerPanel.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));

        JLabel welcomeLabel = new JLabel("Welcome back, " + username + "!");
        welcomeLabel.setFont(SolumBaseGUI.TITLE_FONT.deriveFont(32f));
        welcomeLabel.setForeground(SolumBaseGUI.NEON_PURPLE);

        headerPanel.add(welcomeLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 35, 35));
        menuPanel.setBackground(SolumBaseGUI.BACKGROUND);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        logWorkoutButton = new JButton("Log Workout");
        viewHistoryButton = new JButton("Workout History");
        profileButton = new JButton("Account and Profile");

        styleDashboardButton(logWorkoutButton);
        styleDashboardButton(viewHistoryButton);
        styleDashboardButton(profileButton);

        menuPanel.add(logWorkoutButton);
        menuPanel.add(viewHistoryButton);
        menuPanel.add(profileButton);

        add(menuPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(SolumBaseGUI.BACKGROUND);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 35, 40));

        logoutButton = new JButton("Log Out");
        styleDashboardButton(logoutButton);
        logoutButton.setPreferredSize(new Dimension(220, 55));

        footerPanel.add(logoutButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private void styleDashboardButton(JButton button) {
        button.setFont(SolumBaseGUI.BUTTON_FONT.deriveFont(20f));
        button.setFocusPainted(false);
        button.setBackground(SolumBaseGUI.BUTTON_BACKGROUND);
        button.setForeground(SolumBaseGUI.NEON_PURPLE);
        button.setBorder(BorderFactory.createLineBorder(SolumBaseGUI.NEON_PURPLE, 2));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setOpaque(true);

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