package tests;

import static org.junit.Assert.*;

import java.util.List;

import gameFiles.Dice;
import gameFiles.DiceValue;
import gameFiles.Game;
import gameFiles.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RatioTest {

	Dice d1;
    Dice d2;
    Dice d3;
    
	Player player;
    Game game;
    List<DiceValue> cdv;
    int bet;
    int winCount;
    int loseCount;
	
	@Before
	public void setUp() throws Exception {
		d1 = new Dice();
	    d2 = new Dice();
	    d3 = new Dice();
		player = new Player("Fred", 100);
		player.setLimit(0);
		game = new Game(d1, d2, d3);
		cdv = game.getDiceValues();
		bet = 5;
		winCount = 0;
		loseCount = 0;
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
	public void test() {
		
		int totalWins = 0;
		int totalLose = 0;
		
		for (int i = 0; i < 100; i++)
        {
			
        	String name = "Fred";
        	int balance = 100;
        	int limit = 0;
            player = new Player(name, balance);
            player.setLimit(limit);
            bet = 5;

            System.out.println(String.format("Start Game %d: ", i));
            System.out.println(String.format("%s starts with balance %d, limit %d", 
            		player.getName(), player.getBalance(), player.getLimit()));

            int turn = 0;
            while (player.balanceExceedsLimitBy(bet) && player.getBalance() < 200)
            {
                turn++;                    
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
                	winCount++; 
                }
                else {
                    System.out.printf("%s lost, balance now %d\n\n",
                    		player.getName(), player.getBalance());
                	loseCount++;
                }
                
                
            } //while

            System.out.print(String.format("%d turns later.\nEnd Game %d: ", turn, i));
            System.out.println(String.format("%s now has balance %d\n", player.getName(), player.getBalance()));
            System.out.println(String.format("Win count = %d, Lose Count = %d, %.2f \n", winCount, loseCount, (float) winCount/(winCount+loseCount)));
           
            totalWins += winCount;
            totalLose += loseCount;
            winCount = 0;
            loseCount = 0;
            
        } //for
		
		System.out.println(String.format("Win count = %d, Lose Count = %d, %.2f", totalWins, totalLose, (float) totalWins/(totalWins+totalLose)));
		
	}

}
