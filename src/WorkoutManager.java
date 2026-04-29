import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
                SELECT COALESCE(SUM(duration), 0)
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
                SELECT COALESCE(SUM(calories_burned), 0)
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

                int rows = deleteWorkoutStmt.executeUpdate();

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

    public DefaultCategoryDataset getCaloriesDataset(String username) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = """
            SELECT workout_id, calories_burned
            FROM workouts
            WHERE username = ?
            ORDER BY workout_datetime ASC
            """;

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dataset.addValue(
                        rs.getInt("calories_burned"),
                        "Calories",
                        "Workout " + rs.getInt("workout_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public int getWorkoutCount(String username) {
        String sql = "SELECT COUNT(*) AS workout_count FROM workouts WHERE username = ?";

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("workout_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getRecentAverageCaloriesBurned(String username, int limit) {
        String sql = """
            SELECT calories_burned
            FROM workouts
            WHERE username = ?
            ORDER BY workout_datetime DESC
            LIMIT ?
            """;

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setInt(2, limit);

            ResultSet rs = pstmt.executeQuery();

            int total = 0;
            int count = 0;

            while (rs.next()) {
                total += rs.getInt("calories_burned");
                count++;
            }

            if (count == 0) {
                return 0;
            }

            return (double) total / count;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getCalorieTrend(String username) {
        String sql = """
            SELECT calories_burned
            FROM workouts
            WHERE username = ?
            ORDER BY workout_datetime DESC
            LIMIT 5
            """;

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            int[] calories = new int[5];
            int count = 0;

            while (rs.next() && count < 5) {
                calories[count] = rs.getInt("calories_burned");
                count++;
            }

            if (count < 3) {
                return 0;
            }

            int newest = calories[0];
            int oldest = calories[count - 1];

            return newest - oldest;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getLatestWorkoutCalories(String username) {
        String sql = """
            SELECT calories_burned
            FROM workouts
            WHERE username = ?
            ORDER BY workout_datetime DESC
            LIMIT 1
            """;

        try (Connection conn = db.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("calories_burned");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int getProjectedCalories(String username, UserProfile.Goal goal) {
        int workoutCount = getWorkoutCount(username);

        if (workoutCount < 3) {
            return -1;
        }

        double recentAverage = getRecentAverageCaloriesBurned(username, 5);
        int trend = getCalorieTrend(username);

        double projection = recentAverage;

        switch (goal) {
            case WEIGHT_LOSS:
                projection = recentAverage * 1.10;

                if (trend > 50) {
                    projection = recentAverage * 1.15;
                } else if (trend < -50) {
                    projection = recentAverage * 1.05;
                }
                break;

            case WEIGHT_GAIN:
                projection = recentAverage * 0.90;

                if (trend > 50) {
                    projection = recentAverage * 0.85;
                } else if (trend < -50) {
                    projection = recentAverage * 0.95;
                }
                break;

            case MAINTENANCE:
            default:
                projection = recentAverage;

                if (trend > 75) {
                    projection = recentAverage * 0.95;
                } else if (trend < -75) {
                    projection = recentAverage * 1.05;
                }
                break;
        }

        return (int) Math.round(projection);
    }

    public String getUserWorkoutHistory(String username) {
        StringBuilder history = new StringBuilder();

        String workoutSql = "SELECT * FROM workouts WHERE username = ?";
        String exerciseSql = "SELECT * FROM exercise_entries WHERE workout_id = ?";

        try (Connection conn = db.connect();
             PreparedStatement workoutStmt = conn.prepareStatement(workoutSql)) {

            workoutStmt.setString(1, username);
            ResultSet workoutRs = workoutStmt.executeQuery();

            boolean first = true;

            while (workoutRs.next()) {

                if (!first) {
                    history.append("\n\n");
                }
                first = false;

                int workoutId = workoutRs.getInt("workout_id");
                String dateTime = workoutRs.getString("workout_datetime");
                int duration = workoutRs.getInt("duration");
                int calories = workoutRs.getInt("calories_burned");
                String notes = workoutRs.getString("notes");

                history.append("Workout ID: ").append(workoutId).append("\n");
                history.append("Date: ").append(formatWorkoutDate(dateTime)).append("\n");
                history.append("Total Duration: ").append(duration).append(" minutes\n");
                history.append("Estimated Total Calories Burned: ").append(calories).append("\n\n");

                history.append("Notes:\n")
                        .append(formatNotes(notes))
                        .append("\n");

                history.append("Exercises:\n");

                try (PreparedStatement exStmt = conn.prepareStatement(exerciseSql)) {
                    exStmt.setInt(1, workoutId);
                    ResultSet exRs = exStmt.executeQuery();

                    while (exRs.next()) {
                        history.append("  Exercise: ").append(exRs.getString("exercise_name")).append("\n");
                        history.append("  Sets: ").append(exRs.getInt("sets")).append("\n");
                        history.append("  Reps: ").append(exRs.getInt("reps")).append("\n");
                        history.append("  Duration: ").append(exRs.getInt("duration")).append(" minutes\n");
                        history.append("  Estimated Calories Burned: ").append(exRs.getInt("calories_burned")).append("\n\n");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history.toString();
    }

    private String formatWorkoutDate(String dateTimeText) {
        try {
            LocalDateTime dt = LocalDateTime.parse(dateTimeText);

            String month = dt.getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH);
            int day = dt.getDayOfMonth();
            int year = dt.getYear();

            String time = dt.format(DateTimeFormatter.ofPattern("h:mm a"));

            return month + " " + day + getDaySuffix(day) + ", " + year + " at " + time + " PST";

        } catch (Exception e) {
            return dateTimeText;
        }
    }

    private String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) return "th";

        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    private String formatNotes(String notes) {
        if (notes == null || notes.trim().isEmpty()) {
            return "- None";
        }

        String[] lines = notes.split("\\R");
        StringBuilder formatted = new StringBuilder();

        for (String line : lines) {
            String trimmed = line.trim();

            if (trimmed.isEmpty()) {
                continue;
            }

            if (trimmed.startsWith("-")) {
                formatted.append(trimmed).append("\n");
            } else {
                formatted.append("- ").append(trimmed).append("\n");
            }
        }

        return formatted.toString();
    }
}