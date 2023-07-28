/**
 * This hand consists of three cards with the same rank.
 * @author danie
 *
 */
public class Triple extends Hand{

	/**
	 * Setting up a Hand Triple
	 * @param player
	 * @param cards
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player,cards);
	}
	/**
	 * a method for checking if this is a valid hand.
	 * @return valid or not in boolean
	 */
	public boolean isValid() {
		if (this.size() == 3) {
			BigTwoCard card0 = (BigTwoCard)this.getCard(0);
			BigTwoCard card1 = (BigTwoCard)this.getCard(1);
			BigTwoCard card2 = (BigTwoCard)this.getCard(2);
			
			if ((card0.rank == card1.rank) && (card1.rank == card2.rank)) {
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
	 * @return the type of the hand Triple
	 */
	public String getType() {
		return "Triple";
	}
	

}
