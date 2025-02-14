//Purpose: This class displays the main menu with options for starting and quit the game 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//This MainMenu class is done by (ERIC TEOH WEI XIANG, GAN SHAO YANG)
public class MainMenu extends JPanel {

    public ChessBoard kwazamChessBoard;
    private boolean gameBegin = false; //variable to track the game start already or not

    //main menu (ERIC TEOH WEI XIANG, GAN SHAO YANG)
    public MainMenu(ChessBoard kwazamChessBoard, JFrame frame) {
        this.kwazamChessBoard = kwazamChessBoard;

        JPanel panel = new JPanel(); // Create panel to hold components for the main menu
        panel.setLayout(new BorderLayout()); // Use BorderLayout to organize components within the panel
        ImageIcon backgroundImage = new ImageIcon("pieces/Title.png"); // background image for menu


        JLabel backgroundLabel = new JLabel(backgroundImage); // Create a label to display the background image
        backgroundLabel.setLayout(null); // Set the layout manager for the background label to null

        panel.add(backgroundLabel, BorderLayout.NORTH); //add background image on top of panel
        frame.add(panel); // Add the panel to the frame
        JPanel buttonPanel = new JPanel(); // Create a panel for buttons
        JButton startGame = new JButton("Start Game"); // Create "Start Game" button


        //start game function
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {// (ERIC TEOH WEI XIANG, GAN SHAO YANG)

                if (!gameBegin) { // Check if the game havent started
                    gameBegin = true; // Set the game as started

                    // Create JFrame for the chess board
                    JFrame boardFrame = new JFrame();
                    boardFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to full-screen mode
                    boardFrame.setResizable(false); // Prevent resizing
                    boardFrame.setLocationRelativeTo(null); // Center the window on the screen
                    boardFrame.setLayout(new GridBagLayout()); // Use GridBagLayout for organizing components

                    // initialize the chess board into the frame
                    ChessBoard kwazamChessBoard = new ChessBoard();
                    GridBagConstraints boardConstraints = new GridBagConstraints(); // Create constraints for the chess board
                    boardConstraints.gridy = 0; // Set the grid y position to 0 (top row)
                    boardConstraints.gridx = 0; // Set the grid x position to 0 (leftmost column)
                    boardFrame.add(kwazamChessBoard, boardConstraints);
                    // Create the options panel for the chess board

                    // Add the Options panel below the chess board in the frame
                    Selections save = new Selections(kwazamChessBoard, boardFrame,MainMenu.this);
                    GridBagConstraints selectionsConstraints = new GridBagConstraints(); // Create constraints for the options panel
                    selectionsConstraints.gridy = 1; // Set the grid y position to 1 (second row)
                    selectionsConstraints.gridx = 0; // Set the grid x position to 0 (leftmost column)
                    boardFrame.add(save, selectionsConstraints); // Add the options panel to the frame

                    boardFrame.setVisible(true);   // Make the board frame visible

                    boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set the default close operation for the board window

                }

            }

        });
        // Create a "Quit" button
        JButton exitGame = new JButton(" Quit");
        //exit game function
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                System.exit(0); // Exit the application when click the "Quit" button
            }

        });

        // Add the buttons to the button panel
        buttonPanel.add(startGame); //add start game button
        buttonPanel.add(exitGame); // add exit game button
        panel.add(buttonPanel, BorderLayout.SOUTH); //add the button panel to bottom of the main menu panel

        // Add the main panel to the frame
        frame.add(panel);

    }
    //check whether the game has started or not (GAN SHAO YANG)
    public  void setGameBegin(boolean gameBegin){
        this.gameBegin=gameBegin; // Update the game state
    }

}
