public class ExerciseEntry {

    private int exerciseEntryId;
    private String exerciseName;
    private int sets;
    private int reps;
    private double weight;
    private int restTimeSeconds;
    private String muscleGroup;
    private double distance;
    private String paceSpeed;
    private String inclineResistance;
    private String exerciseNotes;

    public ExerciseEntry(int exerciseEntryId,
                         String exerciseName,
                         int sets,
                         int reps,
                         double weight,
                         int restTimeSeconds,
                         String muscleGroup,
                         double distance,
                         String paceSpeed,
                         String inclineResistance,
                         String exerciseNotes) {

        this.exerciseEntryId = exerciseEntryId;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.restTimeSeconds = restTimeSeconds;
        this.muscleGroup = muscleGroup;
        this.distance = distance;
        this.paceSpeed = paceSpeed;
        this.inclineResistance = inclineResistance;
        this.exerciseNotes = exerciseNotes;
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

    public double getWeight() {
        return weight;
    }

    public int getRestTimeSeconds() {
        return restTimeSeconds;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public double getDistance() {
        return distance;
    }

    public String getPaceSpeed() {
        return paceSpeed;
    }

    public String getInclineResistance() {
        return inclineResistance;
    }

    public String getExerciseNotes() {
        return exerciseNotes;
    }

    // ✅ FIXED: This is what your Workout.java was calling
    public void viewExercise() {
        System.out.println("Exercise ID: " + exerciseEntryId);
        System.out.println("Exercise Name: " + exerciseName);
        System.out.println("Sets: " + sets);
        System.out.println("Reps: " + reps);
        System.out.println("Weight: " + weight);
        System.out.println("Rest Time: " + restTimeSeconds + " sec");
        System.out.println("Muscle Group: " + muscleGroup);
        System.out.println("Distance: " + distance);
        System.out.println("Pace/Speed: " + paceSpeed);
        System.out.println("Incline/Resistance: " + inclineResistance);

        if (exerciseNotes != null && !exerciseNotes.isEmpty()) {
            System.out.println("Notes: " + exerciseNotes);
        }
    }
}