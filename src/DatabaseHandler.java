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
                goal TEXT NOT NULL
            );
            """;

        String workoutsTable = """
            CREATE TABLE IF NOT EXISTS workouts (
                workout_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                workout_datetime TEXT NOT NULL,
                duration INTEGER NOT NULL
            );
            """;

        String exercisesTable = """
            CREATE TABLE IF NOT EXISTS exercise_entries (
                exercise_entry_id INTEGER PRIMARY KEY AUTOINCREMENT,
                workout_id INTEGER NOT NULL,
                exercise_name TEXT NOT NULL,
                sets INTEGER NOT NULL,
                reps INTEGER NOT NULL,
                FOREIGN KEY (workout_id) REFERENCES workouts(workout_id)
            );
            """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(usersTable);
            stmt.execute(workoutsTable);
            stmt.execute(exercisesTable);

            System.out.println("Database initialized.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}