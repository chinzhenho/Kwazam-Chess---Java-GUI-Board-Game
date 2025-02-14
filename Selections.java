//Purpose: This class provides functionality for saving, loading, starting a new game and reset the game and setting the board size.

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

// This Selection Class is done bt (ERIC TEOH WEI XIANG, GAN SHAO YANG)
public class Selections extends JPanel {
    JFrame boardFrame; // about the game board frame
    public MainMenu menu; // about the main menu
    private int round; // Variable to track the current round of the game
    private int count; // Variable to store whose turn it is
    private ChessBoard kwazamChessBoard; // about the kwazam chess board 
    private int requestChessBoardSize; // Variable to store the requested board size
    private String[] chessBoardSizeChoice = { "Standard", "Mid-Sized", "Extra-Large" }; // Options for board size
    
    // Constructor for the options menu (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    public Selections(ChessBoard kwazamChessBoard, JFrame boardFrame, MainMenu menu) {
        this.kwazamChessBoard = kwazamChessBoard; // Initialize the chess board
        this.menu = menu; // Initialize the main menu
        this.boardFrame = boardFrame; // Initialize the board frame
        

        // Create buttons for saving, loading, starting a new game, and turn to menu
        JButton saveButton = new JButton("Save Game");
        JButton loadButton = new JButton("Load Game");
        JButton newgameButton = new JButton("New Game");
        JButton quitButton = new JButton("Menu");

        // Create a drop-down menu for selecting the board size
        JComboBox<String> dropdown = new JComboBox<>(chessBoardSizeChoice);

        dropdown.setSelectedIndex(0);  // Set the default selection to "Default"

        // Panel to hold the buttons and drop-down menu
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Add buttons and the drop-down menu to the panel
        add(buttonPanel.add(saveButton));
        add(buttonPanel.add(loadButton));
        add(buttonPanel.add(quitButton));
        add(buttonPanel.add(newgameButton));
        add(buttonPanel.add(dropdown));

        // Add an action listener to the load button to load the last saved game state
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // Call the loadGame method with the filename "Chess_Steps_Record.txt" to load the game state
                loadGame("Chess_Steps_Record.txt"); 
            }
        });
        
        // Add an action listener to the save button to save the current state of the game
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // Call the saveGame method with the filename "Chess_Steps_Record.txt" to save the game state
                saveGame("Chess_Steps_Record.txt");
            }
        });
        
        // Add an action listener to the new game button to restart the game and start a new game
        newgameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // Call the newGame method to reset the game state and start a new game
                newGame();
            }
        });

        // Add an action listener to the quit button to quit the game and return to the menu
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // Set the game state to not begun in the menu
                menu.setGameBegin(false);
                // Dispose of the game board frame to close the game window
                boardFrame.dispose();
            }
        });

        

        // Add an action listener to the drop-down menu to resize the chess board
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // Get the selected size option from the drop-down menu
                String selected = (String) dropdown.getSelectedItem(); 

                // Adjust the board size based on the selection
                if (selected.equals("Standard")) {
                    requestChessBoardSize = 70;
                    kwazamChessBoard.squareBoxSize = requestChessBoardSize;
                } else if (selected.equals("Mid-Sized")) {
                    requestChessBoardSize = 80;
                    kwazamChessBoard.squareBoxSize = requestChessBoardSize;
                } else if (selected.equals("Extra-Large")) {
                    requestChessBoardSize = 90;
                    kwazamChessBoard.squareBoxSize = requestChessBoardSize;
                }

                // Update the board with the new tile size and repaint
                kwazamChessBoard.updateTileSize(requestChessBoardSize);
                repaint();
            }
        });
    }

    // Save the game state to a file (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    private void saveGame(String fileDirectory) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileDirectory))) {
            // Write the game state to the file
            writer.println(kwazamChessBoard.getCount()); // Save whose turn it is (Count to check Red or Blue turn)
            writer.println(kwazamChessBoard.getRound()); // Save the current round (Round to change the position of Tor and Xor)
            writer.println(kwazamChessBoard.getSauRamCount()); // Save the SauRam count (Count to change the Image Sau and Ram)



    
            // Write each piece's information to the file
            for (GamePiece piece : kwazamChessBoard.pieceList) {
                // Write the piece's name, column, row, and color information to the file
                writer.println(piece.name + "," + piece.columns + "," + piece.rows + "," + piece.isRedPiece);
            }
    
            System.out.println("Game Saved to: " + fileDirectory); // Log the save operation to the console
        } catch (IOException exception) {
            // Handle IO exceptions by printing the stack trace
            exception.printStackTrace(); 
        }
    }
    

    // Start a new game by resetting the board (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    private void newGame() {

        kwazamChessBoard.pieceList.clear();// Clear the current list of pieces on the board
        kwazamChessBoard.addPieces();// Add the initial pieces to the board
        // Reset the move count and round number
        count = 1;
        round = 0; 
        kwazamChessBoard.resetGame(); // Reset the game state on the board
        // Update the displayed move count and round number
        kwazamChessBoard.updateCount(count);
        kwazamChessBoard.updateRound(round);
        kwazamChessBoard.repaint();// Repaint the board to reflect the new game state

    }

    // Load the saved game state from a file (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    private void loadGame(String fileDirectory) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileDirectory))) {
             // Clear the board state before loading
            kwazamChessBoard.pieceList.clear();
            kwazamChessBoard.newPieces.clear();
    
            // Read the saved game state from the file
            String line;
    
            // Load whose turn it is
            if ((line = reader.readLine()) != null) {
                kwazamChessBoard.updateCount(Integer.parseInt(line.trim())); // Update the count
            }
    
            // Load the current round
            if ((line = reader.readLine()) != null) {
                kwazamChessBoard.updateRound(Integer.parseInt(line.trim())); // Update the round
            }
    
            // Load the SauRam count 
            if ((line = reader.readLine()) != null) {
                kwazamChessBoard.setSauRamCount(Integer.parseInt(line.trim())); // Update the SauRam count
            }
    
            // Load each piece and add it to the board
            while ((line = reader.readLine()) != null) {
                String[] value = line.split(","); // Split the data into piece attributes
                String pieceName = value[0];
                int column = Integer.parseInt(value[1]);
                int row = Integer.parseInt(value[2]);
                boolean isRedPiece = Boolean.parseBoolean(value[3]);
    
                // Create and add the piece to the board
                switch (pieceName) {
                    case "Ram":
                        kwazamChessBoard.pieceList.add(new RamPiece(kwazamChessBoard, column, row, isRedPiece));
                        break;
                    case "Biz":
                        kwazamChessBoard.pieceList.add(new BizPiece(kwazamChessBoard, column, row, isRedPiece));
                        break;
                    case "Tor":
                        kwazamChessBoard.pieceList.add(new TorPiece(kwazamChessBoard, column, row, isRedPiece));
                        break;
                    case "Xor":
                        kwazamChessBoard.pieceList.add(new XorPiece(kwazamChessBoard, column, row, isRedPiece));
                        break;
                    case "Sau":
                        kwazamChessBoard.pieceList.add(new SauPiece(kwazamChessBoard, column, row, isRedPiece));
                        break;
                    case "InvertedRam":
                        kwazamChessBoard.pieceList.add(new InvertedRam(kwazamChessBoard, column, row, isRedPiece));
                        break;
                    
                    default:
                        System.err.println("Unknown piece type: " + pieceName); // Log unknown piece types
                        break;
                }
            }
    
            // Refresh the board and repaint
            kwazamChessBoard.repaint();
            System.out.println("Game Loaded from: " + fileDirectory); // Log the load operation
        } catch (IOException | NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Error loading game: " + exception.getMessage(),
                    "Load Game Error", JOptionPane.ERROR_MESSAGE); // Show error message if loading fails
            exception.printStackTrace(); // Handle exceptions
        }
    } 
}