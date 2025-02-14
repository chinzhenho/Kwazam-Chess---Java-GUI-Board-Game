//Purpose: This class is a subclass of the GamePiece class, 
//representing a specific type of game piece call Sau piece with its own movement and image.
//Design Pattern: Inheritance is utilized to create different types of pieces based 
//on the common GamePiece superclass.

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SauPiece extends GamePiece {
    // Constructor for the Sau piece class, initialize position, color, and
    // image. (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public SauPiece(ChessBoard kwazamChessBoard, int column, int row, boolean isRedPiece) {
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

        this.name = "Sau"; // Assigns a name to this type of piece.
        updateImage(); // Set the image based on position and color
    }

    // Update the image based on the piece's color and the SauRam count (CHIN ZHEN
    // HO, BERNARD RYAN SIM KANG XUAN)
    public void updateImage() {
        String filename;

        // Check if the piece is not red
        if (!isRedPiece) {
            // Blue Sau logic
            if (kwazamChessBoard.getSauRamCount() == 0) {
                filename = "pieces/BlueinvertedSau.png"; // Blue Sau use inverted image when count == 0
            } else {
                filename = "pieces/BlueSau.png"; // Normal image when count == 1
            }
        } else {
            // Red Sau logic
            if (kwazamChessBoard.getSauRamCount() == 1) {
                filename = "pieces/RedinvertedSau.png"; // Red Sau use inverted image when count == 1
            } else {
                filename = "pieces/RedSau.png"; // Normal image when count == 0
            }
        }

        // Load the image file for the SauPiece
        try {
            pieceImage = ImageIO.read(new File(filename));
        } catch (IOException exception) {
            System.err.println("Error loading image: " + filename);
            pieceImage = null; // Set pieceImage to null as a fallback
        }
    }

    @Override
    // Checks if the Sau's move collides with any pieces or goes out of the board
    // (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public boolean moveCollidesWithPiece(int column, int row) {
        // Check if the target position is outside the board boundaries
        // kwazamChessBoard.cols and kwazamChessBoard.rows represent the number of
        // columns and rows on the board
        if (column >= kwazamChessBoard.cols || column < 0 || row >= kwazamChessBoard.rows || row < 0) {
            return true; // Prevent move outside the board
        }

        // if the target position is occupied by another piece
        GamePiece targetPiece = kwazamChessBoard.getPiece(column, row);
        if (targetPiece != null) {
            // Allow movement only if the target piece is on the opposite team
            // if the target piece is on the same team.
            // example:
            // targetPiece.isRedPiece == this.isRedPiece , true == true ,so is true
            return targetPiece.isRedPiece == this.isRedPiece; // Collision occurs if the piece is on the same team
        }

        // No collision
        return false;
    }

    @Override
    // Checks if the Sau's movement is valid (CHIN ZHEN HO, BERNARD RYAN SIM KANG
    // XUAN)
    public boolean isValidMovement(int column, int row) {
        // Sau can move one square in any direction

        // Calculate the absolute difference in columns between the current position and
        // the target position
        // determines how many columns the piece is moving
        // Example: current position is (3, 5), target position is (4, 5)
        // columnDifference = Math.abs(this.columns - column)
        // columnDifference = Math.abs(3 - 4)
        // columnDifference = 1
        int columnDifference = Math.abs(this.columns - column);

        // Calculate the absolute difference in rows between the current position and
        // the target position
        // determines how many rows the piece is moving
        // Example: current position is (3, 5), target position is (3, 6)
        // rowDifference = Math.abs(this.rows - row)
        // rowDifference = Math.abs(5 - 6)
        // rowDifference = 1
        int rowDifference = Math.abs(this.rows - row);

        // Check if the move is one square in any direction
        // The column difference should be less than or equal to 1
        // The row difference should be less than or equal to 1
        // Return true if both conditions are true, indicating a valid movement
        return (columnDifference <= 1) && (rowDifference <= 1);
    }
}
