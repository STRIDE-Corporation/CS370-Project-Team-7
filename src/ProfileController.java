import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileController {

    private ProfileScreen profileView;
    private MainDashBoard dashboardView;
    private UserProfile currentUser;

    public ProfileController(ProfileScreen profileView, MainDashBoard dashboardView, UserProfile currentUser) {
        this.profileView = profileView;
        this.dashboardView = dashboardView;
        this.currentUser = currentUser;

        this.profileView.setProfileData(currentUser);
        this.profileView.addBackListener(new BackListener());
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dashboardView.setVisible(true);
            profileView.dispose();
        }
    }
}