package chess;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class King extends Piece {

	public King(Color color, int row, int column) {
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

	        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
	            Piece piece = square[row][col].getPiece();
	            if (piece == null || piece.getColor() != getColor()) {
	                legalMoves.add(new Move(currentRow, currentCol, row, col));
	            }
	        }
	    }
	    
	    if (currentRow == 0 && currentCol == 4 && !hasMoved()) {
	        Piece rookShort = square[0][7].getPiece();
	        if (rookShort instanceof Rook && !rookShort.hasMoved() && rookShort.getColor() == getColor() && square[0][5].getPiece() == null && square[0][6].getPiece() == null) {
	        	Board copy = board.copyBoard();
	        	copy.makeMove(new Move(currentRow, currentCol, 0, 5), new Scanner(System.in), getColor());
	        	if (!copy.isCheck(getColor(), copy)) {
	        		legalMoves.add(new Move(currentRow, currentCol, 0, 6));
	        	}
	        }

	        Piece rookLong = square[0][0].getPiece();
	        if (rookLong instanceof Rook && !rookLong.hasMoved() && rookLong.getColor() == getColor() && square[0][3].getPiece() == null && square[0][2].getPiece() == null && square[0][1].getPiece() == null) {
	        	Board copy = board.copyBoard();
	        	copy.makeMove(new Move(currentRow, currentCol, 0, 3), new Scanner(System.in), getColor());
	        	if (!copy.isCheck(getColor(), copy)) {
	        		legalMoves.add(new Move(currentRow, currentCol, 0, 2));
	        	}
	        }
	    } else if (currentRow == 7 && currentCol == 4) {
	        Piece rookShort = square[7][7].getPiece();
	        if (rookShort instanceof Rook && !rookShort.hasMoved() && rookShort.getColor() == getColor() && square[7][5].getPiece() == null && square[7][6].getPiece() == null) {
	        	Board copy = board.copyBoard();
	        	copy.makeMove(new Move(currentRow, currentCol, 7, 5), new Scanner(System.in), getColor());
	        	if (!copy.isCheck(getColor(), copy)) {
	        		legalMoves.add(new Move(currentRow, currentCol, 7, 6));
	        	}
	        }

	        Piece rookLong = square[7][0].getPiece();
	        if (rookLong instanceof Rook && !rookLong.hasMoved() && rookLong.getColor() == getColor() && square[7][3].getPiece() == null && square[7][2].getPiece() == null && square[7][1].getPiece() == null) {
	        	Board copy = board.copyBoard();
	        	copy.makeMove(new Move(currentRow, currentCol, 7, 3), new Scanner(System.in), getColor());
	        	if (!copy.isCheck(getColor(), copy)) {
	        		legalMoves.add(new Move(currentRow, currentCol, 7, 2));
	        	}
	        }
	    }

	    
	    return legalMoves;
	}

	@Override
	public char getSymbol() {
		return 'K';
	}
}