/**
 *  This hand consists of five cards, with four having the same rank. 
 * @author danie
 *
 */
public class Quad extends Hand{
	/**
	 * Setting up a Hand
	 * @param player
	 * @param cards
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	/**
	 * a method for checking if this is a valid hand.
	 * @return valid or not in boolean
	 */
	public boolean isValid() {
		
		
		if (this.size() == 4) {
			BigTwoCard card0 = (BigTwoCard)this.getCard(0);
			BigTwoCard card1 = (BigTwoCard)this.getCard(1);
			BigTwoCard card2 = (BigTwoCard)this.getCard(2);
			BigTwoCard card3 = (BigTwoCard)this.getCard(3);
			
			if ((card0.rank == card1.rank) && (card1.rank == card2.rank)&& (card2.rank == card3.rank)) {
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
		return "Quad";
	}
}
