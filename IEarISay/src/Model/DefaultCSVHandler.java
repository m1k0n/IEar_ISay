package Model;

import java.io.File;
import java.util.ArrayList;

public class DefaultCSVHandler extends BasicCSVHandler
{
	public DefaultCSVHandler(int newNbCol)
	{
		this.newNbCol = newNbCol;
	}
	
	// This variable will override the number of columns used in exportGridToCSV
	private int newNbCol;

	public void updateNbCol()
	{
		super.setNbColumns(newNbCol);
	}

	@Override
	public ArrayList<String> importCsv(File file)
	{
		return super.importCsv(file);
	}

}
