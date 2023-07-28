/**
 * This hand consists of two cards with the same rank.
 * @author danie
 *
 */
public class Pair extends Hand{
	
	/**
	 * Setting up a Hand Pair
	 * @param player
	 * @param cards
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	/**
	 * a method for checking if this is a valid hand.
	 * @return valid or not in boolean
	 */
	public boolean isValid() {
		if (this.size() == 2) {
			Card card0 = this.getCard(0);
			Card card1 = this.getCard(1);
			
			if (card0.rank == card1.rank) {
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
	 * @return the type of the hand Pair
	 */
	public String getType() {
		return "Pair";
	}
}
