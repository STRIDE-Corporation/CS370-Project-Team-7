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
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));

        JLabel welcomeLabel = new JLabel("Welcome back, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel);

        add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        logWorkoutButton = new JButton("Log a Workout");
        logWorkoutButton.setFont(new Font("Arial", Font.PLAIN, 16));

        viewHistoryButton = new JButton("Workout History");
        viewHistoryButton.setFont(new Font("Arial", Font.PLAIN, 16));

        profileButton = new JButton("My Profile");
        profileButton.setFont(new Font("Arial", Font.PLAIN, 16));

        menuPanel.add(logWorkoutButton);
        menuPanel.add(viewHistoryButton);
        menuPanel.add(profileButton);

        add(menuPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Log Out");
        footerPanel.add(logoutButton);

        add(footerPanel, BorderLayout.SOUTH);
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