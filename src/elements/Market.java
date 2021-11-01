
package elements;

import java.io.PrintStream;
import java.util.*;
import elements.*;

/**
 * Market class is the place where transactions happen and open Market operation is done.
 * @author batuhanGoc
 */
public class Market{
	
	/** Priority queue which active sellingOrders are kept
	 */
	private PriorityQueue<SellingOrder> sellingOrders = new PriorityQueue<SellingOrder>();
	/** Priority queue which active buyingOrders are kept
	 */
	private PriorityQueue<BuyingOrder> buyingOrders = new PriorityQueue<BuyingOrder>();
	/** ArrayList which past successful transactions are kept 
	 */
	private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	/** Keeps track of the failed queries that happened in 
	 */
	private int failedQuery = 0; 
	/** fee/1000 is taken as a fee from every transaction happened in this market
	 */
	private int fee;
	
	/**
	 * Market constructor with one parameter: int fee 
	 * @param fee IS initialized as the market's fee
	 */
    public Market(int fee) {
		
		this.fee = fee;
    }
	
    /** Adds the given SellingOrder to Market's sellingOrders field
     * @param order the SellingOrder that will be added to sellingOrders
     */
	public void giveSellOrder(SellingOrder order) {
		sellingOrders.add(order);
	}
	
	/** Adds the given BuyingOrder to Market's buyingOrders field
     * @param order the BuyingOrder that will be added to buyingOrders
     */
	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.add(order);
	}
	
	/**This method's main purpose is to change the overall price of PQoin in market.Traders variable is given as mentioned before.
	 *It is done via trader#0 -The System-. Trader#0 gives buying or selling Orders until the given price can not make any transactions.  
	 * @param price the price which trader#0 tries to achieve
	 * @param traders represents the traders that are in the market. Used in reaching trader#0 and calling checkTransactions
	 */
	public void makeOpenMarketOperation(double price , ArrayList<Trader> traders) {
		//Checking if somebody has offered to sell at a lower price than given price
		if(sellingOrders.isEmpty() == false && sellingOrders.peek().getPrice() <= price) {
			//Until there is nobody that offers to sell at a lower price than given price trader#0 buys their PQoins
			while(sellingOrders.isEmpty() == false && sellingOrders.peek().getPrice() <= price) {
				
				
				//Giving the trader#0 wallet necessary money 
				traders.get(0).getWallet().addDollars(sellingOrders.peek().getAmount() * sellingOrders.peek().getPrice());
				traders.get(0).buy(sellingOrders.peek().getAmount(),sellingOrders.peek().getPrice(),this);
				
				checkTransactions(traders);
			}
		}
		//Checking if somebody has offered to buy at a higher price than given price
		if(buyingOrders.isEmpty() == false && buyingOrders.peek().getPrice() >= price) {
			//Until there is nobody that offers to buy at a higher price than given price trader#0 sells them PQoins
			while(buyingOrders.isEmpty() == false && buyingOrders.peek().getPrice() >= price) {
				
				//Giving the trader#0 wallet necessary PQoins
				traders.get(0).getWallet().addCoins(buyingOrders.peek().getAmount());
				traders.get(0).sell(buyingOrders.peek().getAmount(), buyingOrders.peek().getPrice(), this);
				
				checkTransactions(traders);				
			}
		}
	}
	
	/**This method compares the lowest selling offer and highest buying offer.  
	 * If highest buying offer is higher than lowest selling offer this method makes transactions between those offers.
	 * @param traders Since orders doesn't store any traders but traderIDs , traders ArrayList is necessary to reach specific traders from IDs.
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		
		//Checking sizes since after this we will assume sizes are not 0
		if (sellingOrders.size() == 0 || buyingOrders.size() == 0) {
	    	return;
	    }
		
		// while highest buying offer is higher than lowest selling offer. 
		while(sellingOrders.peek().getPrice() <= buyingOrders.peek().getPrice()) {
			
			//If code reaches here we know a transaction will occur
			
			BuyingOrder buyingOrder = buyingOrders.poll();
			SellingOrder sellingOrder = sellingOrders.poll();
		    double price = sellingOrder.getPrice();
		    double buyerPrice = buyingOrder.getPrice();
		    Wallet buyerWallet = traders.get(buyingOrder.getTraderID()).getWallet();
		    Wallet sellerWallet = traders.get(sellingOrder.getTraderID()).getWallet();
		    
		    //Storing the transaction
		    Transaction newtransaction = new Transaction(sellingOrder,buyingOrder);
		    transactions.add(newtransaction);
		    
		    //Checking which order has higher amount since the order with higher amount should return to its priority queue
		    if(sellingOrder.getAmount() < buyingOrder.getAmount()) {
		        
		    	buyerWallet.addCoins(sellingOrder.getAmount());
		    	buyerWallet.addBlockedDollars(-1*buyerPrice*sellingOrder.getAmount());
		    	sellerWallet.addDollars(price*sellingOrder.getAmount()*(1000-fee)*0.001);
		    	sellerWallet.addBlockedCoins(-1*sellingOrder.getAmount());
		    	
		    	//returning some dollars to buyer since buyer blocked his money according to buyerPrice not price
		    	buyerWallet.addDollars((buyerPrice - price)*sellingOrder.getAmount());
		    	
		    	//buyingOrder returns to buyingOrders since it's amount is not reached yet
		    	buyingOrder.addAmount(-1*sellingOrder.getAmount());
		    	buyingOrders.add(buyingOrder);
		    }
		    else if(sellingOrder.getAmount() > buyingOrder.getAmount()) {
		    	
		    	buyerWallet.addCoins(buyingOrder.getAmount());
		    	buyerWallet.addBlockedDollars(-1*buyerPrice*buyingOrder.getAmount());
		    	sellerWallet.addDollars(price*buyingOrder.getAmount()*(1000-fee)*0.001);
		    	sellerWallet.addBlockedCoins(-1*buyingOrder.getAmount());
		    	
		    	buyerWallet.addDollars((buyerPrice - price)*buyingOrder.getAmount());
		    	
		    	sellingOrder.addAmount(-1*buyingOrder.getAmount());
		    	sellingOrders.add(sellingOrder);
		    }
		    else {
		    	
		    	buyerWallet.addCoins(buyingOrder.getAmount());
		    	buyerWallet.addBlockedDollars(-1*buyerPrice*buyingOrder.getAmount());
		    	
		    	buyerWallet.addDollars((buyerPrice - price)*buyingOrder.getAmount());
		    	
		    	sellerWallet.addDollars(price*buyingOrder.getAmount()*(1000-fee)*0.001);
		    	sellerWallet.addBlockedCoins(-1*buyingOrder.getAmount());
		    }
		    //After the operations sellingOrders' or buyingOrders' size might become zero 
		    if (sellingOrders.size() == 0 || buyingOrders.size() == 0) {
		    	break;
		    }		    		    
		}				
	}
		
	/**This method calculates the total Dollars and PQoins in market and prints them 
	 * @param out Since printing is done to output file a PrintStream variable is needed
	 */
	public void printMarketSize(PrintStream out) {
	
	    double totalDollarInBuying = 0.0;
	    double totalPQoinInSelling = 0.0;
		
		for(SellingOrder sellingOrder : sellingOrders) {
			totalPQoinInSelling += sellingOrder.getAmount();
	    }
		
		for(BuyingOrder buyingOrder :buyingOrders) {
			totalDollarInBuying += buyingOrder.getAmount()*buyingOrder.getPrice();
	    }
		
		out.print("Current market size: ");
		out.print(String.format("%.5f" , totalDollarInBuying )+ " ");
		out.println(String.format("%.5f" , totalPQoinInSelling ));
	}
	
	/** Get method of transactions
	 * @return transactions
	 */
	public ArrayList<Transaction> getTransactions(){
		return transactions;
	}
	
	/** This method adds one to failed queries
	 */
	public void addFailedQuery() {
		failedQuery++;
		
	/** Get method of failedQuery 
	 * @return total number of failed queries
	 */
	}
	public int getFailedQuery() {
		return failedQuery;
	}
	
	/** This method calculates the market price for selling
	 * Basically this method returns the highest price buyers are paying if there are any buyers.
	 * @return highest price buyers are paying. If there are no buyers , returns 0.
	 */
	public double getSellMarketPrice() {
		if(buyingOrders.isEmpty()) {
		    return 0.0;
		}
		return buyingOrders.peek().getPrice();
	}
	
	/** This method calculates the market price for buying
	 * Basically this method returns the lowest price sellers are willing to accept if there are any sellers.
	 * @return lowest price sellers accept. If there are no sellers , returns 0.
	 */
	public double getBuyMarketPrice() {
		if(sellingOrders.isEmpty()) {
			return 0.0;
			}
		return sellingOrders.peek().getPrice();
	}
}

