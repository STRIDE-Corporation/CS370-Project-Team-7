public class ExerciseEntry {

    private int exerciseEntryId;
    private String exerciseName;
    private int sets;
    private int reps;
    private int duration;
    private int caloriesBurned;

    public ExerciseEntry(int exerciseEntryId, String exerciseName, int sets, int reps, int duration, int caloriesBurned) {
        this.exerciseEntryId = exerciseEntryId;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

    public int getExerciseEntryId() {
        return exerciseEntryId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getDuration() {
        return duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void viewExercise() {
        System.out.println("Exercise ID: " + exerciseEntryId);
        System.out.println("Exercise Name: " + exerciseName);
        System.out.println("Sets: " + sets);
        System.out.println("Reps: " + reps);
        System.out.println("Duration: " + duration + " minutes");
    }
}