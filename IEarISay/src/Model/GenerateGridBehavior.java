package Model;


/**
 * This interface and it's various implementations follow a Strategy pattern. If you are not aware of what this design pattern is, you may want to consult the documentation. This interface will try to generate various type of grid depending on the number of buttons, adding some empty buttons to match the desired amount of columns if necessary. /!\In order to use those classes, if is extremely important to use the generateNbButtons(nbButtons) first, then use the other methods.
 *
 */

public interface GenerateGridBehavior {
	
	public int generateNbButtons(int nbButtons);
	
	public int generateNbLines(int nbButtons);
	
	public int generateNbColumns();
	
	public void toPrint();
}
