import java.util.*;

public class Deck {

  private ArrayList<Card> cards;
  private int currentCardIndex = 0;

  public Deck() {
    cards = new ArrayList<Card>();
    for (int i=0; i<52; i++) {
      Rank rank = null;
      Suit suit = null;
      switch (i % 13) {
        case 0: rank = Rank.TWO; break;
        case 1: rank = Rank.THREE; break;
        case 2: rank = Rank.FOUR; break;
        case 3: rank = Rank.FIVE; break;
        case 4: rank = Rank.SIX; break;
        case 5: rank = Rank.SEVEN; break;
        case 6: rank = Rank.EIGHT; break;
        case 7: rank = Rank.NINE; break;
        case 8: rank = Rank.TEN; break;
        case 9: rank = Rank.JACK; break;
        case 10: rank = Rank.QUEEN; break;
        case 11: rank = Rank.KING; break;
        case 12: rank = Rank.ACE; break;
      }
      switch (i / 13) {
        case 0: suit = Suit.CLUBS; break;
        case 1: suit = Suit.DIAMONDS; break;
        case 2: suit = Suit.HEARTS; break;
        case 3: suit = Suit.SPADES; break;
      }
      cards.add(new Card(rank, suit));
    }
    shuffle();
  }

  public Card dealCard() {
    Card c = getCardAt(currentCardIndex);
    currentCardIndex++;
    return c;
  }

  // Just used for some testing
  public Card[] getFiveCards() {
    Card[] cards = new Card[5];
    for (int i=0; i<5; i++)
      cards[i] = dealCard();
    return cards;
  }

  public Card getCardAt(int i) {
    return cards.get(i).copy();
  }

  public void printDeck() {
    for (Card c : cards) c.printCard();
  }

  public void shuffle() {
    ArrayList<Card> cardsCopy = new ArrayList<Card>();
    for (Card c : cards) cardsCopy.add(c.copy());
    cards.clear();
    while (cardsCopy.size() > 0) {
      int rand = (int) (Math.random() * cardsCopy.size());
      cards.add(cardsCopy.get(rand).copy());
      cardsCopy.remove(rand);
    }
    currentCardIndex = 0;
  }

}
