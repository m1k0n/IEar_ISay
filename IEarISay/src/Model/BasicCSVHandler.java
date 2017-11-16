package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * This class is just the basic structure of the csvHandler and ultimately not desired in the final program. Consider it as a sketch rather than a functioning class.
 * Several variation will be needed with specified numbers of columns to import/export (cf : gridGenerator).
 * 
 */

public class BasicCSVHandler implements CSVHandler{
	
	//This variable represent the number of columns desired in the final csv file.
	//It will therefore vary on the several implementations of this class to match the grid created by the user
	private int nbColumns = 2;
	
	//This variable represent the number of columns in the csv being read.
	private int nbColumnsRead;
	
	private OutputStreamWriter myFileWriter = null;
	private BufferedWriter myBufferedWriter = null;

	/**
	 *	Export method for the csv
	 */
	@Override
	public boolean exportGridToCsv(File file, ArrayList<String> alString) {
		//Return value used to verify if the export worked properly
		boolean exportSuccesful = true;
		
		
		//Copy of alString, but with each word doubled
		ArrayList<String> alStringDoubled = new ArrayList<String>();
		int iteratorAlStringDoubled = 0;
		for (int i=0; i<alString.size(); i++){
			alStringDoubled.add(alString.get(i));
			alStringDoubled.add(alString.get(i));
		}
		
		try{
			this.myFileWriter = new FileWriter(file.getAbsolutePath());
			this.myFileWriter = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), StandardCharsets.UTF_8);
			this.myBufferedWriter = new BufferedWriter(this.myFileWriter);
			
			//Initialization : will write "I hear I say" this.nbColumns time
			for(int i=0; i<this.nbColumns; i+=2){
				this.myBufferedWriter.append("I hear;I say;");
			}
			this.myBufferedWriter.newLine();
			
			//Number of word left to write on the csv
			int remainingNbWordToWrite = alStringDoubled.size();
			
			//Will copy alString in the csv to a this.nbColumns rows grid
			for(int i=0; i<alStringDoubled.size()+2; i+=this.nbColumns){	
				
				//Number of word left to write on the selected line
				int remainingNbWordOnLine = this.nbColumns;
				
				//String containing the text to be written : each cell's content of line, separated by ";"
				String nextLineToWrite = "";
					
				//if at the beginning of the list, add "Start"
				if(i==0){
					nextLineToWrite = "Start;";
					remainingNbWordOnLine--;
				}
				
				//if at the end of the list, add "End"
				else if (remainingNbWordToWrite==0 && remainingNbWordOnLine==1) {
					nextLineToWrite += "End";
					remainingNbWordOnLine--;
					
				}
				
				while(remainingNbWordOnLine>0){
					//if at the end of the list, add "End"
					if (remainingNbWordToWrite==0 && remainingNbWordOnLine==1) {
						nextLineToWrite += "End";
						remainingNbWordOnLine--;
						
					}
					//If there is still some word to write, write them
					if(remainingNbWordToWrite>0){
						nextLineToWrite += alStringDoubled.get(iteratorAlStringDoubled) + ";";
						iteratorAlStringDoubled++;
						remainingNbWordToWrite--;
					}
					//else add blank text (the empty space seems to be required)
					else{
						nextLineToWrite += " ;";
					}
					remainingNbWordOnLine--;
				}
				
				//Write nextLineToWrite in the csv, and go to the next line
				this.myBufferedWriter.append(nextLineToWrite);
				this.myBufferedWriter.newLine();
			}
			
		}
		catch(IOException exception){
			exportSuccesful = false;
			exception.printStackTrace();
		}
		finally {
			try {
				this.myBufferedWriter.flush();
				this.myBufferedWriter.close();
				this.myFileWriter.close();
			} catch (IOException e) {
				exportSuccesful = false;
				e.printStackTrace();
			}
			
		}
		return exportSuccesful;
	}
	
	public int getNbColumns() {
		return this.nbColumns;
	}
	
	/**
	 * Set the number of columns
	 * @param newNbCol
	 */
	public void setNbColumns(int newNbCol){
		this.nbColumns = newNbCol;
	}

	//This method should not be used in this class !
	@Override
	public void updateNbCol() {
		return;
	}

	/**
	 *	Importation method for the csv
	 */
	@Override
	public ArrayList<String> importCsv(File file) {
			FileReader monFichier = null;
			BufferedReader tampon = null;
			ArrayList<String> aLImport = new ArrayList<String>();
			String[] tabString ;
			int sizeLine;
		
			try {
				// file path
				monFichier = new FileReader(file.getAbsolutePath());
				tampon = new BufferedReader(monFichier);

				// read the first line of the file .csv
				String ligneTemp = tampon.readLine();
				tabString = ligneTemp.split(";");
				this.setNbColumnsRead(tabString.length);
				
				//Start of the actual content (first line only acting as a header)
				ligneTemp = tampon.readLine();
				
				
						// faire la gestion des erreurs 
						/*JOptionPane.showMessageDialog(this, "Le fichier .csv n'est pas valide", "Erreur !",
									JOptionPane.ERROR_MESSAGE);*/
				
				// read all the lines one by one and split them to keep the
				// structure's name
				while (ligneTemp != null) {					
					tabString = ligneTemp.split(";");
					
					sizeLine = tabString.length;
					//This for start at 1 in order to skip the first cell, assuming it is always "Start" and is therefore not required in the ArrayList
					for (int i = 1; i < sizeLine; i+=2) {
						
						if(!tabString[i].equals("End ")){
							aLImport.add(tabString[i]);
						}
						
					}
					ligneTemp = tampon.readLine();
				}

			} catch (IOException exception) {
				exception.printStackTrace();
				return null;
			} finally {
				try {
				if (tampon != null)
				{
					tampon.close();
				}
				if (monFichier != null)
					{
						monFichier.close();
					}
				} catch (IOException exception1) {
					exception1.printStackTrace();
					return null;
				}
			}
		return aLImport;	
	}

	@Override
	public int getNbColumns(File file) {
		return this.nbColumns;
	}
	
	public int getNbColumnsRead(){
		return this.nbColumnsRead;
	}
	
	/**
	 * Set the number of columns to read
	 * @param nbCol
	 */
	public void setNbColumnsRead(int nbCol){
		this.nbColumnsRead = nbCol; 
	}
}
