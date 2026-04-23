public class UserProfile {

    private int userID;
    private String username;
    private String password;
    private int height;
    private int weight;
    private Goal goal;

    public enum Goal {
        WEIGHT_LOSS,
        MUSCLE_GAIN,
        MAINTENANCE
    }

    public UserProfile(int userID, String username, String password, int height, int weight, Goal goal) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}