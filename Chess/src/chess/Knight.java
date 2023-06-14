package chess;

import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(Color color, int row, int column) {
		super(color, row, column);
	}

	@Override
	public List<Move> getPieceMoves(Square[][] square, Board board) {
	    List<Move> legalMoves = new ArrayList<>();

	    int currentRow = getRow();
	    int currentCol = getColumn();

	    int[] moveRows = { -2, -1, 1, 2, 2, 1, -1, -2 };
	    int[] moveCols = { 1, 2, 2, 1, -1, -2, -2, -1 };

	    for (int i = 0; i < 8; i++) {
	        int newRow = currentRow + moveRows[i];
	        int newCol = currentCol + moveCols[i];

	        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
	            Piece piece = square[newRow][newCol].getPiece();
	            if (piece == null || piece.getColor() != getColor()) {
	                legalMoves.add(new Move(currentRow, currentCol, newRow, newCol));
	            }
	        }
	    }

	    return legalMoves;
	}

	@Override
	public char getSymbol() {
		return 'N';
	}
}
