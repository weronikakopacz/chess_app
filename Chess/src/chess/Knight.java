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

	    int startRow = getRow();
	    int startCol = getColumn();

	    int[] moveRows = { -2, -1, 1, 2, 2, 1, -1, -2 };
	    int[] moveCols = { 1, 2, 2, 1, -1, -2, -2, -1 };

	    for (int i = 0; i < 8; i++) {
	        int endRow = startRow + moveRows[i];
	        int endCol = startCol + moveCols[i];

	        if (isValidMove(square, endRow, endCol)) {
	            legalMoves.add(new Move(startRow, startCol, endRow, endCol));
	        }
	    }

	    return legalMoves;
	}

	@Override
	public char getSymbol() {
		return 'N';
	}
}