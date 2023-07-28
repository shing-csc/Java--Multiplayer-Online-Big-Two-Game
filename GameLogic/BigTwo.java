import java.util.ArrayList;
/**
 * implements the CardGame interface and is used to model a Big Two card game
 * @author danie
 *
 */
public class BigTwo implements CardGame{
	/**
	 * – a constructor for creating a Big Two card game. You should (i) create 4 
players and add them to the player list; and (ii) create a BigTwoUI object for providing 
the user interface.
	 */
	public BigTwo() {
		//Create four players
		CardGamePlayer p0= new CardGamePlayer();
		CardGamePlayer p1= new CardGamePlayer();
		CardGamePlayer p2= new CardGamePlayer();
		CardGamePlayer p3= new CardGamePlayer();
		
		//Adding the players to the ArrayList
		this.playerList.add(p0);
		this.playerList.add(p1);
		this.playerList.add(p2);
		this.playerList.add(p3);
		
		//Creating a BigTwoUI object based on the current BigTwo class
		ui = new BigTwoUI(this); 

	}
	
	
	private int numOfPlayers;
	private BigTwoDeck deck = new BigTwoDeck();
	private ArrayList<CardGamePlayer> playerList = new ArrayList<CardGamePlayer>();
	private ArrayList<Hand> handsOnTable = new ArrayList<Hand>();
	private int currentPlayerIdx;
	private BigTwoUI ui;
	//Use to calculate the number of times the players type in nothing(null)
	public int num_of_null = 0; 
	
	//List of the cards get selected by the player in each time
	public CardList selected_c= new CardList();
	//Check if the program/game ends
	public boolean end = false;
	

	@Override
	/**
	 * a method for getting the number of players
	 * @return number of players
	 */
	public int getNumOfPlayers() {
		
		return this.numOfPlayers;
	}
	@Override
	/**
	 * a method for retrieving the deck of cards being used
	 * @return the deck
	 */
	public Deck getDeck() {
		
		return this.deck;
	}
	@Override
	/**a method for retrieving the list of players
	 * @return the list of players
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		
		return this.playerList;
	}
	@Override
	/**
	 * a method for retrieving the list of hands played on the table.
	 * @return list of hands on the table
	 */
	public ArrayList<Hand> getHandsOnTable() {
		
		return this.handsOnTable;
	}
	@Override
	/**a method for retrieving the index of the current player
	 * @return index of the current player
	 */
	public int getCurrentPlayerIdx() {
		
		return this.currentPlayerIdx;
	}
	
	//===================================================================
	@Override
	/**
	 * a method for starting/restarting the game with a given shuffled deck of cards. 
	 * 
	 */
	public void start(Deck deck) {

		for (CardGamePlayer p: playerList) {
			p.removeAllCards();
		}
		for (Hand h : handsOnTable) {
			h.removeAllCards();
		}
		for (int i = 0; i < deck.size(); i++) {
			if (i%4 == 0) {
				playerList.get(0).addCard(deck.getCard(i));
			}
			else if (i%4 == 1) {
				playerList.get(1).addCard(deck.getCard(i));
			}
			else if (i%4 == 2) {
				playerList.get(2).addCard(deck.getCard(i));	
			}
			else if (i%4 == 3) {
				playerList.get(3).addCard(deck.getCard(i));
			}	
		}
		Card c = new BigTwoCard(0,2);
		for (int i = 0; i < 13; i++) {
			if ((playerList.get(0).getCardsInHand().getCard(i).rank == c.rank) && (playerList.get(0).getCardsInHand().getCard(i).suit == c.suit)){
				currentPlayerIdx = 0;
				ui.setActivePlayer(0);
			}
			else if ((playerList.get(1).getCardsInHand().getCard(i).rank == c.rank) && (playerList.get(1).getCardsInHand().getCard(i).suit == c.suit)) {
				currentPlayerIdx = 1;
				ui.setActivePlayer(1);
			}
			else if ((playerList.get(2).getCardsInHand().getCard(i).rank == c.rank) && (playerList.get(2).getCardsInHand().getCard(i).suit == c.suit)) {
				currentPlayerIdx = 2;
				ui.setActivePlayer(2);
			}
			else if ((playerList.get(3).getCardsInHand().getCard(i).rank == c.rank) && (playerList.get(3).getCardsInHand().getCard(i).suit == c.suit)) {
				currentPlayerIdx = 3;
				ui.setActivePlayer(3);
			}
		}
		
		playerList.get(0).getCardsInHand().sort();
		playerList.get(1).getCardsInHand().sort();
		playerList.get(2).getCardsInHand().sort();
		playerList.get(3).getCardsInHand().sort();
		
		while(!this.end) {
			ui.repaint();
			ui.promptActivePlayer();
			System.out.println();
		}
		if (this.end) {
			ui.repaint();
			System.out.println();
			System.out.println("Game ends");
			for (int i = 0; i< 4; i++) {
				if (this.playerList.get(i).getCardsInHand().size()!=0) {
					System.out.println("Player " + i + " has " + this.playerList.get(i).getCardsInHand().size() +" cards in hand.");
				}
				else {
					System.out.println("Player " + i + " wins the game.");
				}
			}
		}
	}
	
	@Override
	/**
	 * a method for making a move by a player with the specified index using the cards specified by the list of indices.
	 */
	public void makeMove(int playerIdx, int[] cardIdx) {
		
		checkMove(playerIdx,cardIdx);
		
	}
	@Override
	/**
	 * a method for checking a move made by a player
	 */
	public void checkMove(int playerIdx, int[] cardIdx) {
		Card c = new BigTwoCard(0,2);
		
		selected_c = playerList.get(playerIdx).play(cardIdx);
		Hand h0 = this.composeHand(this.playerList.get(playerIdx), selected_c);
		
		
		//handsOnTable is empty when is the first move
		if (this.handsOnTable.isEmpty()) {
			if (selected_c != null && selected_c.contains(c) && h0 != null && h0.isValid()) {
				
				this.playerList.get(playerIdx).removeCards(selected_c);
				this.handsOnTable.add(h0);
				this.currentPlayerIdx = (this.currentPlayerIdx + 1)%4;
				ui.setActivePlayer(currentPlayerIdx);
				this.num_of_null = 0;
			}
			else {
				System.out.println("Not a legal move!!!");
			}
		}
		else {
			if (this.num_of_null >= 3) {
				if (selected_c != null && (h0 != null) && (h0.isValid())){
					this.playerList.get(playerIdx).removeCards(selected_c);
					this.handsOnTable.add(h0);
					this.currentPlayerIdx = (this.currentPlayerIdx + 1)%4;
					ui.setActivePlayer(currentPlayerIdx);
					this.num_of_null = 0;
				}
				else {
					System.out.println("Not a legal move!!!");
				}
			}
			else if (selected_c!= null && (h0 != null) && (h0.isValid()) && this.num_of_null < 3) {
				
				if (h0.beats(this.handsOnTable.get(handsOnTable.size()-1))) {
			
					this.playerList.get(playerIdx).removeCards(selected_c);
					this.handsOnTable.add(h0);
					this.currentPlayerIdx = (this.currentPlayerIdx + 1)%4;
					ui.setActivePlayer(currentPlayerIdx);
					this.num_of_null = 0;
				}
				else {
					System.out.println("Not a legal move!!!");
				}
			}
			
			
			else if (this.selected_c == null || h0 == null) {
				if (this.num_of_null <3) {
					//this.handsOnTable.add(null);
					this.currentPlayerIdx = (this.currentPlayerIdx + 1)%4;
					ui.setActivePlayer(currentPlayerIdx);
					System.out.println("{Pass}");
					this.num_of_null += 1;
				}
				else {
					System.out.println("Not a legal move!!!");
				}
			}
			else {
				System.out.println("Not a legal move!!!");
			}
		}	
		if (this.endOfGame()) {
			this.end = true;
		}
	}
			
			
		
		
		
		/**
		 * If is the first move:
		 * 		Check the selected_c contains diamond 3 or not
		 * 		if (contain)&&(move is valid)
		 * 			valid move    -> Remove the cards and add to the table
		 * 		else if not contain
		 * 			not valid move -> Ask the user to in-take again -> while loop 
		 * 								
		 * else if not first move
		 * 		check the move's type same as the last move or not
		 * 		by fetch to the move before on the table
		 * 		
		 * 		if ((same type as previous)*):
		 * 
		 * 			check new hand beats table hand or not
		 * 			if beats
		 * 				valid move -> Remove the cards and add to the table
		 * 
		 * 			else if not beats
		 * 				not valid move -> Ask the user to in-take again -> while loop 
		 * 		
		 * 		else if (the previous three are all null)
		 * 			Similar as first move
		 * 
		 * 		else if not same type
		 * 			not valid move
		 * 
		 * 		else if move is empty/returns null
		 * 			if the three previous hands on table are not all null
		 * 				valid -> add null to the table
		 * 			else 
		 * 				not valid -> as u need to give ordering(U are the boss)
		 * 
		 * 		*(same type) means if number of null before <= 2 
		 * 
		 * */
		
		
		
	
	@Override
	/**
	 * a method for checking if the game ends
	 * @return game end or not
	 */
	public boolean endOfGame() {
		for (int i = 0; i < 4; i++) {
			if (playerList.get(i).getCardsInHand().size()==0) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * a method for starting a Big Two card game. I
	 * @param args
	 */
	public static void main(String[] args) {
		BigTwo b = new BigTwo();
//		BigTwoDeck d = new BigTwoDeck();
		
		b.deck.shuffle();
		b.start(b.deck);

	}
	/**
	 * a method for returning a valid hand from the specified list of cards of the player. 
	 * @param player
	 * @param cards
	 * @return
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		Hand h0 = null;
		if (cards == null) {
			h0 = null;
		}
		else if (cards.size()==1) {
			h0 = new Single(player,cards);
			
		}
		else if (cards.size()==2) {
			h0 = new Pair(player,cards);
			
		}
		else if (cards.size()==3) {
			h0 = new Triple(player,cards);
			
		}
		else if (cards.size()==4) {
			h0 = new Quad(player,cards);
			
		}
		else if (cards.size()==5) {
			Hand h1 = new Straight(player,cards);
			Hand h2 = new Flush(player,cards);
			Hand h3 = new FullHouse(player,cards);
			Hand h4 = new StraightFlush(player,cards);
			
			if (h1.isValid()) {
				h0 = h1;
			}
			else if (h2.isValid()) {
				h0 = h2;
			}
			else if (h3.isValid()) {
				h0 = h3;
			}
			else if (h4.isValid()) {
				h0 = h4;
			}
		}
		return h0;
		
	}
	


}
