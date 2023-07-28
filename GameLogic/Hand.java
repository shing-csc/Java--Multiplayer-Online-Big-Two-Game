/**
 * The Hand class is a subclass of the CardList class and is used to model a hand of cards
 * @author danie
 *
 */

public abstract class Hand extends CardList{
	
	/**
	 * a constructor for building a hand with the specified player and list of cards
	 * @param player
	 * @param cards
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		
		this.player = player;
		
		for (int i = 0; i < cards.size(); i++) {
			this.addCard(cards.getCard(i));
		}
		this.sort();
	}
	private final CardGamePlayer player;
	//An integer to distinguish different types of hands with size of 5 
	public int level;
	
	/**
	 * a method for retrieving the player of this hand.
	 * @return
	 */
	public CardGamePlayer getPlayer() {
		return this.player;
		
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 * @return
	 */
	public Card getTopCard() {
	 	//The below code have problems
		if (!this.isEmpty()) {
			int temp = this.size() - 1;
			this.sort();
			return (this.getCard(temp));
		}
		else {
			return null;
		}
	}
	/**
	 * a method for checking if this hand beats a specified hand.
	 * @param hand
	 * @return
	 */
	public boolean beats(Hand hand) {
		
		if (hand == null || !hand.isValid() || !this.isValid() || this.size() != hand.size()) {
			return false;
		}
		else {
			if ((this.getType()== hand.getType()) && (this.getTopCard().compareTo(hand.getTopCard()) == 1)) {
				return true;
			}
			else if (this.size() == 5 &&hand.size() == 5){
				if (this.level > hand.level) {
					return true;
				}
				else {	
					return false;	
				}
			}
			else {
				return false;
			}
		}
		
	}
	/**
	 * a method for checking if this is a valid hand
	 * @return
	 */
	public abstract boolean isValid(); 
	/**
	 * a method for returning a string specifying the type of this hand.
	 * @return
	 */
	public abstract String getType();
	
}
