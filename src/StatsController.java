import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;

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
        DefaultCategoryDataset original =
                workoutManager.getCaloriesDataset(currentUser.getUsername());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int r = 0; r < original.getRowCount(); r++) {
            Comparable<?> rowKey = original.getRowKey(r);

            for (int c = 0; c < original.getColumnCount(); c++) {
                Comparable<?> colKey = original.getColumnKey(c);

                if (!colKey.toString().equalsIgnoreCase("Next Workout")
                        && !colKey.toString().equalsIgnoreCase("Projection")) {

                    Number value = original.getValue(r, c);
                    if (value != null) {
                        dataset.addValue(value, rowKey, colKey);
                    }
                }
            }
        }

        int projectedCalories = workoutManager.getProjectedCalories(
                currentUser.getUsername(),
                currentUser.getGoal()
        );

        if (projectedCalories != -1) {
            dataset.addValue(projectedCalories, "Next Workout Projection", "Projection");
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Estimated Calories Burned",
                "Workout",
                "Calories",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        applyOrbitronChartStyle(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        GradientPaint workoutGradient = new GradientPaint(
                0f, 0f, SolumBaseGUI.GLOW_STRONG,
                0f, 180f, SolumBaseGUI.NEON_PURPLE
        );

        GradientPaint projectionGradient = new GradientPaint(
                0f, 0f, new Color(90, 210, 255),
                0f, 180f, new Color(5, 70, 190)
        );

        renderer.setSeriesPaint(0, workoutGradient);
        renderer.setSeriesPaint(1, projectionGradient);

        renderer.setDrawBarOutline(true);
        renderer.setSeriesOutlinePaint(0, new Color(230, 190, 255));
        renderer.setSeriesOutlineStroke(0, new BasicStroke(2.5f));

        renderer.setSeriesOutlinePaint(1, new Color(150, 230, 255));
        renderer.setSeriesOutlineStroke(1, new BasicStroke(3.5f));

        renderer.setShadowVisible(false);
        renderer.setItemMargin(0.05);
        renderer.setMaximumBarWidth(0.075);

        applyCustomLegend(plot);

        if (projectedCalories != -1) {
            int minTarget = (int) Math.round(projectedCalories * 0.9);
            int maxTarget = (int) Math.round(projectedCalories * 1.1);
            addTargetRangeBand(plot, minTarget, maxTarget);
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(SolumBaseGUI.BACKGROUND);
        chartPanel.setOpaque(true);
        chartPanel.setBorder(null);

        return chartPanel;
    }

    private void applyCustomLegend(CategoryPlot plot) {
        LegendItemCollection legendItems = new LegendItemCollection();
        Shape box = new Rectangle2D.Double(-4, -4, 8, 8);

        legendItems.add(new LegendItem(
                "Actual Calories",
                "Calories burned in workouts",
                null,
                null,
                box,
                SolumBaseGUI.NEON_PURPLE
        ));

        legendItems.add(new LegendItem(
                "Next Workout Projection",
                "Your target for next workout",
                null,
                null,
                box,
                new Color(50, 160, 255)
        ));

        legendItems.add(new LegendItem(
                "Target Range",
                "Recommended range",
                null,
                null,
                box,
                new Color(0, 220, 160)
        ));

        plot.setFixedLegendItems(legendItems);
    }

    private void applyOrbitronChartStyle(JFreeChart chart) {
        chart.setBackgroundPaint(SolumBaseGUI.BACKGROUND);

        if (chart.getTitle() != null) {
            chart.getTitle().setFont(SolumBaseGUI.title(24f));
            chart.getTitle().setPaint(SolumBaseGUI.WHITE);
        }

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(SolumBaseGUI.BACKGROUND);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(55, 45, 75));

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(SolumBaseGUI.button(16f));
        domainAxis.setTickLabelFont(SolumBaseGUI.text(13f));
        domainAxis.setLabelPaint(SolumBaseGUI.WHITE);
        domainAxis.setTickLabelPaint(SolumBaseGUI.WHITE);
        domainAxis.setAxisLinePaint(SolumBaseGUI.NEON_PURPLE);
        domainAxis.setTickMarkPaint(SolumBaseGUI.NEON_PURPLE);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(SolumBaseGUI.button(16f));
        rangeAxis.setTickLabelFont(SolumBaseGUI.text(13f));
        rangeAxis.setLabelPaint(SolumBaseGUI.WHITE);
        rangeAxis.setTickLabelPaint(SolumBaseGUI.WHITE);
        rangeAxis.setAxisLinePaint(SolumBaseGUI.NEON_PURPLE);
        rangeAxis.setTickMarkPaint(SolumBaseGUI.NEON_PURPLE);

        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(SolumBaseGUI.text(13f));
            chart.getLegend().setItemPaint(SolumBaseGUI.WHITE);
            chart.getLegend().setBackgroundPaint(SolumBaseGUI.BACKGROUND);
            chart.getLegend().setFrame(new BlockBorder(SolumBaseGUI.BACKGROUND));
        }
    }

    private void addTargetRangeBand(CategoryPlot plot, int min, int max) {
        IntervalMarker band = new IntervalMarker(min, max);

        band.setPaint(new Color(0, 220, 160, 45));
        band.setOutlinePaint(new Color(0, 220, 160));
        band.setOutlineStroke(new BasicStroke(2.0f));

        plot.addRangeMarker(band, Layer.BACKGROUND);
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
            return "Plan locked\n\nLog at least 3 workouts to unlock.\n\nProgress: "
                    + workoutCount + "/3 workouts";
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
            analysis = "Below range. Increase intensity.";
        } else if (latestCalories > maxTarget) {
            analysis = "Above range. Balance recovery.";
        } else {
            analysis = "Good job. Stay consistent.";
        }

        return "Goal: " + currentUser.getGoal().name() + "\n\n" +
                "Latest workout analysis:\n" + analysis + "\n\n" +
                "Next workout target:\n" + projectedCalories + " calories\n\n" +
                "Target range:\n" + minTarget + " - " + maxTarget + "\n\n" +
                "Weekly suggestion:\n3 workouts (~" + weeklyGoal + " calories)";
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dashboardView.setVisible(true);
            view.dispose();
        }
    }
}