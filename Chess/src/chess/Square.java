package chess;

public class Square {
	private int column;
	private int row;
	private Piece piece;

	public Square(int column, int row) {
		this.column = column;
		this.row = row;
		this.piece = null;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
    
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
}