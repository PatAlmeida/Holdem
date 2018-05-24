import java.util.*;
import java.io.*;

public class PlayerList {

  private Player[] players;

  public PlayerList() throws IOException {
    players = new Player[Game.NUM_PLAYERS];
    ArrayList<String> names = PlayerList.getNameListFromFile();
    players[0] = new Player("Pat");
    ArrayList<Integer> indexesPicked = new ArrayList<Integer>();
    for (int i=1; i<Game.NUM_PLAYERS; i++) {
      boolean doAgain = true;
      int rand = -1;
      while (doAgain) {
        rand = (int) (Math.random() * names.size());
        boolean found = false;
        for (int j : indexesPicked) {
          if (j == rand) found = true;
        }
        doAgain = found;
      }
      indexesPicked.add(rand);
      players[i] = new Player(names.get(rand));
    }
  }

  private static ArrayList<String> getNameListFromFile() throws IOException {
    Scanner scan = new Scanner(new File("data/names.txt"));
    ArrayList<String> names = new ArrayList<String>();
    while (scan.hasNext()) names.add(scan.next());
    return names;
  }

  public boolean onePlayerLeft() {
    int i = 0;
    for (Player p : players) {
      if (p.isInHand()) i++;
    }
    return (i==1);
  }

  public int getBiggestCurrentBet() {
    int biggestCurrentBet = 0;
    for (Player p : players) {
      if (p.getCurrentBet() > biggestCurrentBet)
        biggestCurrentBet = p.getCurrentBet();
    }
    return biggestCurrentBet;
  }

  public int getLeftToAct(BettingRound round) {
    int biggestCurrentBet = getBiggestCurrentBet();

    int leftToAct = 0;
    for (Player p : players) {
      if (p.needsToAct(round, biggestCurrentBet)) leftToAct++;
    }

    return leftToAct;
  }

  public void resetBeforeHand() {
    for (Player p : players) {
      if (p.getChips() > 0) p.resetBeforeHand();
    }
  }

  public Player getPlayerById(int i) {
    return players[i];
  }

  public void printNames() {
    for (Player p : players)
      System.out.println(p.getName());
  }

  public int allBets() {
    int sum = 0;
    for (Player p : players) {
      sum += p.getCurrentBet();
    }
    return sum;
  }

  public void resetBeforeRound() {
    for (Player p : players) {
      p.resetBeforeRound();
    }
  }

  public Player getPlayerByName(String name) {
    for (Player p : players) {
      if (p.getName().equals(name))
        return p;
    }
    System.out.println("Error finding player: " + name);
    System.exit(0);
    return null;
  }

}
