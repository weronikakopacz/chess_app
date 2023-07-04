package chess;

import java.util.List;

public class Queen extends Piece {

	public Queen(Color color, int row, int column) {
		super(color, row, column);
	}

	@Override
	public List<Move> getPieceMoves(Square[][] square, Board board) {
		int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0},  {1, 1}};

	    return HorizontalOrVerticalMoves(directions, square);
	}

	@Override
	public char getSymbol() {
		return 'Q';
	}
}