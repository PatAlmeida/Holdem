import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class GamePanel extends JPanel {

  // Used this to show the whole deck
  //private JLabel[] labels;

  private PlayerPanel[] playerPanels;
  private PotAndCommunityPanel potAndCommunityPanel;

  public GamePanel() {

    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 10, 10, 10);

    playerPanels = new PlayerPanel[Game.NUM_PLAYERS];
    for (int i=0; i<playerPanels.length; i++) {
      playerPanels[i] = new PlayerPanel(i);
    }

    potAndCommunityPanel = new PotAndCommunityPanel();

    constraints.gridx = 2;
    constraints.gridy = 2;
    add(playerPanels[0], constraints);
    constraints.gridx = 0;
    constraints.gridy = 1;
    add(playerPanels[1], constraints);
    constraints.gridx = 1;
    constraints.gridy = 0;
    add(playerPanels[2], constraints);
    constraints.gridx = 2;
    constraints.gridy = 0;
    add(playerPanels[3], constraints);
    constraints.gridx = 3;
    constraints.gridy = 0;
    add(playerPanels[4], constraints);
    constraints.gridx = 4;
    constraints.gridy = 1;
    add(playerPanels[5], constraints);
    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.gridwidth = 3;
    add(potAndCommunityPanel, constraints);

    // Used this to show the whole deck
    /*
    labels = new JLabel[52];
    constraints.insets = new Insets(2, 2, 2, 2);
    for (int x=0; x<13; x++) {
      for (int y=0; y<4; y++) {
        int index = (4 * x) + y;
        ImageIcon icon = Game.game.getCardInDeckAt(index).getImage();
        labels[index] = new JLabel(icon);
        constraints.gridx = x;
        constraints.gridy = y;
        add(labels[index], constraints);
      }
    }
    */

  }

  public void update() {
    for (PlayerPanel p : playerPanels) {
      p.update();
    }
    potAndCommunityPanel.update();
  }

}
