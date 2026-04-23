public class ExerciseEntry {

    private int exerciseEntryId;
    private String exerciseName;
    private int sets;
    private int reps;

    public ExerciseEntry(int exerciseEntryId, String exerciseName, int sets, int reps) {
        this.exerciseEntryId = exerciseEntryId;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
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

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void viewExercise() {
        System.out.println("Exercise ID: " + exerciseEntryId);
        System.out.println("Exercise Name: " + exerciseName);
        System.out.println("Sets: " + sets);
        System.out.println("Reps: " + reps);
    }
}