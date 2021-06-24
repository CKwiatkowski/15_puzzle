

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
        this.xpos = (HopscotchGUI.getTileSide() * col);
        this.ypos = (HopscotchGUI.getTileSide() * row);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
        this.setYpos(row * HopscotchGUI.getTileSide());
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
        this.setXpos(col * HopscotchGUI.getTileSide());
    }


    public String getNum() {
        return num;
    }

    /*public void setNum(String num) {
        this.num = num;
    }*/

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
