import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import javax.swing.*;
import java.util.Collections;
import java.util.ArrayList;

import static java.awt.Color.WHITE;


public final class HopscotchGUI extends JPanel implements MouseListener {

    // trying to recreate colors from worksheet
    private static final Color DARK_BLUE = new Color(9, 90, 166);
    private static final int tileSide = 70;
    private static final int sideBuffer = 5;
    private static final int frameWidth = 305;
    private static final int frameHeight = 333;

    int oldCol;
    int oldRow;
    char direction;

    public static int getFrameWidth() {
        return frameWidth;
    }

    public static int getFrameHeight() {
        return frameHeight;
    }

    public static int getTileSide() {
        return tileSide;
    }

    public static int getSideBuffer() {
        return sideBuffer;
    }

    private ArrayList<HopscotchTile> tiles = initializeTiles();

    private HopscotchTile blankTile;

    HopscotchGUI() {
        JFrame myframe = new JFrame("Hopscotch");
        myframe.setSize(frameWidth, frameHeight);
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.addMouseListener(this);
        myframe.add(this);
    }

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

    @Override
    //this method is empty - just clicking doesn't do anything
    public void mouseClicked(MouseEvent e) {
        // only left clicks count
        if (e.getButton() == 1) {

            //todo: there is probably a better way to determine this
            oldCol = e.getX() / (tileSide + sideBuffer);
            oldRow = e.getY() / (tileSide + sideBuffer);

            // confirm validity of the move
            if (e.getX() < 0 || e.getX() > frameHeight || e.getY() < 0 || e.getY() > frameHeight) {
                throw new IndexOutOfBoundsException("Clicked outside the game's frame");
            }
            if (blankTile.getRow() == oldRow) {
                if (blankTile.getCol() < oldCol) {
                    direction = 'l';
                } else if (blankTile.getCol() > oldCol) {
                    direction = 'r';
                } else
                    return;
            }
            if (blankTile.getCol() == oldCol) {
                if (blankTile.getRow() < oldRow) {
                    direction = 'u';
                } else if (blankTile.getRow() > oldRow) {
                    direction = 'd';
                } else
                    return;
            } else if (oldCol < 0 || oldCol > 3 || oldRow < 0 || oldRow > 3) {
                throw new IndexOutOfBoundsException("Invalid tile placement:");
            }
            moveTiles(direction);
        }
    }

    @Override
    // decided to leave this one empty - animating drag&drop is too much work for now. Maybe later.
    public void mousePressed(MouseEvent e) {

    }

    @Override
    // i'll leave this empty for now as well
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    //empty
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    //empty
    public void mouseExited(MouseEvent e) {
    }

    // this does not seem to be necessary any more
        /*private boolean validMove(int x, int y, HopscotchTile t, String[] dir) {

            // does the cursor move outside of the frame?
            if (x < 0 || x > frameHeight || y < 0 || y > frameHeight) {
                System.out.println("cursor out of bounds");
                return false;
            }

            //did the cursor move on the blank tile?
             if (t.getRow() == oldRow && t.getRow() == blankTileRow || t.getCol() == oldCol && t.getCol() == blankTileCol) {
                return true;
            }
            return false;
        }*/

    //change tiles in list, then move them on screen
    //Multithreading does not seem necessary here. But maybe add a r/w lock until movement is done
    //catch errors if tiles move wrong?
    private void moveTiles(char direction) {
        System.out.println("directions");
        switch (direction) {
            case 'd':
                System.out.println("down");
                for (HopscotchTile t : tiles) {
                    if (t.getRow() < blankTile.getRow() && t.getCol() == blankTile.getCol())
                        t.setRow(t.getRow() + 1);
                    System.out.print(t.getNum());
                }
                break;
            case 'u':
                for (HopscotchTile t : tiles) {
                    if (t.getRow() > blankTile.getRow() && t.getCol() == blankTile.getCol())
                        t.setRow(t.getRow() - 1);
                }
                break;
            case 'r':
                for (HopscotchTile t : tiles) {
                    if (t.getCol() < blankTile.getCol() && t.getRow() == blankTile.getRow())
                        t.setCol(t.getCol() + 1);
                }
                break;
            case 'l':
                for (HopscotchTile t : tiles) {
                    if (t.getCol() < blankTile.getCol() && t.getRow() == blankTile.getRow())
                        t.setCol(t.getCol() + 1);
                }
                break;
            default:
                throw new IllegalArgumentException("This is not a valid direction");
        }

        repaint();
    }

    @Override
    //initializing the empty tiles
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D mydraw = (Graphics2D) g;

        mydraw.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        for (HopscotchTile t : tiles) {
            mydraw.setColor(DARK_BLUE);
            if (t.getNum().equals("0")) {
                blankTile = t;
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

}

