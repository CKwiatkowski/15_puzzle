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

    private static final Color DARK_BLUE = new Color(9, 90, 166);
    private static final int tileSide = 70;
    private static final int frameWidth = 281;
    private static final int frameHeight = 309;
    private static final int frameTitleHeight = 28;

    private int oldCol;
    private int oldRow;
    private char direction;

    private static int getFrameTitleHeight() {
        return frameTitleHeight;
    }

    public static int getTileSide() {
        return tileSide;
    }

    private ArrayList<HopscotchTile> tiles = initializeTiles();

    private HopscotchTile blankTile = tiles.get(15);

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

            oldCol = e.getX() / (tileSide);
            oldRow = (e.getY() - getFrameTitleHeight()) / (tileSide);


            // confirm validity of the move
            if (e.getX() < 0 || e.getX() > frameHeight || e.getY() < 0 || e.getY() > frameHeight) {
                throw new IndexOutOfBoundsException("Clicked outside the game's frame");
            }

            if (blankTile.getRow() == oldRow) {
                if (blankTile.getCol() < oldCol) {
                    direction = 'l';
                } else if (blankTile.getCol() > oldCol) {
                    direction = 'r';
                }
            } else if (blankTile.getCol() == oldCol) {
                if (blankTile.getRow() < oldRow) {
                    direction = 'u';
                } else if (blankTile.getRow() > oldRow) {
                    direction = 'd';
                }
            } else if (oldCol < 0 || oldCol > 3 || oldRow < 0 || oldRow > 3) {
                throw new IndexOutOfBoundsException("Invalid tile placement:");
            } else
                return;
            moveTiles(direction);
        }
    }

    @Override
    // decided to leave this one empty for now
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


    //change tiles in list, then move them on screen
    private void moveTiles(char direction) {
        HopscotchTile tile;
        switch (direction) {
            case 'd':
                for (int i = tiles.size() - 1; i >= 0; --i) {
                    //temporary variable
                    tile = tiles.get(i);
                    //update blank Tile if necessary
                    blankCheck(tile);
                    if (tile.getRow() >= oldRow && tile.getRow() < blankTile.getRow() && tile.getCol() == blankTile.getCol()) {
                        int tempRow = tile.getRow();
                        tile.setRow(blankTile.getRow());
                        blankTile.setRow(tempRow);
                        Collections.swap(tiles, i, tiles.indexOf(blankTile));
                    }
                }
                break;
            case 'u':
                for (int i = tiles.size() - 1; i >= 0; --i) {
                    tile = tiles.get(i);
                    blankCheck(tile);
                    if (tile.getRow() <= oldRow && tile.getRow() > blankTile.getRow() && tile.getCol() == blankTile.getCol()) {
                        int tempRow = tile.getRow();
                        tile.setRow(blankTile.getRow());
                        blankTile.setRow(tempRow);
                        Collections.swap(tiles, i, tiles.indexOf(blankTile));
                    }
                }
                break;
            case 'l':
                for (int i = tiles.size() - 1; i >= 0; --i) {
                    tile = tiles.get(i);
                    blankCheck(tile);
                    if (tile.getCol() <= oldCol && tile.getCol() > blankTile.getCol() && tile.getRow() == blankTile.getRow()) {
                        int tempCol = tile.getCol();
                        tile.setCol(blankTile.getCol());
                        blankTile.setCol(tempCol);
                        Collections.swap(tiles, i, tiles.indexOf(blankTile));
                    }
                }

                break;
            case 'r':
                for (int i = tiles.size() - 1; i >= 0; --i) {
                    //temporary variable
                    tile = tiles.get(i);
                    //update blank Tile if necessary
                    blankCheck(tile);
                    if (tile.getCol() >= oldCol && tile.getCol() < blankTile.getCol() && tile.getRow() == blankTile.getRow()) {
                        int tempCol = tile.getCol();
                        tile.setCol(blankTile.getCol());
                        blankTile.setCol(tempCol);
                        Collections.swap(tiles, i, tiles.indexOf(blankTile));
                    }
                }

                break;
            default:
                throw new IllegalArgumentException("This is not a valid direction");
        }
        repaint();
    }


    private void blankCheck(HopscotchTile t) {
        if (t.getNum().equals("0")) {
            blankTile = t;
            blankTile.setRow(t.getRow());
        }
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
            System.out.printf("Tile %s in row %d with pos %d/%d\n", t.getNum(), t.getRow(), t.getXpos(), t.getYpos());
            if (t.getNum().equals("0")) {
                //blankTile = t;
                continue;
            }
            mydraw.fillRect(t.getXpos(), t.getYpos(), tileSide, tileSide);
            mydraw.setColor(WHITE);
            mydraw.drawRect(t.getXpos(), t.getYpos(), tileSide, tileSide);
            mydraw.setFont(new Font("Arial", Font.BOLD, 35));

            //credits for the following part of the code go to Gilbert LeBlanc on Stackoverflow
            // https://stackoverflow.com/questions/14284754/java-center-text-in-rectangle/14284949
            FontMetrics fm = mydraw.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(t.getNum(), mydraw);
            int x = t.getXpos() + (tileSide - (int) r.getWidth()) / 2;
            int y = t.getYpos() + (tileSide - (int) r.getHeight()) / 2 + fm.getAscent();
            g.drawString(t.getNum(), x, y);
        }
    }

}

