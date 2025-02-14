// Purpose: This abstract class represents a generic piece on the chessboard.
// It contains common attributes and methods shared by all types of pieces.
// Design Pattern: Implements the Factory Method pattern as the superclass for all piece types in the game.

import java.awt.*;
// This GamePiece class is done by (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
public abstract class GamePiece {

    public int rows; // Row position of the piece on the chessboard
    public int columns; // Column position of the piece on the chessboard
    public int yPosition; // Y position of the piece on the screen
    public int xPosition; // X position of the piece on the screen
    public int value; // Value of the piece
    public boolean isRedPiece; // Boolean to check if the piece is red
    public String name; // Name of the piece
    
    Image pieceImage; // Image that represents the piece
    ChessBoard kwazamChessBoard; // Reference to the chessboard
    
    //Constructor for Piece with a reference to the chessboard
    public GamePiece(ChessBoard kwazamChessBoard)
    {
        this.kwazamChessBoard = kwazamChessBoard;
    }
    // Abstract method to check if a move would result in a collision with another piece
    public abstract boolean moveCollidesWithPiece(int columns, int rows) ; // Default boolean return type for collision check
    
    
    // Abstract method to check if a piece can move to a given position (column, row)
    public abstract boolean isValidMovement(int columns, int rows);  //Default boolean return type for validity
    

    // Method to draw the piece image on the board at the given x and y position
    public void paint(Graphics2D gTod)
    {
        gTod.drawImage(pieceImage, xPosition+15, yPosition+15, null); // Draw the piece at the correct position on the screen
    }

}
