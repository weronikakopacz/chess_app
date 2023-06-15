package chess;
import java.util.List;

public abstract class Piece implements Cloneable {
    private Color color;
    private int row;
    private int column;
    private boolean moved;

    public Piece(Color color, int row, int column) {
        this.color = color;
        this.row = row;
        this.column = column;
        moved = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
    
    public void setRow(int row) {
		this.row = row;
	}
    
    public void setColumn(int column) {
		this.column = column;
	}

    public Color getColor() {
        return color;
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public abstract List<Move> getPieceMoves(Square[][] square, Board board);

    public abstract char getSymbol();
    
    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}