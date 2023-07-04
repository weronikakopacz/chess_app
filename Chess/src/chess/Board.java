package chess;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Board {
	private Square[][] squares;
	private Move lastMove;

	public Board() {
		squares = new Square[8][8];
		initializeBoard();
		initializePieces();
	}
    
    public Square getSquare(int row, int col) {
        return squares[row][col];
    }

	private void initializeBoard() {
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				squares[row][column] = new Square(column, row);
			}
		}
	}

	private void initializePieces() {
	    squares[0][0].setPiece(new Rook(Color.WHITE, 0, 0));
	    squares[0][1].setPiece(new Knight(Color.WHITE, 0, 1));
	    squares[0][2].setPiece(new Bishop(Color.WHITE, 0, 2));
	    squares[0][3].setPiece(new Queen(Color.WHITE, 0, 3));
	    squares[0][4].setPiece(new King(Color.WHITE, 0, 4));
	    squares[0][5].setPiece(new Bishop(Color.WHITE, 0, 5));
	    squares[0][6].setPiece(new Knight(Color.WHITE, 0, 6));
	    squares[0][7].setPiece(new Rook(Color.WHITE, 0, 7));
	    for (int i = 0; i < 8; i++) {
	        squares[1][i].setPiece(new Pawn(Color.WHITE, 1, i));
	    }

	    squares[7][0].setPiece(new Rook(Color.BLACK, 7, 0));
	    squares[7][1].setPiece(new Knight(Color.BLACK, 7, 1));
	    squares[7][2].setPiece(new Bishop(Color.BLACK, 7, 2));
	    squares[7][3].setPiece(new Queen(Color.BLACK, 7, 3));
	    squares[7][4].setPiece(new King(Color.BLACK, 7, 4));
	    squares[7][5].setPiece(new Bishop(Color.BLACK, 7, 5));
	    squares[7][6].setPiece(new Knight(Color.BLACK, 7, 6));
	    squares[7][7].setPiece(new Rook(Color.BLACK, 7, 7));
	    for (int i = 0; i < 8; i++) {
	        squares[6][i].setPiece(new Pawn(Color.BLACK, 6, i));
	    }
	}

	public String getSquareArray() {
	    StringBuilder boardString = new StringBuilder();

	    for (int row = 7; row >= 0; row--) {
	        for (int column = 0; column < 8; column++) {
	            Square square = squares[row][column];
	            char pieceChar = square.getPiece() != null ? square.getPiece().getSymbol() : 'X';
	            String colorCode;

	            if (pieceChar == 'X') {
	                colorCode = "\033[38;5;245m";
	            } else {
	                colorCode = square.getPiece().getColor() == Color.BLACK ? "\033[44m" : "";
	            }

	            String resetCode = "\u001B[0m";
	            boardString.append(colorCode).append(pieceChar).append(resetCode);
	        }
	        boardString.append('\n');
	    }

	    return boardString.toString();
	}
	
    public boolean isMoveLegal(Move move, Color playerColor) {  
        List<Move> legalMoves = legalMoves(playerColor);
        return legalMoves.contains(move);
    }
    
    public void makeMove(Move move, Scanner scanner, Color currentPlayer) {
        int startRow = move.getStartRow();
        int startCol = move.getStartCol();
        int endRow = move.getEndRow();
        int endCol = move.getEndCol();

        Square startSquare = squares[startRow][startCol];
        Square endSquare = squares[endRow][endCol];

        Piece piece = startSquare.getPiece();
        piece.setMoved(true);

        if (piece instanceof Pawn && startCol != endCol && endSquare.getPiece() == null) {
            int capturedCol = endCol;
            int capturedRow = startRow;
            squares[capturedRow][capturedCol].setPiece(null);
        } else if (piece instanceof King && Math.abs(endCol - startCol) == 2 && !isCheck(currentPlayer, this)) {
            int rookFromCol, rookToCol;
            if (endCol > startCol) {
                rookFromCol = 7;
                rookToCol = endCol - 1;
            } else {
                rookFromCol = 0;
                rookToCol = endCol + 1;
            }
            Square rookFromSquare = squares[startRow][rookFromCol];
            Square rookToSquare = squares[startRow][rookToCol];
            Piece rook = rookFromSquare.getPiece();
            rook.setMoved(true);
            rookToSquare.setPiece(rook);
            rookFromSquare.setPiece(null);
            rook.setRow(endRow);
            rook.setColumn(rookToCol);
        }

        endSquare.setPiece(piece);
        startSquare.setPiece(null);

        piece.setRow(endRow);
        piece.setColumn(endCol);

        if (piece instanceof Pawn && (endRow == 0 || endRow == 7)) {
        	Piece promotedPiece = new Queen(currentPlayer, endRow, endCol);
            endSquare.setPiece(promotedPiece);
        }
        
        lastMove = move;
    }

	public Move getLastMove() {
        return lastMove;
    }
    
	public boolean isCheck(Color kingColor, Board board) {
	    Square kingSquare = findKingSquare(kingColor);
	    if (kingSquare == null) {
	        return false;
	    }

	    Color opponentColor = (kingColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
	    List<Move> opponentMoves = collectAllPossibleMoves(opponentColor, board);

	    for (Move move : opponentMoves) {
	        if (move.getEndRow() == kingSquare.getRow() && move.getEndCol() == kingSquare.getColumn()) {
	            return true;
	        }
	    }

	    return false;
	}

    public boolean isCheckmate(Color kingColor) {
        if (!isCheck(kingColor, this)) {
            return false;
        }
        Color playerColor = (kingColor == Color.WHITE) ? Color.WHITE : Color.BLACK;
        List<Move> opponentMoves = legalMoves(playerColor);
        
        return opponentMoves.isEmpty();
    }

    public boolean isStalemate(Color playerColor) {
        if (isCheck(playerColor, this)) {
            return false;
        }
        
        List<Move> possibleMoves = legalMoves(playerColor);
        return possibleMoves.isEmpty();
    }
 
    private List<Move> collectAllPossibleMoves(Color playerColor, Board board) {
        List<Move> possibleMoves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = squares[row][col];
                Piece piece = square.getPiece();
                if (piece != null && piece.getColor() == playerColor) {
                    List<Move> legalMoves = piece.getPieceMoves(squares, board);
                    possibleMoves.addAll(legalMoves);
                }
            }
        }

        return possibleMoves;
    }
    
    public List<Move> legalMoves(Color playerColor) {
        List<Move> legalMoves = new ArrayList<>();
        List<Move> possibleMoves = collectAllPossibleMoves(playerColor, this);

        for (Move move : possibleMoves) {
            Board copy = copyBoard();

            copy.makeMove(move, new Scanner(System.in), playerColor);

            if (!copy.isCheck(playerColor, copy)) {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    public Board copyBoard() {
        Board copy = new Board();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col].getPiece();
                if (piece != null) {
                    Piece pieceCopy = (Piece) piece.clone();
                    copy.getSquare(row, col).setPiece(pieceCopy);
                } else {
                    copy.getSquare(row, col).setPiece(null);
                }
            }
        }

        if (lastMove != null) {
            copy.lastMove = (Move) lastMove.clone();
        }

        return copy;
    }
    
    private Square findKingSquare(Color kingColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = squares[row][col];
                Piece piece = square.getPiece();
                if (piece instanceof King && piece.getColor() == kingColor) {
                    return square;
                }
            }
        }
        return null;
    }
}