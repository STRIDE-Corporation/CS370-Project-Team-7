import java.sql.*;
import java.time.LocalDateTime;
import org.jfree.data.category.DefaultCategoryDataset;

public class WorkoutManager {

    private final DatabaseHandler db;

    public WorkoutManager(DatabaseHandler db) {
        this.db = db;
    }

    public Workout addWorkout(String username, String notes) {
        String sql = "INSERT INTO workouts(username, workout_datetime, duration, calories_burned, notes) VALUES(?,?,?,?,?)";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String workoutDateTime = LocalDateTime.now().toString();

            pstmt.setString(1, username);
            pstmt.setString(2, workoutDateTime);
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
            pstmt.setString(5, notes);

            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                int workoutId = keys.getInt(1);
                return new Workout(workoutId, username, 0, 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addExerciseToWorkout(int workoutId, ExerciseEntry entry) {
        String sql = "INSERT INTO exercise_entries(workout_id, exercise_name, sets, reps, duration, calories_burned) VALUES(?,?,?,?,?,?)";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, workoutId);
            pstmt.setString(2, entry.getExerciseName());
            pstmt.setInt(3, entry.getSets());
            pstmt.setInt(4, entry.getReps());
            pstmt.setInt(5, entry.getDuration());
            pstmt.setInt(6, entry.getCaloriesBurned());

            pstmt.executeUpdate();

            updateWorkoutDuration(workoutId);
            updateWorkoutCalories(workoutId);

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

    private void updateWorkoutCalories(int workoutId) {
        String sql = """
            UPDATE workouts
            SET calories_burned = (
                SELECT SUM(calories_burned)
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
                int caloriesBurned = workoutRs.getInt("calories_burned");
                String notes = workoutRs.getString("notes");

                history.append("Workout ID: ").append(workoutId).append("\n");
                history.append("Date: ").append(dateTime).append("\n");
                history.append("Total Duration: ").append(duration).append(" minutes\n");
                history.append("Total Calories Burned: ").append(caloriesBurned).append("\n");
                history.append("Exercises:\n");
                history.append("Notes: ").append(notes == null ? "None" : notes).append("\n");

                try (PreparedStatement exerciseStmt = conn.prepareStatement(exerciseSql)) {
                    exerciseStmt.setInt(1, workoutId);
                    ResultSet exerciseRs = exerciseStmt.executeQuery();

                    boolean hasExercises = false;

                    while (exerciseRs.next()) {
                        hasExercises = true;

                        ExerciseEntry entry = new ExerciseEntry(
                                exerciseRs.getInt("exercise_entry_id"),
                                exerciseRs.getString("exercise_name"),
                                exerciseRs.getInt("sets"),
                                exerciseRs.getInt("reps"),
                                exerciseRs.getInt("duration"),
                                exerciseRs.getInt("calories_burned")
                        );

                        history.append("  Exercise: ").append(entry.getExerciseName()).append("\n");
                        history.append("  Sets: ").append(entry.getSets()).append("\n");
                        history.append("  Reps: ").append(entry.getReps()).append("\n");
                        history.append("  Duration: ").append(entry.getDuration()).append(" minutes\n");
                        history.append("  Calories Burned: ").append(entry.getCaloriesBurned()).append("\n");
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

    public DefaultCategoryDataset getUserCaloriesDataset(String username) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = """
        SELECT workout_id, workout_datetime, calories_burned
        FROM workouts
        WHERE username = ?
        ORDER BY workout_datetime ASC
        """;

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int workoutId = rs.getInt("workout_id");
                int caloriesBurned = rs.getInt("calories_burned");

                dataset.addValue(
                        caloriesBurned,
                        "Calories Burned",
                        "Workout " + workoutId
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
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
                checkStmt.setInt(1, workoutId);
                checkStmt.setString(2, username);

                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    conn.rollback();
                    return false;
                }

                deleteExercisesStmt.setInt(1, workoutId);
                deleteExercisesStmt.executeUpdate();

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