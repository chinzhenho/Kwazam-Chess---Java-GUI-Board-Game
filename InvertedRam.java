//Purpose: This class is a subclass of the Game Piece class, 
//representing a specific type of game piece call Inverted Ram with its own movement and image.
//Design Pattern: Inheritance is utilized to create different types of pieces based 
//on the common Piece superclass.

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class InvertedRam extends GamePiece {
    // Constructor for the InvertedRam class, initialize position, color, and image.
    // (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public InvertedRam(ChessBoard kwazamChessBoard, int columns, int rows, boolean isRedPiece) {
        super(kwazamChessBoard); // Calls the constructor of the superclass GamePiece

        // Initialize the row and column position of the piece on the chessboard
        this.rows = rows;
        this.columns = columns;

        // Set the color of the piece (true for red, false for blue)
        this.isRedPiece = isRedPiece;

        // calculate and set the xPosition and yPosition of the piece
        // based on the squares box size on the chessboard and the piece's row and
        // column.
        // For example, if the square box size is 70, and the piece is at row 8 and
        // column 5:
        this.yPosition = rows * kwazamChessBoard.squareBoxSize; // this.yPosition = 8 * 70
        this.xPosition = columns * kwazamChessBoard.squareBoxSize; // this.xPosition = 5 * 70

        this.name = "InvertedRam"; // Assigns a name to this type of piece.

        // Determine the filename of the image based on the color of the piece
        // If the piece is red, use "pieces/RedinvertedRam.png"
        // If the piece is blue, use "pieces/BlueinvertedRam.png"
        String filename = isRedPiece ? "pieces/RedinvertedRam.png" : "pieces/BlueinvertedRam.png";

        try {
            pieceImage = ImageIO.read(new File(filename)); // Loads the image file for the Inverted Ram piece using
                                                           // ImageIO.read().
        } catch (IOException exception) {
            exception.printStackTrace(); // If an IOException occurs, it prints the stack trace.
        }
    }

    // prevent go outside the board. (CHIN ZHEN HO, BERNARD
    // RYAN SIM KANG XUAN)
    @Override
    public boolean moveCollidesWithPiece(int column, int row) {
        // Check if the target position is outside the board boundaries
        if (column > 4 || column < 0 || row > 7 || row < 0) { // if column is within 0-4,and row is within 0-7,then is
                                                              // inside the board,our board size is 5(column) x 8(row)
            return true; // Collision, as the target position is outside the board
        }
        // go down
        if (this.rows < row) { // checks if the piece is moving downwards by comparing the current row
                               // (this.rows) with the target row (row).
                               // If the current row is less than the target row, it means the piece is moving
                               // downwards.

            for (int r = this.rows + 1; r < row; r++) { // If the piece is moving downwards, the method iterates through
                                                        // each row between the current position and the target
                                                        // position.
                if (kwazamChessBoard.getPiece(this.columns, r) != null) { // it uses the kwazamChessBoard.getPiece
                                                                          // method to checks if there is another piece
                                                                          // in the same column (this.columns) at that
                                                                          // row.
                    return true; // returns true,if there is another piece blocking the move
                }
            }
        }
        return false; // if without finding any blocking pieces, it returns false, indicating no
                      // collisions.
    }

    @Override
    // Determines if the movement is valid for the Inverted Ram piece. (CHIN ZHEN
    // HO, BERNARD RYAN SIM KANG XUAN)
    public boolean isValidMovement(int column, int row) {

        // Calculate the difference in rows between the current position and the target
        // position
        int rowDifference = this.rows - row; // example:
                                             // rowDifference = this.rows - row
                                             // rowDifference = 5 - 6
                                             // rowDifference = -1

        // Calculate the difference in columns between the current position and the
        // target position
        int columnDifference = this.columns - column;// example:
                                                     // columnDifference = this.columns - column
                                                     // columnDifference = 3 - 3
                                                     // columnDifference = 0

        // Check if the move is one step downward in the same column
        // The InvertedRam can only move one step downward, which means the row
        // difference should be -1
        // and the column difference should be 0 (no horizontal movement)
        return ((rowDifference == -1) && (columnDifference == 0));
        // example of wrong movement:
        // InvertedRam piece is at column 3, row 5.
        // We want to move the piece to column 3, row 4.
        // rowDifference = this.rows - row
        // rowDifference = 5 - 4
        // rowDifference = 1
        // columnDifference = this.columns - column
        // columnDifference = 3 - 3
        // columnDifference = 0

        // rowDifference == -1 is false (since rowDifference
        // is 1)
        // columnDifference == 0 is true
        // The overall condition ((rowDifference == -1) &&
        // (columnDifference == 0)) is
        // false
        // returns false, indicating that moving is not a
        // valid movement.

        // example of correct movement
        // InvertedRam piece is at column 3, row 5.
        // We want to move the piece to column 3, row 6.
        // rowDifference == -1 is true (since rowDifference is -1)
        // columnDifference == 0 is true
        // returns true, indicating that moving is a valid
        // movement.

    }
}