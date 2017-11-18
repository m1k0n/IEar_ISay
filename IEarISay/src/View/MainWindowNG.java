package View;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.ApplicationConstants;
import utils.DominosUtils;
import Model.BasicCSVHandler;
import Model.CSVHandler;
import Model.DefaultCSVHandler;
import Model.DefaultGenerateGridBehavior;
import Model.Domino;
import Model.GenerateGridBehavior;

/**
 * 
 * {@link JFrame}
 *
 */
public class MainWindowNG extends JFrame
{
	private int nbLines = 5;

	private int nbCol = 6; // MUST BE EVEN (nbRows/2 = 0)

	private int nbButtons = this.nbLines * this.nbCol;

	private ArrayList<String> allWord = new ArrayList<String>();

	private String defaultText = " "; // Default text on all the buttons

	public JPanel grid = new JPanel(new GridLayout(this.nbLines, this.nbCol));

	private GenerateGridBehavior generateGridBehavior;

	private CSVHandler csvHandler;

	public MainWindowNG(String titre)
	{
		super(titre);

		configure();
		
		setContentPane(createInsidePanel());
		Dimension dimensionMinimale = new Dimension(900, 250);
		this.setMinimumSize(dimensionMinimale);
		pack();

	}

	/**
	 * This method creates the interface
	 * 
	 * @return JPanel
	 */
	private JPanel createInsidePanel()
	{
		JPanel boutons = new JPanel(new GridLayout(6, 1));
		JPanel result = new JPanel(new BorderLayout());

		// Creation of the grid
		Border BorderTitledGrid = BorderFactory.createTitledBorder("Grid");
		Border BorderTitledGrid2 = BorderFactory.createTitledBorder("Your choice");

		this.allWord = new ArrayList<String>(); // Reset the ArrayList to have empty buttons
		fillGrid(allWord);
		grid.setBorder(BorderTitledGrid);
		// End of the creation of the grid

		// Creation of the buttons that manage functionalities
		JButton butCreer = new JButton("Create");
		JButton butShuffle = new JButton("Shuffle");
		JButton butImport = new JButton("Import");
		JButton butExport = new JButton("Export");
		JButton butAbout = new JButton("About");

		butCreer.setPreferredSize(new Dimension(170, 120));

		// Adding the Listeners
		butCreer.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				actionBtnCreate();

			}
		});

		butShuffle.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				/*
				 * Fill the ArrayList with empty Strings to match the number of pair in the grid Used to shuffle properly
				 */
				// FAUX ?
				// while (allWord.size() * 2 + 2 < nbButtons)
				// {
				// allWord.add("");
				// }

				shuffleWords();
				fillGridAfterShuffle(DominosUtils.shuffleDominos(allWord, nbCol));
			}
		});

		butImport.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				actionBtnImport();

			}
		});

		butExport.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				actionBtnExport();

			}
		});

		butAbout.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Desktop.getDesktop().browse(new URI("www.lequipe.fr"));
				}
				catch (URISyntaxException | IOException e)
				{
					JOptionPane.showMessageDialog(MainWindowNG.this, e.getMessage());
				}
			}
		});

		boutons.add(butCreer);
		boutons.add(butShuffle);
		boutons.add(butImport);
		boutons.add(butExport);
		boutons.add(butAbout);

		boutons.setBorder(BorderTitledGrid2);

		result.add(grid, BorderLayout.CENTER);
		result.add(boutons, BorderLayout.EAST);

		return result;
	}

	/**
	 * This method manages the create button
	 */
	public void actionBtnCreate()
	{

		Object[] options = { "Lines and Columns",
			"Enter the text directly" };

		int optionSelected = JOptionPane.showOptionDialog(this,
				"Choose a creation mode :",
				"Creation mode",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, // do not use a custom Icon
				options, // the titles of buttons
				options[0]); // default button title

		if (optionSelected == 0)
		{ // If Lines and Columns
			Object[] possibilities = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // Number of lines you can select, capped at 10
																							// voluntarily
			int newNbLines = (int)JOptionPane.showInputDialog(this,
					"Number of lines :",
					"Create Grid Input",
					JOptionPane.PLAIN_MESSAGE,
					null, possibilities,
					1);

			if (newNbLines > 0)
			{ // If valid amount of line
				Object[] possibilities2 = { 4, 6, 8, 10 }; // Number of columns you can select, capped at 10 voluntarily,
																			// must be even(nbCol/2 = 0)
				this.nbLines = newNbLines;
				int newNbCol = (int)JOptionPane.showInputDialog(this,
						"Number of Columns :",
						"Create Grid Input",
						JOptionPane.PLAIN_MESSAGE,
						null, possibilities2,
						4);

				if (newNbCol > 0)
				{ // if valid amount of columns

					this.nbCol = newNbCol;
					this.nbButtons = this.nbLines * this.nbCol;
					this.grid.removeAll();
					grid.setLayout(new GridLayout(this.nbLines, this.nbCol));

					this.allWord = new ArrayList<String>(); // Reset the ArrayList to have empty buttons
					fillGrid(this.allWord);

				}

				return;
			}
		}
		else if (optionSelected == 1)
		{ // If Enter the text directly
			String userText = (String)JOptionPane.showInputDialog(this,
					"Enter the texts separated by a ';'.\n"
							+ "In order to keep a clean display,\nit is recommanded to use a minimum of 7 words :\n"
							+ "\n",
					"Enter your text",
					JOptionPane.PLAIN_MESSAGE,
					null,
					null,
					0);

			// int nbWord = userText.length() - userText.replace(";", "").length(); //number of word to put in the grid (=
			// number of ';' in user's input)

			// The following lines will splits the user input and add each word(separated by ';') to the ArrayList<String>
			// alWord
			String[] tempTabString = userText.split(";");
			int nbWord = tempTabString.length;
			this.allWord = new ArrayList<String>();
			for (String s : tempTabString)
			{
				this.allWord.add(s);
			}
			Collections.shuffle(this.allWord);

			this.nbButtons = (nbWord * 2) + 2; // number of squares required in the grid

			Object[] possibilities = { 2, 4, 6, 8, 10 }; // Number of lines you can select, capped at 10 voluntarily
			int newNbCol = (int)JOptionPane.showInputDialog(this,
					"You have entered " + nbWord + " words. How many columns do you want in the final grid ? :",
					"Create Grid Input",
					JOptionPane.PLAIN_MESSAGE,
					null, possibilities,
					1);

			this.generateGridBehavior = new DefaultGenerateGridBehavior(newNbCol);

			this.generateGrid();

		}
	}

	private void createGrid()
	{
		
	}
	
	// Fill the panel 'grid' with buttons using this.nbButtons
	public void fillGrid(final ArrayList<String> alWord)
	{
		grid.removeAll();
		if (this.nbButtons > 0)
		{
			JButton buttonStart = new JButton(ApplicationConstants.START_LABEL);
			JButton buttonEnd = new JButton(ApplicationConstants.END_LABEL);

			buttonStart.setEnabled(false);
			buttonEnd.setEnabled(false);

			grid.add(buttonStart);

			String buttonText = this.defaultText;// variable containing next pair of button's content

			for (int i = 0; i < this.nbButtons - 2; i = i + 2)
			{

				if (i / 2 < this.allWord.size() && this.allWord.size() != 0)
				{
					buttonText = this.allWord.get(i / 2);
				}
				else
				{
					buttonText = this.defaultText;
				}

				final HearSayCombo temp = new HearSayCombo(buttonText);
				grid.add(temp.getISayButton());
				grid.add(temp.getIHearButton());

				temp.getIHearButton().setFont(new Font("times new roman", Font.PLAIN, 14));
				temp.getISayButton().setFont(new Font("times new roman", Font.PLAIN, 14));

				if (i == 0 && temp.getISayButton().getText().equals(" "))
				{
					temp.getISayButton().setText("Click to edit");
				}

				temp.getISayButton().addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e)
					{ // Allows user to modify ISayButton's content by clicking on them
						String newText = JOptionPane.showInputDialog("Enter the new text :");
						if (newText != null)
						{ // if user correctly entered a new String
							temp.setText(newText);
							alWord.add(newText);
						}

					}
				});

			}
			grid.add(buttonEnd);
			grid.validate();
			grid.repaint();
		}
	}

	// Fill the panel 'grid' with buttons using this.nbButtons
	/**
	 * 
	 */
	public void fillGridAfterShuffle(List<Domino> dominos)
	{
		grid.removeAll();
		if (this.nbButtons > 0)
		{
			String buttonText = this.defaultText;// variable containing next pair of button's content

			for (Domino domino : dominos)
			{
				HearSayCombo combo = new HearSayCombo();
				combo.getIHearButton().setText(domino.getFirstWord());
				combo.getISayButton().setText(domino.getSecondWord());

				grid.add(combo.getIHearButton());
				grid.add(combo.getISayButton());
			}

			grid.validate();
			grid.repaint();
		}
	}

	/*
	 * The following method will update the grid with the selected GenerateGridBehavior. The result will wary depending
	 * on the number of words entered by the user and the behavior selected. Note that those behaviors follow a Strategy
	 * pattern.
	 * 
	 * /!\ In order to use those classes, if is extremely important to use the generateNbButtons(nbButtons) first, then
	 * use the other methods
	 */
	public void generateGrid()
	{
		this.nbButtons = this.generateGridBehavior.generateNbButtons(this.nbButtons);
		this.nbLines = this.generateGridBehavior.generateNbLines(this.nbButtons);
		this.nbCol = this.generateGridBehavior.generateNbColumns();
		grid.setLayout(new GridLayout(this.nbLines, this.nbCol));
		fillGrid(allWord);
	}

	/**
	 * Shuffle the words
	 */
	public void shuffleWords()
	{
		Collections.shuffle(allWord);
	}

	/**
	 * This method manages the export button
	 */
	public void actionBtnExport()
	{
		this.csvHandler = new DefaultCSVHandler(this.nbCol);

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
		chooser.setFileFilter(filter);

		File destinationFile = null;

		// This value store whether or not user saved properly
		int didUserSaved = chooser.showSaveDialog(this);

		// if user saved correctly
		if (didUserSaved == JFileChooser.APPROVE_OPTION)
		{
			// if user's selected file is not a csv file, "force" csv extension
			if (!chooser.getSelectedFile().getName().contains(".csv"))
			{
				destinationFile = new File(chooser.getSelectedFile().getPath().concat(".csv"));
			}
			else
			{
				destinationFile = new File(chooser.getSelectedFile().getPath());
			}
			// if the grid was successfully exported, notify the user
			if (this.csvHandler.exportGridToCsv(destinationFile, this.allWord))
			{
				JOptionPane.showMessageDialog(this,
						"The grid has been successfully exported",
						"Export successful",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		this.csvHandler.updateNbCol();
		this.csvHandler.exportGridToCsv(destinationFile, this.allWord);

	}

	/**
	 * This method manages the import button Opens a JFileChooser window to choose which file of companies to import
	 */
	public void actionBtnImport()
	{

		File file = null;
		this.csvHandler = new BasicCSVHandler();
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			file = chooser.getSelectedFile();
			this.allWord = this.csvHandler.importCsv(file);
			this.nbCol = this.csvHandler.getNbColumnsRead();
			this.nbButtons = (this.allWord.size() * 2) + 2;
		}
		this.generateGridBehavior = new DefaultGenerateGridBehavior(this.nbCol);
		this.generateGrid();
	}

	private void configure()
	{
		// Those 4 lines manage the language of the applications
		Locale newLocale = new Locale("en");
		Locale.setDefault(newLocale);
		UIManager.getDefaults().setDefaultLocale(newLocale);
		JComponent.setDefaultLocale(newLocale);
		try
		{
			this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/flex.png")));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		// Graphical interface
		try
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				if (JOptionPane.showConfirmDialog(null, "Quit ?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		});

		
	}
}
