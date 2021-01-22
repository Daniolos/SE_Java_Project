package kassensystem;

import java.awt.*;
import java.util.Objects;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.*;

public final class SearchRendererTest {
  private final JTextField field = new JTextField("Suchtext bitte hier eingeben");
  private final SearchRenderer renderer = new SearchRenderer();

  //Titel der Tabelle
  private final String[] title = {"Artikelname", "EAN", "Preis", "St�ckzahl"};

  //Tabellendaten
  private final Object[][] playerdata = {
		  	{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
			{"Apfel", "0192992", "19,92", 2},
			{"Tomate", "3432342", "2,34", 3},
			{"Brot", "2345323","213,32", 7},
  };
  private final TableModel model = new DefaultTableModel(playerdata, title);
  private final JTable table = new JTable(model);

  public JComponent makeUI() {
    table.setDefaultRenderer(Object.class, renderer);
    field.getDocument().addDocumentListener(new DocumentListener() {
      @Override public void insertUpdate(DocumentEvent e) {
        fireDocumentChangeEvent();
      }
      @Override public void removeUpdate(DocumentEvent e) {
        fireDocumentChangeEvent();
      }
      @Override public void changedUpdate(DocumentEvent e) {
        /* not needed */
      }
    });
    fireDocumentChangeEvent();

    JPanel sp = new JPanel(new BorderLayout(5, 5));
    sp.add(new JLabel("Sucheingabe:"), BorderLayout.WEST);
    sp.add(field);
    sp.add(Box.createVerticalStrut(2), BorderLayout.SOUTH);
    sp.setBorder(BorderFactory.createTitledBorder("Suche"));

    JPanel p = new JPanel(new BorderLayout(5, 5));
    p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    p.add(sp, BorderLayout.NORTH);
    p.add(new JScrollPane(table));
    return p;
  }

  private void fireDocumentChangeEvent() {
    String pattern = field.getText().trim();
    renderer.setPattern(pattern);
    table.repaint();
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        createAndShowGUI();
      }
    });
  }

  public static void createAndShowGUI() {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.getContentPane().add(new SearchRendererTest().makeUI());
    f.pack();
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
}

class SearchRenderer implements TableCellRenderer {
  private static final Color BACKGROUND_SELECTION_COLOR = new Color(220, 240, 255);
  private final transient Highlighter.HighlightPainter highlightPainter
    = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
  private final JTextField field = new JTextField();
  private String pattern = "";
  private String prev;

  public boolean setPattern(String str) {
    if (str == null || str.equals(pattern)) {
      return false;
    } else {
      prev = pattern;
      pattern = str;
      return true;
    }
  }
  public SearchRenderer() {
    super();
    field.setOpaque(true);
    field.setBorder(BorderFactory.createEmptyBorder());
    field.setForeground(Color.BLACK);
    field.setBackground(Color.WHITE);
    field.setEditable(false);
  }
  @Override public Component getTableCellRendererComponent(
    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    String txt = Objects.toString(value, "");
    Highlighter highlighter = field.getHighlighter();
    highlighter.removeAllHighlights();
    field.setText(txt);
    field.setBackground(isSelected ? BACKGROUND_SELECTION_COLOR : Color.WHITE);
    if (pattern != null && !pattern.isEmpty() && !pattern.equals(prev)) {
      Matcher matcher = Pattern.compile(pattern).matcher(txt);
      if (matcher.find()) {
        int start = matcher.start();
        int end   = matcher.end();
        try {
          highlighter.addHighlight(start, end, highlightPainter);
        } catch (BadLocationException e) {
          e.printStackTrace();
        }
      }
    }
    return field;
  }
}