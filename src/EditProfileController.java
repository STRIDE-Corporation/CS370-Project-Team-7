import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfileController {

    private EditProfileScreen editView;
    private ProfileScreen profileView; // We go back here when done
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

                // accountManager.updateProfile() MUST exist in your AccountManager.java file
                boolean success = accountManager.updateProfile(
                        currentUser.getUserID(), newHeight, newWeight, newGoal
                );

                if (success) {
                    // Update memory
                    currentUser.setHeight(newHeight);
                    currentUser.setWeight(newWeight);
                    currentUser.setGoal(newGoal);

                    editView.showMessage("Profile updated successfully!");

                    // Refresh the read-only screen with new data and go back to it
                    profileView.setProfileData(currentUser);
                    profileView.setVisible(true);
                    editView.dispose();
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
            // Just go back without saving
            profileView.setVisible(true);
            editView.dispose();
        }
    }
}