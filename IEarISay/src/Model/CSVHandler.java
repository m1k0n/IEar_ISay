package Model;

import java.io.File;
import java.util.ArrayList;

public interface CSVHandler {	
	
	public boolean exportGridToCsv(File file, ArrayList<String> alString);
	
	public void updateNbCol();
	
	public ArrayList<String> importCsv(File file);
	
	public int getNbColumns(File file);
	
	public int getNbColumnsRead();
}
