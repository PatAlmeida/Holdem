import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class Card {

  private Rank rank;
  private Suit suit;

  public Card(Rank _rank, Suit _suit) {
    rank = _rank;
    suit = _suit;
  }

  public Card copy() {
    return new Card(rank, suit);
  }

  public int getRankValue() {
    switch(rank) {
      case TWO: return 2;
      case THREE: return 3;
      case FOUR: return 4;
      case FIVE: return 5;
      case SIX: return 6;
      case SEVEN: return 7;
      case EIGHT: return 8;
      case NINE: return 9;
      case TEN: return 10;
      case JACK: return 11;
      case QUEEN: return 12;
      case KING: return 13;
      case ACE: return 14;
      default: return -1;
    }
  }

  public String getSuitString() {
    switch(suit) {
      case CLUBS: return "clubs";
      case DIAMONDS: return "diamonds";
      case HEARTS: return "hearts";
      case SPADES: return "spades";
      default: return null;
    }
  }

  public Rank getRank() {
    return rank;
  }

  public Suit getSuit() {
    return suit;
  }

  public ImageIcon getImage() {
    return Game.loadImage("images/" + getSuitString() + "/"
        + getRankValue() + ".png");
  }

  @Override
  public String toString() {
    return rank + " of " + suit;
  }

  public void printCard() {
    System.out.println(this);
  }

}
