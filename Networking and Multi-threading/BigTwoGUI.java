import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.util.StringTokenizer;


public class BigTwoGUI implements CardGameUI{
	
	/**
	 * a constructor for creating a BigTwoGUI. The parameter 
game is a reference to a Big Two card game associates with this GUI
	 * @param game
	 */
	public BigTwoGUI(BigTwo game) {
		this.game = game;
	}
	
	public synchronized void go() {
		
		playerList = game.getPlayerList();
		handsOnTable = game.getHandsOnTable();
		
		//Creating these buttons to allow them can be used in the enable() function
		this.frame = new JFrame();
		
		this.playButton = new JButton();
		this.passButton = new JButton();
		this.bigTwoPanel = new JPanel();
		
		
		this.chatArea = new JTextArea();
		this.msgArea = new JTextArea();
		this.chatInput = new JTextField(40);
		
		this.pokerImage = new Image[4][13];
		this.iconImage = new Image[4];
		
		this.label = new JLabel("Message: ");
		
		String s = new String();
		
		for (int i = 0; i < 13; i++) {
			this.selected[i] = false;
		}
		
		for (int i = 0; i < 4 ; i++) {
			for (int j = 0; j < 13; j++) {
				int temp_i = i+1;
				int temp_j = j+1;
				pokerImage[i][j] = new ImageIcon(String.format("src/%s_%s.gif", temp_j,temp_i)).getImage();
				
			}
		}
		
		int j = 0;
		for (int i = 0 ; i < 4; i++) {
			iconImage[i] = new ImageIcon(String.format("src/%s_%s.png",j,i)).getImage().getScaledInstance(70, 80, Image.SCALE_DEFAULT);
		}
		
		
		this.back = new ImageIcon("src/back.gif").getImage();
		this.throne = new ImageIcon("src/0_4.png").getImage().getScaledInstance(70, 80, Image.SCALE_DEFAULT);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.menuBar = new JMenuBar();
		this.menu = new JMenu("Menu");
		this.message = new JMenu("Clear");
		this.connectMenu = new JMenu("Connect");
		
		this.item1 = new JMenuItem("Restart");
		this.item2 = new JMenuItem("Exit");
		
		this.item3 = new JMenuItem("Clear Game Message");
		this.item4 = new JMenuItem("Clear Chat");
		
		this.item1.addActionListener(new RestartMenuItemListener());
		this.item2.addActionListener(new QuitMenuItemListener());
		
		this.item3.addActionListener(new ClearMessageItemListener());
		this.item4.addActionListener(new ClearChatItemListener());
		
		//this.item5.addActionListener(new ConnectItemListener());
		
		this.menu.add(item1);
		this.menu.add(item2);
		
		this.message.add(item3);
		this.message.add(item4);
		
		//this.connectMenu.add(item5);
		
		this.menuBar.add(menu);
		this.menuBar.add(message);
		//this.menuBar.add(connectMenu);
		
		frame.add(menuBar,BorderLayout.NORTH);
		
		//The implementation of BigTwoPanel
		
		centerPanel = new JPanel();
		
		centerPanel.setLayout(new GridLayout(5, 1));
		
		revolutions = 0;
		
		while(revolutions < 5) {
				if (revolutions < 4) {
					bigTwoPanel = new BigTwoPanel(revolutions);
					centerPanel.add(bigTwoPanel);
					revolutions++;
				}
				else {
					BigTwoPanel_2 bigTwoPanel_2 = new BigTwoPanel_2();
					centerPanel.add(bigTwoPanel_2);
					revolutions++;
				}
			
		}
		
		
		
		frame.add(centerPanel, BorderLayout.CENTER);
		
		
		playButton = new JButton("Play");
		playButton.addActionListener(new PlayButtonListener());
		
		passButton = new JButton("Pass");
		passButton.addActionListener(new PassButtonListener());
		
		
		panel_2 = new JPanel();
		
		panel_2.add(playButton);
		panel_2.add(passButton);
		
		panel_2.add(label);

		chatInput.addActionListener(new ChatInputListener());
		panel_2.add(chatInput);
		
		frame.add(panel_2,BorderLayout.SOUTH);
		
		
		
		
		this.msgArea = new JTextArea(20, 40);
		this.chatArea = new JTextArea(20, 40);
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		msgArea.setBorder(BorderFactory.createLineBorder(Color.cyan));
		chatArea.setBorder(BorderFactory.createLineBorder(Color.orange));
		
		msgArea.setBackground(Color.orange);
		chatArea.setBackground(Color.cyan);
		
		msgArea.setEditable(false);
		chatArea.setEditable(false);
		
		lb1 = new JLabel("Game Message Box");
		panel.add(lb1);
		
		scrollPane_1 = new JScrollPane( msgArea );
		panel.add(scrollPane_1);
		
		lb2 = new JLabel("Chat Box");
		panel.add(lb2);
		
		msgArea.setLineWrap(true);
		chatArea.setLineWrap(true);
		
		scrollPane_2 = new JScrollPane( chatArea );
		panel.add(scrollPane_2);
		
		frame.add(panel,BorderLayout.EAST);

		enable();
		
		frame.setSize(1200,900);
		frame.setVisible(true);
		
	}
	
	private BigTwo game = null;
	private boolean[] selected = new boolean[MAX_CARD_NUM];
	private int activePlayer = -1;
	private int revolutions = 0;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JPanel bigTwoPanel_2;
	private JPanel centerPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private JTextArea chatArea;
	private JTextField chatInput;
	private Image[][] pokerImage;
	private Image[] iconImage;
	private Image back;
	private Image throne;
	private JMenuBar menuBar;
    private JMenu menu;
    private JMenu message;
    private JMenuItem item1;
    private JMenuItem item2;
    private JMenuItem item3;
    private JMenuItem item4;
    private JMenuItem item5;
    private JPanel panel;
    private JPanel panel_2;
    private JLabel label;
    private JLabel lb1;
    private JLabel lb2;
    private JScrollPane scrollPane_1;
    private JScrollPane scrollPane_2;
    private JMenu connectMenu;
	
	//Added by myself
	private int[] cardIdx;
	private int height;
	//Variables copied from the BigTwoUI of assignment3
	
	private final static int MAX_CARD_NUM = 13; // max. no. of cards each player holds
	
	private ArrayList<CardGamePlayer> playerList; // the list of players
	private ArrayList<Hand> handsOnTable; // the list of hands played on the
	private Scanner scanner; // the scanner for reading user in put
	private int number = -1;
	
	//Not sure variables on the above are needed or not
	
	
	@Override
	/**
	 * a method for setting the index of the active player (i.e., the player having control of the GUI). 
	This is extracted from the BigTwoGUI of the assignment3;
	 */
	public void setActivePlayer(int activePlayer) {
		
		if (activePlayer < 0 || activePlayer >= playerList.size()) {
			this.activePlayer = -1;
		} else {
			this.activePlayer = activePlayer;
		}
		
	}

	@Override
	/**
	 * a method for repainting the GUI. 
	 */
	public void repaint() {
		//Should be reprint the GUI here, treat this as the main function,
		//As the repaint function is called by the main in the BigTwo() function
		
		//THIS PART IS EXTRACTED FROM THE repaint() function in BigTwoGui of Assignment3
		
		handsOnTable = game.getHandsOnTable();
		
		//Re paint every column of the GUI: 
		if (game.end == true) {
			//PRINT THE CONGRATATIONS IN THE MESSAGE BOX
			this.clearMsgArea();
			
			
			for (int i = 0; i < 4; i++) {
				
				if (game.getPlayerList().get(i).getNumOfCards() == 0) {
					printMsg("BIG TWO GAME ENDS");
					printMsg(String.format("PLAYER %s WINS IN THE GAME OF THRONES \n", i));
				}
				
			}
			
			for (int i = 0; i< 4; i++) {
				if (game.getPlayerList().get(i).getNumOfCards() == 0) {
					printMsg(String.format("Player %s has %s cards in hand", i, "0"));
				}
				else {
					printMsg(String.format("Player %s has %s cards in hand", i, game.getPlayerList().get(i).getNumOfCards()));
				}
			}
		}
		frame.repaint();
		
	}

	@Override
	/**
	 * a method for printing the specified string to the message
area of the GUI. 
	 */
	public void printMsg(String msg) {
		this.msgArea.append(msg);
		this.msgArea.append("\n");
	}

	/**
	 * a method for printing the specified string to the chat
area of the GUI. 
	 */
	public void printChatMsg(String msg) {
		this.chatArea.append(msg + "\n");
	}
	@Override
	/**
	 * a method for clearing the message area of the GUI. 
	 */
	public void clearMsgArea() {
		this.msgArea.setText("");
		
	}
	
	/**
	 * A method for clearing the chat area of the GUI
	 */
	public void clearChatArea() {
		this.chatArea.setText("");
	}
	
	
	@Override
	/**
	 * a method for resetting the GUI. You should (i) reset the list of selected 
cards; (ii) clear the message area; and (iii) enable user interactions
	 */
	public void reset() {
		clearMsgArea();
		this.chatArea.setText("");
		this.chatInput.setText("");
		this.enable();
		
		
	}

	
	// enable() and disable() are being called separately in the start and end 
	// of the game (Enabling and disabling the UI)
	
	// For the enable() function, we should enable the selection of cards 
	// by the player: Only allow some of the cards to be clicked?
	// But how did the GUI run when player changed: cards can be selected is different
	
	@Override
	/**
	 * a method for enabling user interactions with the GUI
	 */
	public void enable() {
		this.playButton.setEnabled(true);
		this.passButton.setEnabled(true);
		this.chatInput.setEnabled(true);
		
		// Is this can enable the BigTwoPanel for selection of cards 
		// though mouse clicks?
		this.bigTwoPanel.setEnabled(true);
	}

	@Override
	/**
	 * a method for disabling user interactions with the GUI. 
	 */
	public void disable() {
		this.playButton.setEnabled(false);
		this.passButton.setEnabled(false);
		this.chatInput.setEnabled(false);
		
		// Is this can enable the BigTwoPanel for selection of cards 
		// though mouse clicks?
		this.bigTwoPanel.setEnabled(false);
		
	}

	@Override
	/**
	 * a method for prompting the active player to select cards 
and make his/her move. A message should be displayed in the message area showing it 
is the active player’s turn.

	 */
	public void promptActivePlayer() {
		
		//1. Print a message in the panel though printMsg() function
		printMsg(playerList.get(activePlayer).getName() + "'s turn: ");
		
		//2. Setting up the status of the passButton
		
		this.playerList = game.getPlayerList();	
		this.number = game.getHandsOnTable().size()- 1;
		
		
		if (this.playerList.indexOf(number) == activePlayer) {
			passButton.setEnabled(false);
		}
		else if (game.getHandsOnTable().size()<=0) {
			passButton.setEnabled(false);
		}
		else {
			passButton.setEnabled(true);
		}

		
		//We do not need to call the makeMove() function here as:
		//When we click the buttons, the actionPerformed() will call the 
		//makeMove() function automatically
		
	}
	
	/**
	 * A method to get the cards that are selected by the player
	 * @return a cardlist of selected cards
	 */
	public int[] getSelected() {
		
		//THE BELOW CODE IS COPIED FROM THE BigTwoUI() FROM THE ASSIGNMENT3	
		
		//1. Create a new integer array with the length of number of selected card
		//   Which is the number of true inside the selected()
		int[] temp_cardIdx = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}
		if (count != 0) {
			temp_cardIdx = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					temp_cardIdx[count] = j;
					count++;
				}
			}
		}
		return temp_cardIdx;
		
		
		//2. This should be implemented in the MouseReleased to get the selected cards
		
		//3. Keep in mind to implement with the selected[ ] boolean array 
		
		
	}
	
	//BELOW ARE THE INNER CLASSES
	/**
	 * an inner class that extends the JPanel class and implements the MouseListener interface. 
	 * @author danie
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		
		//Only edit these two
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2566712449442880656L;
		private int x_LeftUp = 140;
		private int y_LeftUp = 40;
		private int index;
		private TitledBorder playerTitledBorder;
		private int numOfCards;
		
		private int card_h = 110;
		private int card_w = 80;
		private int spacing = 40;
		private int rising = 20;
		private Graphics2D g2d;
		private int temp_x;
		private int temp_y;
		private int temp_x_2;
		private int temp_y_2;
		private Color background_color;
		
		private int x_coord; 
		private int y_coord;
		private int length;
		
		/**
		 * A constructor for creating a BigTwoPanel object
		 * @param value
		 */
		public BigTwoPanel(int value) {
			
			this.addMouseListener(this);
			background_color = new Color(26,188,156);
			this.setBorder(BorderFactory.createTitledBorder("Player " + value + "'s cards "));	
			index = value; 
			for (int i = 0; i < 13;i++) {
				selected[i] = false;
			}
			
			
		}
		
		/**
		 *  method inherited from the JPanel class to draw the card game table
		 */
		public void paintComponent(Graphics g) {
			
			//THE BELOW COMMENTED CODE HAS SOME PROBLEMS
			
			g2d = (Graphics2D) g;
			g2d.setColor(background_color);
			numOfCards = game.getPlayerList().get(index).getNumOfCards();
			
			g.fillRect(0, 0, getWidth(), getHeight());
			
			if (!game.end) {
				g2d.drawImage(iconImage[index], 20, 40, this);
			}
			else if (game.end){
				
				g2d.drawImage(iconImage[index], 20, 40, this);
				
				if (numOfCards == 0) {
					g2d.drawImage(throne, 100, 40, this);
				}
				else {
					
					int count = 0;
					while(count < numOfCards) {
						
						if (selected[count]== true) {
						
							this.temp_x = game.getPlayerList().get(index).getCardsInHand().getCard(count).getSuit();
							this.temp_y = game.getPlayerList().get(index).getCardsInHand().getCard(count).getRank();
							
							this.temp_x_2 = this.x_LeftUp + count*spacing;
							this.temp_y_2 = this.y_LeftUp - rising;
							
						}
						else if (selected[count] == false) {
							this.temp_x = game.getPlayerList().get(index).getCardsInHand().getCard(count).getSuit();
							this.temp_y = game.getPlayerList().get(index).getCardsInHand().getCard(count).getRank();
						
							this.temp_x_2 = this.x_LeftUp + count*spacing;
							this.temp_y_2 = this.y_LeftUp;
				
						}
						g2d.drawImage(pokerImage[temp_x][temp_y],temp_x_2 ,temp_y_2 , this);
						count++;
					}
				}
			}
			
			if (!game.end && index == activePlayer) {
				int count = 0;
				
				while(count < numOfCards) {
					
					if (selected[count]== true) {
					
						this.temp_x = game.getPlayerList().get(index).getCardsInHand().getCard(count).getSuit();
						this.temp_y = game.getPlayerList().get(index).getCardsInHand().getCard(count).getRank();
						
						this.temp_x_2 = this.x_LeftUp + count*spacing;
						this.temp_y_2 = this.y_LeftUp - rising;
						
					}
					else if (selected[count] == false) {
						this.temp_x = game.getPlayerList().get(index).getCardsInHand().getCard(count).getSuit();
						this.temp_y = game.getPlayerList().get(index).getCardsInHand().getCard(count).getRank();
					
						this.temp_x_2 = this.x_LeftUp + count*spacing;
						this.temp_y_2 = this.y_LeftUp;
			
					}
					g2d.drawImage(pokerImage[temp_x][temp_y],temp_x_2 ,temp_y_2 , this);
					count++;
				}
				
			}
			else if (!game.end && index != activePlayer){
				int count = 0;
				
				while(count < numOfCards) {
					this.temp_x_2 = this.x_LeftUp + count*spacing;
					this.temp_y_2 = this.y_LeftUp;
					g2d.drawImage(back,temp_x_2 ,temp_y_2 , this);
					count++;
				}
			}
			
		}
		
		@Override
		/**
		 *  Implements the mouseReleased() method from the MouseListener interface to handle mouse click events. 
		 */
		public void mouseReleased(MouseEvent e) {
			
			if (game.end) {
				return;
			}
			
			
			x_coord = e.getX();
			y_coord = e.getY();
			
			length = game.getPlayerList().get(activePlayer).getNumOfCards() - 1;
			
			int i = length;
			
			while( (i <= length) == true ) {
				if ((selected[i] == false) && (activePlayer == this.index)) {
					if (x_coord <= x_LeftUp + i * spacing + card_w) { 
						if ( x_coord >= x_LeftUp + i * spacing) {
							if (y_coord <= y_LeftUp + card_h ) {
								if (y_coord >= y_LeftUp ) {
									selected[i] = true;
									this.repaint();
									i--;
									break;
								}
							}
						}
					}
				}
				
				else if ((selected[i] == true) && (activePlayer == this.index)){
					if (x_coord <= x_LeftUp + i * spacing + card_w) { 
						if ( x_coord >= x_LeftUp + i * spacing) {
							if (y_coord + rising <= y_LeftUp + card_h) {
								if (y_coord + rising >= y_LeftUp) {
									selected[i] = false;
									this.repaint();
									i--;
									break;
								}
							}
						}
					}
					
				}
				i--;
			}
			
			
		}
		
		
		//No need to edit the below ones
		@Override
		/**
		 * Nothing to be edited
		 */
		public void mouseClicked(MouseEvent e) {
			//No need to be edited 
		}
		@Override
		/**
		 * Nothing to be edited
		 */
		public void mousePressed(MouseEvent e) {
			// No need to be edited
		}
		@Override
		/**
		 * Nothing to be edited
		 */
		public void mouseEntered(MouseEvent e) {
			// No need to be edited
		}
		@Override
		/**
		 * Nothing to be edited
		 */
		public void mouseExited(MouseEvent e) {
			// No need to be edited
		}
	}
	
	/**
	 * A class extends the JPanel where used to displace the cards on the table
	 * @author danie
	 *
	 */
	class BigTwoPanel_2 extends JPanel {
		
		
		
		private int x_LeftUp = 40;
		private int y_LeftUp = 40;
		private Color background_color;
		private Graphics2D g2d;
		
		
		private int lastHandPeople;
		private int handsOnTableSize;
		private Image currentCard;
		private Hand lastHandOnTable;
		private TitledBorder lastHandBorder;
		private int temp_x;
		private int temp_y;
		
		/**
		 * Constructor of BigTwoPanel_2 for creating the panel
		 */
		public BigTwoPanel_2() {
			
			handsOnTableSize = game.getHandsOnTable().size();
			background_color = new Color(36,113,163);
			
			if (handsOnTableSize == 0) {
				this.setBorder(BorderFactory.createTitledBorder("{Empty}"));	
			}
			else if (handsOnTableSize > 0) {
				
				this.setBorder(BorderFactory.createTitledBorder("Played by Player " + game.getPlayerList().indexOf(game.getHandsOnTable().get(game.getHandsOnTable().size() - 1).getPlayer())));	
				
			}
			
		}
		/**
		 * Called automatically to paint the panel out
		 */
		public void paintComponent(Graphics g) {
			
			handsOnTableSize = game.getHandsOnTable().size();
			background_color = new Color(36,113,163);
			
			if (handsOnTableSize == 0) {
				this.setBorder(BorderFactory.createTitledBorder("{Empty}"));	
			}
			else if (handsOnTableSize > 0) {
				
				this.setBorder(BorderFactory.createTitledBorder("Played by Player " + lastHandPeople +": " + game.getHandsOnTable().get(game.getHandsOnTable().size() - 1) + ""));	
				
			}
			
			g2d = (Graphics2D) g;
			g2d.setColor(background_color);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			if (game.getHandsOnTable().size()> 0) {
				int last = game.getHandsOnTable().size() - 1;
				lastHandOnTable = game.getHandsOnTable().get(last);
				
				
				int i = 0;
				while((game.getHandsOnTable().size() != 0) &&(i < lastHandOnTable.size())) {
					this.temp_x = lastHandOnTable.getCard(i).getSuit();
					this.temp_y = lastHandOnTable.getCard(i).getRank();
					
					g2d.drawImage(pokerImage[temp_x][temp_y],x_LeftUp + i*90,y_LeftUp, this);

					i++;
					
				}
			}
			
		}

	}
	
	
	
	/**
	 * an inner class that implements the ActionListener interface, detect when the play button is clicked
	 * @author danie
	 *
	 */
	class PlayButtonListener implements ActionListener {

		@Override
		/**
		 * method from the ActionListener interface to handle button-click events for the “Play” button. 
		 */
		public void actionPerformed(ActionEvent e) {
			
			cardIdx = getSelected();
			game.checkMove(activePlayer, cardIdx);
			
			int length = selected.length;
			
			if (game.legal != true) {
				
			}
			else if (game.legal == true) {
				
				for (int i = 0; i < length; i++) {
					selected[i] = false;
				}
				repaint();
			}
			
		}
		
	}
	/**
	 * an inner class that implements the ActionListener interface, detect when people click the pass button
	 * @author danie
	 *
	 */
	class PassButtonListener implements ActionListener {

		@Override
		/**
		 * method from the ActionListener interface to handle button-click events for the “Pass” button.
		 */
		public void actionPerformed(ActionEvent e) {
			
			cardIdx = getSelected();
			
			game.makeMove(activePlayer, cardIdx);
			for (int k = 0; k < selected.length; k++) {
					selected[k] = false;
			}
			repaint();
			
			
		}
	}
	
	
	
	/**
	 * an inner class that implements the ActionListener interface, detected when have chat input
	 * @author danie
	 *
	 */
	class ChatInputListener implements ActionListener{

		@Override
		/**
		 * method from the ActionListener interface to handle menu-item-click events for the “Restart” menu item
		 */
		public void actionPerformed(ActionEvent e) {
			chatArea.append("Player "+ activePlayer + ": \n" + ((JTextField) e.getSource()).getText());
			chatInput.setText("");
		}
		
		
		
	}
	
	
	
	/**
	 * an inner class that implements the ActionListener interface, detected when click the clear message button
	 * @author danie
	 *
	 */
	class ClearMessageItemListener implements ActionListener{

		@Override
		/**
		 * detected when click the clear message button
		 */
		public void actionPerformed(ActionEvent e) {
			clearMsgArea();
			
		}
		
	}
	/**
	 * an inner class that implements the ActionListener interface, detect when click the clear chat button
	 * @author danie
	 *
	 */
	class ClearChatItemListener implements ActionListener{

		@Override
		/**
		 * detect when click the clear chat button
		 */
		public void actionPerformed(ActionEvent e) {
			clearChatArea();
			
		}
		
	}
	
	/**
	 *  an inner class that implements the ActionListener interface, detected when people click the restart button
	 * @author danie
	 *
	 */
	class RestartMenuItemListener implements ActionListener {

		@Override
		/**
		 * detected when people click the restart button
		 */
		public void actionPerformed(ActionEvent e) {
			reset();
			BigTwoDeck d = new BigTwoDeck();
			d.shuffle();
			game.start(d);
			
		}
		
	}
	/**
	 *  an inner class that implements the ActionListener interface, detected when people click the exit button
	 * @author danie
	 *
	 */
	class QuitMenuItemListener implements ActionListener {

		@Override
		/**
		 * detected when people click the exit button
		 */
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		
		}
		
	}

}
