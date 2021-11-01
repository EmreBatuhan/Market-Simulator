package elements;

/**
 * Order class is the parent class of SellingOrder and BuyingOrder.
 * @author batuhanGoc
 */
public abstract class Order {
	
	double amount;
	double price;
	int traderID;
	
	/**
	 * Constructor with 3 variables. This constructor is used in both child Classes.
	 * @param traderID ID of the trader
	 * @param amount how many PQoin he wants to trade 
	 * @param price how much trader values a PQoin
	 */
	public Order(int traderID, double amount, double price) {
		
		this.traderID = traderID;
		this.amount = amount;
		this.price = price;
	}
	
	/** get method of amount
	 * @return amount of the PQoins in offer
	 */
	public double getAmount() {
		return amount;
	}
	
	/** get method of price
	 * @return price of PQoins
	 */
	public double getPrice() {
		return price;
	}
	
	/** get method of traderID
	 * @return ID of the trader
	 */
	public int getTraderID() {
		return traderID;
	}
	
	/** this method changes the amount
	 * @param amount how much the field amount will be changed
	 */
    public void addAmount(double amount) {
    	this.amount += amount;
    }
}

