//Purpose: This class is a subclass of the GamePiece class, 
//representing a specific type of game piece call Ram piece with its own movement and image.
//Design Pattern: Inheritance is utilized to create different types of pieces based 
//on the common GamePiece superclass.

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RamPiece extends GamePiece {

    // Constructor for the RamPiece class, initialize position, color, and
    // image.(CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public RamPiece(ChessBoard kwazamChessBoard, int column, int row, boolean isRedPiece) {
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

        this.name = "Ram"; // Assigns a name to this type of piece.
        updateImage(); // Set the image based on position and color
    }

    // Update the image based on the piece's color and the SauRam count (CHIN ZHEN
    // HO, BERNARD RYAN SIM KANG XUAN)
    public void updateImage() {
        String filename;

        // Check if the piece is not red
        if (!isRedPiece) {

            // Blue Ram logic
            if (kwazamChessBoard.getSauRamCount() == 0) {
                filename = "pieces/BlueinvertedRam.png"; // Blue Ram uses inverted image when count == 0
            } else {
                filename = "pieces/BlueRam.png"; // Normal image when count == 1
            }
        } else {
            // Red Ram logic
            if (kwazamChessBoard.getSauRamCount() == 1) {
                filename = "pieces/RedinvertedRam.png"; // Red Ram uses inverted image when count == 1
            } else {
                filename = "pieces/RedRam.png"; // Normal image when count == 0
            }
        }

        // Load the image file for the RamPiece
        try {
            pieceImage = ImageIO.read(new File(filename));
        } catch (IOException exception) {
            System.err.println("Error loading image: " + filename);
            pieceImage = null; // Set pieceImage to null as a fallback
        }
    }

    @Override
    // Checks if the Ram's move collides with any pieces or goes out of the board
    // (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public boolean moveCollidesWithPiece(int column, int row) {
        // Check if the target position is outside the board
        // kwazamChessBoard.cols and kwazamChessBoard.rows represent the number of
        // columns and rows on the board
        if (column >= kwazamChessBoard.cols || column < 0 || row >= kwazamChessBoard.rows || row < 0) {
            return true; // Collision, as the target position is outside the board
        }

        // Check for collision when moving upward
        // Compare the current row (this.rows) with the target row (row)
        // If the current row is greater than the target row, it means the piece is
        // moving upwards
        if (this.rows > row) {
            // Iterate through each row between the current position and the target position
            for (int r = this.rows - 1; r > row; r--) {
                // Check if there is another piece blocking the move
                // Use kwazamChessBoard.getPiece to check if there is a piece at the
                // column and row
                // For each row in the iteration, checks if there is another piece in
                // the same column (this.columns) at that row.
                if (kwazamChessBoard.getPiece(this.columns, r) != null) {
                    return true; // Collision, because another piece blocking the move
                }
            }
        }

        return false; // No collisions detected
    }

    @Override
    // Checks if the Ram's movement is valid (CHIN ZHEN HO, BERNARD RYAN SIM KANG
    // XUAN)
    public boolean isValidMovement(int column, int row) {
        // Ram can move one step upward in the same column

        // Calculate the difference in rows between the current position and the target
        // position
        // example:cuurent position is (3,5)
        // target position is (3,4)
        // example:
        // rowDifference = this.rows - row
        // rowDifference = 5 - 4
        // rowDifference = 1
        int rowDifference = this.rows - row;

        // Calculate the difference in columns between the current position and the
        // target position
        // example:
        // columnDifference = this.columns - column
        // columnDifference = 3 - 3
        // columnDifference = 0
        int columnDifference = this.columns - column;

        // checks if the move is one step upward in the same column.
        return ((rowDifference == 1) && (columnDifference == 0)); // returns true, when rowDifference = 1 and
                                                                  // columnDifference = 0,so is valid movement.
    }
}
