package elements;

/**
 * BuyingOrder class represents an offer of a buyer.
 * @author batuhanGoc
 */
public class BuyingOrder extends Order implements Comparable<BuyingOrder>{
	
	/**
	 * Same constructor extended from its parent
	 * @param traderID ID of the buyer
	 * @param amount how many PQoin buyer wants
	 * @param price how much buyer wants to pay per PQoin
	 */
	public BuyingOrder(int traderID, double amount, double price) {
		super(traderID,amount,price);
	}

	/** In order to sort BuyingOrder elements in priority queues , a compareTo method is written.
	 */
	public int compareTo(BuyingOrder other) {
		int priceDiff = Double.compare(other.price,price);
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