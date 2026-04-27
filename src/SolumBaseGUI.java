import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class SolumBaseGUI {

    public static final Color TEAL = new Color(0, 188, 174);
    public static final Color PURPLE = new Color(95, 93, 255);
    public static final Color BACKGROUND = new Color(245, 245, 245);
    public static final Color WHITE = Color.WHITE;
    public static final Color BLACK = Color.BLACK;

    public static Font TITLE_FONT;
    public static Font TEXT_FONT;
    public static Font BUTTON_FONT;

    static {
        try {
            //Load font files
            InputStream boldStream = SolumBaseGUI.class.getResourceAsStream("/assets/Orbitron-Bold.ttf");
            InputStream regularStream = SolumBaseGUI.class.getResourceAsStream("/assets/Orbitron-Regular.ttf");

            //  If files not found → crash with message (helps debugging)
            if (boldStream == null || regularStream == null) {
                throw new RuntimeException("Orbitron font files NOT found in /assets folder");
            }

            //Create fonts
            Font boldFont = Font.createFont(Font.TRUETYPE_FONT, boldStream);
            Font regularFont = Font.createFont(Font.TRUETYPE_FONT, regularStream);

            //Register fonts with system
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(boldFont);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(regularFont);

            //Make fonts BIG so you can SEE it's working
            TITLE_FONT = boldFont.deriveFont(36f);
            TEXT_FONT = regularFont.deriveFont(18f);
            BUTTON_FONT = boldFont.deriveFont(16f);

            System.out.println("Orbitron font loaded successfully!");

        } catch (Exception e) {
            e.printStackTrace();

            //Fallback (if something breaks)
            TITLE_FONT = new Font("SansSerif", Font.BOLD, 36);
            TEXT_FONT = new Font("SansSerif", Font.PLAIN, 18);
            BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);

            System.out.println("Using fallback fonts (Orbitron failed)");
        }
    }
}