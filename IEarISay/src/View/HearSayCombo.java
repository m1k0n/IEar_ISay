package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * This class allows the creation of a pair of button I Hear/I Say
 * The primary function of this class is allowing the programmer to
 * modify the text of both button without having to call them each. 
 */
public class HearSayCombo {
	private Dimension taille 	  = new Dimension(90,90);
	private JButton IHearButton;
	private JButton ISayButton;
	private Border 	BorderButtons = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
	private Color 	colorIHear 	  = new Color(204, 229, 255); //Light blue
	private Color 	colorISay     = new Color(255, 229, 204); //Light beige
	
	
	
	
	/**
	 * Should be used over default constructor, create empty buttons and add them to panelToAddButtons
	 * @param panelToAddButtons
	 */
	public HearSayCombo(){
		this.IHearButton = new JButton("");
		this.ISayButton  = new JButton("");
		this.ISayButton.setPreferredSize(taille);
		this.IHearButton.setPreferredSize(taille);
		this.standardEdit();
	}
	

	/**
	 * Standard constructor, create buttons with text textButtons, and add them to panelToAddButtons
	 * @param textButtons
	 * @param panelToAddButtons
	 */
	public HearSayCombo(String textButtons){
		this.IHearButton = new JButton(textButtons);
		this.ISayButton  = new JButton(textButtons);
		this.ISayButton.setPreferredSize(taille);
		this.IHearButton.setPreferredSize(taille);
		this.standardEdit();
	}

	/**
	 * Edit the text of both buttons
	 * @param textButtons
	 */
	public void setText(String textButtons){
		this.IHearButton.setText(textButtons);
		this.ISayButton.setText(textButtons);
	}
	
	
	/**
	 * Set all buttons to default settings for this app and add them to panelToAddButtons
	 * @param panelToAddButtons
	 */
	public void standardEdit(){
		
		this.IHearButton.setBackground(colorIHear); 
		this.ISayButton.setBackground(colorISay);	
		
		this.IHearButton.setEnabled(false);
		
		//this.IHearButton.setBorder(BorderButtons);
		//this.ISayButton.setBorder(BorderButtons);
		
//		panelToAddButtons.add(this.ISayButton);
//		panelToAddButtons.add(this.IHearButton);
	}
	

	/**
	 * Set new colors for the buttons
	 * @param colorIHear
	 * @param colorISay
	 */
	public void setColors(Color colorIHear, Color colorISay){
		this.colorIHear = colorIHear;
		this.colorISay  = colorISay;
	}
	

	/**	
	 * Set a new border for both buttons
	 * @param newBorder
	 */
	public void setBorder(Border newBorder){
		this.BorderButtons = newBorder;
	}
	
	public JButton getISayButton(){
		return this.ISayButton;
	}
	
	public JButton getIHearButton(){
		return this.IHearButton;
	}
	
	
}
