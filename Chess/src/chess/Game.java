package chess;

import java.util.Scanner;

public class Game {
    private Board board;
    private Color currentPlayer;

    public Game() {
        board = new Board();
        currentPlayer = Color.WHITE;
    }

    public void play() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(board.getSquareArray());
                System.out.println("Current player: " + currentPlayer);
                System.out.print("Enter your move (e.g., e2-e4): ");
                String moveString = scanner.nextLine();

                if (!isMoveValid(moveString)) {
                    continue;
                }

                String[] moveParts = moveString.split("-");
                String fromSquare = moveParts[0].trim();
                String toSquare = moveParts[1].trim();

                int fromCol = fromSquare.charAt(0) - 'a';
                int fromRow = fromSquare.charAt(1) - '1';
                int toCol = toSquare.charAt(0) - 'a';
                int toRow = toSquare.charAt(1) - '1';

                Move move = new Move(fromRow, fromCol, toRow, toCol);

                if (!board.isMoveLegal(move, currentPlayer)) {
                    System.out.println("Invalid move. Please try again.");
                    continue;
                }

                board.makeMove(move, scanner, currentPlayer);

                Color opponentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

                if (board.isCheckmate(opponentPlayer)) {
                    System.out.println("Checkmate! Player " + currentPlayer + " wins.");
                    break;
                } else if (board.isStalemate(opponentPlayer)) {
                    System.out.println("Stalemate! The game ends in a draw.");
                    break;
                }

                changePlayer();
            }
        }
    }
    
    private boolean isMoveValid(String moveString) {
        String[] moveParts = moveString.split("-");
        if (moveParts.length != 2) {
            System.out.println("Invalid move format. Please try again.");
            return false;
        }

        String fromSquare = moveParts[0].trim();
        String toSquare = moveParts[1].trim();

        if (fromSquare.length() != 2 || toSquare.length() != 2) {
            System.out.println("Invalid move format. Please try again.");
            return false;
        }

        return true;
    }
    
    private void changePlayer() {
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}