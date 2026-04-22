import java.time.LocalDate;

public class Workout {

    private int workoutId;       // Primary Key in DB
    private int userId;          // Foreign Key linking to the User
    private LocalDate date;      // Date the workout occurred
    private int durationMinutes; // Length of the workout
    private String type;         // e.g., "Cardio", "Strength", "Flexibility"

    public Workout(int userId, LocalDate date, int durationMinutes, String type) {
        this.userId = userId;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.type = type;
    }

    public Workout(int workoutId, int userId, LocalDate date, int durationMinutes, String type) {
        this.workoutId = workoutId;
        this.userId = userId;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.type = type;
    }



    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "ID=" + workoutId +
                ", UserID=" + userId +
                ", Date=" + date +
                ", Duration=" + durationMinutes + " mins" +
                ", Type='" + type + '\'' +
                '}';
    }
}
