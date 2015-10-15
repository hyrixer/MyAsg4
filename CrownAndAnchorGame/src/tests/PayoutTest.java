package tests;

import static org.junit.Assert.*;
import gameFiles.Dice;
import gameFiles.DiceValue;
import gameFiles.Game;
import gameFiles.Player;

import java.util.List;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class PayoutTest {
	
	
	Dice d1;
    Dice d2;
    Dice d3;
    
	Player player;
    Game game;
    List<DiceValue> cdv;
    int bet;
    
	
	@Before
	public void setUp() {
		d1 = new Dice();
	    d2 = new Dice();
	    d3 = new Dice();
	    //ensure unique dice rolls
		while ((d1.getValue() == d2.getValue()) || (d1.getValue() == d3.getValue()) || (d2.getValue() == d3.getValue())) {
			d1 = new Dice(); 
		    d2 = new Dice();
		    d3 = new Dice();
		}
		player = new Player("Fred", 100);
		player.setLimit(0);
		game = new Game(d1, d2, d3);
		cdv = game.getDiceValues();
		bet = 15;
	}
	
	@After
	public void tearDown() {
		d1 = null;
		d2 = null;
		d3 = null;
		player = null;
		game = null;
		cdv = null;
	}
	
	@Test
	public void testPayout() {
		int turn = 0;
        while (player.balanceExceedsLimitBy(bet) && player.getBalance() < 200 && turn < 100)
        {
            turn++;                    
        	// make pick equal to only one dice value
            DiceValue pick = d1.getValue();
        	//assertEquals(d1.getValue(), pick);
        	
        	System.out.printf("Turn %d: %s bet %d on %s\n",
        			turn, player.getName(), bet, pick); 
        	
        	int previousBalance = player.getBalance();
        	int winnings = game.playRound(player, pick, bet);
        	
        	//balance will not increase if pick matches only one dice value
        	//assertFalse(player.getBalance() == previousBalance + winnings );
        	//assertTrue(player.getBalance() == previousBalance);
        	
            cdv = game.getDiceValues();
            
            System.out.printf("Rolled %s, %s, %s\n",
            		cdv.get(0), cdv.get(1), cdv.get(2));
            
            if (winnings > 0) {
                System.out.printf("%s won %d, balance now %d\n\n",
                		player.getName(), winnings, player.getBalance());
            	
            }        
        } 
	}
	

}
