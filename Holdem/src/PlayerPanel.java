import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class PlayerPanel extends JPanel {

  // This could be...
  // private Player player
  private int playerIndex;

  private JLabel[] cardImages;
  private JLabel nameLabel, chipsLabel;
  private JLabel[] currentBet;
  private JPanel currentBetPanel;

  public PlayerPanel(int _playerIndex) {
    playerIndex = _playerIndex;
    Player p = Game.game.getPlayer(playerIndex);

    // showing the back of the card
    cardImages = new JLabel[2];
    cardImages[0] = new JLabel(Game.loadImage("images/other/transparent.png"));
    cardImages[1] = new JLabel(Game.loadImage("images/other/transparent.png"));
    JPanel holeCardsPanel = new JPanel();
    holeCardsPanel.add(cardImages[0]);
    holeCardsPanel.add(cardImages[1]);

    nameLabel = new JLabel(p.getName());
    chipsLabel = new JLabel("$" + p.getChips());

    currentBetPanel = new JPanel();
    currentBet = new JLabel[2];
    currentBet[0] = new JLabel("" + p.getCurrentBet()); // will be 0 on creation
    currentBet[0].setFont(new Font("Times New Roman", Font.BOLD, 20));
    currentBet[0].setForeground(Color.RED);
    currentBet[1]= new JLabel(Game.loadImage("images/bets/chips.png"));
    currentBetPanel.add(currentBet[0]);
    currentBetPanel.add(currentBet[1]);
    currentBetPanel.setVisible(false);

    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    add(holeCardsPanel, constraints);
    constraints.gridy = 1;
    add(nameLabel, constraints);
    constraints.gridy = 2;
    add(chipsLabel, constraints);
    constraints.gridy = 3;
    add(currentBetPanel, constraints);
  }

  public void update() {
    Player p = Game.game.getPlayer(playerIndex);

    if (p.isInHand()) {
      if (!p.isLocalPlayer()) {
        cardImages[0].setIcon(Game.loadImage("images/other/back.png"));
        cardImages[1].setIcon(Game.loadImage("images/other/back.png"));
      } else {
        cardImages[0].setIcon(p.getHoleCards()[0].getImage());
        cardImages[1].setIcon(p.getHoleCards()[1].getImage());
      }
    } else {
      cardImages[0].setIcon(Game.loadImage("images/other/transparent.png"));
      cardImages[1].setIcon(Game.loadImage("images/other/transparent.png"));
    }

    chipsLabel.setText("$" + p.getChips());

    currentBet[0].setText("" + p.getCurrentBet());
    if (p.getCurrentBet() != 0) {
      currentBetPanel.setVisible(true);
    } else {
      currentBetPanel.setVisible(false);
    }
  }

}
