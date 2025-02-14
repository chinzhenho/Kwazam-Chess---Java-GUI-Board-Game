//Purpose: This class contains the main methods responsible for initializing the game 
//by creating the JFrame and adding the board and menu components.

import javax.swing.*;
import java.awt.*;

public class Main {
     // Main class to start and initialize the application (ERIC TEOH WEI XIANG)
    public static void main(String[] args)
    {
        // Create a JFrame to hold the entire chess application
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(1000, 1000)); // Set a minimum size for the window
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setLayout(new GridBagLayout()); // Use GridBagLayout for organizing components in the frame
        
        // Add the chess board into the frame 
        ChessBoard kwazamChessBoard = new ChessBoard(); 
        
        // Create the main menu component, which interacts with the board and frame 
        MainMenu menu = new MainMenu(kwazamChessBoard, frame);

        frame.add(menu); 

        // Set the window to be visible, so users can interact with it
        frame.setVisible(true);
        // Set the close operation to exit the application when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
