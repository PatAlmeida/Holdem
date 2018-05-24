import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class HoldemWindow extends JFrame {

  private GreetingPanel greetingPanel;
  private HandHistoryPanel handHistoryPanel;
  private ActionsPanel actionsPanel;
  private TempBottomPanel tempBottomPanel;
  private GamePanel gamePanel;

  public HoldemWindow() {
    super("NL Holdem");
    setLayout(new BorderLayout());
    setLocation(350, 55);

    greetingPanel = new GreetingPanel();
    add(greetingPanel, BorderLayout.NORTH);
    handHistoryPanel = new HandHistoryPanel();
    add(handHistoryPanel, BorderLayout.WEST);
    actionsPanel = new ActionsPanel();
    add(actionsPanel, BorderLayout.EAST);
    tempBottomPanel = new TempBottomPanel();
    add(tempBottomPanel, BorderLayout.SOUTH);
    gamePanel = new GamePanel();
    add(gamePanel, BorderLayout.CENTER);

    setIconImage(Game.loadImage("images/bets/chips.png").getImage());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  // Called whenever the GUI is to be updated
  public void update() {
    gamePanel.update();
    pack();
  }

}
