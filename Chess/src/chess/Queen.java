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

	    int currentRow = getRow();
	    int currentCol = getColumn();

	    int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0},  {1, 1}};

	    for (int[] direction : directions) {
	        int dRow = direction[0];
	        int dCol = direction[1];

	        int row = currentRow + dRow;
	        int col = currentCol + dCol;

	        while (row >= 0 && row < 8 && col >= 0 && col < 8) {
	            Piece piece = square[row][col].getPiece();

	            if (piece == null) {
	                legalMoves.add(new Move(currentRow, currentCol, row, col));
	            } else {
	                if (piece.getColor() != getColor()) {
	                    legalMoves.add(new Move(currentRow, currentCol, row, col));
	                }
	                break;
	            }

	            row += dRow;
	            col += dCol;
	        }
	    }

	    return legalMoves;
	}

	@Override
	public char getSymbol() {
		return 'Q';
	}
}