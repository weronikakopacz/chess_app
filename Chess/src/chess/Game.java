package chess;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private Board board;
    private Color currentPlayer;
    private boolean vsBot;
    private Color botColor;
    private boolean botMoved;

    public Game(boolean vsBot) {
        board = new Board();
        currentPlayer = Color.WHITE;
        this.vsBot = vsBot;
        botColor = Color.BLACK;
        botMoved = false;
    }

    public void play() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(board.getSquareArray());
                System.out.println("Current player: " + currentPlayer);

                if (vsBot && currentPlayer == botColor && !botMoved) {
                    System.out.println("Bot's turn.");
                    Move randomMove = getRandomMove(board.legalMoves(currentPlayer));
                    System.out.println("Bot moves: " + randomMove);
                    board.makeMove(randomMove, scanner, currentPlayer);
                    botMoved = true;
                } else {
                    System.out.print("Enter your move (e.g., e2-e4): ");
                    String moveString = scanner.nextLine();

                    if (shouldBotMove(moveString)) {
                        botColor = currentPlayer;
                        System.out.println("Bot's turn.");
                        Move randomMove = getRandomMove(board.legalMoves(currentPlayer));
                        System.out.println("Bot moves: " + randomMove);
                        board.makeMove(randomMove, scanner, currentPlayer);
                        botMoved = true;
                    } else if (!isMoveValid(moveString)) {
                        continue;
                    } else {
                        Move move = parseMove(moveString);
                        if (!board.isMoveLegal(move, currentPlayer)) {
                            System.out.println("Invalid move. Please try again.");
                            continue;
                        }
                        board.makeMove(move, scanner, currentPlayer);
                    }
                }

                Color opponentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

                if (board.isCheckmate(opponentPlayer)) {
                    System.out.println("Checkmate! Player " + currentPlayer + " wins.");
                    break;
                } else if (board.isStalemate(opponentPlayer)) {
                    System.out.println("Stalemate! The game ends in a draw.");
                    break;
                }

                currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
                botMoved = false;
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

    private Move parseMove(String moveString) {
        String[] moveParts = moveString.split("-");
        String fromSquare = moveParts[0].trim();
        String toSquare = moveParts[1].trim();
        int fromCol = fromSquare.charAt(0) - 'a';
        int fromRow = fromSquare.charAt(1) - '1';
        int toCol = toSquare.charAt(0) - 'a';
        int toRow = toSquare.charAt(1) - '1';
        return new Move(fromRow, fromCol, toRow, toCol);
    }

    private boolean shouldBotMove(String moveString) {
        return currentPlayer == Color.WHITE && vsBot && moveString.isEmpty() && !botMoved;
    }

    private Move getRandomMove(List<Move> moves) {
        Random random = new Random();
        int randomIndex = random.nextInt(moves.size());
        return moves.get(randomIndex);
    }

    public static void main(String[] args) {
        System.out.print("Choose game mode (1 for 1v1, 2 for 1vBot): ");
        try (Scanner scanner = new Scanner(System.in)) {
            int gameMode = scanner.nextInt();
            boolean vsBot = (gameMode == 2);
            Game game = new Game(vsBot);
            game.play();
        }
    }
}
