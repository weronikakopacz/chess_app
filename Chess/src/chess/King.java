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

	    int startRow = getRow();
        int startCol = getColumn();

        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] direction : directions) {
            int dRow = direction[0];
            int dCol = direction[1];

            int endRow = startRow + dRow;
            int endCol = startCol + dCol;

            if (isValidMove(square, endRow, endCol)) {
                legalMoves.add(new Move(startRow, startCol, endRow, endCol));
            }
        }
	    
        if (startRow == 0 && startCol == 4 && !hasMoved()) {
            checkCastling(square, board, legalMoves, 0, 7, 0, 5, 0, 6);
            checkCastling(square, board, legalMoves, 0, 0, 0, 3, 0, 2);
        } else if (startRow == 7 && startCol == 4) {
            checkCastling(square, board, legalMoves, 7, 7, 7, 5, 7, 6);
            checkCastling(square, board, legalMoves, 7, 0, 7, 3, 7, 2);
        }

	    
	    return legalMoves;
	}

	@Override
	public char getSymbol() {
		return 'K';
	}
	
	private void checkCastling(Square[][] squares, Board board, List<Move> legalMoves, int rookRow, int rookCol, int moveRow, int moveCol, int endRow, int endCol) {
	    Piece rook = squares[rookRow][rookCol].getPiece();
	    boolean isShortCastling = endCol > moveCol;

	    if (rook instanceof Rook && !rook.hasMoved() && rook.getColor() == getColor() && squares[moveRow][moveCol].getPiece() == null) {
	        int startCol = isShortCastling ? moveCol + 1 : moveCol - 1;
	        int maxCol = isShortCastling ? endCol - 1 : endCol + 1;

	        for (int col = startCol; col <= maxCol; col++) {
	            if (squares[moveRow][col].getPiece() != null) {
	                return;
	            }
	        }

	        Board copy = board.copyBoard();
	        copy.makeMove(new Move(getRow(), getColumn(), moveRow, moveCol), new Scanner(System.in), getColor());

	        if (!copy.isCheck(getColor(), copy)) {
	            legalMoves.add(new Move(getRow(), getColumn(), endRow, endCol));
	        }
	    }
	}

}