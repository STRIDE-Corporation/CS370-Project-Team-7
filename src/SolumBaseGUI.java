import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class SolumBaseGUI {

    public static final Color TEAL = new Color(0, 188, 174);
    public static final Color PURPLE = new Color(95, 93, 255);

    // 🔥 Indigo Tron Theme
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

    // 🔥 Standard window (Login / Register / etc.)
    public static void styleFrame(JFrame frame) {
        frame.setSize(1100, 700);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setLocationRelativeTo(null);
    }

    // 🔥 Fullscreen (Dashboard)
    public static void styleFrameFullscreen(JFrame frame) {
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
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

            System.out.println("Orbitron font loaded successfully!");

        } catch (Exception e) {
            e.printStackTrace();

            TITLE_FONT = new Font("SansSerif", Font.BOLD, 36);
            TEXT_FONT = new Font("SansSerif", Font.PLAIN, 18);
            BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);

            System.out.println("Using fallback fonts (Orbitron failed)");
        }
    }
}