

public class HopscotchTile {

    private int row;
    private int col;
    private String num;
    private int xpos;
    private int ypos;

    HopscotchTile(String num, int row, int col) {
        this.num = num;
        this.row = row;
        this.col = col;
        this.xpos = (HopscotchGUI.getTileSide() * row);
        this.ypos = (HopscotchGUI.getTileSide() * col);
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int Xpos) {
        xpos = Xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int Ypos) {
        ypos = Ypos;
    }

}
