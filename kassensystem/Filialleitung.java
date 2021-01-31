import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Database.DatenLeser;
import Database.DatenSchreiber;
import Database.Lager;
import Parsing.XMLParser;

/**
 * 
 * @author Daniel Jürgeleit
 *
 */

public class Filialleitung extends JFrame implements ActionListener {
	// Hier sind alle Interaktionsvariablen
	private JPanel formularPanel;
	private JPanel interactionsPanel;
	private JPanel bestandsListePanel;
	private JScrollPane scrollPane;
	private TableRowSorter<DefaultTableModel> tr;
	private JButton switchButton;
	private JLabel artikelLabel;
	private JButton addButton;
	private JButton modifyButton;
	private int selectedForModification;
	private JButton removeButton;
	private JLabel createCategoryLabel;
	private JTextField createCategoryInput;
	private JButton createCategoryButton;
	private JButton deleteCategoryButton;
	private JTable bestandsListe;
	private DefaultTableModel bestandsListeModel;
	private JLabel suchLabel;
	private JTextField suchInput;
	private JButton suchButton;

	// Hier sind alle Formular Variablen
	private JLabel articleNameLabel;
	private JTextField articleNameInput;
	private JLabel eanLabel;
	private JTextField eanInput;
	private JLabel articlePriceLabel;
	private JTextField articlePriceInput;
	private JLabel articleQuantityLabel;
	private JTextField articleQuantityInput;
	private JLabel basePriceLabel;
	private JTextField basePriceInput;
	private JLabel basePriceUnitLabel;
	private JComboBox<String> basePriceUnitBox;
	private JLabel articleAmountLabel;
	private JTextField articleAmountInput;
	private JLabel categoryLabel;
	private DefaultComboBoxModel<String> categoryModel;
	private JComboBox<String> categoryBox;
	private JButton cancelButton;
	private JButton submitButton;
	private JButton clearButton;
	private JButton submitModificationsButton;
	private JLabel fehlermeldungLabel;

	private static Lager lager;

	// Alle Spaltennamen werden in einem String Array gespeichert
	String[] columnNames = { "Artikelname", "EAN", "Stückpreis in €", "Stückzahl", "Grundpreis", "Grundpreiseinheit",
			"Menge", "Mengeneinheit", "Kategorie" };

	// Hier werden in der Klasse die Artikeleigenschaften verwart
	static String[] categories = {};
	String[] grundPreisEinheiten = { "€/kg", "€/100g", "€/l", "€/100ml", "n" };
	String[] mengenEinheiten = { "kg", "g", "l", "ml", "n" };
	static String[][] dataBestand = {};

	// hier wird die Bidlschirm-Größe mit dem Dimension Object initialisiert
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double screenWidth = screenSize.getWidth();
	double screenHeight = screenSize.getHeight();

	/**
	 * Filialleitung ist der Konstruktor der Klasse Hier werden alle GUI Elemente
	 * wie Panels und Buttons instanziert und zur contentPane hinzugefügt Auf
	 * JButtons und JComboBoxes werden ActionListener gesetzt Auf JTextFields werden
	 * werden DocumentListener angewandt
	 */
	public Filialleitung() {
		DatenLeser bla = new DatenLeser();
		XMLParser xml = new XMLParser(bla.getData());
		// String article = xml.getChild("articles");
		lager = new Lager(xml.getXML());

		getData();

		setLayout(new BorderLayout());
		formularPanel = new JPanel();
		interactionsPanel = new JPanel();
		switchButton = new JButton("zu Einkauf wechseln");
		artikelLabel = new JLabel("Artikel");
		addButton = new JButton("Artikel Hinzufügen");
		modifyButton = new JButton("Artikel Bearbeiten");
		removeButton = new JButton("Artikel Löschen");
		createCategoryLabel = new JLabel("Kategorie");
		createCategoryInput = new JTextField("");
		createCategoryButton = new JButton("Erstellen");
		deleteCategoryButton = new JButton("Löschen");
		suchLabel = new JLabel("Suche");
		suchInput = new JTextField("");
		suchButton = new JButton("Eingabe");

		switchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.switchToEinkauf();
				} catch (Exception ex) {
				}
			}

		});

		/**
		 * Button, um zu einem Formular zu gelangen, in dem man Artikeleigenschaften
		 * angeben kann, um mit diesen einen Artikel zu erstellen.
		 */

		addButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				addButton.setEnabled(false);
				removeButton.setEnabled(true);
				clearForm();
				submitModificationsButton.setVisible(false);
				formularPanel.setVisible(true);
				fehlermeldungLabel.setText("");

				articleAmountLabel.setText("Menge in " + mengenEinheiten[0]);
			}

		});

		/**
		 * Button zum Bearbeiten von Artikeln.
		 */

		modifyButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (bestandsListe.getSelectedRow() == -1) {
					return;
				}

				suchInput.setText("");
				suche();

				int selectedRow = bestandsListe.getSelectedRow();
				selectedForModification = selectedRow;

				addButton.setEnabled(true);
				removeButton.setEnabled(false);
				fehlermeldungLabel.setText("");
				submitModificationsButton.setVisible(true);
				formularPanel.setVisible(true);

				String name = dataBestand[selectedRow][0].toString();
				String ean = dataBestand[selectedRow][1].toString();
				String articlePrice = dataBestand[selectedRow][2].toString();
				String articleQuantity = dataBestand[selectedRow][3].toString();
				String basePrice = dataBestand[selectedRow][4].toString();
				String basePriceUnit = dataBestand[selectedRow][5].toString();
				String articleAmount = dataBestand[selectedRow][6].toString();
				// articleAmountUnit is set automatically
				String category = dataBestand[selectedRow][8].toString();

				articleNameInput.setText(name);
				eanInput.setText(ean);
				articlePriceInput.setText(articlePrice);
				articleQuantityInput.setText(articleQuantity);
				basePriceInput.setText(basePrice);
				basePriceUnitBox.setSelectedItem(basePriceUnit);
				articleAmountInput.setText(articleAmount);
				categoryBox.setSelectedItem(category);

			}

		});

		/**
		 * Button zum Löschen von Artikeln.
		 */

		removeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				if (bestandsListe.getSelectedRow() == -1) {
					return;
				}

				suchInput.setText("");
				suche();

				int selectedRow = bestandsListe.getSelectedRow();
				String ean = (String) bestandsListeModel.getValueAt(selectedRow, 1);

				lager.delete(ean);
				DatenSchreiber DatenSchreiber = new DatenSchreiber(lager);
				DatenSchreiber.Schreiben();

				String[][] copy = new String[dataBestand.length - 1][];
				for (int i = 0, j = 0; i < dataBestand.length; i++) {
					if (i != selectedRow) {
						copy[j++] = dataBestand[i];
					}
				}
				dataBestand = Arrays.copyOf(copy, copy.length);

				bestandsListeModel.removeRow(selectedRow);

			}

		});

		/**
		 * Button zum Anlegen von Kategorien.
		 */

		createCategoryButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String input = createCategoryInput.getText();
				int index = Arrays.asList(categories).indexOf(input);
				if (!input.equals("") & index == -1) {
					if ((input.matches("[a-zäöüßA-ZÄÖÜ]+")) && (3 <= input.length()) && (input.length() <= 32)) {
						categories = Arrays.copyOf(categories, categories.length + 1);
						categories[categories.length - 1] = input;

						categoryModel.addElement(input);

						createCategoryInput.setText("");

						lager.getKategorien().addKategorie(input);
					}
				}
			}

		});

		/**
		 * Button zum Löschen von angelegten Kategorien.
		 */

		deleteCategoryButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String input = createCategoryInput.getText();
				int index = Arrays.asList(categories).indexOf(input);
				if (index > -1 && !input.equals("Keine Kategorie")) {

					String[] copy = new String[categories.length - 1];

					for (int i = 0, j = 0; i < categories.length; i++) {
						if (i != index) {
							copy[j++] = categories[i];
						}
					}
					categories = Arrays.copyOf(copy, copy.length);

					categoryModel.removeElement(input);

					lager.getKategorien().removeKategorie(input, lager.getArtikel());

					createCategoryInput.setText("");
				}
			}

		});

		/**
		 * Button zum Suchen nach Artikeln.
		 */

		suchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				suche();
			}
		});

		articleNameLabel = new JLabel("Artikelname");
		articleNameInput = new JTextField("");
		eanLabel = new JLabel("EAN / PLU");
		eanInput = new JTextField("");
		articlePriceLabel = new JLabel("Stückpreis in €");
		articlePriceInput = new JTextField("");
		articleQuantityLabel = new JLabel("Stückzahl");
		articleQuantityInput = new JTextField("");
		basePriceLabel = new JLabel("Grundpreis");
		basePriceInput = new JTextField("");
		basePriceUnitLabel = new JLabel("Grundpreiseinheit");
		basePriceUnitBox = new JComboBox<String>(grundPreisEinheiten);
		articleAmountLabel = new JLabel("Menge in "); // soll dynamisch anpassbar
		articleAmountInput = new JTextField("");
		categoryLabel = new JLabel("Kategorie");
		categoryModel = new DefaultComboBoxModel<String>(categories);
		categoryBox = new JComboBox<>(categoryModel);
		submitButton = new JButton("Artikel Hinzufügen");
		submitModificationsButton = new JButton("Artikel Bearbeiten");
		clearButton = new JButton("Formular leeren");
		cancelButton = new JButton("Abbrechen");
		fehlermeldungLabel = new JLabel("");

		constraintForm();

		/**
		 * Button zum leeren des Dokuments zur Bearbeitung von Artikeln.
		 */

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearForm();
				fehlermeldungLabel.setText("");
			}
		});

		/**
		 * Button zum Abbrechen.
		 */

		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				addButton.setEnabled(true);
				removeButton.setEnabled(true);
				clearForm();
				formularPanel.setVisible(false);
				fehlermeldungLabel.setText("");
			}

		});

		/**
		 * Button zum Bestätigen des Hinzufügen eines Artikels.
		 */

		submitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String name = articleNameInput.getText();
				String ean = eanInput.getText();
				String articlePrice = articlePriceInput.getText();
				String articleQuantity = articleQuantityInput.getText();
				String basePrice = basePriceInput.getText();
				String basePriceUnit = String.valueOf(basePriceUnitBox.getSelectedItem());
				String articleAmount = articleAmountInput.getText();
				int index = Arrays.asList(grundPreisEinheiten).indexOf(basePriceUnit);
				String articleAmountUnit = mengenEinheiten[index];
				String category = String.valueOf(categoryBox.getSelectedItem());

				String[] completeArticle = { name, ean, articlePrice, articleQuantity, basePrice, basePriceUnit,
						articleAmount, articleAmountUnit, category };

				// hier Überprüfen ob die eingegebenen Eigenschaften in ordnung sind
				int check = lager.ArtikelHinzufuegen(name, ean, articlePrice, articleQuantity, basePrice, basePriceUnit,
						articleAmount, articleAmountUnit, category);

				switch (check) {
				case -1:
					fehlermeldungLabel.setText("Name ungültig");
					break;
				case -2:
					fehlermeldungLabel.setText("EAN ungültig");
					break;
				case -3:
					fehlermeldungLabel.setText("Stückpreis ungültig");
					break;
				case -4:
					fehlermeldungLabel.setText("Stückzahl ungültig");
					break;
				case -5:
					fehlermeldungLabel.setText("Grundpreis ungültig");
					break;
				case -6:
					fehlermeldungLabel.setText("Grundpreiseinheit ungültig");
					break;
				case -7:
					fehlermeldungLabel.setText("Menge ungültig");
					break;
				case -8:
					fehlermeldungLabel.setText("Mengeneinheit ungültig");
					break;
				case -9:
					fehlermeldungLabel.setText("Kategorie ungültig");
					break;
				case -10:
					fehlermeldungLabel.setText("Artikel vorhanden");
					break;
				case 0:
					DatenSchreiber DatenSchreiber = new DatenSchreiber(lager);
					DatenSchreiber.Schreiben();

					dataBestand = Arrays.copyOf(dataBestand, dataBestand.length + 1);
					dataBestand[dataBestand.length - 1] = completeArticle;

					bestandsListeModel.addRow(completeArticle);
					clearForm();
					formularPanel.setVisible(false);

					removeButton.setEnabled(true);
					addButton.setEnabled(true);
					fehlermeldungLabel.setText("");
				}

			}

		});

		/**
		 * Button zum Bestätigen des überarbeiteten Artikels.
		 */

		submitModificationsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String name = articleNameInput.getText();
				String ean = eanInput.getText();
				String articlePrice = articlePriceInput.getText();
				String articleQuantity = articleQuantityInput.getText();
				String basePrice = basePriceInput.getText();
				String basePriceUnit = String.valueOf(basePriceUnitBox.getSelectedItem());
				String articleAmount = articleAmountInput.getText();
				int index = Arrays.asList(grundPreisEinheiten).indexOf(basePriceUnit);
				String articleAmountUnit = mengenEinheiten[index];
				String category = String.valueOf(categoryBox.getSelectedItem());

				String[] completeArticle = { name, ean, articlePrice, articleQuantity, basePrice, basePriceUnit,
						articleAmount, articleAmountUnit, category };

				// hier Überprüfen ob die eingegeben Eigenschaften in Ordnung sind

				int selectedRow = selectedForModification;
				if (selectedRow == -1) {
					return;
				}

				int check = lager.ArtikelHinzufuegen(name, ean, articlePrice, articleQuantity, basePrice, basePriceUnit,
						articleAmount, articleAmountUnit, category);

				switch (check) {
				case -1:
					fehlermeldungLabel.setText("Name ungültig");
					break;
				case -2:
					fehlermeldungLabel.setText("EAN ungültig");
					break;
				case -3:
					fehlermeldungLabel.setText("Stückpreis ungültig");
					break;
				case -4:
					fehlermeldungLabel.setText("Stückzahl ungültig");
					break;
				case -5:
					fehlermeldungLabel.setText("Grundpreis ungültig");
					break;
				case -6:
					fehlermeldungLabel.setText("Grundpreiseinheit ungültig");
					break;
				case -7:
					fehlermeldungLabel.setText("Menge ungültig");
					break;
				case -8:
					fehlermeldungLabel.setText("Mengeneinheit ungültig");
					break;
				case -9:
					fehlermeldungLabel.setText("Kategorie ungültig");
					break;
				case -10:
					String oldEan = (String) bestandsListeModel.getValueAt(selectedRow, 1);
					lager.delete(oldEan);

					lager.ArtikelHinzufuegen(name, ean, articlePrice, articleQuantity, basePrice, basePriceUnit,
							articleAmount, articleAmountUnit, category);
					DatenSchreiber DatenSchreiber = new DatenSchreiber(lager);
					DatenSchreiber.Schreiben();

					String[][] copy = new String[dataBestand.length - 1][];
					for (int i = 0, j = 0; i < dataBestand.length; i++) {
						if (i != selectedRow) {
							copy[j++] = dataBestand[i];
						}
					}
					dataBestand = Arrays.copyOf(copy, copy.length);

					bestandsListeModel.removeRow(selectedRow);

					dataBestand = Arrays.copyOf(dataBestand, dataBestand.length + 1);
					dataBestand[dataBestand.length - 1] = completeArticle;

					bestandsListeModel.addRow(completeArticle);

					clearForm();
					formularPanel.setVisible(false);
					removeButton.setEnabled(true);
					fehlermeldungLabel.setText("");
				}

			}

		});

		formularPanel.setVisible(false);
		formularErzeugen();

		interaktionenErzeugen();

		bestandsListeErzeugen();

	}

	/**
	 * getData führt die Funktionen getCategories und getDataBestand aus
	 */
	public static void getData() {
		getCategories();
		getDataBestand();
	}

	/**
	 * Die Funktion füllt die variable categories mit den Kategorien aus der
	 * Datenbank
	 */
	public static void getCategories() {
		// import
		String[] categoriesFromDB = lager.getKategorien().toStringArray();
		categories = Arrays.copyOf(categoriesFromDB, categoriesFromDB.length);
	}

	/**
	 * Die Funktion füllt die Variable dataBestand mit Artikeln aus der Datenbank
	 */
	public static void getDataBestand() {
		// import Array from Database
		String[][] dataFromDB = lager.toStringArray();
		dataBestand = Arrays.copyOf(dataFromDB, dataFromDB.length);
	}

	/**
	 * Die suche zeigt die, in die Suchzeile eingegebenen, Artikel an Es wird nach
	 * Artikelnamen und EAN gefiltert
	 */
	private void suche() {
		String text = suchInput.getText();
		if (text.length() == 0) {
			tr.setRowFilter(null);
		} else {
			tr.setRowFilter(RowFilter.regexFilter(text, 0, 1));
		}
	}

	/**
	 * clearForm() leert alle Textfelder und macht sie bearbeitbar
	 */
	private void clearForm() {
		articleNameInput.setEnabled(true);
		eanInput.setEnabled(true);
		articlePriceInput.setEnabled(true);
		articleQuantityInput.setEnabled(true);
		basePriceInput.setEnabled(true);
		basePriceUnitBox.setEnabled(true);
		articleAmountInput.setEnabled(true);
		categoryBox.setEnabled(true);
		articleNameInput.setText("");
		eanInput.setText("");
		articlePriceInput.setText("");
		articleQuantityInput.setText("");
		basePriceInput.setText("");
		basePriceUnitBox.setSelectedItem(0);
		articleAmountInput.setText("");
		categoryBox.setSelectedItem(0);
	}

	/**
	 * hier werden alle Funktionen die mit dem Erstellen der Formular GUI zu tun
	 * haben gesammelt
	 */
	private void formularErzeugen() {
		this.getContentPane().add(formularPanel, BorderLayout.WEST);
		formularPanel.setPreferredSize(new Dimension((int) screenWidth * 2 / 10, (int) screenHeight - 200));
		formularPanel.setLayout(new GridLayout(15, 2, 5, 5));
		formularPanel.setBorder(BorderFactory.createTitledBorder("Formular"));
		formularPanel.add(articleNameLabel);
		formularPanel.add(articleNameInput);
		formularPanel.add(eanLabel);
		formularPanel.add(eanInput);
		formularPanel.add(articlePriceLabel);
		formularPanel.add(articlePriceInput);
		formularPanel.add(articleQuantityLabel);
		formularPanel.add(articleQuantityInput);
		formularPanel.add(basePriceLabel);
		formularPanel.add(basePriceInput);
		formularPanel.add(basePriceUnitLabel);
		formularPanel.add(basePriceUnitBox);
		formularPanel.add(articleAmountLabel);
		formularPanel.add(articleAmountInput);
		formularPanel.add(categoryLabel);
		formularPanel.add(categoryBox);
		formularPanel.add(cancelButton);
		formularPanel.add(submitButton);
		formularPanel.add(clearButton);
		formularPanel.add(submitModificationsButton);
		submitModificationsButton.setVisible(false);
		formularPanel.add(fehlermeldungLabel);
	}

	/**
	 * hier werden alle Funktionen die mit dem Erstellen der Interaktionen GUI zu
	 * tun haben gesammelt
	 */
	private void interaktionenErzeugen() {
		this.getContentPane().add(interactionsPanel, BorderLayout.CENTER);
		interactionsPanel.setLayout(new GridLayout(15, 1, 5, 5));
		interactionsPanel.setBorder(BorderFactory.createTitledBorder("Interaktionen"));
		interactionsPanel.add(switchButton);
		interactionsPanel.add(artikelLabel);
		interactionsPanel.add(addButton);
		interactionsPanel.add(modifyButton);
		interactionsPanel.add(removeButton);
		interactionsPanel.add(createCategoryLabel);
		interactionsPanel.add(createCategoryInput);
		interactionsPanel.add(createCategoryButton);
		interactionsPanel.add(deleteCategoryButton);
		interactionsPanel.add(suchLabel);
		interactionsPanel.add(suchInput);
		interactionsPanel.add(suchButton);
	}

	/**
	 * hier werden alle Funktionen die mit dem Erstellen der Bestandslisten GUI zu
	 * tun haben gesammelt
	 */
	private void bestandsListeErzeugen() {
		// Bestandslisten GUI
		bestandsListePanel = new JPanel();

		this.getContentPane().add(bestandsListePanel, BorderLayout.EAST);
		bestandsListePanel.setBorder(BorderFactory.createTitledBorder("Bestandsliste"));
		bestandsListeModel = new DefaultTableModel(dataBestand, columnNames); // neue Objekte erzeugen
		bestandsListe = new JTable(bestandsListeModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		bestandsListe.getTableHeader().setEnabled(false);
		bestandsListe
				.setPreferredScrollableViewportSize(new Dimension((int) screenWidth * 2 / 3, (int) screenHeight - 200));
		bestandsListe.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(bestandsListe);
		bestandsListePanel.add(scrollPane);
		tr = new TableRowSorter<DefaultTableModel>(bestandsListeModel);
		bestandsListe.setRowSorter(tr);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	/**
	 * Hier wird eingegrenzt welche Textfelder nach Eingabe in bestimmte andere
	 * Textfelder deaktiviert werden und mit n aufgefüllt werden Es ist ein extra
	 * Feature um Zeit bei der Eingabe zu sparen und um dafür zu sorgen, dass die
	 * richtigen Felder benutzt werden
	 */
	private void constraintForm() {
		articlePriceInput.getDocument().addDocumentListener(new DocumentListener() {

			private void handleChange() {
				String priceValue = articlePriceInput.getText();
				String baseUnitValue = String.valueOf(basePriceUnitBox.getSelectedItem());
				if (priceValue.equals("n")) {
					articleQuantityInput.setEnabled(false);
					articleQuantityInput.setText("n");
					if (!baseUnitValue.equals("n")) {
						articleAmountInput.setEnabled(true);
					}
				} else if (priceValue.equals("")) {
					articleQuantityInput.setEnabled(true);
					if (!baseUnitValue.equals("n")) {
						articleAmountInput.setEnabled(true);
					}
				} else {
					articleQuantityInput.setEnabled(true);
					articleAmountInput.setEnabled(false);
					articleAmountInput.setText("n");
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				handleChange();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				handleChange();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		basePriceInput.getDocument().addDocumentListener(new DocumentListener() {

			private void handleChange() {
				String baseValue = basePriceInput.getText();
				if (baseValue.equals("n")) {
					basePriceUnitBox.setEnabled(false);
					basePriceUnitBox.setSelectedItem("n");
				} else {
					basePriceUnitBox.setEnabled(true);
					basePriceUnitBox.setSelectedItem(mengenEinheiten[0]);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				handleChange();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				handleChange();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		basePriceUnitBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					String quantity = articleQuantityInput.getText();
					String basePriceUnitValue = String.valueOf(basePriceUnitBox.getSelectedItem());
					if (basePriceUnitValue == "n") {
						articleAmountInput.setEnabled(false);
						articleAmountInput.setText("n");
						articleAmountLabel.setText("keine Mengenangabe");
					} else if (!quantity.equals("n") & !quantity.equals("")) {
						articleAmountInput.setEnabled(false);
						articleAmountInput.setText("n");
						articleAmountLabel.setText("keine Mengenangabe");
					} else {
						articleAmountInput.setEnabled(true);
						articleAmountLabel.setText("Menge in " + basePriceUnitValue);
					}
				} catch (Exception ex) {
				}
			}

		});
	}

}
