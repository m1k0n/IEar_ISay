package Controler;

import javax.swing.JFrame;

import View.MainWindowNG;

/**
 * This method launches the application
 *
 */
public class Main
{

	public static void main(String[] args)
	{
		JFrame myMainWindow = new MainWindowNG("I Ear, I Say");
		myMainWindow.setLocationRelativeTo(null);
		myMainWindow.setVisible(true);
	}
}
