import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class runs and displays
 * the program and makes the menu
 * bar options etc.
 * 
 * @author Eileen Balci / DarkNinja Shark / Wolfcow INC.
 * @version 6.11.2007 Jun
 */
public class HangManDriver { 

		private static HangManGUI game = new HangManGUI();
		
		/**
		 * Runs the Hangman Game
		 */
		public static void main(String[] args) {
			
			JFrame window = new JFrame("HANG MAN");
			
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.getContentPane().add(game);
			makeMenuBar(window);//adds the menu bar
			
			//makes it so users can't resize window
			window.setResizable(false);
			
			window.pack();
			window.setVisible(true);
		}
		
		/**
	     * Create the main frame's menu bar.
	     * which will hold menu options such as
	     * Quit which will quit the program
	     * and
	     * New Game which will start a new game!
	     * 
	     * Taken from: 
	     * 	Objects First with Java a Pratical Intro Using BlueJ
	     *  Authors: Michael Kolling and David J Barnes 
	     *  Modified by: Eileen Balci
	     */
	    private static void makeMenuBar(JFrame frame)
	    {
	    	//used to make shortcut keys
	        final int SHORTCUT_MASK =
	            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

	        JMenuBar menubar = new JMenuBar();
	        frame.setJMenuBar(menubar);
	        
	        JMenu menu;
	        JMenuItem item;
	        
	        // create the Game menu
	        menu = new JMenu("Game Options");
	        menubar.add(menu);
	        
	        //will make a new game when user selects it
	        //or if user uses the shortcut ctrl+N
	        item = new JMenuItem("New Game");
	        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
	        	item.addActionListener(new ActionListener() {
	                           	   public void actionPerformed(ActionEvent e) 
	                               { 
	                        	      game.addBackComponents(); 
	                               }
	                           });
	        menu.add(item);//adds new game to menu
	        
	       
	        menu.addSeparator();//separates the new game and reset options from quit
	        
	        //will allow user to quit but selecting it from the menu
	        //or by useing the shortcut ctr+Q
	        item = new JMenuItem("Quit");
	            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
	            item.addActionListener(new ActionListener() {
	                               public void actionPerformed(ActionEvent e) 
	                               { 
	                            	   quit(); 
	                               }
	                           });
	        menu.add(item);
	         
	        // put a spacer into the menubar, so the next menu appears to the right
	        menubar.add(Box.createHorizontalGlue());

	        // create the Help menu
	        menu = new JMenu("Help");
	        menubar.add(menu);
	        
	        item = new JMenuItem("How To Play...");
	            item.addActionListener(new ActionListener() {
	                               public void actionPerformed(ActionEvent e) 
	                               { 
	                            	   showAbout(); 
	                               }
	                           });
	        menu.add(item);
	    }
	    
	    /**
	     * Quit function: quit the application.
	     */
	    private static void quit()
	    {
	        System.exit(0);
	    }
	    
	    /**
	     * An explaination of the
	     * Connect Four game and how to play.
	     */
	    private static void showAbout()
	    {
	        JOptionPane.showMessageDialog(null, 
	                    "HANG MAN\n" + 
	                    "A one player game that involves puzzle solving skills.\n\n" +
	                    "Try to guess the word by guessing what letters are\n" + 
	                    "in the word. If you guess a correct letter, it will show up" +
	                    " in the location it would be in the word\n" +
	                    "if you guess all the letters in the word\n" +
	                    "or if you think you know the word and you type it in the text field" +
	                    " and get it right YOU WIN!\nIf you guess an incorrect word or letter you will lose a chance\n" +
	                    "If you lose all your chances (and your hangman hangs) then you lose." +
	                    "\nIf you click on the \"Give Up\" button it will tell you what the word was and start a new game." +
	                    "\n\n\nGood luck and\nENJOY THE GAME!");
	        
	        
	    }
}
