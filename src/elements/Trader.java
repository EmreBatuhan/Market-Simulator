package elements;

/**Traders can make Orders in market and has wallet which stores dollars and PQoins.
 * @author batuhanGoc
 */
public class Trader{
	private int id;
	private Wallet wallet;
	public static int numberOfUsers = 0;
	
	/**
	 * Constructor of Trader. Takes parameters for their wallet.IDs are assigned according
	 * @param dollars starting dollar amount of trader 
	 * @param coins starting PQoin amount of trader
	 */
	public Trader(double dollars, double coins) {
		this.wallet = new Wallet(dollars,coins);
		id = numberOfUsers;
		numberOfUsers++;
	}
	
	/**
	 * This method represents a selling action of a trader. 
	 * If the trader has enough coins action is sent to market as a SellingOrder
	 * @param amount how many PQoin seller wants to sell
	 * @param price how much seller wants to gain per PQoin
	 * @param market the market which order is sent to
	 * @return 1 if true , 0 if false
	 */
	public int sell(double amount, double price, Market market) {
		if (amount <= wallet.getCoins()) {
			SellingOrder sellingOrder = new SellingOrder(id,amount,price);
			market.giveSellOrder(sellingOrder);
			wallet.addBlockedCoins(amount);
			wallet.addCoins(amount*-1);			
			
			return 1;
		}
		market.addFailedQuery();
		return 0;
	}
	
	/**
	 * This method represents a buying action of a trader. 
	 * If the trader has enough coins action is sent to market as a BuyingOrder
	 * @param amount how many PQoin buyer wants to buy
	 * @param price how much buyer wants to pay per PQoin
	 * @param market the market which order is sent to
	 * @return 1 if true , 0 if false
	 */
	public int buy(double amount, double price, Market market) {
		if (amount*price <= wallet.getDollars()) {
			BuyingOrder buyingOrder = new BuyingOrder(id,amount,price);
			market.giveBuyOrder(buyingOrder);
			wallet.addBlockedDollars(amount*price);
			wallet.addDollars(amount*price*-1);
			
			return 1;
		}
		market.addFailedQuery();
		return 0;
	}
	

	/** This method is very similar to sell method above however this method takes price from getSellMarketPrice() method
	 * 
	 * @param amount how many PQoin seller wants to sell
	 * @param market the market which order is sent to
	 */
	public void sellMarketPrice(double amount,Market market) {
		this.sell(amount, market.getSellMarketPrice(), market);
	}
	
	/**This method is very similar to buy method above however this method takes price from getBuyMarketPrice() method
	 * 
	 * @param amount how many PQoin buyer wants to buy
	 * @param market the market which order is sent to
	 */
	public void buyMarketPrice(double amount,Market market) {
		this.buy(amount,market.getBuyMarketPrice(),market);	
	}
	
	/** get method of wallet
	 * @return trader's wallet
	 */
	public Wallet getWallet() {
		return wallet;
	}
	
	/**get method of id
	 * @return the id of the trader
	 */
	public int getID() {
		return id;
	}
	
}