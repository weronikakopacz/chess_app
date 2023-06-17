package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

	public Pawn(Color color, int row, int column) {
        super(color, row, column);
    }

    public List<Move> getPieceMoves(Square[][] square, Board board) {
        List<Move> legalMoves = new ArrayList<>();

        int direction = getColor() == Color.WHITE ? 1 : -1;

        int startRow = getRow();
        int startCol = getColumn();
        int endRow = startRow + direction;

        // Ruch o jedno pole
        if (isValidMove(square, endRow, startCol)) {
            legalMoves.add(new Move(startRow, startCol, endRow, startCol));

            // Ruch o dwa pola na początku
            if (!hasMoved() && isValidMove(square, endRow + direction, startCol)) {
                legalMoves.add(new Move(startRow, startCol, endRow + direction, startCol));
            }
        }

        // Zbijanie
        int[] captureCols = { startCol - 1, startCol + 1 };
        for (int captureCol : captureCols) {
            if (isValidMove(square, endRow, captureCol)) {
                Piece capturedPiece = square[endRow][captureCol].getPiece();
                if (capturedPiece != null && capturedPiece.getColor() != getColor()) {
                    legalMoves.add(new Move(startRow, startCol, endRow, captureCol));
                }
            }
        }

        // En passant
        Move lastMove = board.getLastMove();
        if (lastMove != null) {
            Square endSquare = board.getSquare(lastMove.getEndRow(), lastMove.getEndCol());
            Piece movedPiece = endSquare.getPiece();

            if (movedPiece instanceof Pawn && Math.abs(lastMove.getStartRow() - lastMove.getEndRow()) == 2) {
                int enPassantRow = getColor() == Color.WHITE ? 4 : 3;
                if (startRow == enPassantRow && isValidMove(square, endRow, lastMove.getEndCol())) {
                    legalMoves.add(new Move(startRow, startCol, endRow, lastMove.getEndCol()));
                }
            }
        }

        return legalMoves;
    }

    @Override
    public char getSymbol() {
        return 'P';
    }
}