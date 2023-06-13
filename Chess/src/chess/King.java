package chess;

import java.util.List;
import java.util.ArrayList;

public class King extends Piece {

	public King(Color color, int row, int column) {
		super(color, row, column);
	}

	@Override
	public List<Move> getLegalMoves(Square[][] square, Board board) {
	    List<Move> legalMoves = new ArrayList<>();

	    int currentRow = getRow();
	    int currentCol = getColumn();

	    int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0},  {1, 1}};

	    for (int[] direction : directions) {
	        int dRow = direction[0];
	        int dCol = direction[1];

	        int row = currentRow + dRow;
	        int col = currentCol + dCol;

	        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
	            Piece piece = square[row][col].getPiece();

	            if (piece == null || piece.getColor() != getColor()) {
	                legalMoves.add(new Move(currentRow, currentCol, row, col));
	            }
	        }
	    }
	    
	    // TODO:roszada
	    
	    return legalMoves;
	}

	@Override
	public char getSymbol() {
		return 'K';
	}
	
    @Override
    public void setRow(int row) {
        super.setRow(row);
        Board.getInstance().updateKingSquare(this);
    }

    @Override
    public void setColumn(int column) {
        super.setColumn(column);
        Board.getInstance().updateKingSquare(this);
    }
}
