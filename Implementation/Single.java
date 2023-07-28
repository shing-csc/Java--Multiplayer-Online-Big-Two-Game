/**
 * This hand consists of only one single card. 
 * @author danie
 *
 */
public class Single extends Hand{
	
	/**
	 * Setting up a Hand Single
	 * @param player
	 * @param cards
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	/**
	 * a method for checking if this is a valid hand.
	 * @return valid or not in boolean
	 */
	public boolean isValid() {
		if (this.size() == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * @return the type of the hand Single
	 */
	public String getType() {
		return "Single";
	}
	
}
