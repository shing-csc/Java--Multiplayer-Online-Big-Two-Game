/**
 *  This hand consists of five cards with consecutive ranks
 * @author danie
 *
 */
public class Straight extends Hand{

	
	
	/**
	 * Setting up a Hand with level 1
	 * @param player
	 * @param cards
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player,cards);
		this.level = 1;
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
			
			if ((card0.rank + 1 == card1.rank) && (card1.rank + 1 == card2.rank)&& ((card2.rank + 1)%13 == card3.rank)&& ((card3.rank + 1)%13 == card4.rank)) {
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
		return "Straight";
	}
}
