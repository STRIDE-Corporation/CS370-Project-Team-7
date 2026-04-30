import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class EditProfileController {

    private EditProfileScreen editView;
    private ProfileScreen profileView;
    private UserProfile currentUser;
    private AccountManager accountManager;

    public EditProfileController(EditProfileScreen editView, ProfileScreen profileView,
                                 UserProfile currentUser, AccountManager accountManager) {
        this.editView = editView;
        this.profileView = profileView;
        this.currentUser = currentUser;
        this.accountManager = accountManager;

        this.editView.setProfileData(currentUser);
        this.editView.addSaveListener(new SaveListener());
        this.editView.addCancelListener(new CancelListener());
        this.editView.addDeleteListener(new DeleteListener());
    }

    private void returnToProfileScreen() {
        profileView.setProfileData(currentUser);
        profileView.setExtendedState(ProfileScreen.MAXIMIZED_BOTH);
        profileView.setVisible(true);
        editView.dispose();
    }

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int newHeight = Integer.parseInt(editView.getHeightInput());
                int newWeight = Integer.parseInt(editView.getWeightInput());
                UserProfile.Goal newGoal = UserProfile.Goal.valueOf(editView.getGoalInput());

                if (!ValidationUtils.isValidHeight(newHeight)) {
                    editView.showError("Height must be between 36 and 96 inches.");
                    return;
                }

                if (!ValidationUtils.isValidWeight(newWeight)) {
                    editView.showError("Weight must be between 70 and 400 lbs.");
                    return;
                }

                boolean success = accountManager.updateProfile(
                        currentUser.getUserID(), newHeight, newWeight, newGoal
                );

                if (success) {
                    currentUser.setHeight(newHeight);
                    currentUser.setWeight(newWeight);
                    currentUser.setGoal(newGoal);

                    editView.showMessage("Profile updated successfully!");
                    returnToProfileScreen();
                } else {
                    editView.showError("Database error: Could not update profile.");
                }

            } catch (NumberFormatException ex) {
                editView.showError("Height and weight must be valid numbers.");
            }
        }
    }

    private class CancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            returnToProfileScreen();
        }
    }

    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(
                    editView,
                    "Are you sure you want to delete your profile?\nThis will permanently delete your account and workout history.",
                    "Confirm Delete Profile",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            boolean deleted = accountManager.deleteProfile(
                    currentUser.getUserID(),
                    currentUser.getUsername()
            );

            if (deleted) {
                editView.showMessage("Profile deleted successfully.");

                LoginScreen loginScreen = new LoginScreen();
                DatabaseHandler db = new DatabaseHandler();
                AccountManager newAccountManager = new AccountManager(db);
                WorkoutManager newWorkoutManager = new WorkoutManager(db);
                new LoginController(loginScreen, newAccountManager, newWorkoutManager);

                profileView.dispose();
                editView.dispose();
                loginScreen.setVisible(true);

            } else {
                editView.showError("Database error: Could not delete profile.");
            }
        }
    }
}