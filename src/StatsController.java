import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

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
        view.addBackListener(new BackListener());
    }

    private ChartPanel createChart() {

        DefaultCategoryDataset dataset =
                workoutManager.getUserCaloriesDataset(currentUser.getUsername());

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

        return new ChartPanel(chart);
    }
    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dashboardView.setVisible(true);
            view.dispose();
        }
    }
}