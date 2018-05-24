import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class TempBottomPanel extends JPanel {
  private JLabel title;
  public TempBottomPanel() {
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    title = new JLabel("Bottom Panel");
    constraints.gridx = 0;
    constraints.gridy = 0;
    add(title, constraints);
    setBorder(BorderFactory.createLineBorder(Color.yellow));
  }
}
