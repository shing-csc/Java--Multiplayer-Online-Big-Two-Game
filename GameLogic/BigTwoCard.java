/**
 * The BigTwoCard class is a subclass of the Card class and is used to model a card used in a 
Big Two card game.
 * @author danie
 *
 */
public class BigTwoCard extends Card{
	public int this_t_rank = 0;
	public int card_t_rank = 0;
	/**
	 * a constructor for building a card with the specified 
suit and rank. suit is an integer between 0 and 3, and rank is an integer between 0 and 
12.
	 * @param suit
	 * @param rank
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
		
	}
	
	
	/**
	 * a method for comparing the order of this card with the 
specified card. Returns a negative integer, zero, or a positive integer when this card is 
less than, equal to, or greater than the specified card.
	 */
	public int compareTo(Card card){
		this_t_rank = 0;
		
		if ((this.getRank() == 0) || (this.getRank() == 1)) {
			this_t_rank = this.getRank() + 13;
			
			if ((card.getRank() == 0) || (card.getRank() == 1)) {
				card_t_rank = card.getRank() + 13;
				if (this_t_rank > card_t_rank) {
					return 1;
				}
				else if (this_t_rank < card_t_rank) {
					return -1;
				}
				else{
					if (this.getSuit()>card.getSuit()) {
						return 1;
					}
					else if (this.getSuit() < card.getSuit()) {
						return -1;
					}
					else{
						return 0;
					}
				}
				
			}
			else {
				if (this_t_rank>card.getRank()) {
					return 1;
				}
				else if (this_t_rank < card.getRank()) {
					return -1;
				}
				else{
					if (this.getSuit()>card.getSuit()) {
						return 1;
					}
					else if (this.getSuit() < card.getSuit()) {
						return -1;
					}
					else{
						return 0;
					}
				}
				
			}
			
		}
		
		else if ((card.getRank() == 0) || (card.getRank() == 1)) {
			card_t_rank = card.getRank() + 13;
			
			if ((this.getRank() == 0) || (this.getRank() == 1)) {
				this_t_rank = this.getRank() + 13;
				if (this_t_rank > card_t_rank) {
					return 1;
				}
				else if (this_t_rank < card_t_rank) {
					return -1;
				}
				else{
					if (this.getSuit()>card.getSuit()) {
						return 1;
					}
					else if (this.getSuit() < card.getSuit()) {
						return -1;
					}
					else{
						return 0;
					}
				}
			}
			else {
				if (this.getRank() > card_t_rank) {
					return 1;
				}
				else if (this.getRank() < card_t_rank) {
					return -1;
				}
				else{
					if (this.getSuit()>card.getSuit()) {
						return 1;
					}
					else if (this.getSuit() < card.getSuit()) {
						return -1;
					}
					else{
						return 0;
					}
				}
			}
			
		}
		
		else {
			if (this.getRank()>card.getRank()) {
				return 1;
			}
			else if (this.getRank() < card.getRank()) {
				return -1;
			}
			else{
				if (this.getSuit()>card.getSuit()) {
					return 1;
				}
				else if (this.getSuit() < card.getSuit()) {
					return -1;
				}
				else{
					return 0;
				}
			}
		}
	}
	
	
	
}
