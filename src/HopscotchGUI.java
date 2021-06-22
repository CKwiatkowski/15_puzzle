import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.util.Collections;
import java.util.ArrayList;

import static java.awt.Color.WHITE;


public final class HopscotchGUI {

    // trying to recreate colors from worksheet
    private static final Color DARK_BLUE = new Color(9, 90, 166);
    protected static final int tileSide = 70;
    protected static final int sideBuffer = 5;
    private static final int frameWidth = 305;
    private static final int frameHeight = 333;
    protected static int blankTileRow;
    protected static int blankTileCol;

    private static ArrayList<HopscotchTile> initializeTiles() {
        ArrayList<HopscotchTile> tiles = new ArrayList<HopscotchTile>();
        ArrayList<String> tileNumbers = new ArrayList<String>(
                Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"));
        Collections.shuffle(tileNumbers);
        tileNumbers.add("0");
        for (String s : tileNumbers)
            tiles.add(new HopscotchTile(s, tileNumbers.indexOf(s) / 4, tileNumbers.indexOf(s) % 4));
        return tiles;
    }


    private static class HopscotchGame extends JPanel {

        private int xMouseOld;
        private int yMouseOld;
        private int oldCol;
        private int oldRow;

        ArrayList<HopscotchTile> tiles = initializeTiles();

        HopscotchTile currentTile;

        @Override
        //initializing the empty tiles
        public void paint(Graphics g) {
            Graphics2D mydraw = (Graphics2D) g;

            mydraw.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            for (HopscotchTile t : tiles) {
                mydraw.setColor(DARK_BLUE);
                if (t.getNum().equals("0")) {
                    blankTileRow = t.getRow();
                    blankTileCol = t.getCol();
                    continue;
                }
                mydraw.fillRect(t.getXpos() + sideBuffer, t.getYpos() + sideBuffer, tileSide, tileSide);
                mydraw.setColor(WHITE);
                mydraw.setFont(new Font("Arial", Font.BOLD, 35));

                //credits for the following part of the code go to Gilbert LeBlanc on Stackoverflow
                // https://stackoverflow.com/questions/14284754/java-center-text-in-rectangle/14284949
                FontMetrics fm = mydraw.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(t.getNum(), mydraw);
                int x = t.getXpos() + (tileSide + sideBuffer - (int) r.getWidth()) / 2;
                int y = t.getYpos() + (tileSide + sideBuffer - (int) r.getHeight()) / 2 + fm.getAscent();
                g.drawString(t.getNum(), x, y);
            }
        }

        private class mylistener implements MouseInputListener {

            @Override
            //this method is empty - just clicking doesn't do anything
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            //this will calculate the position of the mouse which determines the selected tile
            // if no tile is selected, nothing happens
            public void mousePressed(MouseEvent e) {
                // only left clicks count
                if (e.getButton() == 1) {
                    xMouseOld = e.getX();
                    yMouseOld = e.getY();
                    oldCol = xMouseOld / tileSide + sideBuffer;
                    oldRow = yMouseOld / tileSide + sideBuffer;
                }

            }

            @Override
            //movement complete if the mouse is released
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            //empty
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            //move the tiles, calculate the direction!
            public void mouseDragged(MouseEvent e) {
                checkDirection(e.getX(), e.getY());
            }

            @Override
            //empty
            public void mouseExited(MouseEvent e) {

            }

            @Override
            //this method is going to stay empty
            public void mouseMoved(MouseEvent e) {

            }

        }

        private void checkDirection(int newX, int newY) {

            boolean down;
            boolean right;

            String[] directions = new String[2];

            // see todo in main: this right here needs to be improved
            int thisCol = newX / tileSide + sideBuffer;
            int thisRow = newY / tileSide + sideBuffer;

            down = newX > xMouseOld;
            right = newY > yMouseOld;

            directions[0] = down ? "down" : "up";
            directions[1] = right ? "right" : "left";

            if (validMove(newX, newY, thisCol, thisRow, directions)) {
                moveTiles();
            }
        }


        private boolean validMove(int x, int y, int col, int row, String[] dir) {

            // does the cursor move outside of the frame?
            if (x < 0 || x > frameHeight || y < 0 || y > frameHeight) {
                System.out.println("cursor out of bounds");
                return false;

                // cursor must move in the same column or row
                // is there a blank tile in this column or row?
            } else if (row == oldRow && row == blankTileRow || col == oldCol && col == blankTileCol) {
                return true;
            }
            return false;
        }

        //change tiles in list, then call paint method again
        // Multithreading does not seem necessary here
        private void moveTiles() {

            repaint();
        }
    }

    static class HopscotchFrame {

        JFrame myframe;

        HopscotchFrame() {

            ArrayList<HopscotchTile> tiles = new ArrayList<HopscotchTile>();
            initializeTiles();

            myframe = new JFrame("Hopscotch");

            myframe.setSize(frameWidth, frameHeight);
            myframe.setLocationRelativeTo(null);
            myframe.setVisible(true);
            myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myframe.add(new HopscotchGame());
        }

    }

}
