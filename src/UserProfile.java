public class UserProfile {

    private int userID;
    private String username;
    private String password;
    private int height; // stored internally in inches
    private int weight; // stored internally in pounds
    private Goal goal;
    private UnitPreference unitPreference;

    public enum Goal {
        WEIGHT_LOSS,
        WEIGHT_GAIN,
        MAINTENANCE
    }

    public enum UnitPreference {
        IMPERIAL,
        METRIC
    }

    public UserProfile(int userID, String username, String password, int height, int weight, Goal goal, UnitPreference unitPreference) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
        this.unitPreference = unitPreference;
    }

    public boolean verifyUser(String inputUsername) {
        return this.username.equals(inputUsername);
    }

    public boolean verifyPass(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public Goal getGoal() {
        return goal;
    }

    public UnitPreference getUnitPreference() {
        return unitPreference;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setUnitPreference(UnitPreference unitPreference) {
        this.unitPreference = unitPreference;
    }
}