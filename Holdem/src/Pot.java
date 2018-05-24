import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class Pot {

  private int value;

  public Pot() {
    value = 0;
  }

  public int getValue() {
    return value;
  }

  public void addChips(int i) {
    value += i;
  }

  public void reset() {
    value = 0;
  }

}
