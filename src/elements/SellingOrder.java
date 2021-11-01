package elements;

/**
 * SellingOrder class represents an offer of a seller.
 * @author batuhanGoc
 */
public class SellingOrder extends Order implements Comparable<SellingOrder>{
	
	/**
	 * Same constructor extended from its parent
	 * @param traderID ID of the seller
	 * @param amount how many PQoin seller wants to sell
	 * @param price how much seller wants to gain per PQoin
	 */
	public SellingOrder(int traderID, double amount, double price) {
		super(traderID,amount,price);
	}
	
	/** In order to sort SellingOrder elements in priority queues , a compareTo method is written.
	 */
	public int compareTo(SellingOrder other) {
		int priceDiff = Double.compare(price,other.price);
        if (priceDiff != 0) {
        	return priceDiff;
        }
		int amountDiff = Double.compare(other.amount,amount);
		if (amountDiff != 0) {
			return amountDiff;
		}
		return traderID - other.traderID ; 
	}
	
	
}