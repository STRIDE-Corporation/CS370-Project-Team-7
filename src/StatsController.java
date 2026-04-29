import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import java.awt.BasicStroke;
import java.awt.Color;

public class StatsController {

    private StatsScreen view;
    private MainDashBoard dashboardView;
    private UserProfile currentUser;
    private WorkoutManager workoutManager;

    public StatsController(StatsScreen view,
                           MainDashBoard dashboardView,
                           UserProfile currentUser,
                           WorkoutManager workoutManager) {

        this.view = view;
        this.dashboardView = dashboardView;
        this.currentUser = currentUser;
        this.workoutManager = workoutManager;

        view.setChart(createChart());
        setPersonalizedInsight();
        view.setPlanText(createPersonalizedPlan());
        view.addBackListener(new BackListener());
    }

    private ChartPanel createChart() {

        DefaultCategoryDataset dataset =
                workoutManager.getUserCaloriesDataset(currentUser.getUsername());

        int projectedCalories = workoutManager.getProjectedCalories(
                currentUser.getUsername(),
                currentUser.getGoal()
        );

        if (projectedCalories != -1) {
            dataset.addValue(
                    projectedCalories,
                    "Next Workout Projection",
                    "Next Workout"
            );
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Calories Burned",
                "Workout",
                "Calories",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        CategoryPlot plot = chart.getCategoryPlot();

        plot.setBackgroundPaint(new Color(35, 35, 40));
        plot.setRangeGridlinePaint(new Color(90, 90, 95));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 80, 80));
        renderer.setSeriesPaint(1, SolumBaseGUI.NEON_PURPLE);

        if (projectedCalories != -1) {
            int minTarget = (int) Math.round(projectedCalories * 0.9);
            int maxTarget = (int) Math.round(projectedCalories * 1.1);

            ValueMarker minMarker = new ValueMarker(minTarget);
            minMarker.setPaint(new Color(0, 220, 160));
            minMarker.setStroke(new BasicStroke(
                    2.5f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,
                    1.0f,
                    new float[]{10.0f, 6.0f},
                    0.0f
            ));
            minMarker.setLabel("Min Target (" + minTarget + ")");
            minMarker.setLabelPaint(new Color(0, 220, 160));
            minMarker.setLabelAnchor(RectangleAnchor.RIGHT);
            minMarker.setLabelTextAnchor(TextAnchor.CENTER_RIGHT);
            plot.addRangeMarker(minMarker, Layer.FOREGROUND);

            ValueMarker maxMarker = new ValueMarker(maxTarget);
            maxMarker.setPaint(new Color(0, 220, 160));
            maxMarker.setStroke(new BasicStroke(
                    2.5f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND,
                    1.0f,
                    new float[]{10.0f, 6.0f},
                    0.0f
            ));
            maxMarker.setLabel("Max Target (" + maxTarget + ")");
            maxMarker.setLabelPaint(new Color(0, 220, 160));
            maxMarker.setLabelAnchor(RectangleAnchor.RIGHT);
            maxMarker.setLabelTextAnchor(TextAnchor.CENTER_RIGHT);
            plot.addRangeMarker(maxMarker, Layer.FOREGROUND);
        }

        return new ChartPanel(chart);
    }

    private void setPersonalizedInsight() {
        int projectedCalories = workoutManager.getProjectedCalories(
                currentUser.getUsername(),
                currentUser.getGoal()
        );

        if (projectedCalories == -1) {
            view.setInsight("Log at least 3 workouts to unlock your personalized plan.");
            return;
        }

        int trend = workoutManager.getCalorieTrend(currentUser.getUsername());

        if (trend > 50) {
            view.setInsight("Your calorie burn is trending upward. Next goal: aim for about " + projectedCalories + " calories.");
        } else if (trend < -50) {
            view.setInsight("Your calorie burn is trending downward. Next goal: aim for about " + projectedCalories + " calories.");
        } else {
            view.setInsight("Your calorie burn is steady. Next goal: aim for about " + projectedCalories + " calories.");
        }
    }

    private String createPersonalizedPlan() {
        int workoutCount = workoutManager.getWorkoutCount(currentUser.getUsername());

        if (workoutCount < 3) {
            return "Plan locked\n\n" +
                    "Log at least 3 workouts to unlock your personalized plan.\n\n" +
                    "Current progress: " + workoutCount + "/3 workouts";
        }

        int projectedCalories = workoutManager.getProjectedCalories(
                currentUser.getUsername(),
                currentUser.getGoal()
        );

        int minTarget = (int) Math.round(projectedCalories * 0.9);
        int maxTarget = (int) Math.round(projectedCalories * 1.1);
        int weeklyGoal = projectedCalories * 3;
        int latestCalories = workoutManager.getLatestWorkoutCalories(currentUser.getUsername());

        String analysis;

        if (latestCalories < minTarget) {
            analysis = "Your latest workout was " + latestCalories +
                    " calories, which was below your target range. Try increasing duration or intensity next time.";
        } else if (latestCalories > maxTarget) {
            analysis = "Your latest workout was " + latestCalories +
                    " calories, which was above your target range. Great effort, but make sure recovery stays balanced.";
        } else {
            analysis = "Your latest workout was " + latestCalories +
                    " calories, which landed inside your target range. Keep this pace.";
        }

        String recommend;

        if (latestCalories < minTarget) {
            recommend = "Increase your calorie burn next workout toward at least " + minTarget + ".";
        } else if (latestCalories > maxTarget) {
            recommend = "Reduce your calorie burn slightly toward " + projectedCalories + " to stay in range.";
        } else {
            recommend = "Stay within your current range of " + minTarget + " - " + maxTarget + " calories.";
        }

        return "Goal: " + currentUser.getGoal().name() + "\n\n" +
                "Latest workout analysis:\n" +
                analysis + "\n\n" +
                "Next workout target:\n" +
                projectedCalories + " calories\n\n" +
                "Target range:\n" +
                minTarget + " - " + maxTarget + " calories\n\n" +
                "Weekly suggestion:\n" +
                "3 workouts, about " + weeklyGoal + " total calories\n\n" +
                "Recommendation:\n" +
                recommend;
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dashboardView.setVisible(true);
            view.dispose();
        }
    }
}