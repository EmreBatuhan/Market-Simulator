package elements;

public class Wallet{
	
	private double dollars;
	private double coins;
	private double blockedDollars;
	private double blockedCoins;
	
	/** The class which stores trader's dollars and PQoins
	 * 
	 * @param dollars starting dollar of the Wallet
	 * @param coins starting coins of the Wallet
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
		this.blockedDollars = 0;
		this.blockedCoins = 0;
	}
	/** get method of dollars
	 * @return quantity of dollars
	 */
	public double getDollars() {
		return dollars;
	}
	
	/** get method of coins
	 * @return quantity of PQoins
	 */
	public double getCoins() {
		return coins;
	}

	/** get method of blockedDollars
	 * @return quantity of blocked dollars
	 */
	public double getBlockedDollars() {
		return blockedDollars;
	}

	/** get method of blockedCoins
	 * @return quantity of blocked coins
	 */
	public double getBlockedCoins() {
		return blockedCoins;
	}

	/** add method of dollars
	 * @return dollars + amount
	 */
	public void addDollars(double amount) {
		dollars += amount;
	}

	/** add method of coins
	 * @return coins + amount
	 */
	public void addCoins(double amount) {
		coins += amount;
	}

	/** add method of blockedDollars
	 * @return blockedDollars + amount
	 */
	public void addBlockedDollars(double amount) {
		blockedDollars += amount;
	}

	/** add method of blockedCoins
	 * @return blockedCoins + amount
	 */
	public void addBlockedCoins(double amount) {
		blockedCoins += amount;
	}
}