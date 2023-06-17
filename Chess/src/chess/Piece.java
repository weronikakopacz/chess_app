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
    
    public boolean isValidMove(Square[][] square, int endRow, int endCol) {
        if (endRow >= 0 && endRow < 8 && endCol >= 0 && endCol < 8) {
            Piece piece = square[endRow][endCol].getPiece();
            return piece == null || piece.getColor() != getColor();
        }
        return false;
    }
    
    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}