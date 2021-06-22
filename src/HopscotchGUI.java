import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Arrays;
import javax.swing.*;
import java.util.Collections;
import java.util.ArrayList;

import static java.awt.Color.WHITE;


public final class HopscotchGUI /*implements ActionListener, MouseListener, MouseMotionListener, KeyListener*/ {

    // trying to recreate colors from worksheet
    private static final Color DARK_BLUE = new Color(9, 90, 166);
    protected static final int tileSide = 70;
    protected static final int sideBuffer = 5;


    private static ArrayList<HopscotchTile> initializeTiles() {
        ArrayList<HopscotchTile> tiles = new ArrayList<HopscotchTile>();
        String[] tileNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        Collections.shuffle(Arrays.asList(tileNumbers));
        for (int i = 0; i < 15; i++) {
            tiles.add(new HopscotchTile(tileNumbers[i], i / 4, i % 4));
        }
        return tiles;
    }


    private static class HopscotchGame extends JPanel {

        ArrayList<HopscotchTile> tiles = initializeTiles();

        @Override
        //initializing the empty tiles
        public void paint(Graphics g) {
            Graphics2D mydraw = (Graphics2D) g;

            mydraw.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            for (HopscotchTile t : tiles) {
                mydraw.setColor(DARK_BLUE);
                mydraw.fillRect(t.getXpos() + sideBuffer, t.getYpos() + sideBuffer, tileSide, tileSide);
                mydraw.setColor(WHITE);
                mydraw.setFont(new Font("Arial", Font.BOLD, 35));
                //mydraw.drawString(t.getNum(), t.getXpos() + (int)(tileSide * 0.3), t.getYpos() + (int)(tileSide * 0.7));

                FontMetrics fm = mydraw.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(t.getNum(), mydraw);
                int x = t.getXpos() + (tileSide + sideBuffer - (int) r.getWidth()) / 2;
                int y = t.getYpos() + (tileSide + sideBuffer - (int) r.getHeight()) / 2 + fm.getAscent();
                g.drawString(t.getNum(), x, y);
            }

            /* column 1
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
            mydraw.fillRect(230, 155, 70, 70); */

            //rendering
        }
    }

    static class HopscotchFrame {

        JFrame myframe;

        HopscotchFrame() {

            ArrayList<HopscotchTile> tiles = new ArrayList<HopscotchTile>();
            initializeTiles();

            myframe = new JFrame("Hopscotch");

            myframe.setSize(305, 333);
            myframe.setLocationRelativeTo(null);
            myframe.setVisible(true);
            myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myframe.add(new HopscotchGame());
        }

    }

}
