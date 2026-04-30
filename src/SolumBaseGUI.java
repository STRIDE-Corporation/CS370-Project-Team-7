import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.InputStream;
import java.util.Enumeration;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;

public class SolumBaseGUI {

    public static final Color TEAL = new Color(0, 188, 174);
    public static final Color PURPLE = new Color(95, 93, 255);

    public static final Color NEON_PURPLE = new Color(160, 90, 255);
    public static final Color GLOW_STRONG = new Color(210, 160, 255);

    public static final Color BACKGROUND = new Color(10, 10, 15);
    public static final Color FIELD_BACKGROUND = new Color(25, 25, 35);
    public static final Color BUTTON_BACKGROUND = new Color(30, 25, 50);
    public static final Color BUTTON_HOVER = new Color(55, 40, 90);

    public static final Color WHITE = Color.WHITE;
    public static final Color BLACK = Color.BLACK;

    public static Font TITLE_FONT;
    public static Font TEXT_FONT;
    public static Font BUTTON_FONT;

    public static void styleFrame(JFrame frame) {
        frame.setSize(1100, 700);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BACKGROUND);
    }

    public static void styleFrameFullscreen(JFrame frame) {
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BACKGROUND);
    }

    private static void applyGlobalFont(Font font) {
        FontUIResource uiFont = new FontUIResource(font);

        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof FontUIResource) {
                UIManager.put(key, uiFont);
            }
        }
    }

    public static Font title(float size) {
        return TITLE_FONT.deriveFont(Font.BOLD, size);
    }

    public static Font text(float size) {
        return TEXT_FONT.deriveFont(Font.PLAIN, size);
    }

    public static Font button(float size) {
        return BUTTON_FONT.deriveFont(Font.BOLD, size);
    }

    public static void showError(Component parent, String message) {
        showThemedDialog(parent, "Error", message, JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessage(Component parent, String message) {
        showThemedDialog(parent, "Message", message, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirm(Component parent, String message) {
        applyPopupTheme();

        int result = JOptionPane.showConfirmDialog(
                parent,
                message,
                "Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return result == JOptionPane.YES_OPTION;
    }

    private static void showThemedDialog(Component parent, String title, String message, int type) {
        applyPopupTheme();
        JOptionPane.showMessageDialog(parent, message, title, type);
    }

    private static void applyPopupTheme() {
        UIManager.put("OptionPane.background", BACKGROUND);
        UIManager.put("Panel.background", BACKGROUND);
        UIManager.put("OptionPane.messageForeground", WHITE);
        UIManager.put("OptionPane.messageFont", text(18f));
        UIManager.put("OptionPane.buttonFont", button(14f));

        UIManager.put("Button.background", BUTTON_BACKGROUND);
        UIManager.put("Button.foreground", NEON_PURPLE);
        UIManager.put("Button.focus", GLOW_STRONG);
        UIManager.put("Button.border", BorderFactory.createLineBorder(NEON_PURPLE, 2));
    }

    public static void styleChart(JFreeChart chart) {
        chart.setBackgroundPaint(BACKGROUND);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(20, 20, 30));
        plot.setOutlineVisible(false);

        plot.setRangeGridlinePaint(NEON_PURPLE.darker());
        plot.setDomainGridlinesVisible(false);

        CategoryAxis domainAxis = plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        domainAxis.setLabelFont(text(16f));
        domainAxis.setTickLabelFont(text(14f));
        domainAxis.setLabelPaint(WHITE);
        domainAxis.setTickLabelPaint(WHITE);

        rangeAxis.setLabelFont(text(16f));
        rangeAxis.setTickLabelFont(text(14f));
        rangeAxis.setLabelPaint(WHITE);
        rangeAxis.setTickLabelPaint(WHITE);

        if (chart.getTitle() != null) {
            chart.getTitle().setFont(title(24f));
            chart.getTitle().setPaint(NEON_PURPLE);
        }

        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(text(14f));
            chart.getLegend().setBackgroundPaint(BACKGROUND);
            chart.getLegend().setItemPaint(WHITE);
        }

        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        GradientPaint neonGradient = new GradientPaint(
                0f, 0f, new Color(140, 80, 255),
                0f, 300f, new Color(80, 40, 200)
        );

        renderer.setSeriesPaint(0, neonGradient);
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
    }

    static {
        try {
            InputStream boldStream = SolumBaseGUI.class.getResourceAsStream("/assets/Orbitron-Bold.ttf");
            InputStream regularStream = SolumBaseGUI.class.getResourceAsStream("/assets/Orbitron-Regular.ttf");

            if (boldStream == null || regularStream == null) {
                throw new RuntimeException("Orbitron font files NOT found in /assets folder");
            }

            Font boldFont = Font.createFont(Font.TRUETYPE_FONT, boldStream);
            Font regularFont = Font.createFont(Font.TRUETYPE_FONT, regularStream);

            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(boldFont);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(regularFont);

            TITLE_FONT = boldFont.deriveFont(36f);
            TEXT_FONT = regularFont.deriveFont(18f);
            BUTTON_FONT = boldFont.deriveFont(16f);

            applyGlobalFont(TEXT_FONT);

            System.out.println("Orbitron font loaded + applied globally!");

        } catch (Exception e) {
            e.printStackTrace();

            TITLE_FONT = new Font("SansSerif", Font.BOLD, 36);
            TEXT_FONT = new Font("SansSerif", Font.PLAIN, 18);
            BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);

            applyGlobalFont(TEXT_FONT);

            System.out.println("Using fallback fonts");
        }
    }
}