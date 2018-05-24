import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class PotAndCommunityPanel extends JPanel {

  private JPanel potPanel;
  private JLabel[] potLabels;

  public PotAndCommunityPanel() {
    potPanel = new JPanel();
    potLabels = new JLabel[2];
    potLabels[0] = new JLabel("" + Game.game.getPotValue());
    potLabels[0].setFont(new Font("Times New Roman", Font.BOLD, 20));
    potLabels[0].setForeground(Color.RED);
    potLabels[1]= new JLabel(Game.loadImage("images/bets/chips.png"));
    potPanel.add(potLabels[0]);
    potPanel.add(potLabels[1]);

    add(potPanel);
  }

  public void update() {
    potLabels[0].setText("" + Game.game.getPotValue());
  }

}
