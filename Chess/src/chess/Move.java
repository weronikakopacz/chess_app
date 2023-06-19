package chess;

public class Move implements Cloneable {
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;

    public Move(int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Move other = (Move) obj;
        return startRow == other.startRow && startCol == other.startCol && endRow == other.endRow && endCol == other.endCol;
    }

    @Override
    public String toString() {
        char startColChar = (char) (startCol + 'a');
        char endColChar = (char) (endCol + 'a');
        int startRowNum = startRow + 1;
        int endRowNum = endRow + 1;
        return String.format("%c%d-%c%d", startColChar, startRowNum, endColChar, endRowNum);
    }
    
    @Override
    public Move clone() {
        try {
            return (Move) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}