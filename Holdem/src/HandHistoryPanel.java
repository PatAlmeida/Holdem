import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class HandHistoryPanel extends JPanel {
  private JLabel handHistoryTitleLabel;
  public HandHistoryPanel() {
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    handHistoryTitleLabel = new JLabel("Hand History");
    constraints.gridx = 0;
    constraints.gridy = 0;
    add(handHistoryTitleLabel, constraints);
    setBorder(BorderFactory.createLineBorder(Color.blue));
  }
}
