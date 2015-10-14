package tests;

import static org.junit.Assert.*;
import gameFiles.Dice;
import gameFiles.DiceValue;
import gameFiles.Game;
import gameFiles.Player;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BettingLimitTest {

	Dice d1;
    Dice d2;
    Dice d3;
    
	Player player;
    Game game;
    List<DiceValue> cdv;
    int bet;
    
	@Before
	public void setUp() throws Exception {
		d1 = new Dice();
	    d2 = new Dice();
	    d3 = new Dice();
	    //ensure unique dice rolls
//		while ((d1.getValue() == d2.getValue()) || (d1.getValue() == d3.getValue()) || (d2.getValue() == d3.getValue())) {
//			d1 = new Dice(); 
//		    d2 = new Dice();
//		    d3 = new Dice();
//		}
		player = new Player("Fred", 20);
		player.setLimit(0);
		game = new Game(d1, d2, d3);
		cdv = game.getDiceValues();
		bet = 5;
	}

	@After
	public void tearDown() throws Exception {
		d1 = null;
		d2 = null;
		d3 = null;
		player = null;
		game = null;
		cdv = null;
	}

	@Test
	public void testBettingLimit() {
		int turn = 0;
        while (player.balanceExceedsLimitBy(bet) && player.getBalance() < 200 && turn < 100)
        {
            turn++;                    
        	// make random pick so that it is possible to lose
            DiceValue pick = DiceValue.getRandom();
        	
        	System.out.printf("Turn %d: %s bet %d on %s\n",
        			turn, player.getName(), bet, pick); 
        	
        	
        	int winnings = game.playRound(player, pick, bet);
        	
        	
        	
            cdv = game.getDiceValues();
            
            System.out.printf("Rolled %s, %s, %s\n",
            		cdv.get(0), cdv.get(1), cdv.get(2));
            
            if (winnings > 0) {
                System.out.printf("%s won %d, balance now %d\n\n",
                		player.getName(), winnings, player.getBalance());
            	
            }      
            else {
                System.out.printf("%s lost, balance now %d\n\n",
                		player.getName(), player.getBalance());
            	///loseCount++;
            }
            
        } 
        //assert that the player cannot reach the limit
        assertTrue(player.getBalance() != player.getLimit());
        
        System.out.print(String.format("%d turns later.\n: ", turn ));
        System.out.println(String.format("%s now has balance %d\n", player.getName(), player.getBalance()));
	}

}
