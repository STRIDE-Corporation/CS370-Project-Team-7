import java.sql.*;
import java.time.LocalDateTime;

public class WorkoutManager {

    private final DatabaseHandler db;

    public WorkoutManager(DatabaseHandler db) {
        this.db = db;
    }

    public Workout addWorkout(String username) {
        String sql = "INSERT INTO workouts(username, workout_datetime, duration) VALUES(?,?,?)";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String workoutDateTime = LocalDateTime.now().toString();

            pstmt.setString(1, username);
            pstmt.setString(2, workoutDateTime);
            pstmt.setInt(3, 0);

            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                int workoutId = keys.getInt(1);
                return new Workout(workoutId, username, 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addExerciseToWorkout(int workoutId, String exerciseName, int sets, int reps, int duration) {
        String sql = "INSERT INTO exercise_entries(workout_id, exercise_name, sets, reps, duration) VALUES(?,?,?,?,?)";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, workoutId);
            pstmt.setString(2, exerciseName);
            pstmt.setInt(3, sets);
            pstmt.setInt(4, reps);
            pstmt.setInt(5, duration);

            pstmt.executeUpdate();

            updateWorkoutDuration(workoutId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateWorkoutDuration(int workoutId) {
        String sql = """
        UPDATE workouts
        SET duration = (
            SELECT SUM(duration)
            FROM exercise_entries
            WHERE workout_id = ?
        )
        WHERE workout_id = ?
    """;

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, workoutId);
            pstmt.setInt(2, workoutId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserWorkoutHistory(String username) {
        StringBuilder history = new StringBuilder();

        String workoutSql = "SELECT * FROM workouts WHERE username = ?";
        String exerciseSql = "SELECT * FROM exercise_entries WHERE workout_id = ?";

        try (Connection conn = db.connect();
             PreparedStatement workoutStmt = conn.prepareStatement(workoutSql)) {

            workoutStmt.setString(1, username);
            ResultSet workoutRs = workoutStmt.executeQuery();

            boolean found = false;

            while (workoutRs.next()) {
                found = true;

                int workoutId = workoutRs.getInt("workout_id");
                String dateTime = workoutRs.getString("workout_datetime");
                int duration = workoutRs.getInt("duration");

                history.append("Workout ID: ").append(workoutId).append("\n");
                history.append("Date: ").append(dateTime).append("\n");
                history.append("Duration: ").append(duration).append(" minutes\n");
                history.append("Exercises:\n");

                try (PreparedStatement exerciseStmt = conn.prepareStatement(exerciseSql)) {
                    exerciseStmt.setInt(1, workoutId);
                    ResultSet exerciseRs = exerciseStmt.executeQuery();

                    boolean hasExercises = false;

                    while (exerciseRs.next()) {
                        hasExercises = true;
                        history.append("  Exercise: ").append(exerciseRs.getString("exercise_name")).append("\n");
                        history.append("  Sets: ").append(exerciseRs.getInt("sets")).append("\n");
                        history.append("  Reps: ").append(exerciseRs.getInt("reps")).append("\n");
                        history.append("  Duration: ").append(exerciseRs.getInt("duration")).append(" minutes\n");
                        history.append("  -------------------\n");
                    }

                    if (!hasExercises) {
                        history.append("  No exercises recorded.\n");
                    }
                }

                history.append("=======================\n\n");
            }

            if (!found) {
                return "No workouts found for this user.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error loading workout history.";
        }

        return history.toString();
    }

    public boolean deleteWorkout(int workoutId, String username) {
        String checkSql = "SELECT workout_id FROM workouts WHERE workout_id = ? AND username = ?";
        String deleteExercisesSql = "DELETE FROM exercise_entries WHERE workout_id = ?";
        String deleteWorkoutSql = "DELETE FROM workouts WHERE workout_id = ? AND username = ?";

        try (Connection conn = db.connect()) {
            conn.setAutoCommit(false);

            try (
                    PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                    PreparedStatement deleteExercisesStmt = conn.prepareStatement(deleteExercisesSql);
                    PreparedStatement deleteWorkoutStmt = conn.prepareStatement(deleteWorkoutSql)
            ) {
                //confirm user is owner of workout
                checkStmt.setInt(1, workoutId);
                checkStmt.setString(2, username);

                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    conn.rollback();
                    return false;
                }

                //delete array of exercises
                deleteExercisesStmt.setInt(1, workoutId);
                deleteExercisesStmt.executeUpdate();

                //delete workout
                deleteWorkoutStmt.setInt(1, workoutId);
                deleteWorkoutStmt.setString(2, username);

                int rowsAffected = deleteWorkoutStmt.executeUpdate();

                conn.commit();
                return rowsAffected > 0;

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
}
