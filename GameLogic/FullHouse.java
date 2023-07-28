/**
 * This hand consists of five cards, with two having the same rank and three 
having another same rank.
 * @author danie
 *
 */
public class FullHouse extends Hand{

	/**
	 * Setting up a Hand with level 3
	 * @param player
	 * @param cards
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player,cards);
		this.level = 3;
	}
	
	/**
	 * Getting the top card of FullHouse 
	 */
	public Card getTopCard() {
		BigTwoCard card0 = (BigTwoCard)this.getCard(0);
		BigTwoCard card1 = (BigTwoCard)this.getCard(1);
		BigTwoCard card2 = (BigTwoCard)this.getCard(2);
		BigTwoCard card3 = (BigTwoCard)this.getCard(3);
		BigTwoCard card4 = (BigTwoCard)this.getCard(4);
		
		if ((card0.rank == card1.rank) && (card1.rank == card2.rank)) {
			return card2;
		}
		else {
			return card4;
		}
		
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
			
			if ((card0.rank == card1.rank) && (card1.rank == card2.rank)) {
				if (card3.rank == card4.rank) {
					return true;
				}
				else {
					return false;
				}
			}
			else if ((card2.rank == card3.rank) && (card3.rank == card4.rank)) {
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
		else {
			return false;
		}

		
	}
	
	/**
	 * @return the type of the hand
	 */
	public String getType() {
		return "FullHouse";
	}
}
