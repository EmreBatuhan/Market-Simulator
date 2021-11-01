package executable;

import java.util.*;
import elements.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import java.util.Scanner;

public class Main{
	
	/** myRandom is used in query 777 to randomly give PQoins to traders
	 */
	public static Random myRandom;
	
	/**
	 * This part reads inputs and turns them into outputs.
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String args[]) throws FileNotFoundException{	
				
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
        
		int seed = in.nextInt();
	    myRandom = new Random(seed);
		
		int B = in.nextInt(); // B is market fee
		int C = in.nextInt(); // C is the number of users
		int D = in.nextInt(); // D is the number of queries
		
		Market market = new Market(B);
		ArrayList<Trader> traders = new ArrayList<Trader>();
		
		for(int i=0 ; i<C ; i++) {
			
			double dollar_amount = in.nextDouble();
			double PQoin_amount = in.nextDouble();
			Trader newtrader = new Trader(dollar_amount,PQoin_amount);
			traders.add(newtrader);
			
		}
		
		for(int i=0 ; i<D ; i++) {
			
			System.out.println(i+ " "+traders.get(7).getWallet().getDollars()+" , "+traders.get(7).getWallet().getBlockedDollars());
			
			int command = in.nextInt();
			    		
			if(command == 10) {
			    	
			    int trader_id = in.nextInt();
			    double price = in.nextDouble();
			   	double amount = in.nextDouble();
			    	
			   	traders.get(trader_id).buy(amount, price, market);	
			}
			    
			if(command == 11) {
			    	
			   	int trader_id = in.nextInt();
			   	double amount = in.nextDouble();
			   	
			   	traders.get(trader_id).buyMarketPrice(amount, market);   	
			}
			    
	        if(command == 20) {
			    	
		    	int trader_id = in.nextInt();
		    	double price = in.nextDouble();
		    	double amount = in.nextDouble();
			    	
		    	traders.get(trader_id).sell(amount, price, market);
			}
			    
            if(command == 21) {
			    	
		    	int trader_id = in.nextInt();
		    	double amount = in.nextDouble();
		    
			   	traders.get(trader_id).sellMarketPrice(amount, market);
			   	
            }
                
            if(command == 3) {
                	
            	int trader_id = in.nextInt();
		    	double amount = in.nextDouble();
		    	
		    	traders.get(trader_id).getWallet().addDollars(amount);
            }
                
            if(command == 4) {
            	
            	int trader_id = in.nextInt();
		    	double amount = in.nextDouble();
		    	
		    	if(traders.get(trader_id).getWallet().getDollars() >= amount) {
		    		traders.get(trader_id).getWallet().addDollars(-1*amount);
		    	}
		    	else {
		    		market.addFailedQuery();
		    	}
            }
		    	
		    if(command == 5) {
	            	
	            int trader_id = in.nextInt();
	            
	            out.print("Trader "+trader_id+": ");
        		out.print(String.format("%.5f", traders.get(trader_id).getWallet().getDollars() + traders.get(trader_id).getWallet().getBlockedDollars())+"$ ");
	            out.println(String.format("%.5f",traders.get(trader_id).getWallet().getCoins() + traders.get(trader_id).getWallet().getBlockedCoins())+"PQ");
		    }
		    
		    if(command == 777) {
		    	
		        for	(int j = 0;j < traders.size();j++) {		    	   
		        	double randomDouble = myRandom.nextDouble()*10;
		            traders.get(j).getWallet().addCoins(randomDouble);
		            
		        }
		    }
		    
		    if(command == 666) {
		    	
		    	double price = in.nextDouble();
		    	
		    	market.makeOpenMarketOperation(price, traders);
		    }
		    
		    if(command == 500) {
		    	
		        market.printMarketSize(out);
		    }
		    
		    if(command == 501) {
		    	
		    	out.println("Number of successful transactions: " + market.getTransactions().size());
		    }
		    
		    if(command == 502) {
		    	out.println("Number of invalid queries: " + market.getFailedQuery());
		    }
		    
            if(command == 505) {
		    	
            	out.print("Current prices: ");
            	out.print(String.format("%.5f",market.getSellMarketPrice())+" ");
            	out.print(String.format("%.5f",market.getBuyMarketPrice())+" ");
            	out.println(String.format("%.5f",((market.getBuyMarketPrice()+market.getSellMarketPrice())*0.5)));
            		
		    }
            
            if(command == 555) {
            	
            	for(Trader trader: traders) {
            		out.print("Trader "+trader.getID()+": ");
            		out.print(String.format("%.5f", trader.getWallet().getDollars() + trader.getWallet().getBlockedDollars())+"$ ");
    	            out.println(String.format("%.5f",trader.getWallet().getCoins() + trader.getWallet().getBlockedCoins())+"PQ");
            	}
            }
            market.checkTransactions(traders);
		}
	}
}

