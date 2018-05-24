import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class ActionsPanel extends JPanel {
  private JLabel title;
  public ActionsPanel() {
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    title = new JLabel("Actions");
    constraints.gridx = 0;
    constraints.gridy = 0;
    add(title, constraints);
    setBorder(BorderFactory.createLineBorder(Color.green));
  }
}
