package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

	public Pawn(Color color, int row, int column) {
        super(color, row, column);
    }

    @Override
    public List<Move> getPieceMoves(Square[][] square, Board board) {
        List<Move> legalMoves = new ArrayList<>();

        int direction = getColor() == Color.WHITE ? 1 : -1;
        int startRow = getColor() == Color.WHITE ? 1 : 6;

        int currentRow = getRow();
        int currentCol = getColumn();
        //ruch o jedno pole
        int nextRow = currentRow + direction;
        if (nextRow >= 0 && nextRow < 8 && square[nextRow][currentCol].getPiece() == null) {
            legalMoves.add(new Move(currentRow, currentCol, nextRow, currentCol));            
        }
        //ruch o dwa pola
        if (currentRow == startRow && square[nextRow][currentCol].getPiece() == null && square[nextRow + direction][currentCol].getPiece() == null) {
            legalMoves.add(new Move(currentRow, currentCol, nextRow + direction, currentCol));
        }
        //zbijanie
        int[] captureCols = { currentCol - 1, currentCol + 1 };
        for (int captureCol : captureCols) {
            if (nextRow >= 0 && nextRow < 8 && captureCol >= 0 && captureCol < 8) {
                Piece capturedPiece = square[nextRow][captureCol].getPiece();
                if (capturedPiece != null && capturedPiece.getColor() != getColor()) {
                    legalMoves.add(new Move(currentRow, currentCol, nextRow, captureCol));
                }
            }
        }
        //en passant
        Move lastMove = board.getLastMove();
        if (lastMove != null) {
            Square endSquare = board.getSquare(lastMove.getEndRow(), lastMove.getEndCol());
            Piece movedPiece = endSquare.getPiece();

            if (movedPiece instanceof Pawn && Math.abs(lastMove.getStartRow() - lastMove.getEndRow()) == 2) {
                int enPassantRow = getColor() == Color.WHITE ? 4 : 3;
                if (currentRow == enPassantRow) {
                    int enPassantCol = lastMove.getEndCol();
                    legalMoves.add(new Move(currentRow, currentCol, nextRow, enPassantCol));
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