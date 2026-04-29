public class ValidationUtils {

    public static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean isValidHeight(int height) {
        return isBetween(height, 36, 96);
    }

    public static boolean isValidWeight(int weight) {
        return isBetween(weight, 70, 400);
    }

    public static boolean isValidSets(int sets) {
        return isBetween(sets, 1, 20);
    }

    public static boolean isValidReps(int reps) {
        return isBetween(reps, 1, 100);
    }

    public static boolean isValidDuration(int duration) {
        return isBetween(duration, 1, 300);
    }

    public static boolean isValidCalories(int calories) {
        return isBetween(calories, 100, 1000);
    }

    public static boolean isValidExerciseName(String name) {
        return name != null && !name.trim().isEmpty() && name.trim().length() <= 50;
    }

    public static boolean isValidUsername(String username) {
        return username != null && username.trim().length() >= 3 && username.trim().length() <= 20;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4 && password.length() <= 30;
    }
}