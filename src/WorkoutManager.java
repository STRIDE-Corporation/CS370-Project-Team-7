import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class WorkoutManager {

    private final DatabaseHandler db;

    public WorkoutManager(DatabaseHandler db) {
        this.db = db;
    }

    public Workout addWorkout(String username,
                              int duration,
                              int caloriesBurned,
                              String workoutSplit,
                              String moodEnergy,
                              String difficulty,
                              String workoutNotes) {

        String sql = """
            INSERT INTO workouts(
                username,
                workout_datetime,
                duration,
                calories_burned,
                workout_split,
                mood_energy_level,
                difficulty_rating,
                workout_notes
            )
            VALUES(?,?,?,?,?,?,?,?)
            """;

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String workoutDateTime = LocalDateTime.now().toString();

            pstmt.setString(1, username);
            pstmt.setString(2, workoutDateTime);
            pstmt.setInt(3, duration);
            pstmt.setInt(4, caloriesBurned);
            pstmt.setString(5, workoutSplit);
            pstmt.setString(6, moodEnergy);
            pstmt.setString(7, difficulty);
            pstmt.setString(8, workoutNotes);

            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();

            if (keys.next()) {
                int workoutId = keys.getInt(1);
                return new Workout(workoutId, username, duration);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addExerciseToWorkout(int workoutId, ExerciseEntry entry) {
        String sql = """
            INSERT INTO exercise_entries(
                workout_id,
                exercise_name,
                sets,
                reps,
                weight,
                rest_time_seconds,
                muscle_group,
                distance,
                pace_speed,
                incline_resistance,
                exercise_notes
            )
            VALUES(?,?,?,?,?,?,?,?,?,?,?)
            """;

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, workoutId);
            pstmt.setString(2, entry.getExerciseName());
            pstmt.setInt(3, entry.getSets());
            pstmt.setInt(4, entry.getReps());
            pstmt.setDouble(5, entry.getWeight());
            pstmt.setInt(6, entry.getRestTimeSeconds());
            pstmt.setString(7, entry.getMuscleGroup());
            pstmt.setDouble(8, entry.getDistance());
            pstmt.setString(9, entry.getPaceSpeed());
            pstmt.setString(10, entry.getInclineResistance());
            pstmt.setString(11, entry.getExerciseNotes());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUserWorkoutHistory(String username) {
        StringBuilder history = new StringBuilder();

        String workoutSql = "SELECT * FROM workouts WHERE username = ? ORDER BY workout_id DESC";
        String exerciseSql = "SELECT * FROM exercise_entries WHERE workout_id = ?";

        try (Connection conn = db.connect();
             PreparedStatement workoutStmt = conn.prepareStatement(workoutSql)) {

            workoutStmt.setString(1, username);
            ResultSet workoutRs = workoutStmt.executeQuery();

            boolean found = false;

            while (workoutRs.next()) {
                found = true;

                int workoutId = workoutRs.getInt("workout_id");
                String rawDateTime = workoutRs.getString("workout_datetime");
                int duration = workoutRs.getInt("duration");
                int caloriesBurned = workoutRs.getInt("calories_burned");
                String workoutSplit = workoutRs.getString("workout_split");
                String moodEnergy = workoutRs.getString("mood_energy_level");
                String difficulty = workoutRs.getString("difficulty_rating");
                String workoutNotes = workoutRs.getString("workout_notes");

                history.append("Workout ID: ").append(workoutId).append("\n");
                history.append("Date: ").append(formatDateTimePST(rawDateTime)).append("\n");
                history.append("Duration: ").append(duration).append(" minutes\n");
                history.append("Calories Burned: ").append(caloriesBurned).append("\n");
                history.append("Workout Split: ").append(workoutSplit).append("\n");
                history.append("Mood/Energy: ").append(moodEnergy).append("\n");
                history.append("Difficulty: ").append(difficulty).append("\n");

                if (workoutNotes != null && !workoutNotes.isEmpty()) {
                    history.append("Workout Notes: ").append(workoutNotes).append("\n");
                }

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
                        history.append("  Weight: ").append(exerciseRs.getDouble("weight")).append("\n");
                        history.append("  Rest Time: ").append(exerciseRs.getInt("rest_time_seconds")).append(" sec\n");
                        history.append("  Muscle Group: ").append(exerciseRs.getString("muscle_group")).append("\n");
                        history.append("  Distance: ").append(exerciseRs.getDouble("distance")).append("\n");
                        history.append("  Pace/Speed: ").append(exerciseRs.getString("pace_speed")).append("\n");
                        history.append("  Incline/Resistance: ").append(exerciseRs.getString("incline_resistance")).append("\n");

                        String exerciseNotes = exerciseRs.getString("exercise_notes");
                        if (exerciseNotes != null && !exerciseNotes.isEmpty()) {
                            history.append("  Notes: ").append(exerciseNotes).append("\n");
                        }

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

    private String formatDateTimePST(String rawDateTime) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(rawDateTime);

            ZonedDateTime pstTime = localDateTime.atZone(ZoneId.of("America/Los_Angeles"));

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("MMM d, yyyy 'at' h:mm a z");

            return pstTime.format(formatter);

        } catch (Exception e) {
            return rawDateTime;
        }
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