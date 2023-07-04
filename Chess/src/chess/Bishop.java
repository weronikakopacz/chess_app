package chess;

import java.util.List;

public class Bishop extends Piece {

	public Bishop(Color color, int row, int column) {
		super(color, row, column);
	}
	
	@Override
	public List<Move> getPieceMoves(Square[][] square, Board board) {
		int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
	            
	    return HorizontalOrVerticalMoves(directions, square);
	}

	@Override
	public char getSymbol() {
		return 'B';
	}
}