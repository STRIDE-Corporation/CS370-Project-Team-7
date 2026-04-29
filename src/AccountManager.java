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

    public boolean updateUnitPreference(int userId, UserProfile.UnitPreference unitPreference) {
        String sql = "UPDATE users SET unit_preference = ? WHERE user_id = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, unitPreference.name());
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProfile(int userId, String username) {
        String deleteExercisesSql = """
        DELETE FROM exercise_entries
        WHERE workout_id IN (
            SELECT workout_id FROM workouts WHERE username = ?
        )
        """;

        String deleteWorkoutsSql = "DELETE FROM workouts WHERE username = ?";
        String deleteUserSql = "DELETE FROM users WHERE user_id = ? AND username = ?";

        try (Connection conn = db.connect()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement deleteExercisesStmt = conn.prepareStatement(deleteExercisesSql);
                    PreparedStatement deleteWorkoutsStmt = conn.prepareStatement(deleteWorkoutsSql);
                    PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserSql)
            ) {
                deleteExercisesStmt.setString(1, username);
                deleteExercisesStmt.executeUpdate();

                deleteWorkoutsStmt.setString(1, username);
                deleteWorkoutsStmt.executeUpdate();

                deleteUserStmt.setInt(1, userId);
                deleteUserStmt.setString(2, username);

                int rows = deleteUserStmt.executeUpdate();

                conn.commit();
                return rows > 0;

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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