import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {

    private final DatabaseHandler db;

    public AccountManager(DatabaseHandler db) {
        this.db = db;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserProfile createAccount(String username, String password, int height, int weight,
                                     UserProfile.Goal goal,
                                     UserProfile.UnitPreference unitPreference) {

        String sql = "INSERT INTO users(username, password, height, weight, goal, unit_preference) VALUES(?,?,?,?,?,?)";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, height);
            pstmt.setInt(4, weight);
            pstmt.setString(5, goal.name());
            pstmt.setString(6, unitPreference.name());

            pstmt.executeUpdate();
            return login(username, password);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserProfile login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                int height = rs.getInt("height");
                int weight = rs.getInt("weight");

                UserProfile.Goal goal = UserProfile.Goal.valueOf(rs.getString("goal"));
                UserProfile.UnitPreference unitPreference =
                        UserProfile.UnitPreference.valueOf(rs.getString("unit_preference"));

                return new UserProfile(userId, username, password, height, weight, goal, unitPreference);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void displayAllUsers() {
        String sql = "SELECT * FROM users";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("user_id"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Height: " + rs.getInt("height"));
                System.out.println("Weight: " + rs.getInt("weight"));
                System.out.println("Goal: " + rs.getString("goal"));
                System.out.println("Unit Preference: " + rs.getString("unit_preference"));
                System.out.println("-------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}