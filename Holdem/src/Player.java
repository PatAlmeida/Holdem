import java.util.*;

public class Player {

  private String name;
  private int chips;
  private boolean inHand;
  private Card[] holeCards;
  private int currentBet;
  private boolean isLocalPlayer;
  private boolean actedOnce;

  public Player(String _name) {
    name = _name;
    chips = Game.STARTING_CHIPS;
    inHand = false;
    currentBet = 0; // will reset every betting round
    actedOnce = false;

    // Probably should not do it this way...
    isLocalPlayer = name.equals("Pat");

    holeCards = new Card[2];
  }

  public void act() {
    if (inHand) {
      ArrayList<ActionOption> options = getOptions();

      ActionOption action;
      /* if (options.contains(ActionOption.CALL)) {
        action = ActionOption.CALL;
        call();
      } else {
        action = ActionOption.CHECK;
        check();
      }*/
      int rand = (int) (Math.random() * 100);
      if (options.contains(ActionOption.BET) && rand >= 0 && rand < 10) {
        action = ActionOption.BET;
        bet(Game.BIG_BLIND_VALUE * 2);
      } else {
        int rand2 = (int) (Math.random() * 100);
        if (rand2 < 25) {
          action = ActionOption.FOLD;
          fold();
        } else {
          if (options.contains(ActionOption.CALL)) {
            action = ActionOption.CALL;
            call();
          } else {
            action = ActionOption.CHECK;
            check();
          }
        }
      }

      if (!actedOnce) actedOnce = true;
      System.out.println(name + ": " + action);
    }
  }

  public void call() {
    int biggestCurrentBet = Game.game.getBiggestCurrentBet();
    int newChips = biggestCurrentBet - currentBet;
    currentBet = biggestCurrentBet;
    chips -= newChips;
  }
  public void check() {
    // do nothing for now
  }
  public void bet(int i) {
    currentBet += i;
    chips -= i;
  }
  public void raise(int i) {
    chips -= i - currentBet;
    currentBet = i;
  }
  public void fold() {
    inHand = false;
  }

  public boolean needsToAct(BettingRound round, int biggestCurrentBet) {
    boolean needToAct = false;
    if (inHand) {
      if (!actedOnce) {
        needToAct = true;
      } else {
        if (currentBet < biggestCurrentBet) {
          needToAct = true;
        }
      }
    }
    return needToAct;
  }

  public ArrayList<ActionOption> getOptions() {
    ArrayList<ActionOption> options = new ArrayList<ActionOption>();
    int biggestCurrentBet = Game.game.getBiggestCurrentBet();
    if (currentBet < biggestCurrentBet) {
      options.add(ActionOption.CALL);
      options.add(ActionOption.RAISE);
    }
    if (currentBet == biggestCurrentBet && currentBet == 0) {
      options.add(ActionOption.CHECK);
      options.add(ActionOption.BET);
    }
    // this should only happen pre-flop
    if (currentBet == biggestCurrentBet && currentBet != 0) {
      options.add(ActionOption.CHECK);
      options.add(ActionOption.RAISE);
    }
    options.add(ActionOption.FOLD);
    return options;
  }

  public Card[] getHoleCards() {
    Card[] newCards = new Card[2];
    newCards[0] = holeCards[0].copy();
    newCards[1] = holeCards[1].copy();
    return newCards;
  }

  public void receiveHoleCards(Card[] dealtCards) {
    holeCards[0] = dealtCards[0].copy();
    holeCards[1] = dealtCards[1].copy();
  }

  public void resetBeforeHand() {
    inHand = true;
    currentBet = 0;
  }

  public void resetBeforeRound() {
    actedOnce = false;
    currentBet = 0;
  }

  public boolean isLocalPlayer() {
    return isLocalPlayer;
  }

  public boolean isInHand() {
    return inHand;
  }

  public int getCurrentBet() {
    return currentBet;
  }

  public String getName() {
    return name;
  }

  public int getChips() {
    return chips;
  }

}
