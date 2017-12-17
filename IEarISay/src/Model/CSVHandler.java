package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface CSVHandler {	
	
	public boolean exportGridToCsv(File file, List<Domino> domnios);
	
	public void updateNbCol();
	
	public ArrayList<String> importCsv(File file);
	
	public int getNbColumns(File file);
	
	public int getNbColumnsRead();
}
