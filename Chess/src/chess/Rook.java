package chess;

import java.util.List;

public class Rook extends Piece {

	public Rook(Color color, int row, int column) {
		super(color, row, column);
	}

	@Override
	public List<Move> getPieceMoves(Square[][] square, Board board) {
		int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	    return HorizontalOrVerticalMoves(directions, square);
	}

	@Override
	public char getSymbol() {
		return 'R';
	}
}