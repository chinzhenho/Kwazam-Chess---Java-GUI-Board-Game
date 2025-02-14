//Purpose: This class is a subclass of the GamePiece class, 
//representing a specific type of game piece call Biz with its own movement and image.
//Design Pattern: Inheritance is utilized to create different types of pieces based 
//on the common GamePiece superclass.

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BizPiece extends GamePiece {
    // Constructor for the BizPiece class, initialize position, color, and
    // image.(CHIN ZHEN HO , BERNARD RYAN SIM KANG XUAN)
    public BizPiece(ChessBoard kwazamChessBoard, int column, int row, boolean isRedPiece) {
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
        // column 5:
        this.yPosition = rows * kwazamChessBoard.squareBoxSize; // this.yPosition = 8 * 70
        this.xPosition = columns * kwazamChessBoard.squareBoxSize; // this.xPosition = 5 * 70

        this.name = "Biz"; // Assigns a name to this type of piece.

        // Determine the filename of the image based on the color of the piece
        // If the piece is red, use "pieces/RedBiz.png"
        // If the piece is blue, use "pieces/BlueBiz.png"
        String filename = isRedPiece ? "pieces/RedBiz.png" : "pieces/BlueBiz.png";

        try {
            pieceImage = ImageIO.read(new File(filename)); // Loads the image file for the Biz piece using
                                                           // ImageIO.read().
        } catch (IOException exception) {
            exception.printStackTrace(); // Handles any issues that occur while reading the image file.
        }
    }

    @Override
    // Prevent go outside the board. (CHIN ZHEN HO , BERNARD RYAN SIM KANG XUAN)
    public boolean moveCollidesWithPiece(int columns, int rows) {
        // Check if the target position is outside the board boundaries
        if (columns > 4 || columns < 0 || rows > 7 || rows < 0) {// if column is within 0-4,and row is within 0-7,then
                                                                 // is inside the board,our board size is 5(column) x
                                                                 // 8(row)
            return true; // Movement is invalid due to being out of bounds.
        }
        return false; // Movement is within bounds.
    }

    @Override
    // Determines if the movement is valid for the Biz piece. (CHIN ZHEN HO ,
    // BERNARD RYAN SIM KANG XUAN)
    public boolean isValidMovement(int columns, int rows) {
        // The Biz piece can only move to positions where the product of the absolute
        // differences in columns and rows equals 2.

        // Calculate the absolute difference between the current and target columns
        int differentColumns = Math.abs(columns - this.columns);

        // Calculate the absolute difference between the current and target rows
        int differentRows = Math.abs(rows - this.rows);

        // Check if the movement is an "L" shape:
        // Biz Piece move is valid if the product of the differences is 2
        // (indicating one move of 2 squares in one direction and 1 square in the
        // other).

        // example of valid movement:
        // current position is (4,4)
        // want ot move to (6,5)
        // differentColumns = |6 - 4| = 2
        // differentRows = |5 - 4| = 1
        // check 2 * 1 == 2 ,so is valid move

        // example of wrong movemnt:
        // current position is (4,4)
        // want to move to (4,6)
        // differentColumns = |4 - 4| = 0
        // differentRows = |6 - 4| = 2
        // check 0 * 2 == 2 or not, is equal to 0,so is invalid move
        return (differentColumns) * (differentRows) == 2;
    }
}
