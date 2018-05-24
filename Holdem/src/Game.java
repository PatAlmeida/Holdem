import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

/* NOTES
 *  - The default JPanel background color is (238, 238, 238)
 *  - Some classes have imports that are not needed there
 *  - The way hole cards are done in Player will be changed (maybe)
 */

public class Game {

  public static Game game;

  private HoldemWindow window;

  public static final int STARTING_CHIPS = 10000;
  public static final int NUM_PLAYERS = 6;
  public static final int BIG_BLIND_VALUE = 100;

  private int bigBlindIndex = 2;
  private int smallBlindIndex = 1;
  private PlayerList players;
  private Deck deck;
  private Pot pot;
  private CommunityCards communityCards;

  public Game() throws IOException {
    players = new PlayerList();
    deck = new Deck();
    pot = new Pot();
    communityCards = new CommunityCards();
  }

  public static void main(String[] args) throws IOException {
    Game.game = new Game();
    Game.game.window = new HoldemWindow();
    Game.game.start();
    //Game.testHandRankings();
  }

  public void start() {
    doHand();
  }

  public void doHand() {
    deck.shuffle();
    players.resetBeforeHand();
    pot.reset();
    advanceBlinds();
    dealCards();
    postBlinds();
    window.update();

    System.out.println("---- Starting a new hand ----");

    boolean doNextRound = true;
    if (doNextRound) doNextRound = doBettingRound(BettingRound.PRE_FLOP);
    if (doNextRound) doNextRound = doBettingRound(BettingRound.FLOP);
    if (doNextRound) doNextRound = doBettingRound(BettingRound.TURN);
    if (doNextRound) doNextRound = doBettingRound(BettingRound.RIVER);
    if (doNextRound) showdown();
  }

  public boolean doBettingRound(BettingRound round) {
    int actingPlayer;
    if (round == BettingRound.PRE_FLOP)
      actingPlayer = Game.playerAfter(bigBlindIndex);
    else actingPlayer = smallBlindIndex;

    int playersLeftToAct = players.getLeftToAct(round);
    while (playersLeftToAct > 0) {
      getPlayer(actingPlayer).act();
      window.update();
      playersLeftToAct = players.getLeftToAct(round);
      actingPlayer = Game.playerAfter(actingPlayer);
    }
    System.out.println("---- Finished " + round + " ----");

    pot.addChips(players.allBets());
    window.update();
    players.resetBeforeRound();

    return !players.onePlayerLeft();
  }

  public static HandRanking getRankingForHand(Card[] cards) {

    HandRanking handRanking = HandRanking.HIGH_CARD;

    // Checking for a pair
    for (int i=0; i<cards.length-1; i++) {
      Rank cardRank = cards[i].getRank();
      for (int j=i+1; j<cards.length; j++) {
        Rank card2Rank = cards[j].getRank();
        if (cardRank == card2Rank) handRanking = HandRanking.PAIR;
      }
    }

    // Checking for two pair
    if (handRanking == HandRanking.PAIR) {
      Rank firstPairRank = null;
      for (int i=0; i<cards.length-1; i++) {
        Rank cardRank = cards[i].getRank();
        for (int j=i+1; j<cards.length; j++) {
          Rank card2Rank = cards[j].getRank();
          if (cardRank == card2Rank) firstPairRank = cardRank;
        }
      }
      for (int i=0; i<cards.length-1; i++) {
        Rank cardRank = cards[i].getRank();
        for (int j=i+1; j<cards.length; j++) {
          Rank card2Rank = cards[j].getRank();
          if (cardRank == card2Rank && cardRank != firstPairRank)
            handRanking = HandRanking.TWO_PAIR;
        }
      }
    }

    // Checking for three of a kind
    for (int i=0; i<cards.length-1; i++) {
      Rank cardRank = cards[i].getRank();
      for (int j=i+1; j<cards.length; j++) {
        Rank card2Rank = cards[j].getRank();
        if (cardRank == card2Rank) {
          for (int k=j+1; k<cards.length; k++) {
            Rank card3Rank = cards[k].getRank();
            if (card3Rank == cardRank)
              handRanking = HandRanking.THREE_OF_A_KIND;
          }
        }
      }
    }

    // Checking for a straight (need to add wheel)
    if (handRanking != HandRanking.PAIR) {
      for (int i=0; i<cards.length-1; i++) {
        for (int j=i+1; j<cards.length; j++) {
          if (cards[i].getRankValue() > cards[j].getRankValue()) {
            Card temp = cards[i].copy();
            cards[i] = cards[j].copy();
            cards[j] = temp.copy();
          }
        }
      }
      int lowRank = cards[0].getRankValue();
      boolean isStraight = true;
      for (int i=1; i<cards.length; i++) {
        if (cards[i].getRankValue() != lowRank + i) isStraight = false;
      }
      boolean isWheel = true;
      if (cards[0].getRankValue() != 2) isWheel = false;
      if (cards[1].getRankValue() != 3) isWheel = false;
      if (cards[2].getRankValue() != 4) isWheel = false;
      if (cards[3].getRankValue() != 5) isWheel = false;
      if (cards[4].getRankValue() != 14) isWheel = false;
      if (isStraight || isWheel) handRanking = HandRanking.STRAIGHT;
    }

    // Checking for a flush and a straight flush
    if (handRanking != HandRanking.PAIR) {
      Suit firstSuit = cards[0].getSuit();
      boolean isFlush = true;
      for (int i=1; i<cards.length; i++) {
        if (cards[i].getSuit() != firstSuit) isFlush = false;
      }
      if (isFlush) {
        if (handRanking != HandRanking.STRAIGHT)
          handRanking = HandRanking.FLUSH;
        else handRanking = HandRanking.STRAIGHT_FLUSH;
      }
    }

    // Checking for a full house and four of a kind
    if (handRanking == HandRanking.THREE_OF_A_KIND) {
      ArrayList<Integer> differentRanks = new ArrayList<Integer>();
      for (Card c : cards) {
        int rankValue = c.getRankValue();
        boolean inList = false;
        for (int i : differentRanks) {
          if (rankValue == i) inList = true;
        }
        if (!inList) differentRanks.add(rankValue);
      }
      if (differentRanks.size() == 2) {
        int rank0Count = 0;
        int rank1Count = 0;
        for (Card c : cards) {
          if (c.getRankValue() == differentRanks.get(0)) rank0Count++;
          if (c.getRankValue() == differentRanks.get(1)) rank1Count++;
        }
        if (rank0Count == 3 || rank1Count == 3)
          handRanking = HandRanking.FULL_HOUSE;
        else handRanking = HandRanking.FOUR_OF_A_KIND;
      }
    }

    return handRanking;

  }

  public void showdown() {
    System.out.println("Showdown!");
  }

  public int getBiggestCurrentBet() {
    return players.getBiggestCurrentBet();
  }

  public int getBigBlindIndex() {
    return bigBlindIndex;
  }

  public void postBlinds() {
    players.getPlayerById(bigBlindIndex).bet(Game.BIG_BLIND_VALUE);
    players.getPlayerById(smallBlindIndex).bet(Game.BIG_BLIND_VALUE / 2);
  }

  public void dealCards() {
    // Should be for only the players left with chips...
    for (int i=0; i<Game.NUM_PLAYERS; i++) {
      Card[] cards = new Card[2];
      cards[0] = deck.dealCard();
      cards[1] = deck.dealCard();
      players.getPlayerById(i).receiveHoleCards(cards);
    }
  }

  public void advanceBlinds() {
    bigBlindIndex = Game.playerAfter(bigBlindIndex);
    smallBlindIndex = Game.playerAfter(smallBlindIndex);
  }

  public static int playerAfter(int i) {
    if (i == Game.NUM_PLAYERS - 1) return 0;
    else return i+1;
  }

  public int getPotValue() {
    return pot.getValue();
  }

  // Just used for some testing
  public static void printCards(Card[] cards) {
    for (Card c : cards) c.printCard();
  }

  // Just used for some testing
  public static void testHandRankings() throws IOException {
    PrintWriter writer = new PrintWriter("data/hands.txt", "UTF-8");
    for (int i=0; i<10000; i++) {
      Card[] cards = Game.game.deck.getFiveCards();
      /*cards[0] = new Card(Rank.FIVE, Suit.CLUBS);
      cards[1] = new Card(Rank.SIX, Suit.DIAMONDS);
      cards[2] = new Card(Rank.FIVE, Suit.HEARTS);
      cards[3] = new Card(Rank.SIX, Suit.CLUBS);
      cards[4] = new Card(Rank.FIVE, Suit.SPADES);*/
      for (Card c : cards) Game.writeToFile(writer, c.toString());
      Game.writeToFile(writer, "---- Ranking: " +
                       Game.getRankingForHand(cards) + " ----");
      if (i % 10 == 0) Game.game.deck.shuffle();
    }
    writer.close();
  }

  public Card getCardInDeckAt(int i) {
    return deck.getCardAt(i);
  }

  public Player getPlayer(int i) {
    return players.getPlayerById(i);
  }

  public static ImageIcon loadImage(String path) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(path));
    } catch (Exception e) {
      System.out.println("Error loading image");
    }
    return new ImageIcon(img);
  }

  public static void writeToFile(PrintWriter writer, String s) {
    try { writer.println(s); }
    catch (Exception e) { System.out.println("Error"); }
  }

  public static void delay(int ms) {
    try { Thread.sleep(ms); }
    catch (Exception e) { }
  }

}
