import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class GreetingPanel extends JPanel {
  private JLabel greetingText;
  public GreetingPanel() {
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    greetingText = new JLabel("50/100 NL Holdem");
    greetingText.setFont(new Font("Times New Roman", Font.BOLD, 20));
    add(greetingText, constraints);
    setBorder(BorderFactory.createLineBorder(Color.red));
  }
}
