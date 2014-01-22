import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.*;

/**
 * This Class contains all the stuff needed to 
 * make the hangman game work (all the components etc)
 * 
 * @author Eileen Balci
 * @version 6.11.2007 v1.0
 */
public class HangManGUI extends JPanel { 
		//all variables are static because this is the only
		//class that will use them and they are all part of this
		//class!
		
		/*------- GUI Variables: ------*/
		//PANELS:
		  //gameboard
		private static JPanel gameBoard;
		  //scoreboards
		private static JPanel scoreBoard;
		private static JPanel buttonsBoard;
		 	
		//LABELS:
		  //displays nothing
		private static JLabel blank;
		  //displays how many chances to guess they have left before losing
		private static JLabel chancesLeft;
		  //displays a message next to the guess button
		private static JTextField message;
		  //copyright
		private static JLabel copyRight;
		
		  //gameboard images 
		  //BASE of hangman stage
		private static final ImageIcon BASE1 = 
			ImagesLocator.getImage("images/leftBot.jpg");
		private static final ImageIcon BASE2 = 
			ImagesLocator.getImage("images/botA.jpg");
		private static final ImageIcon BASE3 = 
			ImagesLocator.getImage("images/botB.jpg");
		private static final ImageIcon BASE4 = 
			ImagesLocator.getImage("images/baseTrunk.jpg");
		private static final ImageIcon BASE5 =
			ImagesLocator.getImage("images/rightBot.jpg");
		  //TRUNK of hangman stage
		private static ImageIcon TRUNK1 =
			ImagesLocator.getImage("images/trunk.jpg");
		private static final ImageIcon TRUNK2 = 
			ImagesLocator.getImage("images/trunkb.jpg");
		private static final ImageIcon TRUNK3 = 
			ImagesLocator.getImage("images/topCorner.jpg");
		  //TOP of hangman stage
		private static final ImageIcon TOP1 = 
			ImagesLocator.getImage("images/botB.jpg");
		private static final ImageIcon TOP2 = 
			ImagesLocator.getImage("images/botA.jpg");
		private static final ImageIcon TOP3 = 
			ImagesLocator.getImage("images/topWithRope.jpg");
		  //SUPPORT part of hangman stage
		private static final ImageIcon SUP1 = 
			ImagesLocator.getImage("images/supportMain.jpg");
		private static final ImageIcon SUP2 = 
			ImagesLocator.getImage("images/supportFiller2.jpg");
		private static final ImageIcon NOOSE =
			ImagesLocator.getImage("images/noose.jpg");
		  //HANGMAN parts 
		private static final ImageIcon ROPE = 
			ImagesLocator.getImage("images/Rope1.jpg");
		private static final ImageIcon HEAD = 
			ImagesLocator.getImage("images/head.jpg");
		private static final ImageIcon BODY = 
			ImagesLocator.getImage("images/body.jpg");
		private static final ImageIcon ARM1 = 
			ImagesLocator.getImage("images/body1Arm.jpg");
		private static final ImageIcon ARM2 = 
			ImagesLocator.getImage("images/body2Arms.jpg");
		private static final ImageIcon LEG1 = 
			ImagesLocator.getImage("images/body1Leg.jpg");
		private static final ImageIcon LEG2 = 
			ImagesLocator.getImage("images/body2Legs.jpg");
		  //Hangman decoration: the word hangman
		private static final ImageIcon H =
			ImagesLocator.getImage("images/h.jpg");
		private static final ImageIcon A =
			ImagesLocator.getImage("images/a.jpg");
		private static final ImageIcon N =
			ImagesLocator.getImage("images/n.jpg");
		private static final ImageIcon M =
			ImagesLocator.getImage("images/m.jpg");
		private static final ImageIcon G =
			ImagesLocator.getImage("images/g.jpg");
		private static final ImageIcon A2 =
			ImagesLocator.getImage("images/a2.jpg");
		private static final ImageIcon N2 =
			ImagesLocator.getImage("images/n2.jpg");
		
		//BUTTONS:
		  //letter buttons
		private static JButton lA, lB, lC, lD, lE, lF, lG, lH, lI, lJ, lK, lL, 
			lM, lN, lO, lP, lQ, lR, lS, lT, lU, lV, lW, lX, lY, lZ, guess, newWord, giveUp;
		

		/*-------- Logic Variables: --------*/
		
		
		//Final Variables
		private static final int NUM_ROWS = 12;
		private static final int NUM_COLS = 12;
		
		//Number of players
		private static int numPlayers = 0;
			
		//Counters
		  //counts how many chances are left
		private static int chances = 6;
		 
		//Strings
		  //determines what word the user wants his/her opponent to guess
		private static String wordChoice = "";	
		
		//Booleans
		  //if the user wins this boolean variable will be true
		private static boolean didWin = false;
		  //if this boolean is true it means that the user made a word guess
		private static boolean guessingWholeWord = false;

		//ARRAYS
		//creates a game array that holds JLabels which will fill 
		//the gameboard with the INIT ImageIcon at initilization
		private static JLabel[][] gameArray = new JLabel[NUM_ROWS][NUM_COLS]; 
		
		//creates an array of words for the game to randomly choose
		private static String[] wordArray = new String[70];
		
		//RANDOM
		private static Random randWord = new Random();
		
		/**
		 * constructs components, 
		 * assigns variables and adds them
		 * to their panels
		 * 
		 * sets up all the inital stuff
		 * 
		 */
		public HangManGUI()
		{
			numPlayers = numPlayers();
			
			//if it is a one player game
			//the CPU must randomly pick a word
			//(that is really the only diffrence)
			if(numPlayers == 1){
				addWords();
				getWord();
			}
			else if(numPlayers == 2){
				multiPlalyer();
			}
			
			//set up the game board (which is the same for one player and two player games
			
			setLayout(new BorderLayout());
			
			
			//JPanels
			gameBoard = new JPanel();
			scoreBoard = new JPanel();
			buttonsBoard = new JPanel();

			
			//set layouts for all panels
			gameBoard.setLayout(new GridLayout(NUM_ROWS, NUM_COLS));
			scoreBoard.setLayout(new GridLayout(NUM_ROWS, 1));
			buttonsBoard.setLayout(new GridLayout(4, 7));
			
			//set colors for panels 
			//scoreBoard.setBackground(Color.GRAY);
			gameBoard.setBackground(Color.white);

			gameBoard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(2, 
					Color.LIGHT_GRAY, Color.GRAY), 
					BorderFactory.createLineBorder(Color.DARK_GRAY, 5)));
			
			/*scoreBoard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(2,
					Color.LIGHT_GRAY, Color.GRAY), 
					BorderFactory.createLineBorder(Color.DARK_GRAY, 5)));*/
			
			blank = new JLabel();
			message = new JTextField();
			message.setText("If you think you know the word enter your guess here");
			chancesLeft = new JLabel("You Only Have: " + chances + " more guess(es) left");
			chancesLeft.setFont (new Font ("Sydnie", Font.BOLD, 18));
			copyRight = new JLabel("Copyright 2007 - EB: DarkNinja.Shark@gmail.com");
			copyRight.setFont (new Font ("defalut", Font.BOLD, 10));
			
			JLabel empty = new JLabel();
			JLabel empty1 = new JLabel();
			JLabel empty2 = new JLabel();
			JLabel empty3 = new JLabel();
			JLabel empty4 = new JLabel();
			JLabel empty5 = new JLabel();
			JLabel empty6 = new JLabel();
			
			empty.setIcon(H);
			empty1.setIcon(A);
			empty2.setIcon(N);
			empty3.setIcon(G);
			empty4.setIcon(M);
			empty5.setIcon(A2);
			empty6.setIcon(N2);
			
		    //fill the gameBoard with the initial ImageIcon
			this.fill();
			createSpaces();
		
			
			//sets up the buttons
			lA = new JButton("A");
			lB = new JButton("B"); 
			lC = new JButton("C"); 
			lD = new JButton("D"); 
			lE = new JButton("E"); 
			lF = new JButton("F"); 
			lG = new JButton("G"); 
			lH = new JButton("H"); 
			lI = new JButton("I"); 
			lJ = new JButton("J"); 
			lK = new JButton("K"); 
			lL = new JButton("L"); 
			lM = new JButton("M"); 
			lN = new JButton("N"); 
			lO = new JButton("O"); 
			lP = new JButton("P"); 
			lQ = new JButton("Q"); 
			lR = new JButton("R"); 
			lS = new JButton("S"); 
			lT = new JButton("T"); 
			lU = new JButton("U"); 
			lV = new JButton("V"); 
			lW = new JButton("W"); 
			lX = new JButton("X"); 
			lY = new JButton("Y"); 
			lZ = new JButton("Z"); 
			newWord = new JButton("New Word");
			giveUp = new JButton("Give Up");
			guess = new JButton("Take a Guess?");
			//add the buttons
			lA.addActionListener (new LetterButtonListener());
			lB.addActionListener (new LetterButtonListener());
			lC.addActionListener (new LetterButtonListener());
			lD.addActionListener (new LetterButtonListener());
			lE.addActionListener (new LetterButtonListener());
			lF.addActionListener (new LetterButtonListener());
			lG.addActionListener (new LetterButtonListener());
			lH.addActionListener (new LetterButtonListener());
			lI.addActionListener (new LetterButtonListener());
			lJ.addActionListener (new LetterButtonListener());
			lK.addActionListener (new LetterButtonListener()); 
			lL.addActionListener (new LetterButtonListener());
			lM.addActionListener (new LetterButtonListener());
			lN.addActionListener (new LetterButtonListener()); 
			lO.addActionListener (new LetterButtonListener());
			lP.addActionListener (new LetterButtonListener()); 
			lQ.addActionListener (new LetterButtonListener());
			lR.addActionListener (new LetterButtonListener());
			lS.addActionListener (new LetterButtonListener());
			lT.addActionListener (new LetterButtonListener());
			lU.addActionListener (new LetterButtonListener());
			lV.addActionListener (new LetterButtonListener());
			lW.addActionListener (new LetterButtonListener());
			lX.addActionListener (new LetterButtonListener());
			lY.addActionListener (new LetterButtonListener()); 
			lZ.addActionListener (new LetterButtonListener());
			
			giveUp.addActionListener (new LetterButtonListener());
			newWord.addActionListener (new LetterButtonListener());
			guess.addActionListener(new LetterButtonListener()); //not part of the buttonsBoard
			//add buttons
			buttonsBoard.add(lA);
			buttonsBoard.add(lB);
			buttonsBoard.add(lC);
			buttonsBoard.add(lD);
			buttonsBoard.add(lE);			
			buttonsBoard.add(lF);
			buttonsBoard.add(lG);
			buttonsBoard.add(lH);
			buttonsBoard.add(lI);
			buttonsBoard.add(lJ);
			buttonsBoard.add(lK);
			buttonsBoard.add(lL);
			buttonsBoard.add(lM);
			buttonsBoard.add(lN);
			buttonsBoard.add(lO);
			buttonsBoard.add(lP);
			buttonsBoard.add(lQ);
			buttonsBoard.add(lR);
			buttonsBoard.add(lS);
			buttonsBoard.add(lT);
			buttonsBoard.add(lU);
			buttonsBoard.add(lV);
			buttonsBoard.add(lW);
			buttonsBoard.add(lX);
			buttonsBoard.add(lY);
			buttonsBoard.add(lZ);
			
			buttonsBoard.add(newWord);
			buttonsBoard.add(giveUp);
			
			//add components 
			scoreBoard.add(chancesLeft);
			scoreBoard.add(blank);
			scoreBoard.add(message);
			scoreBoard.add(guess);
			scoreBoard.add(empty);
			scoreBoard.add(empty1);
			scoreBoard.add(empty2);
			scoreBoard.add(empty3);
			scoreBoard.add(empty4);
			scoreBoard.add(empty5);
			scoreBoard.add(empty6);
			scoreBoard.add(copyRight);
			
			//JOptionPane.showMessageDialog(null, "this is the word " + wordChoice);

			
			//adds the JPanels to the BorderLayout
			add(scoreBoard, BorderLayout.EAST);
			add(gameBoard, BorderLayout.CENTER);
			add(buttonsBoard, BorderLayout.SOUTH);
			
			//need to ask user what word to use for guessing
			
		}
		
		/**
		 * determines if it is a one player or a multi player game
		 * choosen by the user.
		 * @return 1 if it is one player 2 if it is multi player
		 */
		public int numPlayers()
		{
			String userInput = JOptionPane.showInputDialog(null, 
					"How many players?\n\nEnter 1 for a one player game\n(with random words choosen for you to guess)" +
					"\n\nEnter 2 for a multi player game\n(where one user enters a word and the other user guesses)").toUpperCase();
			
			if(userInput.equals("1") || userInput.equals("ONE")){
				return 1; //makes it a one player game
			}
			else if(userInput.equals("2") || userInput.equals("TWO")){
				return 2; //makes it a multi player game
			}
			else{
				JOptionPane.showMessageDialog(null, "That is an invalid input, by defalut the " + 
													"game has been set to One Player.");
				return 1; //makes it a one player game
			}
		}
		
		/**
		 * This method gets the word for one of the users
		 * so that the other user can guess it.
		 * (sets up the two player games word choice)
		 */
		public void multiPlalyer(){
			//set the pattern to having only letters
	        Pattern pat = Pattern.compile("[a-zA-Z]+");
	        
			String usersWord = 
				JOptionPane.showInputDialog(null, "Enter the word you want your opponent(s) to guess. (Letters Only)\n" +
						"Make sure your opponent(s) aren't looking at what word you are typing in!").toUpperCase();
			
			Matcher match = pat.matcher(usersWord); //will see if the word matches the pattern
			 
			//check to see if there are any chars other than letters
			//AKA no special chars, white space chars, or digits ONLY LETTERS!
			if (!match.matches()){
                JOptionPane.showInputDialog(null, "Please, use ONLY Letters! a-z or A-Z" + 
                	"\nA random word from out database will be used for this round.");
                
                //since it was in invalid word, lets just pick one form the database.
				addWords();
				getWord();
            }
			else{ //okay, it passed the pattern matcher
				//now check the word length. we don't want words that are longer than 10 chars because
				//it won't fit on the board, and we don't want them to be less than 3 because those 
				//aren't much fun.
				if(usersWord.length() > 11){
					usersWord = JOptionPane.showInputDialog(null, "That word is too long!" +
							" Enter another word that is less than 11 characters long please.").toUpperCase();
				}
				else if(usersWord.length() < 4){
					usersWord = JOptionPane.showInputDialog(null, "That word is too short!" + 
							" Enter another word that is more than 3 characters long please.").toUpperCase();
				}
				else{
					wordChoice = usersWord;
					System.out.println("we got a word and the word is " + wordChoice);
				}
			}
		}
		
		/**
		 * This method fills the game with the correct
		 * initial images and mines in randomized locations
		 */
		public void fill()
		{
	        //fill the board with labels and set the icon
			//to the empty board peice
			for(int rows=0; rows < NUM_ROWS; rows++){
				for(int cols=0; cols < NUM_COLS; cols++){
					//fill board with correct images
					if(rows == 8 && cols == 1){
						//botLeft
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(BASE1);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 8 && cols == 2){
						//botA
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(BASE2);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 8 && cols == 3){
						//base trunk
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(BASE4);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 8 && cols == 4){
						//botA
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(BASE2);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 8 && cols == 5){
						//botB
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(BASE3);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 8 && cols == 6){
						//botRight
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(BASE5);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 7 && cols == 3){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TRUNK1);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 6 && cols == 3){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TRUNK2);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 5 && cols == 3){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TRUNK1);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 4 && cols == 3){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TRUNK2);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 3 && cols == 3){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TRUNK1);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 2 && cols == 3){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TRUNK2);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 1 && cols == 3){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TRUNK3);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 1 && cols == 4){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TOP1);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 1 && cols == 5){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TOP2);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 1 && cols == 6){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(TOP3);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 3 && cols == 4){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(SUP1);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 2 && cols == 4){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(SUP2);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 2 && cols == 5){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(SUP1);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 3 && cols == 6){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(NOOSE);
						gameBoard.add(gameArray[rows][cols]);
					}
					else if(rows == 2 && cols == 6){
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setIcon(ROPE);
						gameBoard.add(gameArray[rows][cols]);
					}
					else{
						//fill the board with the initial image
						gameArray[rows][cols] = new JLabel();
						gameArray[rows][cols].setText("");
						gameBoard.add(gameArray[rows][cols]);
					}

				}
			}
			
		}
		
		/**
		 * This method adds the words to the wordArray
		 */
		public void addWords(){
			//add words to the wordArray
			wordArray[0] = "FIZZ";
			wordArray[1] = "CUPCAKE";
			wordArray[2] = "XAVIER";
			wordArray[3] = "EXTRA";
			wordArray[4] = "FIRETRUCK";
			wordArray[5] = "ZEBRA";
			wordArray[6] = "QUEEN";
			wordArray[7] = "SEVENTEEN";
			wordArray[8] = "RINO";
			wordArray[9] = "FIRECRACKER";
			wordArray[10] = "MONGOOSE";
			wordArray[11] = "ELEPHANT";
			wordArray[12] = "PSYCHOLOGY";
			wordArray[13] = "AMBIGUOUS";
			wordArray[14] = "MOON";
			wordArray[15] = "RANCID";
			wordArray[16] = "TURTLE";
			wordArray[17] = "VICTORIOUS";
			wordArray[18] = "NEIGHBOR";
			wordArray[19] = "HANGMAN";
			wordArray[20] = "KANGAROO";
			wordArray[21] = "XYLOPHONE";
			wordArray[22] = "XENOPHOBIC";
			wordArray[23] = "HEXAGON";
			wordArray[24] = "YELLOW";
			wordArray[25] = "ZIPPER";
			wordArray[26] = "LUMBERJACK";
			wordArray[27] = "ALPHA";
			wordArray[28] = "BEACON";
			wordArray[29] = "GIANT";
			wordArray[30] = "ROCKET";
			wordArray[31] = "TELEPHONE";
			wordArray[32] = "KNEE";
			wordArray[33] = "KNOCK";
			wordArray[34] = "UNIVERSITY";
			wordArray[35] = "ALLIGATOR";
			wordArray[36] = "QUALIFIDE";
			wordArray[37] = "MONKEY";
			wordArray[38] = "FLUFFY";
			wordArray[39] = "SICSSORS";
			wordArray[40] = "BAMBOO";
			wordArray[41] = "FOOTBALL";
			wordArray[42] = "MAPLE";
			wordArray[43] = "SKATEBOARD";
			wordArray[44] = "WHEEL";
			wordArray[45] = "BEACH";
			wordArray[46] = "PURPLE";
			wordArray[47] = "ASSASSIN";
			wordArray[48] = "NINJA";
			wordArray[49] = "SHARK";
			wordArray[50] = "NASSAU";
			wordArray[51] = "ISTANBUL";
			wordArray[52] = "PICNIC";
			wordArray[53] = "PHOTOGRAPHY";
			wordArray[54] = "CATERPILLAR";
			wordArray[55] = "TREE";
			wordArray[56] = "BUCKET";
			wordArray[57] = "WELCOME";
			wordArray[58] = "WINK";
			wordArray[59] = "XEROX";
			wordArray[60] = "MIRROR";
			wordArray[61] = "CAPTIN";
			wordArray[62] = "DECADE";
			wordArray[63] = "BALCONY";
			wordArray[64] = "ATTACK";
			wordArray[65] = "TYRANT";
			wordArray[66] = "MATADOR";
			wordArray[67] = "JOCKEY";
			wordArray[68] = "ROSEMARY";
			wordArray[69] = "OREGANO";
			
				
	
		}
		
		/**
		 * This method gets a word randomly out of the 
		 * string array that holds 100 diffrent words
		 * @return a word for guessing
		 */
		public String getWord()
		{
			int randomIndex = randWord.nextInt(50);
			wordChoice = wordArray[randomIndex];
		
			return wordChoice;
		}
		
		//okay it gets a random word now we need to make it do the line stuff etc.
		
		/**
		 * Gets the lenght of the word and returns it
		 * @return an integer that represents the length of the word
		 */
		public int getLength(){
			return wordChoice.length();
		}
		
		/**
		 * this method creates the spaces needed for the word
		 */
		public void createSpaces(){
			
			//gets the length and puts it in a variable
			int wordsLength = getLength();
			
			//the row stays the same, but the column will change
			//for each letter present in the word
			for(int i=0; i < wordsLength; i++){
				gameArray[10][i].setText("_");
				gameBoard.add(gameArray[10][i]);
			}
		}
		
		/** 
		 * this method is the one used to guess a letter
		 * (for the buttons)
		 * @param letter is the letter the user wants to guess.
		 */
		public void guessLetter(String letter){

			String guessingLetter = letter;
			
			if(wordChoice.contains(guessingLetter)){
				
				//if the letter appears 1 to 4 times in a word, this will find all 4
				//occurances
				int indexOfFirst = wordChoice.indexOf(guessingLetter);
				int indexOfMid = wordChoice.indexOf(guessingLetter, indexOfFirst+1);
				int indexOfMid2 = wordChoice.indexOf(guessingLetter, indexOfMid+1);
				int indexOfLast = wordChoice.lastIndexOf(guessingLetter);
				
				gameArray[10][indexOfFirst].setText(guessingLetter.toUpperCase());
				gameArray[10][indexOfLast].setText(guessingLetter.toUpperCase());
			    
				//in order to prevent an index out of bounds exception
				if(indexOfMid != -1){
					gameArray[10][indexOfMid].setText(guessingLetter.toUpperCase());
				}
				else{
					//do nothing
				}
				
				if(indexOfMid2 != -1){
					gameArray[10][indexOfMid2].setText(guessingLetter.toUpperCase());
				}
				else{
					//do nothing
				}
				
				winChecker();
			}
			else{
				hanggingTheMan();
			}
		}
		
		/**
		 * This method is used when the user guesses the whole word
		 * at one time
		 * @param letter is each individual letter in the word
		 */
		public void guessWord(String letter){

			String guessingLetter = letter;
			
			if(wordChoice.contains(guessingLetter)){
				
				//if the letter appears 1 to 4 times in a word, this will find all 4
				//occurances
				int indexOfFirst = wordChoice.indexOf(guessingLetter);
				int indexOfMid = wordChoice.indexOf(guessingLetter, indexOfFirst+1);
				int indexOfMid2 = wordChoice.indexOf(guessingLetter, indexOfMid+1);
				int indexOfLast = wordChoice.lastIndexOf(guessingLetter);
				
				gameArray[10][indexOfFirst].setText(guessingLetter.toUpperCase());
				gameArray[10][indexOfFirst].setForeground(Color.RED);
				gameArray[10][indexOfLast].setText(guessingLetter.toUpperCase());
				gameArray[10][indexOfLast].setForeground(Color.RED);
			    
				//in order to prevent an index out of bounds exception
				if(indexOfMid != -1){
					gameArray[10][indexOfMid].setText(guessingLetter.toUpperCase());
					gameArray[10][indexOfMid].setForeground(Color.RED);
				}
				else{
					//do nothing
				}
				
				if(indexOfMid2 != -1){
					gameArray[10][indexOfMid2].setText(guessingLetter.toUpperCase());
					gameArray[10][indexOfMid2].setForeground(Color.RED);
				}
				else{
					//do nothing
				}
				
				if(guessingWholeWord = false){
					winChecker();
				}
				
			}
			else{
				if(guessingWholeWord = false){
					hanggingTheMan();
				}
			}
		}
		/**
		 * this method adds the hangman parts
		 */
		public void hanggingTheMan(){
			//subtract guessing chances left
			chances--;
			chancesLeft.setText("You Only Have: " + chances + " more guess(es) left");
			if(chances==5){
				gameArray[3][6].setIcon(HEAD);
				chancesLeft.setForeground(Color.magenta.darker());
			}
			else if(chances==4){
				gameArray[4][6].setIcon(BODY);
				chancesLeft.setForeground(Color.blue.darker());
			}
			else if(chances==3){
				gameArray[4][6].setIcon(ARM1);
				chancesLeft.setForeground(Color.green.darker());
			}
			else if(chances==2){
				gameArray[4][6].setIcon(ARM2);
				chancesLeft.setForeground(Color.yellow.darker());
			}
			else if(chances==1){
				gameArray[5][6].setIcon(LEG1);
				chancesLeft.setText("You Only Have " + chances + " more guess left O_o");
				chancesLeft.setForeground(Color.orange.darker());
			}
			else if(chances==0){
				gameArray[5][6].setIcon(LEG2);
				chancesLeft.setText("You got hung and that is GAME OVER");
				chancesLeft.setForeground(Color.red.darker());
				unEnableButtons();
				JOptionPane.showMessageDialog(null, "You got hung :: GAME OVER\nThe word was " 
						+ wordChoice);
			}
				
		}
		
		/**
		 * checks to see if the user has completed the word
		 * and won!
		 */
		public void winChecker(){
			//gets the length and puts it in a variable
			int wordsLength = getLength();
			String answer = "";
			
			for(int i=0; i<wordsLength; i++){
				answer = answer + gameArray[10][i].getText();
			}
			
			if(answer.equals(wordChoice)){
				JOptionPane.showMessageDialog(null, "YOU WIN!");
				newWord.setEnabled(true);
				didWin = true;
			}
			
		}
		
		/**
		 * removes the abillity to click on all the buttons
		 * except the new word button
		 */
		public void unEnableButtons(){
			
			  lA.setEnabled(false);
			  lB.setEnabled(false);
			  lC.setEnabled(false);
			  lD.setEnabled(false);
			  lE.setEnabled(false);
			  lF.setEnabled(false);
			  lG.setEnabled(false);
			  lH.setEnabled(false);
			  lI.setEnabled(false);
			  lJ.setEnabled(false);
			  lK.setEnabled(false);
			  lL.setEnabled(false);
			  lM.setEnabled(false);
			  lN.setEnabled(false);
			  lO.setEnabled(false);
			  lP.setEnabled(false);
			  lQ.setEnabled(false);
			  lR.setEnabled(false);
			  lS.setEnabled(false);
			  lT.setEnabled(false);
			  lU.setEnabled(false);
			  lV.setEnabled(false);
			  lW.setEnabled(false);
			  lX.setEnabled(false);
			  lY.setEnabled(false);
			  lZ.setEnabled(false);
			  giveUp.setEnabled(false);
		}
		
		/**
		 * Adds back all the components just like the
		 * initial state they started as... (makes a new game)
		 */
		public void addBackComponents(){
			
			gameBoard.removeAll();//removes all gameBoard components
				
			if(numPlayers == 1){
				addWords();
				getWord();
			}
			else if(numPlayers == 2){
				 multiPlalyer(); //get a new word from the user
			}
			//add back all the components (for one or multi player game)
			fill();//refills gameBoard back up to its initial state
			createSpaces();
			guessingWholeWord = false;   
			didWin = false;
			message.setText("If you think you know the word enter your guess here");
			chances = 6;
			chancesLeft.setText("You Only Have: " + chances + " more guess(es) left");
			lA.setEnabled(true);
			lB.setEnabled(true);
			lC.setEnabled(true);
			lD.setEnabled(true);
			lE.setEnabled(true);
			lF.setEnabled(true);
			lG.setEnabled(true);
			lH.setEnabled(true);
			lI.setEnabled(true);
			lJ.setEnabled(true);
			lK.setEnabled(true);
			lL.setEnabled(true);
			lM.setEnabled(true);
			lN.setEnabled(true);
			lO.setEnabled(true);
			lP.setEnabled(true);
			lQ.setEnabled(true);
			lR.setEnabled(true);
			lS.setEnabled(true);
			lT.setEnabled(true);
			lU.setEnabled(true);
			lV.setEnabled(true);
			lW.setEnabled(true);
			lX.setEnabled(true);
			lY.setEnabled(true);
			lZ.setEnabled(true);
			giveUp.setEnabled(true);
			
			chancesLeft.setForeground(Color.black);
    			
		}
         //*****************************************************************
	     //  Button Listener for all the buttons
	     //*****************************************************************
		   private class LetterButtonListener implements ActionListener
		   {
		      //--------------------------------------------------------------
		      //  by clicking a button with a letter on it the user is guessing that
			  //  letter. If it is correct, it will be displayed in the correct place 
			  //  in the word. If wrong, it will not be displayed and either way the
			  //  the button will become unclickable because it was already used.
		      //--------------------------------------------------------------
		      public void actionPerformed (ActionEvent event)
		      {
		    	  
		    	  //figures out what button is being pressed 
		    	  //and preforms its designated action
		    	  if (event.getSource() == lA){
		    		  lA.setEnabled(false);
		    		  guessLetter("A");
		    	  }
		    	  else if(event.getSource() == lB){
		    		  lB.setEnabled(false);
		    		  guessLetter("B");	  
		    	  }
		    	  else if(event.getSource() == lC){
		    		  lC.setEnabled(false);
		    		  guessLetter("C");
		    	  }
		    	  else if(event.getSource() == lD){
		    		  lD.setEnabled(false);
		    		  guessLetter("D");
		    		  
		    	  }
		    	  else if(event.getSource() == lE){
		    		  lE.setEnabled(false);
		    		  guessLetter("E");
		    		  
		    	  }
		    	  else if(event.getSource() == lF){
		    		  lF.setEnabled(false);
		    		  guessLetter("F");
		    		  
		    	  }
		    	  else if(event.getSource() == lG){
		    		  lG.setEnabled(false);
		    		  guessLetter("G");
		    		  
		    	  }
		    	  else if(event.getSource() == lH){
		    		  lH.setEnabled(false);
		    		  guessLetter("H");
		    		
		    	  }
		    	  else if(event.getSource() == lI){
		    		  lI.setEnabled(false);
		    		  guessLetter("I");
		    		
		    	  }
		    	  else if(event.getSource() == lJ){
		    		  lJ.setEnabled(false);
		    		  guessLetter("J");
		    		 
		    	  }
		    	  else if(event.getSource() == lK){
		    		  lK.setEnabled(false);
		    		  guessLetter("K");
		    		 
		    	  }
		    	  else if(event.getSource() == lL){
		    		  lL.setEnabled(false);
		    		  guessLetter("L");
		    		  
		    	  }
		    	  else if(event.getSource() == lM){
		    		  lM.setEnabled(false);
		    		  guessLetter("M");
		    		  
		    	  }
		    	  else if(event.getSource() == lN){
		    		  lN.setEnabled(false);
		    		  guessLetter("N");
		    		 
		    	  }
		    	  else if(event.getSource() == lO){
		    		  lO.setEnabled(false);
		    		  guessLetter("O");
		    		 
		    	  }
		    	  else if(event.getSource() == lP){
		    		  lP.setEnabled(false);
		    		  guessLetter("P");
		    		  
		    	  }
		    	  else if(event.getSource() == lQ){
		    		  lQ.setEnabled(false);
		    		  guessLetter("Q");
		    		  
		    	  }
		    	  else if(event.getSource() == lR){
		    		  lR.setEnabled(false);
		    		  guessLetter("R");
		    		  
		    	  }
		    	  else if(event.getSource() == lS){
		    		  lS.setEnabled(false);
		    		  guessLetter("S");
		    		  
		    	  }
		    	  else if(event.getSource() == lT){
		    		  lT.setEnabled(false);
		    		  guessLetter("T");
		    		  
		    	  }
		    	  else if(event.getSource() == lU){
		    		  lU.setEnabled(false);
		    		  guessLetter("U");
		    		  
		    	  }
		    	  else if(event.getSource() == lV){
		    		  lV.setEnabled(false);
		    		  guessLetter("V");
		    		  
		    	  }
		    	  else if(event.getSource() == lW){
		    		  lW.setEnabled(false);
		    		  guessLetter("W");
		    		  
		    	  }
		    	  else if(event.getSource() == lX){
		    		  lX.setEnabled(false);
		    		  guessLetter("X");
		    		  
		    	  }
		    	  else if(event.getSource() == lY){
		    		  lY.setEnabled(false);
		    		  guessLetter("Y");
		    		  
		    	  }
		    	  else if(event.getSource() == lZ){
		    		  lZ.setEnabled(false);
		    		  guessLetter("Z");
		    		  
		    	  }
		    	  //start new game
		    	  else if (event.getSource() == newWord){
		    		  addBackComponents();
					
		    	  }
		    	  else if(event.getSource() == giveUp){
		    		  JOptionPane.showMessageDialog(null, "You gave up. The answer was: " + wordChoice);
		    		  addBackComponents();
		    	  }
		    	  else if(event.getSource() == guess){
		    		  String usersGuess = message.getText();
		    		  if(usersGuess.toUpperCase().equals(wordChoice.toUpperCase())){
		    			  int lengthOfWord = getLength();
		    			  int i = 0;
		    			  guessingWholeWord = true;
		  				  newWord.setEnabled(true);
		  				  for(i=1; i<lengthOfWord; i++){
		  					  if(gameArray[10][i].getText().equals("_")){
		  						guessWord("A");
		  						guessWord("B");
		  						guessWord("C");
		  						guessWord("D");
		  						guessWord("E");
		  						guessWord("F");
		  						guessWord("G");
		  						guessWord("H");
		  						guessWord("I");
		  						guessWord("J");
		  						guessWord("K");
		  						guessWord("L");
		  						guessWord("M");
		  						guessWord("N");
		  						guessWord("O");
		  						guessWord("P");
		  						guessWord("Q");
		  						guessWord("R");
		  						guessWord("S");
		  						guessWord("T");
		  						guessWord("U");
		  						guessWord("V");
		  						guessWord("W");
		  						guessWord("X");
		  						guessWord("Y");
		  						guessWord("Z");
		  					  }
		  					  else{
		  						  //System.out.println("it didn't check the letters");
		  						  //do nothing
		  					  }
		  				  }
		  				JOptionPane.showMessageDialog(null, "YOU WIN!\nThe word was " + wordChoice);
		  				didWin = true; 
		  				guessingWholeWord = false;
		    		  }
		    		  else{
		    			  JOptionPane.showMessageDialog(null, "Nope, that isn't the word.");
		    			  guessingWholeWord = false;
		    			  hanggingTheMan();
		    		  }
		    	  }
		          else {
		        	 //This should NEVER be reached
		             JOptionPane.showMessageDialog(null, "ERROR with you guess!");
		          }
		    	  
		    	  //if the user wins, make all buttons except new word unclickable
		    	  if(didWin){
		    		  unEnableButtons();
	    		  }
		      }
		   }
		   
}
