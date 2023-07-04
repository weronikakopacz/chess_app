package chess;
import java.util.ArrayList;
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

    protected int getRow() {
        return row;
    }

    protected int getColumn() {
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

    protected boolean hasMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    protected abstract List<Move> getPieceMoves(Square[][] square, Board board);

    protected abstract char getSymbol();
    
    protected boolean isValidMove(Square[][] square, int endRow, int endCol) {
        if (endRow >= 0 && endRow < 8 && endCol >= 0 && endCol < 8) {
            Piece piece = square[endRow][endCol].getPiece();
            return piece == null || piece.getColor() != getColor();
        }
        return false;
    }
    
    protected List<Move> HorizontalOrVerticalMoves(int[][] directions, Square[][] square) {
    	List<Move> legalMoves = new ArrayList<>();

	    int startRow = getRow();
	    int startCol = getColumn();

	    for (int[] direction : directions) {
	        int dRow = direction[0];
	        int dCol = direction[1];

	        int endRow = startRow + dRow;
	        int endCol = startCol + dCol;

	        while (isValidMove(square, endRow, endCol)) {
	            legalMoves.add(new Move(startRow, startCol, endRow, endCol));
	            
	            Piece piece = square[endRow][endCol].getPiece();
	            if (piece != null) {
	                break;
	            }
	            
	            endRow += dRow;
	            endCol += dCol;
	        }
	    }

	    return legalMoves;
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