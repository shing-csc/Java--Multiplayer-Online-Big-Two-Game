/**
 * This hand consists of five cards with the same suit.
 * @author danie
 *
 */
public class Flush extends Hand{
	
	
	/**
	 * Setting up a Hand with level 2
	 * @param player
	 * @param cards
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player,cards);
		this.level = 2;
	}
	/**
	 * a method for checking if this is a valid hand.
	 * @return valid or not in boolean
	 */
	public boolean isValid() {
		
		if (this.size() == 5) {
			BigTwoCard card0 = (BigTwoCard)this.getCard(0);
			BigTwoCard card1 = (BigTwoCard)this.getCard(1);
			BigTwoCard card2 = (BigTwoCard)this.getCard(2);
			BigTwoCard card3 = (BigTwoCard)this.getCard(3);
			BigTwoCard card4 = (BigTwoCard)this.getCard(4);
			
			if ((card0.suit == card1.suit) && (card1.suit == card2.suit)&& (card2.suit == card3.suit)&& (card3.suit == card4.suit)) {
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
	
	/**
	 * @return the type of the hand
	 */
	public String getType() {
		return "Flush";
	}
}
