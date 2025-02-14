//Purpose: This class manage the game logics and render the pieces. 

import javax.swing.*;
import java.awt.*;
import java.util.*;
//This ChessBoard is done by (ERIC TEOH WEI XIANG, CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN, GAN SHAO YANG)
public class ChessBoard extends JPanel {
    int cols = 5; // Number of columns of the kwazam chess board, which is set to 5.
    int rows = 8; // Number of rows of the kwazam chess board which is set to 8.
    int squareBoxSize = 70; // The size of each tiles (set to 70 pixels).   
    int round = 0;// This round is used to change the position of Tor and Xor
    int count = 1;// This number is to check Red or blue turn
    int sauRamCount = 1; // New count for Sau and Ram logic
    String colour;// check colour for the Sau
    int exchange = 4;// Counter for Xor and Tor transformations. 

    ArrayList<GamePiece> newPieces = new ArrayList<>();// create extra arraylist to store the game piece

    ArrayList<GamePiece> pieceList = new ArrayList<>(); // Create array list for piece for painting components

    public GamePiece selectedPiece; // The selected piece that the player is moving

    PlayerSelect input = new PlayerSelect(this); // Create a new PlayerSelect object to handle mouse events

    // Constructor for the kwazam chess board  (GAN SHAO YANG)
    public ChessBoard() {
        this.setPreferredSize(new Dimension(squareBoxSize * cols, squareBoxSize  * rows)); // setting the size of the board
        this.setBackground(Color.GRAY); // Set the background color of the board to gray
        this.addMouseListener(input); // Add mouse listener for handling user input
        this.addMouseMotionListener(input); // Add mouse motion listener for handling mouse drag events
        addPieces(); // Initialize the pieces on the board

    }

    //getter for current turn count (Red and Blue) (CHIN ZHEN HO)
    public int getCount() {
        return count;
    }

    // Getter for sauRamCount (Sau and Ram) ( BERNARD RYAN SIM KANG XUAN)
    public int getSauRamCount() {
        return sauRamCount;
    }

    //getter for current round (Tor and Xor) (CHIN ZHEN HO)
    public int getRound() {
        return round;
    }
    

    // Setter for sauRamCount (BERNARD RYAN SIM KANG XUAN)
    public void setSauRamCount(int sauRamCount) {
        this.sauRamCount = sauRamCount;
    }

    // Returns the current tile size (ERIC TEOH WEI XIANG)
    public int getTileSize(int squareBoxSize) {
        return squareBoxSize;

    }

    // Update the game round when loading a saved game. (CHIN ZHEN HO)
    public int updateRound(int updates) {
        round = updates;
        return round;
    }

    // Update the player's turn count when load the saved game. (CHIN ZHEN HO)
    public int updateCount(int update) {
        count = update;
        return count;

    }

    // Updates the tile size and adjusts the board layout accordingly. (ERIC TEOH WEI XIANG)
    public void updateTileSize(int size) {
        this.squareBoxSize = size; // Set the new size for each tile
        setPreferredSize(new Dimension(squareBoxSize * cols, squareBoxSize * rows)); // Update the board size based on the new tile size
        getTileSize(size); // calls the getTileSize method to update the tile size
        for (GamePiece piece : pieceList) {
            // Update the position of each piece based on the new tile size
            piece.yPosition = (squareBoxSize * piece.rows);
            piece.xPosition = (squareBoxSize * piece.columns);
        }

        revalidate(); // Revalidate the component to ensure the layout is updated
        repaint(); // Repaint the board to reflect the changes
        System.out.println(squareBoxSize); // Log the new tile size to the console for debugging
    }
    
    // Change the inverted Ram piece to normal Ram piece at the specific position (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public void changeInvertedRam(int col, int row) {
        for (GamePiece piece : pieceList) {
            if (piece.columns == col && piece.rows == row && piece.isRedPiece == true) {
                // Replace the inverted Ram piece with a normal Ram piece for the red player
                newPieces.add(new RamPiece(this, col, row, true));
            } 
            else if (piece.columns == col && piece.rows == row && piece.isRedPiece == false) {
                // Replace the inverted Ram piece with a normal Ram piece for the blue player
                newPieces.add(new RamPiece(this, col, row, false));
            } else {
                // Keep the piece unchanged if it doesn't match the specified position
                newPieces.add(piece);
            }

        }
        // Clear the original list and add the new pieces
        pieceList.clear();
        pieceList.addAll(newPieces);
        newPieces.clear();
        repaint(); // Repaint the board to reflect the changes
        selectedPiece = null; // Deselect the piece after the change
    }


    // Change normal Ram piece to inverted Ram piece at specific position (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public void changeRam(int col, int row) {
        for (GamePiece piece : pieceList) {
            if (piece.columns == col && piece.rows == row && piece.isRedPiece == true) {
                // Replace the normal Ram piece with an inverted Ram piece for the red player
                newPieces.add(new InvertedRam(this, col, row, true));
            } else if (piece.columns == col && piece.rows == row && piece.isRedPiece == false) {
                // Replace the normal Ram piece with an inverted Ram piece for the blue player
                newPieces.add(new InvertedRam(this, col, row, false));
            } else {
                // Keep the piece unchanged if it doesn't match the specified position
                newPieces.add(piece);
            }
        }
        // Clear the original list and add the new pieces
        pieceList.clear();
        pieceList.addAll(newPieces);
        newPieces.clear();
        repaint(); // Repaint the board to reflect the changes
        selectedPiece = null; // Deselect the piece after the change
    }

    

    // Get pieces from the initial position and move it to the next position (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    public GamePiece getPiece(int col, int row) {
        for (GamePiece piece : pieceList)
            if (piece.columns == col && piece.rows == row) {
                return piece; // Return the piece if it is found at the specified position
            }
        return null; // Return null if no piece is found at the specified position
    }

    // Move and update the board
    public void makeMove(Movement move) {
        // Update the piece's position to the new location
        move.piece.rows = move.latestRow;
        move.piece.columns = move.latestColumn;
    
        // Update the pixel position based on the new row and column
        move.piece.yPosition = move.latestRow * squareBoxSize;
        move.piece.xPosition = move.latestColumn * squareBoxSize;
    
        round++; // Increment the round count
        count++; // Increment the turn count
        sauRamCount = (sauRamCount == 0) ? 1 : 0; // Toggle sauRamCount between 0 and 1
    
        capture(move); // Check if a piece is captured during the move
        
        // Update the images of all Ram pieces
        for (GamePiece piece : pieceList) {
            if (piece instanceof RamPiece) {
                ((RamPiece) piece).updateImage();
            }
        }
        
        // Update the images of all Sau pieces
        for (GamePiece piece : pieceList) {
            if (piece instanceof SauPiece) {
                ((SauPiece) piece).updateImage();
            }
        }
        

        repaint();
    }
    
    
    
    
    // Check what colour of Sau piece has been kill (CHIN ZHEN HO)
    public void checkSau(Movement move) {
        if (move.kill.name == "Sau") { //first check if the piece killed is Sau
            if (move.kill.isRedPiece) { //check if the Sau piece is red
                colour = "blue"; // if red, then blue wins
            } else {
                colour = "Red"; // if blue, then red wins
            }
            message(colour); // display the 'WIN' message
        }
        selectedPiece = null; // Deselect any selected piece
    }

    // display pop up message when the game end 
    // can choose new game or exit game (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public void message(String colour) {
        String[] response = { "new game", "exit game" }; // Options for user
        ImageIcon icon = new ImageIcon("pieces/win.png"); // Icon for the message dialog
        int x = JOptionPane.showOptionDialog(null, "Congratulations,Team " + colour + " wins", "Winner!!!",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, response, 0); // Display the winning message based in which team color wins
        if (x == JOptionPane.NO_OPTION) { 
            System.exit(0); // Exit the game if the user chooses to do so
        } else {
            pieceList.clear(); // Clear the piece list
            newPieces.clear(); // Clear the new pieces list
            addPieces(); // Add the initial pieces to the board
            sauRamCount = 1; // Reset the SauRam count
            count = 1;  // Reset the turn count
            round = 0; // Reset the round count
            updateCount(count); // Update the turn count
            updateRound(round); // Update the round count
            repaint(); // Repaint the board to reflect the changes
        }
    }

     // Reset the game to its initial state. (BERNARD RYAN SIM KANG XUAN)
     public void resetGame() {
        // Clear existing pieces
        pieceList.clear();
        newPieces.clear();
    
        // Reset the turn and round counts as well as the SauRam count
        count = 1;
        round = 0;
        sauRamCount = 1;
    
        // Add initial pieces
        addPieces();
    
        // Refresh the UI
        repaint();
    }

    // Change the Tor and Xor piece after every 2 turns ( CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public void changePiece() {

        for (GamePiece piece : pieceList) {
            if (piece.name.equals("Xor") && piece.isRedPiece == false) {
                int row = piece.rows;
                int col = piece.columns;
                // Change the Xor piece to Tor piece for the blue player
                newPieces.add(new TorPiece(this, col, row, false));
            }

            if (piece.name.equals("Tor") && piece.isRedPiece == false) {
                int row = piece.rows;
                int col = piece.columns;
                // Change the Tor piece to Xor piece for the blue player
                newPieces.add(new XorPiece(this, col, row, false));
            }

            if (piece.name.equals("Xor") && piece.isRedPiece == true) {
                int row = piece.rows;
                int col = piece.columns;
                // Change the Xor piece to Tor piece for the red player
                newPieces.add(new TorPiece(this, col, row, true));
            }
            
            if (piece.name.equals("Tor") && piece.isRedPiece == true) {
                int row = piece.rows;
                int col = piece.columns;
                // Change the Tor piece to Xor piece for the red player
                newPieces.add(new XorPiece(this, col, row, true));
            }

            if (piece.name != "Xor" && piece.name != "Tor") {
                newPieces.add(piece); // Keep the piece unchanged if it is not Xor or Tor
            }
        }

        // Clear the original list and add the new pieces
        pieceList.clear();
        pieceList.addAll(newPieces);
        newPieces.clear();
        exchange = exchange + 4;
        selectedPiece = null;

    }

    // Change the position of piece every round (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public void changepositionPiece() {

        for (GamePiece piece : pieceList) {
            
            // Why need mirrored position?
            //  The mirrored position is used to change the position of the pieces for the blue player.
            // The mirrored position is calculated by subtracting the current position from the maximum position and negating the result.
            // The mirrored positions ensure that specific pieces (like Xor and Tor) follow unique movement rules, adding strategic complexity 
            //  and maintaining game balance.  
            if (piece.name.equals("Xor") && piece.isRedPiece == false) {
                // Calculate the mirrored row position
                // Subtract the current row position of the piece from 7 and negate the result
                // This mirrors the row position around the 7th row
                int row = -(piece.rows - 7);
                // Calculate the mirrored column position
                // Subtract the current column position of the piece from 4 and negate the result
                // This mirrors the column position around the 4th column
                int col = -(piece.columns - 4);
                // Change the position of the Xor piece for the blue player

                // Example :
                // Assume the piece is at position (5, 3)
                // The calculations will be as follows:
                // row = -(5 - 7) = -(-2) = 2
                // col = -(3 - 4) = -(-1) = 1
                // The new mirrored position will be (2, 1)
                newPieces.add(new XorPiece(this, col, row, false));
            }

            if (piece.name.equals("Tor") && piece.isRedPiece == false) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Tor piece for the blue player
                newPieces.add(new TorPiece(this, col, row, false));
            }

            if (piece.name.equals("Sau") && piece.isRedPiece == false) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Sau piece for the blue player
                newPieces.add(new SauPiece(this, col, row, false));
            }
            
            if (piece.name.equals("Ram") && piece.isRedPiece == false) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Ram piece for the blue player
                newPieces.add(new RamPiece(this, col, row, false));
            }
            
            if (piece.name.equals("InvertedRam") && piece.isRedPiece == false) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the InvertedRam piece for the blue player
                newPieces.add(new InvertedRam(this, col, row, false));
            }

            if (piece.name.equals("Biz") && piece.isRedPiece == false) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Biz piece for the blue player
                newPieces.add(new BizPiece(this, col, row, false));
            }
           

            if (piece.name.equals("Xor") && piece.isRedPiece == true) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Xor piece for the red player
                newPieces.add(new XorPiece(this, col, row, true));
            }
            
            if (piece.name.equals("Tor") && piece.isRedPiece == true) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Tor piece for the red player
                newPieces.add(new TorPiece(this, col, row, true));
            }
            if (piece.name.equals("Sau") && piece.isRedPiece == true) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Sau piece for the red player
                newPieces.add(new SauPiece(this, col, row, true));
            }
            
            if (piece.name.equals("Ram") && piece.isRedPiece == true) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Ram piece for the red player
                newPieces.add(new RamPiece(this, col, row, true));
            }
            
            if (piece.name.equals("InvertedRam") && piece.isRedPiece == true) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the InvertedRam piece for the red player
                newPieces.add(new InvertedRam(this, col, row, true));
            }
             
            if (piece.name.equals("Biz") && piece.isRedPiece == true) {
                int row = -(piece.rows - 7);
                int col = -(piece.columns - 4);
                // Change the position of the Biz piece for the red player
                newPieces.add(new BizPiece(this, col, row, true));
            }

        }

        // Clear the original list and add the new pieces
        pieceList.clear();
        pieceList.addAll(newPieces);
        newPieces.clear();
    }

    // This is to check and change the Ram piece to inverted Ram when it reach the top (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public void checkPosition() {

        for (GamePiece piece : pieceList) {
            if (piece.rows == 7 && piece.name == "InvertedRam") {
                // Change InvertedRam to Ram when it reaches the top row
                changeInvertedRam(piece.columns, piece.rows);

            }
            if (piece.rows == 0 && piece.name == "Ram") {
                // Change Ram to InvertedRam when it reaches the bottom row
                changeRam(piece.columns, piece.rows);

            } 
        }
        selectedPiece = null;

    }

    // kill when piece is defeated (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public void capture(Movement move) {
        pieceList.remove(move.kill); // Remove the killed piece from the board
        repaint(); // Repaint the board to reflect the changes

        // Check if it's time to change pieces based on the count and round
        if (count == 5 && round == 4) {
            // Change the pieces after every 5 turns and 4 rounds
            changePiece(); // Change the pieces
            count = 1; // Reset the turn count
            round = 0; // Reset the round count
        }

        changepositionPiece(); // Change the position of the pieces every round
        checkPosition(); // Check the position of the pieces
        checkSau(move); // Check if a Sau piece has been captured

        selectedPiece = null;

    }

    // validate movement (CHIN ZHEN HO, BERNARD RYAN SIM KANG XUAN)
    public boolean isValidMove(Movement move) {
        // Check if the move is invalid because the pieces are on the same team
        if (sameTeam(move.piece, move.kill)) {
            return false;
        }

        // Check if the move collides with another piece
        if (move.piece.moveCollidesWithPiece(move.latestColumn, move.latestRow)) {
            return false;
        }

        // Check if the move is invalid based on the piece's movement rules
        if (!move.piece.isValidMovement(move.latestColumn, move.latestRow)){
            return false;
        }
        
        return true; // The move is valid if it passes all the checks

    }

    // Check if the two pieces are in the same team. (CHIN ZHEN HO)
    public boolean sameTeam(GamePiece p1, GamePiece p2) {
        if (p1 == null || p2 == null) { // If either piece is null, return false because no comparison can be made.
            return false;
        }
        return p1.isRedPiece == p2.isRedPiece;// Return true if both pieces belong to the same team, otherwise return false.
    }

    // Add pieces into the kwazam chess board (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    public void addPieces() {
        // Add blue pieces to the board
        pieceList.add(new BizPiece(this, 1, 7, false));
        pieceList.add(new BizPiece(this, 3, 7, false));
        pieceList.add(new TorPiece(this, 4, 7, false));
        pieceList.add(new RamPiece(this, 0, 6, false));
        pieceList.add(new RamPiece(this, 1, 6, false));
        pieceList.add(new RamPiece(this, 2, 6, false));
        pieceList.add(new RamPiece(this, 3, 6, false));
        pieceList.add(new RamPiece(this, 4, 6, false));
        pieceList.add(new SauPiece(this, 2, 7, false));
        pieceList.add(new XorPiece(this, 0, 7, false));

        // Add red pieces to the board
        pieceList.add(new BizPiece(this, 1, 0, true));
        pieceList.add(new BizPiece(this, 3, 0, true));
        pieceList.add(new TorPiece(this, 0, 0, true));
        pieceList.add(new RamPiece(this, 0, 1, true));
        pieceList.add(new RamPiece(this, 1, 1, true));
        pieceList.add(new RamPiece(this, 2, 1, true));
        pieceList.add(new RamPiece(this, 3, 1, true));
        pieceList.add(new RamPiece(this, 4, 1, true));
        pieceList.add(new SauPiece(this, 2, 0, true));
        pieceList.add(new XorPiece(this, 4, 0, true));

    }

    // Painting method (ERIC TEOH WEI XIANG)
    public void paintComponent(Graphics g) {
        // Paint tiles of the board

        // Cast Graphics to Graphics2D for more advanced control over geometry, coordinate transformations, color management, and text layout
        Graphics2D gTod = (Graphics2D) g; // Cast graphicsTod to g

        // Paint the dark blue tiles of the board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                gTod.setColor(new Color(0, 0, 139)); // Dark blue color
                gTod.fillRect(squareBoxSize * c, squareBoxSize * r, squareBoxSize, squareBoxSize);
            }
        }

        // Paint the white tiles of the board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                gTod.setColor(Color.WHITE); // Set color to white
                gTod.fillRect(squareBoxSize * c + 3, squareBoxSize * r + 3, squareBoxSize - 5, squareBoxSize - 5);
            }
        }
        
        // If a piece is selected, show the possible moves
        if (selectedPiece != null) {
            // Shows the piece highlights for where the piece can move
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    // Check if the move to the current tile is valid
                    if (isValidMove(new Movement(this, selectedPiece, c, r))) {
                        gTod.setColor(new Color(68, 180, 57, 190)); // semi-transparent Green color
                        gTod.fillRect(squareBoxSize * c, squareBoxSize * r, squareBoxSize, squareBoxSize); // Highlight the valid move
                    }
                }
            }
        }
        // Paint all the pieces on the board
        for (GamePiece piece : pieceList) {
            piece.paint(gTod);// Call the paint method of each piece to draw it on the board
        }
    }

}
