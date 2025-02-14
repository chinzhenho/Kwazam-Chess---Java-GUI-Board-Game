//Purpose: This class is a subclass of the Piece class, 
//representing a specific type of game piece call Tor with its own movement and image.
//Design Pattern: Inheritance is utilized to create different types of pieces based 
//on the common Piece superclass.

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TorPiece extends GamePiece {
    // Constructor of the Tor piece class, initialize position, color, and
    // image. (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public TorPiece(ChessBoard kwazamChessBoard, int column, int row, boolean isRedPiece) {
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

        this.name = "Tor"; // Assigns a name to this type of piece.

        // Determine the filename of the image based on the color of the piece
        // If the piece is red, use "pieces/RedTor.png"
        // If the piece is blue, use "pieces/BlueTor.png"
        String filename = isRedPiece ? "pieces/RedTor.png" : "pieces/BlueTor.png";

        try {
            pieceImage = ImageIO.read(new File(filename));// Loads the image file for the Biz piece using
                                                          // ImageIO.read().
        } catch (IOException exception) {
            exception.printStackTrace(); // Handles any issues that occur while reading the image file.
        }
    }

    @Override
    // Checks if the Tor's move collides with any pieces or goes out of the board
    // (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public boolean moveCollidesWithPiece(int column, int row) {
        // Check if the target position is outside the board boundaries
        if (column > 4 || column < 0 || row > 7 || row < 0) { /// if column is within 0-4,and row is within 0-7,then
                                                              // is inside the board,our board size is 5(column) x
                                                              // 8(row)
            return true; // Movement is invalid due to being out of bounds.
        }
        // Check for collision when moving right
        // If the target column is greater than the current column, the piece is moving
        // right
        if (this.columns < column) {
            // Iterate through each column between the current position and the target
            // position
            for (int col = this.columns + 1; col < column; col++) {
                // Check if there is another piece blocking the move
                if (kwazamChessBoard.getPiece(col, this.rows) != null) {
                    return true; // Collision occurs if there is another piece blocking the move
                }
            }
        }

        // Check for collision when moving downward
        // If the target row is greater than the current row, the piece is moving
        // downward
        if (this.rows < row) {
            // Iterate through each row between the current position and the target position
            for (int r = this.rows + 1; r < row; r++) {
                // Check if there is another piece blocking the move
                if (kwazamChessBoard.getPiece(this.columns, r) != null) {
                    return true; // Collision occurs if there is another piece blocking the move
                }
            }
        }

        // Check for collision when moving left
        // If the target column is less than the current column, the piece is moving
        // left
        if (this.columns > column) {
            // Iterate through each column between the current position and the target
            // position
            for (int col = this.columns - 1; col > column; col--) {
                // Check if there is another piece blocking the move
                if (kwazamChessBoard.getPiece(col, this.rows) != null) {
                    return true; // Collision occurs if there is another piece blocking the move
                }
            }
        }

        // Check for collision when moving upward
        // If the target row is less than the current row, the piece is moving upward
        if (this.rows > row) {
            // Iterate through each column between the current position and the target
            // position
            for (int r = this.rows - 1; r > row; r--) {
                // Check if there is another piece blocking the move
                if (kwazamChessBoard.getPiece(this.columns, r) != null) {
                    return true; // Collision occurs if there is another piece blocking the move
                }
            }
        }

        return false; // No collisions, movement is within bounds.
    }

    @Override
    // determine the movement is valid or not for Tor piece (CHIN ZHEN HO, BERNARD
    // RYAN SIM KANG XUAN)
    public boolean isValidMovement(int column, int row) {
        // Tor piece can only move along its current row or column

        // example:
        // this.columns == column is 2 == 2 ,so is true
        // this.rows == row is 3 == 6 ,so is false
        // in this conditions, returns true, indicating a valid movement.
        return this.columns == column || this.rows == row;
    }
}
