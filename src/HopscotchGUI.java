import java.awt.*;
import javax.swing.*;


public final class HopscotchGUI /*implements ActionListener, MouseListener, MouseMotionListener, KeyListener*/ {

    // trying to recreate colors from worksheet
    public static final Color DARK_BLUE = new Color(9, 90, 166);

    private static class HopscotchGame extends JPanel {

        @Override
        //initializing the empty tiles
        public void paint(Graphics g) {
            Graphics2D mydraw = (Graphics2D) g;
            mydraw.setColor(DARK_BLUE);
            // column 1
            mydraw.fillRect(5, 5, 70, 70);
            mydraw.fillRect(5, 80, 70, 70);
            mydraw.fillRect(5, 155, 70, 70);
            mydraw.fillRect(5, 230, 70, 70);
            // column 2
            mydraw.fillRect(80, 5, 70, 70);
            mydraw.fillRect(80, 80, 70, 70);
            mydraw.fillRect(80, 155, 70, 70);
            mydraw.fillRect(80, 230, 70, 70);
            // column 3
            mydraw.fillRect(155, 5, 70, 70);
            mydraw.fillRect(155, 80, 70, 70);
            mydraw.fillRect(155, 155, 70, 70);
            mydraw.fillRect(155, 230, 70, 70);
            // column 4
            mydraw.fillRect(230, 5, 70, 70);
            mydraw.fillRect(230, 80, 70, 70);
            mydraw.fillRect(230, 155, 70, 70);
        }

        //?
        public void animate(Graphics g) {

            Graphics2D mydraw = (Graphics2D) g;
            mydraw.setColor(DARK_BLUE);

            //rendering
            mydraw.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    static class HopscotchFrame {

        JFrame myframe;

        HopscotchFrame() {
            myframe = new JFrame("Hopscotch");

            myframe.setSize(305, 333);
            myframe.setLocationRelativeTo(null);
            myframe.setVisible(true);
            myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            myframe.add(new HopscotchGame());
        }

    }

}
