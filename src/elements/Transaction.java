package elements;

/**Represents the successful trades between traders
 * 
 * @author batuhanGoc
 */
public class Transaction{
	
	private SellingOrder sellingOrder;
	private BuyingOrder buyingOrder; // Actually description says sellingOrder but it is changed to buyingOrder. 
	
	/**
	 * Constructor of Transaction. 
	 * @param sellingOrder the order which the seller made
	 * @param buyingOrder the order which the buyer made
	 */
	public Transaction(SellingOrder sellingOrder,BuyingOrder buyingOrder) {
		this.sellingOrder = sellingOrder;
		this.buyingOrder = buyingOrder;
	}
	
	
	
}