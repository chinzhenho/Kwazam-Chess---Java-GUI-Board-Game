// Purpose: This class records and updates the movement of chess pieces,
// representing a move made by a player, including the old and new positions.
// This Movement class is done by (GAN SHAO YANG)

public class Movement {
    GamePiece piece; // The piece that is being moved
    GamePiece kill; // The piece that is being captured 

    int previousColumn; // The column of the piece before the move
    int latestColumn; // The column of the piece after the move
    int previousRow;    // The row of the piece before the move
    int latestRow;    // The row of the piece after the move
    
    
    // Constructor to create a new movement (GAN SHAO YANG)
    public Movement(ChessBoard kwazamChessBoard, GamePiece piece, int newColumn, int newRow) {
        // Set the old position of the piece before the move
        this.previousRow = piece.rows; 
        this.previousColumn = piece.columns; 

         // Set the new position of the piece after the move
        this.latestRow = newRow; // Update to the new row
        this.latestColumn = newColumn; // Update to the new column
        

        // Store the piece that is being moved
        this.piece = piece;
         // Check if any piece is captured at the new position
        this.kill = kwazamChessBoard.getPiece(newColumn, newRow); // Get the piece at the new position 
    }
}
