import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileController {

    private ProfileScreen profileView;
    private MainDashBoard dashboardView;
    private UserProfile currentUser;
    private AccountManager accountManager;

    public ProfileController(ProfileScreen profileView, MainDashBoard dashboardView, UserProfile currentUser, AccountManager accountManager) {
        this.profileView = profileView;
        this.dashboardView = dashboardView;
        this.currentUser = currentUser;
        this.accountManager = accountManager;

        this.profileView.setProfileData(currentUser);
        this.profileView.addEditListener(new EditListener());
        this.profileView.addBackListener(new BackListener());
    }
    private class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EditProfileScreen editScreen = new EditProfileScreen();
            new EditProfileController(editScreen, profileView, currentUser, accountManager);

            editScreen.setVisible(true);
            profileView.setVisible(false); 
        }
    }
    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dashboardView.setVisible(true);
            profileView.dispose();
        }
    }
}
