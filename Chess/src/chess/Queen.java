package chess;

import java.util.List;
import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(Color color, int row, int column) {
		super(color, row, column);
	}

	@Override
	public List<Move> getPieceMoves(Square[][] square, Board board) {
	    List<Move> legalMoves = new ArrayList<>();

	    int startRow = getRow();
	    int startCol = getColumn();

	    int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0},  {1, 1}};

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
	public char getSymbol() {
		return 'Q';
	}
}