
//Purpose: This class handles mouse events such as dragging and selecting pieces on the game board.
//Design Pattern: Observer pattern is used as this class observes the Board class to receive updates 
//and changes for mouse events, and actions are taken accordingly.
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// This PlayerSelect class is done by (ERIC TEOH WEI XIANG, GAN SHAO YANG)
public class PlayerSelect extends MouseAdapter {

    ChessBoard kwazamChessBoard;

    // Constructor to initialize PlayerInput with a reference to the  chess board (ERIC TEOH WEI XIANG)
    public PlayerSelect(ChessBoard kwazamChessBoard) {
        this.kwazamChessBoard = kwazamChessBoard;
    }

    // When mouse is dragged, the piece would be dragged also (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    @Override
    public void mouseDragged(MouseEvent event) {
        if (kwazamChessBoard.selectedPiece != null) { // Check if a piece is selected
            // Update the yPosition of the selected piece
            // Set the yPosition to the Y coordinate of the mouse event, adjusted by half the size of a square
            kwazamChessBoard.selectedPiece.yPosition = event.getY() - kwazamChessBoard.squareBoxSize / 2;

            // Update the xPosition of the selected piece
            // Set the xPosition to the X coordinate of the mouse event, adjusted by half the size of a square
            kwazamChessBoard.selectedPiece.xPosition = event.getX() - kwazamChessBoard.squareBoxSize / 2;

            // Example:
            // yPosition = 150 - 50 / 2 = 125
            // xPosition = 200 - 50 / 2 = 175
            // The selected piece's top-left corner will be at (175, 125), centering it under the cursor
            
            kwazamChessBoard.repaint(); // Repaint the board to the new location
        }
    }

    // Handles the selection of a piece when the mouse is pressed  (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    @Override
    public void mousePressed(MouseEvent event) {
        int row = event.getY() / kwazamChessBoard.squareBoxSize; 
        // Calculate row based on mouse Y coordinate
        int col = event.getX() / kwazamChessBoard.squareBoxSize;
        // Calculate column based on mouse X coordinate

        // Example :
        // row = 150 / 50 = 3
        // col = 200 / 50 = 4
        // This means the mouse event occurred in the square at row 3, column 4 on the chessboard

        GamePiece pieceXY = kwazamChessBoard.getPiece(col, row);  // Get the piece at the selected position
        // Check if the player is allowed to select this piece based on their turn
        if (kwazamChessBoard.getCount() % 2 != 0) {
            if ((pieceXY.isRedPiece == false) && (pieceXY != null)) { //blue's turn
                kwazamChessBoard.selectedPiece = pieceXY; // Select the piece
            }

        } else {
            if ((pieceXY.isRedPiece == true) && (pieceXY != null)) {  // red's turn
                kwazamChessBoard.selectedPiece = pieceXY; // Select the piece
            }

        }
    }

    // Handles the release of the mouse, updating the piece to that position (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    @Override
    public void mouseReleased(MouseEvent event) {
        int row = event.getY() / kwazamChessBoard.squareBoxSize;
        // Calculate row based on mouse Y coordinate
        int col = event.getX() / kwazamChessBoard.squareBoxSize;
        // Calculate column based on mouse X coordinate

        if (kwazamChessBoard.selectedPiece != null) {
            Movement move = new Movement(kwazamChessBoard, kwazamChessBoard.selectedPiece, col, row); // Create a new move object

            if (kwazamChessBoard.isValidMove(move)) { //check the move is valid or not

                kwazamChessBoard.makeMove(move); //is valid,then move
            } else {
                //the move is invalid, put the piece to its original position
                kwazamChessBoard.selectedPiece.yPosition = kwazamChessBoard.selectedPiece.rows * kwazamChessBoard.squareBoxSize;
                kwazamChessBoard.selectedPiece.xPosition = kwazamChessBoard.selectedPiece.columns * kwazamChessBoard.squareBoxSize;
            }
        }
        kwazamChessBoard.selectedPiece = null; //unselect the piece
        kwazamChessBoard.repaint();   // Repaint the board for updated state
    }

}
