import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager 
{

    private final DatabaseHandler db;

    public AccountManager(DatabaseHandler db) 
    {
        this.db = db;
    }

    public boolean usernameExists(String username) 
    {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    public UserProfile createAccount(String username, String password, int height, int weight, UserProfile.Goal goal) 
    {
        String sql = "INSERT INTO users(username, password, height, weight, goal) VALUES(?,?,?,?,?)";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, height);
            pstmt.setInt(4, weight);
            pstmt.setString(5, goal.name());

            pstmt.executeUpdate();
            return login(username, password);

        } catch (SQLException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    public UserProfile login(String username, String password) 
    {
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
                String goalString = rs.getString("goal");

                UserProfile.Goal goal = UserProfile.Goal.valueOf(goalString);

                return new UserProfile(userId, username, password, height, weight, goal);
            }

        } catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return null;
    }
    public boolean updateProfile(int userId, int newHeight, int newWeight, UserProfile.Goal newGoal) {
        String sql = "UPDATE users SET height = ?, weight = ?, goal = ? WHERE user_id = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newHeight);
            pstmt.setInt(2, newWeight);
            pstmt.setString(3, newGoal.name());
            pstmt.setInt(4, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void displayAllUsers() 
    {
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
                System.out.println("-------------------");
            }

        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }
}
