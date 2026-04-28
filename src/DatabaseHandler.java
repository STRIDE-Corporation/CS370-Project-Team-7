import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

    private static final String URL = "jdbc:sqlite:fitness.db";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public void initializeDatabase() {
        String usersTable = """
            CREATE TABLE IF NOT EXISTS users (
                user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                height INTEGER NOT NULL,
                weight INTEGER NOT NULL,
                goal TEXT NOT NULL,
                unit_preference TEXT NOT NULL DEFAULT 'IMPERIAL'
            );
            """;

        String workoutsTable = """
            CREATE TABLE IF NOT EXISTS workouts (
                workout_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                workout_datetime TEXT NOT NULL,
                duration INTEGER NOT NULL,
                calories_burned INTEGER DEFAULT 0,
                workout_split TEXT,
                mood_energy_level TEXT,
                difficulty_rating TEXT,
                workout_notes TEXT
            );
            """;

        String exercisesTable = """
            CREATE TABLE IF NOT EXISTS exercise_entries (
                exercise_entry_id INTEGER PRIMARY KEY AUTOINCREMENT,
                workout_id INTEGER NOT NULL,
                exercise_name TEXT NOT NULL,
                sets INTEGER,
                reps INTEGER,
                weight REAL,
                rest_time_seconds INTEGER,
                muscle_group TEXT,
                distance REAL,
                pace_speed TEXT,
                incline_resistance TEXT,
                exercise_notes TEXT,
                FOREIGN KEY (workout_id) REFERENCES workouts(workout_id)
            );
            """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(usersTable);
            stmt.execute(workoutsTable);
            stmt.execute(exercisesTable);

            addColumnIfMissing(stmt, "users", "unit_preference", "TEXT NOT NULL DEFAULT 'IMPERIAL'");

            addColumnIfMissing(stmt, "workouts", "calories_burned", "INTEGER DEFAULT 0");
            addColumnIfMissing(stmt, "workouts", "workout_split", "TEXT");
            addColumnIfMissing(stmt, "workouts", "mood_energy_level", "TEXT");
            addColumnIfMissing(stmt, "workouts", "difficulty_rating", "TEXT");
            addColumnIfMissing(stmt, "workouts", "workout_notes", "TEXT");

            addColumnIfMissing(stmt, "exercise_entries", "weight", "REAL");
            addColumnIfMissing(stmt, "exercise_entries", "rest_time_seconds", "INTEGER");
            addColumnIfMissing(stmt, "exercise_entries", "muscle_group", "TEXT");
            addColumnIfMissing(stmt, "exercise_entries", "distance", "REAL");
            addColumnIfMissing(stmt, "exercise_entries", "pace_speed", "TEXT");
            addColumnIfMissing(stmt, "exercise_entries", "incline_resistance", "TEXT");
            addColumnIfMissing(stmt, "exercise_entries", "exercise_notes", "TEXT");

            System.out.println("Database initialized.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addColumnIfMissing(Statement stmt, String tableName, String columnName, String columnDefinition) {
        try {
            stmt.execute("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnDefinition + ";");
            System.out.println(columnName + " column added to " + tableName + ".");
        } catch (SQLException e) {
            // Column already exists, so ignore.
        }
    }
}