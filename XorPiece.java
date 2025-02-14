//Purpose: This class is a subclass of the Piece class, 
//representing a specific type of game piece call Xor with its own movement and image.
//Design Pattern: Inheritance is utilized to create different types of pieces based 
//on the common Piece superclass.

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class XorPiece extends GamePiece {
    // Constructor for the Xor piece class, initialize position, color, and
    // image.(CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public XorPiece(ChessBoard kwazamChessBoard, int column, int row, boolean isRedPiece) {
        super(kwazamChessBoard); // Calls the constructor of the superclass

        // Initialize the row and column position of the piece on the chessboard
        this.rows = row;
        this.columns = column;

        // Set the color of the piece (true for red, false for blue)
        this.isRedPiece = isRedPiece;

        // calculate and set the xPosition and yPosition of the piece
        // based on the squares box size on the chessboard and the piece's row and
        // column.
        // For example, if the square box size is 70, and the piece is at row 8 and
        // column 5.
        this.yPosition = row * kwazamChessBoard.squareBoxSize; // this.yPosition = 8 * 70
        this.xPosition = column * kwazamChessBoard.squareBoxSize; // this.xPosition = 5 * 70

        this.name = "Xor"; // Assigns a name to this type of piece.

        // Determine the filename of the image based on the color of the piece
        // If the piece is red, use "pieces/RedXor.png"
        // If the piece is blue, use "pieces/BlueXor.png"
        String filename = isRedPiece ? "pieces/RedXor.png" : "pieces/BlueXor.png";

        try {
            pieceImage = ImageIO.read(new File(filename));// Loads the image file for the Biz piece using
                                                          // ImageIO.read().
        } catch (IOException exception) {
            exception.printStackTrace(); // Handles any issues that occur while reading the image file.
        }
    }

    // Checks if the Xor's move collides with any pieces or goes out of the board
    // (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    @Override
    public boolean moveCollidesWithPiece(int column, int row) {
        // Check if the target position is outside the bounds of the board
        if (column > 4 || column < 0 || row > 7 || row < 0) {/// if column is within 0-4,and row is within 0-7,then
                                                             // is inside the board,our board size is 5(column) x
                                                             // 8(row)
            return true; // Movement is invalid due to being out of bounds.
        }
        // Check for collision when moving up and right
        // If the target column is greater than the current column and the target row is
        // less than the current row
        if (this.columns < column && this.rows > row) {
            // Iterate through each diagonal position between the current position and the
            // target position
            for (int i = 1; i < Math.abs(this.columns - column); i++) {
                // Check if there is another piece blocking the move
                if (kwazamChessBoard.getPiece(this.columns + i, this.rows - i) != null) {
                    return true; // Collision occurs if there is another piece blocking the move
                }
            }
        }

        // Check for collision when moving down and right
        // If the target column is greater than the current column and the target row is
        // greater than the current row
        if (this.columns < column && this.rows < row) {
            // Iterate through each diagonal position between the current position and the
            // target position
            for (int i = 1; i < Math.abs(this.columns - column); i++) {
                // Check if there is another piece blocking the move
                if (kwazamChessBoard.getPiece(this.columns + i, this.rows + i) != null) {
                    return true;// Collision occurs if there is another piece blocking the move
                }
            }
        }

        // Check for collision when moving up and left
        // If the target column is less than the current column and the target row is
        // less than the current row
        if (this.columns > column && this.rows > row) {
            // Iterate through each diagonal position between the current position and the
            // target position
            for (int i = 1; i < Math.abs(this.columns - column); i++) {
                // Check if there is another piece blocking the move
                if (kwazamChessBoard.getPiece(this.columns - i, this.rows - i) != null) {
                    return true; // Collision occurs if there is another piece blocking the move
                }
            }
        }

        // Check for collision when moving down and left
        // If the target column is less than the current column and the target row is
        // greater than the current row
        if (this.columns > column && this.rows < row) {
            // Iterate through each diagonal position between the current position and the
            // target position
            for (int i = 1; i < Math.abs(this.columns - column); i++) {
                // Check if there is another piece blocking the move
                if (kwazamChessBoard.getPiece(this.columns - i, this.rows + i) != null) {
                    return true;// Collision occurs if there is another piece blocking the move
                }
            }
        }
        // No collision detected, return false
        return false;
    }

    @Override
    // Valid movement for the Xor piece (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public boolean isValidMovement(int column, int row) {
        // The piece can only move diagonally

        // Calculate the absolute difference in columns between the current position and
        // the target position
        // determine how many columns the piece is moving
        // Example:
        // current position is (2, 3), target position is (4, 5)
        // differentColumns = Math.abs(this.columns - column)
        // differentColumns = Math.abs(2 - 4)
        // differentColumns = 2
        int differentColumns = Math.abs(this.columns - column);

        // Calculate the absolute difference in rows between the current position and
        // the target position
        // determine how many rows the piece is moving
        // Example:
        // current position is (2, 3), target position is (4, 5)
        // differentRows = Math.abs(this.rows - row)
        // differentRows = Math.abs(3 - 5)
        // differentRows = 2
        int differentRows = Math.abs(this.rows - row);

        // Check if the move is diagonal
        // can only move diagonally, which means the column difference should
        // be equal to the row difference
        // Return true if both differences are equal, indicating a valid diagonal
        // movement
        return (differentColumns == differentRows);
    }
}
